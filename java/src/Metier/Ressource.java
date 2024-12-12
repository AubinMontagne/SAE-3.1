package src.Metier;

public class Ressource
{
    private String id;
    private String nom;

    /**
     * Constructeur de la classe Ressource.
     *
     * @param id        L'identifiant de la ressource.
     * @param nom       Le nom de la ressource.
     */
    public Ressource(String nom, String id)
    {
        this.id       = id;
        this.nom      = nom;
    }

    // Get

	public boolean equals(Ressource ressource){
		return this.id.equals(ressource.getId()) &&
		this.nom.equals(ressource.getNom());
	}

    public String getId()     {return this.id;}
    public String getNom()    {return this.nom;}

    public String getAsData(){return this.id + ";" + this.nom;}
	public static Ressource getFromData(String data){
        String[] parts = data.split(";");
        return new Ressource(parts[0], parts[1]);
    }
    // Set
    public void setId (String id) {this.id       = id;}
    public void setNom(String nom){this.nom      = nom;}

    @Override
	public String toString(){
		return this.id + " : " + this.nom + "\n";
	}
}