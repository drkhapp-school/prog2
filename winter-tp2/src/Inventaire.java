/**
 * Objectif: Classe d'inventaire
 *
 * @author: Jean-Philippe Miguel-Gagnon - 1927230
 * @since: Session H2021
 */

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashMap;

public class Inventaire implements Serializable {
    private String nom; // Nom de l'inventaire
    private String description; // Description de l'inventaire
    private String categorie; // Catégorie de l'inventaire
    private String nbSerie; // Numéro de série
    private LocalDate date; // Date de l'achat
    private double prix; // Prix de l'inventaire
    private final LinkedHashMap<LocalDate, String> entretiens; // Liste des entretiens sur l'inventaire

    /**
     * Créer un nouvel inventaire avec un LinkedHasMap d'entretien vide
     * @param nom le nom de l'inventaire
     * @param description la description de l'inventaire
     * @param categorie la catégorie de l'inventaire
     * @param nbSerie le numéro de série
     * @param date la date de l'achat
     * @param prix le prix de l'inventaire
     */
    public Inventaire(String nom, String description, String categorie, String nbSerie, LocalDate date, double prix) {
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
        this.date = date;
        this.nbSerie = nbSerie;
        this.prix = prix;
        this.entretiens = new LinkedHashMap<>();
    }

    /**
     * Modifie l'inventaire avec des nouvelles données
     * @param nom nom à remplacer
     * @param description description à remplacer
     * @param categorie catégorie à remplacer
     * @param date date à remplacer
     * @param nbSerie numéro de série à remplacer
     * @param prix prix à remplacer
     */
    public void modify(String nom, String description, String categorie, LocalDate date, String nbSerie, double prix) {
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
        this.date = date;
        this.nbSerie = nbSerie;
        this.prix = prix;
    }

    /**
     * @return le nom de l'inventaire
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return la description de l'inventaire
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return la description de l'inventaire
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @return la date de l'achat de l'inventaire
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @return le numéro de série de l'inventaire
     */
    public String getNbSerie() {
        return nbSerie;
    }

    /**
     * @return le prix
     */
    public double getPrix() {
        return prix;
    }

    /**
     * @return les entretiens de l'inventaire
     */
    public LinkedHashMap<LocalDate, String> getEntretiens() {
        return entretiens;
    }

    /**
     * Ajouter un entretien à un inventaire
     * @param date la date de l'entretien
     * @param description la description de l'entretien
     */
    public void addEntretien(LocalDate date, String description) {
        entretiens.put(date, description);
    }

    /**
     * Supprimer un entretien à un inventaire
     * @param date la date de l'entretien
     */
    public void delEntretien(LocalDate date) {
        entretiens.remove(date);
    }

    /**
     * Créer un string de l'inventaire
     * @return l'inventaire en string
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(nom).append(", ")
                .append(nbSerie).append(", ")
                .append(categorie).append(", ")
                .append(prix).append(", ")
                .append(date).append(", ")
                .append(description).append("\n");

        entretiens.forEach((date, desc) -> sb.append(date).append(", ").append(desc).append("\n"));

        return sb.toString();
    }

    /**
     * Créer un objet de l'inventaire pour le mettre dans le tableau d'inventaire
     * @return l'inventaire en objet
     */
    public Object[] toObject() {
        return new Object[]{
                nom, categorie, prix, date, description
        };
    }
}
