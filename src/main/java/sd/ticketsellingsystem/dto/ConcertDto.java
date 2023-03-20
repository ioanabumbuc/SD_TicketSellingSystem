package sd.ticketsellingsystem.dto;

import java.util.ArrayList;
import java.util.List;

public class ConcertDto {
    private Long id;
    private String title;
    private int availableTickets;
    private String concertDate;
    private List<Long> bandIds = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public String getConcertDate() {
        return concertDate;
    }

    public void setConcertDate(String concertDate) {
        this.concertDate = concertDate;
    }

    public List<Long> getBandIds() {
        return bandIds;
    }

    public void setBandIds(List<Long> bandIds) {
        this.bandIds = bandIds;
    }
}
