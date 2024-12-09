package Métier;

public class Questions
{
    private String     intitule;
    private String     difficulté;
    private Ressources ressource;
    private Notions    notion;
    private int        temps;
    private String     explication;

    public Questions(String intitule, String difficulté,Ressources ressource,Notions notion,int temps)
    {
        this.intitule   = intitule;
        this.difficulté = difficulté;
        this.ressource  = ressource;
        this.notion     = notion;
        this.temps      = temps;
    }

    public Questions(String intitule, String difficulté,Ressources ressource,Notions notion,int temps, String explication)
    {
        this.intitule    = intitule;
        this.difficulté  = difficulté;
        this.ressource   = ressource;
        this.notion      = notion;
        this.temps       = temps;
        this.explication = explication;
    }

    // Get
    public String getDifficulté()    {return this.difficulté;}
    public Ressources getRessource() {return this.ressource;}
    public Notions getNotion()       {return this.notion;}
    public int getTemps()            {return this.temps;}

    // Set
    private void setDifficulté(String difficulté)   {this.difficulté = difficulté;}
    private void setRessource(Ressources ressource) {this.ressource  = ressource;}
    private void setNotion(Notions notion)          {this.notion     = notion;}
    private void setTemps(int temps)                {this.temps      = temps;}


    //
}