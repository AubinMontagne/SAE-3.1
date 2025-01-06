package src.Metier;


import java.io.File;
import java.util.List;

public abstract class Question{
    private String          dossierChemin;// intitulé + corps de la question
    private Difficulte      difficulte;
    private Notion          notion;
    private int             temps;
    private int             point;
    private String          imageChemin;
    private List<String>    listeFichiers;
	private int id;

	/**
     * Constructeur de la classe Question.
     *
     * @param dossierChemin Le chemin d'accès de la question.
     * @param difficulte    La difficulté de la question, qui peut être : très facile, facile, moyen, difficile.
     * @param notion        La notion concernée par la question.
     * @param temps         Le temps nécessaire pour répondre à la question en millisecondes.
     * @param point         Le nombre de points que rapporte la question.
     */
    public Question(String dossierChemin, Difficulte difficulte,Notion notion,int temps, int point, String imageChemin, List<String> listeFichiers, int id)
    {
        this.dossierChemin   = dossierChemin;
        this.difficulte      = difficulte;
        this.notion          = notion;
        this.temps           = temps;
        this.point           = point;
        this.imageChemin     = imageChemin;
        this.listeFichiers   = listeFichiers;
        this.id              = id;
    }

	// Get
    public String     getEnonceFich()        {return dossierChemin;}
    public String     getDossierChemin()       {return dossierChemin;}
    //public String   getEnonceText()    {return RtfFileReader.getRtfFileAsText(enonceFich);}
    public Difficulte getDifficulte()         {return this.difficulte;}
    public Notion     getNotion()             {return this.notion;}
    public int        getTemps()              {return this.temps;}
    public int        getPoint()              {return this.point;}
    public List<String> getListeFichiers()       {return this.listeFichiers;}
    public String     getImageChemin()        {return this.imageChemin;}
    public int        getId()                 {return this.id;}
    //public String     getExplicationText(){return RtfFileReader.getRtfFileAsText(explicationFich);}

    public String     getAsData(){
        String res = this.dossierChemin + ";" + this.difficulte.getIndice() + ";" + getNotion().getNom() + ";" + this.temps + ";" + this.point + ";" + this.id + ";" + this.imageChemin + ";";
        for(String fichier : this.listeFichiers){
            res += fichier + ",";
        }
        return res;
    }

	// Set
    public void setDossierChemin  (String dossierChemin)   {this.dossierChemin      = dossierChemin;}
    public void setDifficulte     (Difficulte difficulte)  {this.difficulte      = difficulte;}
    public void setNotion         (Notion notion)          {this.notion          = notion;}
    public void setTemps          (int temps)              {this.temps           = temps;}
    public void setPoint          (int point)              {this.point           =  point;}
    public void setImageChemin    (String imageChemin)     {this.imageChemin     = imageChemin;}
    public void setListeFichiers  (List<String> listeFichiers){this.listeFichiers   = listeFichiers;}
    public void ajouterfichier    (String fichier){this.listeFichiers.add(fichier);}
    public int  setId             (int id)                 {return this.id       = id;}

	public String toString(){
		return "Difficulté: " + this.difficulte + "\n" +
			   this.notion.toString() + "\n" +
			   "Temps: " + this.temps + " secondes\n" +
			   "Points: " + this.point + "\n";
	}
}