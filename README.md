# Ticket Service projekt

Ezt a readme-t szánom technikai dokumentációnak. A projektet Java nyelven csináltam, Spring Boot keretrendszerrel és IntelliJ fejlesztői környezetben. 

### Prerequisites

- Java 18 sdk-t használtam, de alacsonyabb verzióval is működik.
- Maven

### Használt technológiák

- Spring Boot Web
- Spring Security (A partnerhez)
- Jackson a file parseolására.
- RestTemplatet használtam a http kommunikációra. (Blokkolja a main threadet, de azt gondoltam, hogy ebben az esetben ez nem probléma.)
- H2 in-memory adatbázis a Core Servicehez
- Hibernate ORM
- Spring Data JPA az adatbázis kommunikációhoz.

### Projekt felépítése

A projekt 2 modulból épül fel, az egyik a **partner**, amelyhez basic authentikációval lehet csatlakozni és a megadott fileokból visszaadja az adatokat. Illetve a **ticketingservice** modul, amely magába foglalja az api, core és ticket modulokatt is.

### Végpontok, szerver válaszok

A feladatban megadott endpointokat implementáltam a kért responseokkal.

A Partner Service a **localhost:8081** porton érhető el, a Ticketing Service pedig **localhost:8080**-on.

#### Partner

| Metódus |       Végpont       |                   Paraméterek |                  Visszatérési érték |
|---------|:-------------------:|------------------------------:|------------------------------------:|
| GET     |     /getEvents      |                             - |          getEvents.json struktúrája |
| GET     | /getEvent/{eventId} | eventId: Long (path variable) | getEvent/{eventId}.json struktúrája |
| POST    |      /reserve       |   eventId: Long<br/>seatId: String |            reserve.json struktúrája |

Hibaüzenet esetén pedig minden esetben  

{<br/>
    "success" : false,<br/>
    "errorCode" : errorCode<br/>
}

formátumban mutatom.

A fileok objektumát egy IntelliJ pluginnal csináltam (Generate POJO from JSON). A partner authentikáció basic auth-al működik, amihez a credentialokat a partner/src/main/java/config/SecurityConfig.java filejába tettem. A credentialokat pedig a ticket service modulban az application.propertiesbe tettem és onnan olvasom.


#### Ticketing Service

| Metódus |       Végpont       |                                      Paraméterek |                   Visszatérési érték |
|---------|:-------------------:|-------------------------------------------------:|-------------------------------------:|
| GET     |     /getEvents      |                                                - |           eventek a partner modultól |
| GET     | /getEvent/{eventId} |                    eventId: Long (path variable) |     konkrét event a partner modultól |
| POST    |        /pay         | eventId: Long<br/>seatId: String, cardId: String | Siker esetén reservation id, success |


### Technikai információk

#### Hibakezelés
Alapvetően a hibakezelésre custom exceptionöket csináltam (BadTokenException, Insufficient exception), amiket a RestControllerExceptionHandler kap meg és ezzel adom vissza a megfelelő hibakódot.
#### Adatbázis
Az adatbázis H2 in-memory adatbázis, a táblákat legenerálom, majd a schema.sql segítségével feltöltöm.
#### User-Token validáció
Ha nincs User-Token, akkor a fent említett handlerben a MissingRequestHeaderException-t elkapom. Ha van, a User-Token-t a CoreService class kapja meg, ami megnézi, hogy jó-e a formátuma, létezik-e az e-mail/user/device hash és hogy a tokenben megadottak összetartoznak-e.
#### Flow
Alapvetően a getEvents és a getEvent a az api controllerből a Ticket modulhoz érkezik, amely lekéri a partnertől az adatokat (getEvent esetén megnézi, hogy az event létezik-e), hiszen ebben az esetben nincs szükség a Core user logikájára.

Fizetés esetén a CoreService ellenőrzi le, hogy a beérkezett kártya a userhez tartozik-e és hogy van-e elég pénze a jegyre. Ha az övé a kártya és van elég pénze, akkor továbbadja az adatokat a Ticket modulnak, ami megnézi, hogy létezik-e az event/szék és hogy elkezdődött-e már az esemény.
#### Logok
A log file a ticketingservice/src/main/resources/logs mappában található.

### További improvementek

- A ticketing service-hez JWT authentication
- Egyéb api végpontok
  - /cancelReservation - foglalás lemondása. 
  - /getAvailableSeats/{eventId} - egy event elérhető székeit visszaadni
  - /getEventsByLocation/{locationName} - egy eseményt visszaadunk a hely alapján
- CI/CD bevezetése

### Egyéb

A teszt adatok közül az első kettőnek átírtam a timestamp-jét jövőbeli dátumra, a harmadikét pedig úgy hagytam, hogy az esemény elkezdődött hibát is tesztelhessem.

A tesztek lefuttatásához a **partner servicenek futnia kell** (kivéve a testPartnerServiceNotAvailable() teszt).
