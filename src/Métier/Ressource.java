ackage Métier;

public class Ressources
{
    private String nom;
    private String accronym;

    public Ressources(String nom, String accronym)
    {
        this.nom = nom;
        this.accronym = accronym;
    }

    // Get

    public String getNom()
    {
        return this.nom;
    }

    public String getAccronym()
    {
        return this.accronym;
    }

    // Set

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public void setAccronym(String accronym)
    {
        this.accronym = accronym;
    }
}