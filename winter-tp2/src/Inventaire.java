import java.time.LocalDate;
import java.util.LinkedHashMap;

public class Inventaire {
    private String nom;
    private String description;
    private String categorie;
    private LocalDate date;
    private int nbSerie;
    private double prix;
    private LinkedHashMap<LocalDate,String> entretiens;

    public Inventaire(String nom, int nbSerie, String categorie, double prix, LocalDate date, String description) {
        this.nom = nom;
        this.nbSerie = nbSerie;
        this.categorie = categorie;
        this.prix = prix;
        this.date = date;
        this.description = description;
    }

    public Inventaire(String nom, LocalDate date, double prix) {
        this.nom = nom;
        this.date = date;
        this.prix = prix;
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

    public int getNbSerie() {
        return nbSerie;
    }

    public double getPrix() {
        return prix;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setNbSerie(int nbSerie) {
        this.nbSerie = nbSerie;
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
