package src.Metier;

public class Notion
{
    private String    nom;
    private Ressource ressourceAssociee;

    /**
     * Constructeur de la classe Notion.
     *
     * @param nom               Le nom de la notion.
     * @param ressourceAssociee La ressource associée à la notion.
     */
    public Notion(String nom, Ressource ressourceAssociee)
    {
        this.nom               = nom;
        this.ressourceAssociee = ressourceAssociee;
    }

    // Get

	public boolean equals(Notion notion){
		return this.nom.equals(notion.getNom()) &&
		this.ressourceAssociee.equals(notion.getRessourceAssociee());
	}

    public String    getNom()               {return this.nom;}
    public Ressource getRessourceAssociee() {return this.ressourceAssociee;}

    public String     getAsData(){ 
        return (this.nom + ";" + this.ressourceAssociee.getId());
    }
    public static Notion getFromData(String data, Metier metier){
        String[] parts = data.split(";");
        return new Notion(parts[0], metier.getRessourceById(parts[2]));
    }
    // Set

    public void setNom(String nom)                               {this.nom = nom;}
    public void setRessourceAssociee(Ressource ressourceAssociee){this.ressourceAssociee = ressourceAssociee;}

	public String toString(){
		String res = "Notion : " + this.getNom();
		res += "Ressource associée : " + this.getRessourceAssociee().getNom();
		return res;
	}
}