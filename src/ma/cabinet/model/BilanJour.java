 
package ma.cabinet.model;

public class BilanJour {
    private String jour;           
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
