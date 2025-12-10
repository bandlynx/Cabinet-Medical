 
package ma.cabinet.model;

public class Assistant extends Utilisateur {

    private String nom;

    public Assistant() {}

    public Assistant(int id, String login, String password, String nom) {
        super(id, login, password);  
        this.nom = nom;
    }
    public Assistant(String login, String password, String nom) {
    super(0, login, password);   
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
        return nom;  
    }
}
