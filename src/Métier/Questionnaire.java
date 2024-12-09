package Métier;

public class Questionnaire {
    private Ressource ressource;
    private Notion notion;
    private int temps;
    private int point;
    private List<Question> listQuestion;

    public Questionnaire(Notion notion, int temps) {
        this.ressource = notion.getRessourcec();
        this.notion    = notion;
        this.temps     = temps;
        this.point     = 0;
    }

    // Get
    public String getRessource() {return this.ressource;}
    public String getNotion()    {return this.notion;   }
    public String getTemps()     {return this.temps;    }
    public String getPoint()     {return this.point;    }

    // Set
    public void setRessource(Ressource ressource){this.ressource = ressource;}
    public void setNotion(Notion notion)         {this.notion    = notion;   }
    public void setTemps(int temps)              {this.temps     = temps;    }

    // Méthode pour gérer les mofication des point
    public void ajouterPoint(int i)
    {
        this.point += i;
    }
    public void enleverPoint(int i)
    {
        this.point -= i;
    }
}
