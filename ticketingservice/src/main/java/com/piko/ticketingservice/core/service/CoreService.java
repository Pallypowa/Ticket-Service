package com.piko.ticketingservice.core.service;

import com.piko.ticketingservice.api.dto.*;
import com.piko.ticketingservice.api.exception.ErrorCodes;
import com.piko.ticketingservice.core.exception.BadCardException;
import com.piko.ticketingservice.core.exception.BadTokenException;
import com.piko.ticketingservice.core.exception.InsufficientFundsException;
import com.piko.ticketingservice.core.model.UserBankCard;
import com.piko.ticketingservice.core.model.UserDevice;
import com.piko.ticketingservice.core.model.Users;
import com.piko.ticketingservice.core.repository.RepositoryManager;
import com.piko.ticketingservice.ticket.exception.SeatDoesNotExistException;
import com.piko.ticketingservice.ticket.service.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

/*
    This class is responsible for handling user-related business logic, like checking the token for a user, checking user fund etc...
 */

@Service
public class CoreService {
    private final RepositoryManager repositoryManager;
    private final HttpServletRequest request;

    private final TicketService ticketService;

    private final Logger logger = LoggerFactory.getLogger(CoreService.class);

    public CoreService(RepositoryManager repositoryManager, HttpServletRequest request, TicketService ticketService) {
        this.repositoryManager = repositoryManager;
        this.request = request;
        this.ticketService = ticketService;
    }

    public boolean validateToken(String token) {
        logger.trace("User token validation started with token " + token);

        //Step 1 - Decode BASE64 token
        String decodedToken = new String(Base64.getDecoder().decode(token));
        String[] tokenValues = decodedToken.split("&");

        //Token has 3 parts
        if (tokenValues.length != 3) {
            throw new BadTokenException(ErrorCodes.BAD_USER_TOKEN, token);
        }

        //Step 2 - Get token values
        String email = tokenValues[0];
        Long userId = Long.valueOf(tokenValues[1]);
        String deviceHash = tokenValues[2];

        /*Step 3 - Validation.
        If any of the combinations don't match (email, userid), (userid, deviceHash), the token is invalid.
        */
        Users user = repositoryManager.getUser(userId);

        if (!user.getEmail().equals(email)) {
            throw new BadTokenException(ErrorCodes.BAD_USER_TOKEN, token);
        }

        List<UserDevice> userDevices = repositoryManager.getDeviceHashesByUser(userId);
        if (userDevices.stream().noneMatch((device) -> device.getDeviceHash().equals(deviceHash))) {
            throw new BadTokenException(ErrorCodes.BAD_USER_TOKEN, token);
        }

        logger.trace("User token " + token + " successfully validated");

        //Save the userId as internal attribute for further processing
        request.setAttribute("userId", userId);
        return true;
    }

    public PaymentResponseDTO processPayment(PaymentDTO paymentDTO) {
        logger.trace("Payment process started");
        //Step 1 - Check if card belongs to user
        //Get user id
        Long eventId = paymentDTO.getEventId();
        String seatId = paymentDTO.getSeatId();
        String cardId = paymentDTO.getCardId();
        Long userId = (Long) request.getAttribute("userId");

        if (!cardBelongsToUser(userId, paymentDTO.getCardId())) {
            throw new BadCardException(ErrorCodes.USER_CARD_ISSUE, paymentDTO.getCardId(), eventId);
        }
        //Step 2 - Check if it has enough money
        if (!hasSufficientFunds(cardId, eventId, seatId)) {
            throw new InsufficientFundsException();
        }

        logger.trace("Payment process successfully validated");

        //Step 3 - Succeed -> Call Ticket module to reserve, Failure -> return 10100 or 10101 error
        return ticketService.reserveSeat(new ReserveDTO(eventId, seatId));
    }

    private boolean cardBelongsToUser(Long userId, String cardId) {
        UserBankCard userBankCard = repositoryManager.getUserBankCardsForUser(userId);
        //We could also check if the Bank Card exists...
        return userBankCard.getCardId().equals(cardId);
    }

    //We need to know the eventId & seatId as well to determine the price
    private boolean hasSufficientFunds(String cardId, Long eventId, String seatId) {
        UserBankCard userBankCard = repositoryManager.getUserBankCardByCardId(cardId);
        Integer funds = userBankCard.getAmount();
        EventDetailsDataDTO eventDetails = ticketService.getEvent(eventId);
        Seat seat = findSeat(eventDetails.getData().getSeats(), seatId);
        //Here we could also handle currencies, conversions etc... :)
        return seat.getPrice() <= funds;
    }

    private Seat findSeat(List<Seat> seats, String seatId) {
        return seats.stream().filter(s -> s.getId().equals(seatId)).findFirst().orElseThrow(SeatDoesNotExistException::new);
    }

}
