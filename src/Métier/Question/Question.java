package Question;

import Metier.Notion;
import Metier.Ressource;
import Metier.Difficulte;

public abstract class Question
{
    private String     intitule;
    private Difficulte difficulte;
    private Ressource ressource;
    private Notion     notion;
    private int        temps;
    private int        point;
    private String     explication;

    public Question(String intitule, Difficulte difficulte,Ressource ressource,Notion notion,int temps, int point)
    {
        this.intitule   = intitule;
        this.difficulte = difficulte;
        this.ressource  = ressource;
        this.notion     = notion;
        this.temps      = temps;
        this.point      = point;
    }

    /**
     * Constructeur de la classe Question.
     *
     * @param intitule      L'intitulé de la question.
     * @param difficulte    La difficulté de la question, qui peut être : très facile, facile, moyen, difficile.
     * @param ressource     La ressource concernée par la question.
     * @param notion        La notion concernée par la question.
     * @param temps         Le temps nécessaire pour répondre à la question en millisecondes.
     * @param point         Le nombre de points que rapporte la question.
     * @param explication   Les explications fournies avec la réponse à la question.
     */

    public Question(String intitule, Difficulte difficulte,Ressource ressource,Notion notion,int temps, int point ,String explication)
    {
        this.intitule    = intitule;
        this.difficulte  = difficulte;
        this.ressource   = ressource;
        this.notion      = notion;
        this.temps       = temps;
        this.explication = explication;
        this.point       = point;
    }

    // Get
    public String     getIntitule()    {return intitule;}
    public Difficulte getDifficulte()  {return this.difficulte;}
    public Ressource  getRessource()   {return this.ressource;}
    public Notion     getNotion()      {return this.notion;}
    public int        getTemps()       {return this.temps;}
    public int        getPoint()       {return this.point;}
    public String     getExplication() {return this.explication;}

    // Set
    public void setIntitule(String intitule)        {this.intitule = intitule;}
    private void setDifficulte(Difficulte difficulte)   {this.difficulte = difficulte;}
    private void setRessource(Ressource ressource)  {this.ressource  = ressource;}
    private void setNotion(Notion notion)           {this.notion = notion;}
    private void setTemps(int temps)                {this.temps = temps;}
    private void setPoint(int point)                {this.point = point;}
    private void setExplication(String explication) {this.explication = explication;}
}