package sd.ticketsellingsystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import sd.ticketsellingsystem.model.Cashier;
import sd.ticketsellingsystem.model.Concert;
import sd.ticketsellingsystem.model.Ticket;
import sd.ticketsellingsystem.resource.CashierResource;
import sd.ticketsellingsystem.service.CashierService;
import sd.ticketsellingsystem.service.ConcertService;

import java.util.Base64;
import java.util.List;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest
class TicketSellingSystemApplicationTests {

    @Autowired
    private CashierResource cashierResource;

    @Autowired
    private ConcertService concertService;


    @Test
    public void testPasswordEncryption() {
        String plainPassword = "myPassword123";
        String expectedEncryptedPassword =
                Base64.getEncoder().encodeToString(plainPassword.getBytes());

        Cashier cashier = new Cashier();
        cashier.setUsername("test");
        cashier.setPassword(plainPassword);

        cashier.prePersist();

        Assert.isTrue(expectedEncryptedPassword.equals(cashier.getPassword()),
                "Password not encrypted correctly");
    }


    @Test
    void testSellTickets() {
        Concert concert = new Concert();
        concert.setTitle("Test Concert");
        concert.setAvailableTickets(5);
        concert.setConcertDate("20/04/2023");
        concert = concertService.addConcert(concert);

        // Test selling tickets with correct data
        ResponseEntity<?> responseEntity = cashierResource.sellTickets(concert.getId(),
                3, new String[]{"John", "Mike", "Anna"});
        Assert.isTrue(responseEntity.getStatusCode() == HttpStatus.CREATED,
                "Ticket not sold correctly");

        // Test selling tickets with wrong number of buyers
        responseEntity = cashierResource.sellTickets(concert.getId(),
                3, new String[]{"John", "Mike", "Anna", "Mary"});
        Assert.isTrue(responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST,
                "Buyers names not verified correctly");


        // Test selling tickets for non-existent concert
        responseEntity = cashierResource.sellTickets(100L,
                3, new String[]{"John", "Mike", "Anna"});
        Assert.isTrue(responseEntity.getStatusCode() == HttpStatus.NOT_FOUND,
                "Concert not found case not handled correctly");

        // Test selling more tickets than available
        responseEntity = cashierResource.sellTickets(concert.getId(), 6,
                new String[]{"John", "Mike", "Anna", "Mary", "Lucy", "Tom"});
        Assert.isTrue(responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST,
                "Tickets exceeded case not handled correctly");

    }

}
