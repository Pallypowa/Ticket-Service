package com.piko.ticketingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piko.ticketingservice.api.dto.PaymentDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ServiceIntegrationTest {


    @Autowired
    private MockMvc mockMvc;
    private final String USER_TOKEN = "User-Token";
    private String TOKEN = "dGVzenQuYWxhZGFyQG90cG1vYmlsLmNvbSYxMDAwJkY2N0MyQkNCRkNGQTMwRkNDQjM2RjcyRENBMjJBODE3"; //teszt.aladar@otpmobil.com&1000&F67C2BCBFCFA30FCCB36F72DCA22A817

    @Test
    void testEmptyUserToken() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/getEvents");
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("10050")));
    }
    @Test
    void testBadUserToken() throws Exception {
        this.TOKEN = "BadToken";
        RequestBuilder request = MockMvcRequestBuilders.get("/getEvents").header(USER_TOKEN, TOKEN);
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("10051")));
    }
    @Test
    void testGetEvents() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/getEvents").header(USER_TOKEN, TOKEN);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("eventId")));
    }

    @Test
    void testGetEvent() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/getEvent/1").header(USER_TOKEN, TOKEN);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"eventId\":1")));
    }

    @Test
    void testSuccessfulPayment() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentDTO paymentDTO = new PaymentDTO(1L, "S2", "C0001");
        String jsonContent = objectMapper.writeValueAsString(paymentDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/pay")
                .content(jsonContent).contentType("application/json")
                .header(USER_TOKEN, TOKEN);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("success\":true")));
    }

    @Test
    void testEventDoesNotExist() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentDTO paymentDTO = new PaymentDTO(4L, "S2", "C0001");
        String jsonContent = objectMapper.writeValueAsString(paymentDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/pay")
                .content(jsonContent).contentType("application/json")
                .header(USER_TOKEN, TOKEN);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("20001")));
    }

    @Test
    void testSeatDoesNotExist() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentDTO paymentDTO = new PaymentDTO(1L, "S999", "C0001");
        String jsonContent = objectMapper.writeValueAsString(paymentDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/pay")
                .content(jsonContent).contentType("application/json")
                .header(USER_TOKEN, TOKEN);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("20002")));
    }

    @Test
    void testSeatIsTaken() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentDTO paymentDTO = new PaymentDTO(1L, "S1", "C0001");
        String jsonContent = objectMapper.writeValueAsString(paymentDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/pay")
                .content(jsonContent).contentType("application/json")
                .header(USER_TOKEN, TOKEN);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("20010")));
    }

    @Test
    void testUserDoesNotHaveEnoughFunds() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentDTO paymentDTO = new PaymentDTO(3L, "S1", "C0001");
        String jsonContent = objectMapper.writeValueAsString(paymentDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/pay")
                .content(jsonContent).contentType("application/json")
                .header(USER_TOKEN, TOKEN);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("10101")));
    }

    @Test
    void testBankCardDoesNotBelongToUser() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentDTO paymentDTO = new PaymentDTO(3L, "S1", "C0002");
        String jsonContent = objectMapper.writeValueAsString(paymentDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/pay")
                .content(jsonContent).contentType("application/json")
                .header(USER_TOKEN, TOKEN);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("10100")));
    }

    @Test
    void testEventHasAlreadyStarted() throws Exception {
        this.TOKEN = "dGVzenQuY2VjaWxpYUBvdHBtb2JpbC5jb20mMzAwMCZFNjg1NjA4NzJCREIyREYyRkZFN0FEQzA5MTc1NTM3OA=="; //Becuase user 1000 does not have enough money...
        ObjectMapper objectMapper = new ObjectMapper();
        PaymentDTO paymentDTO = new PaymentDTO(3L, "S2", "C0003");
        String jsonContent = objectMapper.writeValueAsString(paymentDTO);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/pay")
                .content(jsonContent).contentType("application/json")
                .header(USER_TOKEN, TOKEN);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("20011")));
    }

    @Test
    void testPartnerServiceNotAvailable() throws Exception {
        //To run this test, stop partner service
        RequestBuilder request = MockMvcRequestBuilders.get("/getEvents").header(USER_TOKEN, TOKEN);
        mockMvc.perform(request)
                .andExpect(status().isServiceUnavailable())
                .andExpect(content().string(containsString("20404")));
    }
}
