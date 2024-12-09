public class Notion
{
    private String nom;
    private Ressource ressourceAssociee;

    public Notion(String nom, Ressource ressourceAssociee)
    {
        this.nom = nom;
        this.ressourceAssociee = ressourceAssociee;
    }

    // Get

    public String getNom()
    {
        return this.nom;
    }

    public Ressource getRessourceAssociee()
    {
        return this.ressourceAssociee;
    }

    // Set

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public void setRessourceAssociee(Ressource ressourceAssociee)
    {
        this.ressourceAssociee = ressourceAssociee;
    }
}