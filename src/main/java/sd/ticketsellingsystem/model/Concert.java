package sd.ticketsellingsystem.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int availableTickets;
    private String concertDate;
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name="concert_band",
            joinColumns = @JoinColumn(name="concert_id"),
            inverseJoinColumns = @JoinColumn(name="band_id"))
    private List<Band> bands;

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public String getConcertDate() {
        return concertDate;
    }

    public void setConcertDate(String date) {
        this.concertDate = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBands(List<Band> bands) {
        this.bands = bands;
    }

    public Long getId() {
        return id;
    }

    public List<Band> getBands() {
        return bands;
    }

    public void addBand(Band band){
        this.bands.add(band);
    }

    public void removeBand(Band band){
        this.bands = this.bands.stream().filter((b)->!b.equals(band)).collect(Collectors.toList());
    }

}
