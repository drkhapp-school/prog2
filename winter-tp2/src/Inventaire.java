import java.time.LocalDate;
import java.util.LinkedHashMap;

public class Inventaire {
    private String nom;
    private String description;
    private String categorie;
    private String nbSerie;
    private LocalDate date;
    private double prix;
    private final LinkedHashMap<LocalDate, String> entretiens;

    public Inventaire(String nom, String description, String categorie, LocalDate date, String nbSerie, double prix) {
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
        this.date = date;
        this.nbSerie = nbSerie;
        this.prix = prix;
        this.entretiens = new LinkedHashMap<>();
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getCategorie() {
        return categorie;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getNbSerie() {
        return nbSerie;
    }

    public double getPrix() {
        return prix;
    }

    public Object[] toObject() {
        return new Object[]{
                nom, categorie, prix, date, description
        };
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setNbSerie(String nbSerie) {
        this.nbSerie = nbSerie;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public LinkedHashMap<LocalDate, String> getEntretiens() {
        return entretiens;
    }

    public void addEntretien(LocalDate date, String detail) {
        entretiens.put(date, detail);
    }

    public void delEntretien(LocalDate date, String detail) {
        entretiens.remove(date, detail);
    }
}
