package Metier;

public class Ressource
{
    private int    id;
    private String nom;
    private String accronym;

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

    // Set
    public void setId(int id)                {this.id       = id;}
    public void setNom(String nom)           {this.nom      = nom;}
    public void setAccronym(String accronym) {this.accronym = accronym;}
}