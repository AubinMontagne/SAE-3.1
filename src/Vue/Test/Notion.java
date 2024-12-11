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

	public boolean equals(Notion notion){
		return this.id == notion.getId() && 
		this.nom.equals(notion.getNom()) && 
		this.ressourceAssociee.equals(notion.getRessourceAssociee());
	}

    public int getId()                      {return this.id;}
    public String getNom()                  {return this.nom;}
    public Ressource getRessourceAssociee() {return this.ressourceAssociee;}
    public String     getAsData(){ 
        return (this.id + ";" + this.nom + ";" + this.ressourceAssociee.getId());
    }
    // Set


    public void setId(int id)                                     {this.id = id;}
    public void setNom(String nom)                                {this.nom = nom;}
    public void setRessourceAssociee(Ressource ressourceAssociee) {this.ressourceAssociee = ressourceAssociee;}

    /* 
	public String toString(){
		String res = "Notion : " + this.getNom() + " (id : " + this.getId() + ")\n";
		res += "Ressource associée : " + this.getRessourceAssociee().getNom() + "\n";
		return res;
	}*/
    public String toString(){
		return this.getNom();
	}
}