 
package ma.cabinet.model;

import java.util.Date;

public class Consultation {

    private int id;
    private Date dateConsultation;
    private String description;
    private double prixConsultation;

    private int idPatient;
    private int idMedecin;
    private int idCategorie;
    private int idRendezVous;

    public Consultation() {}

    public Consultation(int id, Date dateConsultation, String description,
                        double prixConsultation, int idPatient, int idMedecin,
                        int idCategorie, int idRendezVous) {
        this.id = id;
        this.dateConsultation = dateConsultation;
        this.description = description;
        this.prixConsultation = prixConsultation;
        this.idPatient = idPatient;
        this.idMedecin = idMedecin;
        this.idCategorie = idCategorie;
        this.idRendezVous = idRendezVous;
    }

    public Consultation(Date dateConsultation, String description,
                        double prixConsultation, int idPatient, int idMedecin,
                        int idCategorie, int idRendezVous) {
        this.dateConsultation = dateConsultation;
        this.description = description;
        this.prixConsultation = prixConsultation;
        this.idPatient = idPatient;
        this.idMedecin = idMedecin;
        this.idCategorie = idCategorie;
        this.idRendezVous = idRendezVous;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getDateConsultation() { return dateConsultation; }
    public void setDateConsultation(Date dateConsultation) { this.dateConsultation = dateConsultation; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrixConsultation() { return prixConsultation; }
    public void setPrixConsultation(double prixConsultation) { this.prixConsultation = prixConsultation; }

    public int getIdPatient() { return idPatient; }
    public void setIdPatient(int idPatient) { this.idPatient = idPatient; }

    public int getIdMedecin() { return idMedecin; }
    public void setIdMedecin(int idMedecin) { this.idMedecin = idMedecin; }

    public int getIdCategorie() { return idCategorie; }
    public void setIdCategorie(int idCategorie) { this.idCategorie = idCategorie; }

    public int getIdRendezVous() { return idRendezVous; }
    public void setIdRendezVous(int idRendezVous) { this.idRendezVous = idRendezVous; }
}
