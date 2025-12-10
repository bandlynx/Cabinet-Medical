 
package ma.cabinet.model;

public class BilanMensuel {
    private int annee;
    private int mois;
    private int nbConsultations;
    private double chiffreAffaires;

    public BilanMensuel(int annee, int mois, int nbConsultations, double chiffreAffaires) {
        this.annee = annee;
        this.mois = mois;
        this.nbConsultations = nbConsultations;
        this.chiffreAffaires = chiffreAffaires;
    }

    public int getAnnee() { return annee; }
    public int getMois() { return mois; }
    public int getNbConsultations() { return nbConsultations; }
    public double getChiffreAffaires() { return chiffreAffaires; }
}