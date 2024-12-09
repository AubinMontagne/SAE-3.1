package Metier;

public abstract class Question
{
    private String     intitule;
    private String     difficulté;
    private Ressource ressource;
    private Notion     notion;
    private int        temps;
    private int        point;
    private String     explication;

    public Question(String intitule, String difficulté,Ressource ressource,Notion notion,int temps, int point)
    {
        this.intitule   = intitule;
        this.difficulté = difficulté;
        this.ressource  = ressource;
        this.notion     = notion;
        this.temps      = temps;
        this.point      = point;
    }

    public Question(String intitule, String difficulté,Ressource ressource,Notion notion,int temps, int point ,String explication)
    {
        this.intitule    = intitule;
        this.difficulté  = difficulté;
        this.ressource   = ressource;
        this.notion      = notion;
        this.temps       = temps;
        this.explication = explication;
        this.point       = point;
    }

    // Get
    public String    getIntitule()    {return intitule;}
    public String    getDifficulté()  {return this.difficulté;}
    public Ressource getRessource()   {return this.ressource;}
    public Notion    getNotion()      {return this.notion;}
    public int       getTemps()       {return this.temps;}
    public int       getPoint()       {return this.point;}
    public String    getExplication() {return this.explication;}

    // Set
    private void setDifficulté(String difficulté)   {this.difficulté = difficulté;}
    private void setRessource(Ressource ressource)  {this.ressource  = ressource;}
    private void setNotion(Notion notion)           {this.notion = notion;}
    private void setTemps(int temps)                {this.temps = temps;}
    private void setPoint(int point)                {this.point = point;}
    private void setExplication(String explication) {this.explication = explication;}
}