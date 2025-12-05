/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.model;

public class BilanJour {
    private String jour;           // "2025-12-05"
    private int nbConsultations;
    private double chiffreAffaires;

    public BilanJour(String jour, int nbConsultations, double chiffreAffaires) {
        this.jour = jour;
        this.nbConsultations = nbConsultations;
        this.chiffreAffaires = chiffreAffaires;
    }

    public String getJour() { return jour; }
    public int getNbConsultations() { return nbConsultations; }
    public double getChiffreAffaires() { return chiffreAffaires; }
}
