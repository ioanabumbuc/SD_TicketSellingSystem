package sd.ticketsellingsystem.dto;

import java.util.ArrayList;
import java.util.List;

public class BandDto {
    private Long id;
    private String bandName;
    private String genre;
    private List<Long> concertIds = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setConcertIds(List<Long> concertIds) {
        this.concertIds = concertIds;
    }

    public void addConcertId(Long id){
        this.concertIds.add(id);
    }
}
