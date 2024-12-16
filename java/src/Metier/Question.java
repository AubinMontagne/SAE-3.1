package src.Metier;

public abstract class Question{
    private String     enonceFich;// intitulé + corps de la question
    private Difficulte difficulte;
    private Notion     notion;
    private int        temps;
    private int        point;
    private String     explicationFich;
	
	/**
     * Constructeur de la classe Question.
     *
     * @param enonceFich    Le nom du fichier rtf contenant l'énoncé de la question.
     * @param difficulte    La difficulté de la question, qui peut être : très facile, facile, moyen, difficile.
     * @param notion        La notion concernée par la question.
     * @param temps         Le temps nécessaire pour répondre à la question en millisecondes.
     * @param point         Le nombre de points que rapporte la question.
     */
    public Question(String enonceFich, Difficulte difficulte,Notion notion,int temps, int point){
        this.enonceFich      = enonceFich;
        this.difficulte      = difficulte;
        this.notion          = notion;
        this.temps           = temps;
        this.point           = point;
		this.explicationFich = " ";
    }

    /**
     * Constructeur de la classe Question.
     *
     * @param enonceFich    Le nom du fichier rtf contenant l'énoncé de la question.
     * @param difficulte    La difficulté de la question, qui peut être : très facile, facile, moyen, difficile.
     * @param notion        La notion concernée par la question.
     * @param temps         Le temps nécessaire pour répondre à la question en secondes.
     * @param point         Le nombre de points que rapporte la question.
     * @param explicationFich   Le nom du fichier rtf contenant l'explication de la question.
     */

    public Question(String enonceFich, Difficulte difficulte, Notion notion, int temps, int point, String explicationFich){
        this.enonceFich    = enonceFich;
        this.difficulte  = difficulte;
        this.notion      = notion;
        this.temps       = temps;
        this.explicationFich = explicationFich;
        this.point       = point;
    }

	// Get
    public String     getEnonceFich(){return enonceFich;}
    public String     getEnonceFichText(){return RtfFileReader.getRtfFileAsText(enonceFich);}
    public Difficulte getDifficulte(){return this.difficulte;}
    public Notion     getNotion()    {return this.notion;}
    public int        getTemps()     {return this.temps;}
    public int        getPoint()     {return this.point;}
    public String     getExplicationFich() {return this.explicationFich;}
    public String     getExplicationFichText(){return RtfFileReader.getRtfFileAsText(explicationFich);}

    public String     getAsData(){
        return this.enonceFich + ";" + this.difficulte.getIndice() + ";" + getNotion().getNom() + ";" + this.temps + ";" + this.point + ";" + this.explicationFich;
    }


	// Set
    public void setEnonceFich(String intitule)      {this.enonceFich = enonceFich;}
    public void setDifficulte(Difficulte difficulte){this.difficulte = difficulte;}
    public void setNotion(Notion notion)            {this.notion = notion;}
    public void setTemps(int temps)                 {this.temps = temps;}
    public void setPoint(int point)                 {this.point = point;}
    public void setExplicationFich(String explicationFich)   {this.explicationFich = explicationFich;}

	public String toString(){
		return "Difficulté: " + this.difficulte + "\n" +
			   this.notion.toString() + "\n" +
			   "Temps: " + this.temps + " secondes\n" +
			   "Points: " + this.point + "\n";
	}
}