package Question;

import java.util.HashMap;
import java.util.Map;
import Metier.Notion;
import Metier.Ressource;

public class AssociationElement extends Question
{
    private HashMap<String, String> associations;

    // Constructeur
    public AssociationElement(String intitule, String difficulté,Ressource ressource,Notion notion,int temps,int points, String explication, HashMap<String,String> associations)
    {
        super(intitule, difficulté, ressource, notion, temps, points, explication);
        this.associations = associations;
    }

    public AssociationElement(String intitule, String difficulté, Ressource ressource, Notion notion, int temps,int points, HashMap<String,String> associations)
    {
        super(intitule, difficulté, ressource, notion, temps, points);
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

    public void afficherAssociations()
    {
        System.out.println("Associations :");
        for (HashMap.Entry<String, String> entry : this.associations.entrySet())
        {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
