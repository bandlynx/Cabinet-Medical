/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.model;

public class Utilisateur {
    protected int id;
    protected String login;
    protected String password;
    protected String nom;

    public Utilisateur() {}

    public Utilisateur(int id, String login, String password, String nom) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.nom = nom;
    }
     private String role; // "ASSISTANT" ou "MEDECIN"
    public Utilisateur(int id, String login, String password) {
    this(id, login, password, "");   // ✔ nom vide, mais plus null
}
      public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}
