 
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
        return getId() + " - " + getNom();  
    }
}
