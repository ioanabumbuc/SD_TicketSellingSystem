package sd.ticketsellingsystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import sd.ticketsellingsystem.dto.TicketDto;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class Factory {

    public void exportTickets(List<TicketDto> tickets, Long concertId) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("tickets/concert-id-" + concertId + '-' + LocalDate.now() + ".json"), tickets);
    }
}
