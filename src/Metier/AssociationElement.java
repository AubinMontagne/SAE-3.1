package Metier;

import java.util.HashMap;

public class AssociationElement extends Question
{
    private HashMap<String, String> associations;

    // Constructeur
    public AssociationElement(String intitule, Difficulte difficulté,Notion notion,int temps,int points,Metier metier, String explication, HashMap<String,String> associations)
    {
        super(intitule, difficulté, notion, temps, points,metier, explication);
        this.associations = associations;
    }

    public AssociationElement(String intitule, Difficulte difficulté, Notion notion, int temps,int points,Metier metier, HashMap<String,String> associations)
    {
        super(intitule, difficulté, notion, temps, points,metier);
        this.associations = associations;
    }

    public HashMap<String, String> getAssociations()
    {
        return this.associations;
    }


    public void ajouterAssociation(String gauche, String droite)
    {
        this.associations.put(gauche, droite);
    }

    public void supprimerAssociation(String gauche)
    {
        this.associations.remove(gauche);
    }

	public void getAsData(String directoryPath)
	{
		// TODO
	}
}
