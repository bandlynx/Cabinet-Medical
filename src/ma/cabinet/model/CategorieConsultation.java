/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ma.cabinet.model;

public class CategorieConsultation {

    private int id;
    private String designation;
    private String description;

    public CategorieConsultation() {}

    public CategorieConsultation(int id, String designation, String description) {
        this.id = id;
        this.designation = designation;
        this.description = description;
    }

    public CategorieConsultation(String designation, String description) {
        this.designation = designation;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        // Ce texte sera affiché dans la comboBox
        return designation;
        // ou si tu préfères :
        // return id + " - " + designation;
    }
}
