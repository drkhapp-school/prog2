import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashMap;

public class Inventaire implements Serializable {
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

    public void modify(String nom, String description, String categorie, LocalDate date, String nbSerie, double prix) {
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
        this.date = date;
        this.nbSerie = nbSerie;
        this.prix = prix;
    }

    public LinkedHashMap<LocalDate, String> getEntretiens() {
        return entretiens;
    }

    public void addEntretien(LocalDate date, String detail) {
        entretiens.put(date, detail);
    }

    public void delEntretien(LocalDate date) {
        entretiens.remove(date);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(this.nom).append(", ")
                .append(this.nbSerie).append(", ")
                .append(this.categorie).append(", ")
                .append(this.prix).append(", ")
                .append(this.date).append(", ")
                .append(this.description).append("\n");

        entretiens.forEach((date, desc) -> sb.append(date).append(", ").append(desc).append("\n"));

        return sb.toString();
    }
}
