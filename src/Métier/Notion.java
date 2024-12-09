package Metier;

public class Notion
{
    private int       id;
    private String    nom;
    private Ressource ressourceAssociee;

    /**
     * Constructeur de la classe Notion.
     *
     * @param id                L'identifiant de la notion.
     * @param nom               Le nom de la notion.
     * @param ressourceAssociee La ressource associée à la notion.
     */
    public Notion(int id, String nom, Ressource ressourceAssociee)
    {
        this.id                = id;
        this.nom               = nom;
        this.ressourceAssociee = ressourceAssociee;
    }

    // Get
    public int getId()                      {return this.id;}
    public String getNom()                  {return this.nom;}
    public Ressource getRessourceAssociee() {return this.ressourceAssociee;}

    // Set
    public void setId(int id)                                     {this.id = id;}
    public void setNom(String nom)                                {this.nom = nom;}
    public void setRessourceAssociee(Ressource ressourceAssociee) {this.ressourceAssociee = ressourceAssociee;}
}