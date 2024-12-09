package Metier;

public class Notion
{
    private Metier    metier;
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
    public Notion(int id, String nom, Ressource ressourceAssociee,metier metier)
    {
        this.metier = metier;
        this.id                = id;
        this.nom               = nom;
        this.ressourceAssociee = ressourceAssociee;
    }

    // Get

    public Metier getMetier() {return metier;}
    public int getId()                      {return this.id;}
    public String getNom()                  {return this.nom;}
    public Ressource getRessourceAssociee() {return this.ressourceAssociee;}
    public String     getAsData(){
        return (this.id + ";" + this.nom + ";" + this.ressourceAssociee.getId());
    }
    public static Notion getFromData(String data){
        String[] parts = data.split(";");
        return new Notion(Integer.parseInt(parts[0]), parts[1], this.metier.getRessourceById(Integer.parseInt(parts[2])));
    }
    // Set

    public void setMetier(Metier metier) {this.metier = metier;}
    public void setId(int id)                                     {this.id = id;}
    public void setNom(String nom)                                {this.nom = nom;}
    public void setRessourceAssociee(Ressource ressourceAssociee) {this.ressourceAssociee = ressourceAssociee;}
}