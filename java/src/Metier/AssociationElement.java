package src.Metier;

import java.util.HashMap;
import java.util.Scanner;

public class AssociationElement extends Question
{
    private HashMap<String, String> hmAssociations;

    // Constructeur
    public AssociationElement(String intitule, Difficulte difficulte,Notion notion,int temps,int points,String explication){
        super(intitule, difficulte, notion, temps, points, explication);
        this.hmAssociations = new HashMap<>();
    }

    public AssociationElement(String intitule, Difficulte difficulte, Notion notion, int temps,int points){
        super(intitule, difficulte, notion, temps, points);
        this.hmAssociations = new HashMap<>();
    }

    public HashMap<String, String> getAssociations()
    {
        return this.hmAssociations;
    }

    public void ajouterAssociation(String gauche, String droite){

		if (!(gauche == null || droite == null) &&
            (!this.hmAssociations.containsKey(gauche) || !this.hmAssociations.containsValue(droite)) &&
            (!this.hmAssociations.containsKey(droite) || !this.hmAssociations.containsValue(gauche))){
            this.hmAssociations.put(gauche, droite);
		}
    }

    public void supprimerAssociation(String gauche){
        this.hmAssociations.remove(gauche);
    }

	public String getAsData(){
        String res = this.getClass().getName() + ";" + super.getAsData() + ";" ;
        for (String gauche : this.hmAssociations.keySet()) {
            res += gauche + "," + this.hmAssociations.get(gauche) + "|";
        }
        return res;
    }
	public static AssociationElement getAsInstance(String ligne, Metier metier){
        Scanner scanner = new Scanner(ligne);
        scanner.useDelimiter(";");

        String[] parts = new String[8];
        for (int i = 0; i < 8; i++) {
            scanner.next();
        }

        AssociationElement associationElement = new AssociationElement(parts[1], Difficulte.getDifficulteByIndice(Integer.parseInt(parts[2])), metier.getNotionByNom(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), parts[6]);

        Scanner associationScanner = new Scanner(parts[7]);
        associationScanner.useDelimiter("\\|");
        while (associationScanner.hasNext()) {
            String association = associationScanner.next();
            Scanner associationPartsScanner = new Scanner(association);
            associationPartsScanner.useDelimiter(",");
            String[] associationParts = new String[2];
            for (int i = 0; i < 2; i++) {
                associationParts[i] = associationPartsScanner.next();
            }
            associationElement.ajouterAssociation(associationParts[0], associationParts[1]);
            associationPartsScanner.close();
        }

        return associationElement;
    }

	public String toString(){
		String res = super.toString();
		res += "Associations : \n";
		for (String gauche : this.hmAssociations.keySet()) {
			res += gauche + " -> " + this.hmAssociations.get(gauche) + "\n";
		}
		return res;
	}
}
