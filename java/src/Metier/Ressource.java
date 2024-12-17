package src.Metier;

public class Ressource{
    private String id;
    private String nom;

    // Constructeur
    /**
     * Constructeur de la classe Ressource.
     *
     * @param id        L'identifiant de la ressource.
     * @param nom       Le nom de la ressource.
     */
    public Ressource(String nom, String id){
        this.id       = id;
        this.nom      = nom;
    }

    // Getter
	public boolean equals(Ressource ressource){
		return this.id.equals(ressource.getId()) &&
		this.nom.equals(ressource.getNom());
	}

    public String getId()    {return this.id; }
    public String getNom()   {return this.nom; }
    public String getAsData(){return this.id + ";" + this.nom;}

	public static Ressource getFromData(String data){
        String[] parts = new String[]{data.substring(0, data.indexOf(";")), data.substring(data.indexOf(";") + 1)};
        return new Ressource(parts[0], parts[1]);
    }

    // Setter
    public void setId (String id) {this.id  = id; }
    public void setNom(String nom){this.nom = nom; }

    @Override
	public String toString(){return this.id + " : " + this.nom + "\n"; }
}