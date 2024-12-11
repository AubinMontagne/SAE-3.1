public class Question{

    private String     intitule;
	private String     resssource;
    private Ressource     tResssource;
    private String notion;
    private Notion tNotion;
    private int        temps;
    private int        point;
    private String     explication;

    public Question(String intitule,String ressource,String notion, int temps, int point)
    {
        this.intitule   = intitule;
		this.resssource = ressource;
        this.notion = notion;
        this.temps      = temps;
        this.point      = point;
    }

    public Question(String intitule,Ressource ressource,Notion notion, int temps, int point)
    {
        this.intitule   = intitule;
		this.tResssource = ressource;
        this.tNotion = notion;
        this.temps      = temps;
        this.point      = point;
    }

    // Get
    public String     getIntitule()    {return intitule;}
	public String     getRessource()   {return this.resssource;}
    public String getNotion() {return this.notion;}
    public int        getTemps()       {return this.temps;}
    public int        getPoint()       {return this.point;}
    public String     getExplication() {return this.explication;}

    public Notion geTNotion(){ return this.tNotion;}
    public Ressource getTRessource(){ return this.tResssource;}



}