/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.model;

import java.util.Date;

public class Paiement {

    private int id;
    private Date datePaiement;
    private double montant;
    private String modePaiement;   // ESPECES / CARTE / CHEQUE
    private String statut;         // EN_ATTENTE / VALIDE / ANNULE

    private int idConsultation;
    private int idAssistant;

    public Paiement() {}

    public Paiement(int id, Date datePaiement, double montant,
                    String modePaiement, String statut,
                    int idConsultation, int idAssistant) {
        this.id = id;
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.modePaiement = modePaiement;
        this.statut = statut;
        this.idConsultation = idConsultation;
        this.idAssistant = idAssistant;
    }

    public Paiement(Date datePaiement, double montant,
                    String modePaiement, String statut,
                    int idConsultation, int idAssistant) {
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.modePaiement = modePaiement;
        this.statut = statut;
        this.idConsultation = idConsultation;
        this.idAssistant = idAssistant;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getDatePaiement() { return datePaiement; }
    public void setDatePaiement(Date datePaiement) { this.datePaiement = datePaiement; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public String getModePaiement() { return modePaiement; }
    public void setModePaiement(String modePaiement) { this.modePaiement = modePaiement; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public int getIdConsultation() { return idConsultation; }
    public void setIdConsultation(int idConsultation) { this.idConsultation = idConsultation; }

    public int getIdAssistant() { return idAssistant; }
    public void setIdAssistant(int idAssistant) { this.idAssistant = idAssistant; }
}
