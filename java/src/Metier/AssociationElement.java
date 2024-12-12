package src.Metier;

import java.util.HashMap;

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

    public HashMap<String, String> getAssociations(){
        return this.hmAssociations;
    }


    public void ajouterAssociation(String gauche, String droite){
		if (!(gauche == null || droite == null)) {
        	this.hmAssociations.put(gauche, droite);
		}
    }

    public void supprimerAssociation(String gauche){
        this.hmAssociations.remove(gauche);
    }

	public void getAsData(String directoryPath) {}
	public static AssociationElement getAsInstance(String pathDirectory, Metier metier){
		return null;
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
