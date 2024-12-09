package Metier;

import java.util.List;

public class Questionnaire {
    private String         nom;
    private Ressource      ressource;
    private Notion         notion;
    private int            temps;
    private int            point;
    private int            pointMax;
    //private tab[] difficulte;
    private List<Question> listQuestion;

    public Questionnaire(String titre,Ressource ressource, Notion notion, int temps) {
        this.nom       = titre;
        this.ressource = ressource;
        this.notion    = notion;
        this.temps     = temps;
        this.point     = 0;
        this.pointMax  = 0;
    }

    // Get
    public Ressource getRessource() {return this.ressource;}
    public Notion getNotion()       {return this.notion;   }
    public int getTemps()           {return this.temps;    }
    public int getPoint()           {return this.point;    }

    // Set
    public void setRessource(Ressource ressource){this.ressource = ressource;}
    public void setNotion(Notion notion)         {this.notion    = notion;   }
    public void setTemps(int temps)              {this.temps     = temps;    }
    public void setPoint(int point)              {this.point     = point;    }

    // Méthode pour gérer les mofication des point
    public void ajouterPoint(int i) {this.point += i;}
    public void enleverPoint(int i) {this.point -= i;}

    public void initListQuestions()
    {
        // A faire quand on aura le data des questions
    }
}
