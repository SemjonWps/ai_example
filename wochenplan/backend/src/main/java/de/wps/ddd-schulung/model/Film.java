import jakarta.persistence.*;

@Entity
@Table(name="filme")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titel;
    private Integer laufzeit;
    private String posterUrl;
    private Integer fsk;
    private String beschreibung;
    private String genre;
    private String hauptdarsteller;
    private String regie;
    private String sprache;

    public Film() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Integer getLaufzeit() {
        return laufzeit;
    }

    public void setLaufzeit(Integer laufzeit) {
        this.laufzeit = laufzeit;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Integer getFsk() {
        return fsk;
    }

    public void setFsk(Integer fsk) {
        this.fsk = fsk;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getHauptdarsteller() {
        return hauptdarsteller;
    }

    public void setHauptdarsteller(String hauptdarsteller) {
        this.hauptdarsteller = hauptdarsteller;
    }

    public String getRegie() {
        return regie;
    }

    public void setRegie(String regie) {
        this.regie = regie;
    }

    public String getSprache() {
        return sprache;
    }

    public void setSprache(String sprache) {
        this.sprache = sprache;
    }
}
