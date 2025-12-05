/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.model;

public class Assistant extends Utilisateur {

    private String nom;

    public Assistant() {}

    public Assistant(int id, String login, String password, String nom) {
        super(id, login, password);  // <--- Corrigé
        this.nom = nom;
    }
    public Assistant(String login, String password, String nom) {
    super(0, login, password);   // id généré automatiquement par MySQL
    this.nom = nom;
}

    public String getNom() { 
        return nom; 
    }

    public void setNom(String nom) { 
        this.nom = nom; 
    }

    @Override
    public String toString() {
        return nom;  // Pour affichage dans les ComboBox
    }
}
