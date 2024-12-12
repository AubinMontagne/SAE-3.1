package src.Metier;

public class Ressource
{
    private static int    nid=0;
    private int id;
    private String nom;
    private String accronym;

    /**
     * Constructeur de la classe Ressource.
     *
     * @param id        L'identifiant de la ressource.
     * @param nom       Le nom de la ressource.
     * @param acronyme  L'acronyme de la ressource.
     */
    public Ressource(String nom, String accronym)
    {
        this.id       = ++nid;
        this.nom      = nom;
        this.accronym = accronym;
    }

    // Get

	public boolean equals(Ressource ressource){
		return this.id == ressource.getId() &&
		this.nom.equals(ressource.getNom()) &&
		this.accronym.equals(ressource.getAccronym());
	}

    public int getId()          {return this.id;}
    public String getNom()      {return this.nom;}
    public String getAccronym() {return this.accronym;}
    public String getAsData(){
        return this.id + ";" + this.nom + ";" + this.accronym;
    }
	public static Ressource getFromData(String data, Metier metier){
        String[] parts = data.split(";");
        return new Ressource(Integer.parseInt(parts[0]), parts[1], parts[2]);
    }
    // Set
    public void setId(int id)                {this.id       = id;}
    public void setNom(String nom)           {this.nom      = nom;}
    public void setAccronym(String accronym) {this.accronym = accronym;}

	public String toString(){
		String res = "Ressource : " + this.nom + "\n";
		res += "Acronyme : " + this.accronym + "\n";
		return res;
	}
}