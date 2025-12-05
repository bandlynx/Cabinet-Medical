/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.model;

public class Medecin extends Utilisateur {

    private String specialite;

    public Medecin() {}

    public Medecin(int id, String login, String password, String nom, String specialite) {
        super(id, login, password, nom);
        this.specialite = specialite;
    }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    @Override
    public String toString() {
        return getId() + " - " + getNom();  // affiche proprement
    }
}
