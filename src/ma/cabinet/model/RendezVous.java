/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.model;

import java.util.Date;
import java.text.SimpleDateFormat;

public class RendezVous {

    private int id;
    private Date dateHeure;
    private String statut;
    private String motif;

    private int idPatient;
    private int idMedecin;

    public RendezVous() {}

    public RendezVous(int id, Date dateHeure, String statut, String motif, int idPatient, int idMedecin) {
        this.id = id;
        this.dateHeure = dateHeure;
        this.statut = statut;
        this.motif = motif;
        this.idPatient = idPatient;
        this.idMedecin = idMedecin;
    }

    public RendezVous(Date dateHeure, String statut, String motif, int idPatient, int idMedecin) {
        this.dateHeure = dateHeure;
        this.statut = statut;
        this.motif = motif;
        this.idPatient = idPatient;
        this.idMedecin = idMedecin;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getDateHeure() { return dateHeure; }
    public void setDateHeure(Date dateHeure) { this.dateHeure = dateHeure; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public int getIdPatient() { return idPatient; }
    public void setIdPatient(int idPatient) { this.idPatient = idPatient; }

    public int getIdMedecin() { return idMedecin; }
    public void setIdMedecin(int idMedecin) { this.idMedecin = idMedecin; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return id + " - " + sdf.format(dateHeure) + " - " + motif;
    }
}
