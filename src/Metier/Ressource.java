package Metier;

public class Ressource
{
    private int    id;
    private String nom;
    private String accronym;

    /**
     * Constructeur de la classe Ressource.
     *
     * @param id        L'identifiant de la ressource.
     * @param nom       Le nom de la ressource.
     * @param acronyme  L'acronyme de la ressource.
     */
    public Ressource(int id, String nom, String accronym)
    {
        this.id       = id;
        this.nom      = nom;
        this.accronym = accronym;
    }

    // Get
    public int getId()          {return this.id;}
    public String getNom()      {return this.nom;}
    public String getAccronym() {return this.accronym;}
    public String     getAsData(){
        return this.id + ";" + this.nom + ";" + this.accronym;
    }

    // Set
    public void setId(int id)                {this.id       = id;}
    public void setNom(String nom)           {this.nom      = nom;}
    public void setAccronym(String accronym) {this.accronym = accronym;}
}