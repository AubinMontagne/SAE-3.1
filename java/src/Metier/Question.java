package src.Metier;

import javax.swing.JEditorPane;
import javax.swing.text.EditorKit;
import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.io.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    public Question(String dossierChemin, Difficulte difficulte,Notion notion,int temps, int point, String imageChemin, int id)
    {
        this.dossierChemin   = dossierChemin;
        this.difficulte      = difficulte;
        this.notion          = notion;
        this.temps           = temps;
        this.point           = point;
        this.imageChemin     = imageChemin;
        this.listeFichiers   = new ArrayList<>();
        this.id              = id;
    }

	// Get
    public String   getDossierChemin()       {return dossierChemin;}
    public String   getEnonceText() {
        File file = new File(this.dossierChemin + "/enonce.rtf");

        try {
            JEditorPane p = new JEditorPane();
            p.setContentType("text/rtf");

            EditorKit kitRtf = p.getEditorKitForContentType("text/rtf");
            kitRtf.read(new FileReader(file), p.getDocument(), 0);
            kitRtf = null;

            EditorKit kitHtml = p.getEditorKitForContentType("text/html");
            Writer writer = new StringWriter();
            kitHtml.write(writer, p.getDocument(), 0, p.getDocument().getLength());

            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getEnonceRTF(){
        String filePath = this.dossierChemin + "/enonce.rtf";
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    public String getExplicationText(){
        File file = new File(this.dossierChemin + "/explication.rtf");

        try {
            JEditorPane p = new JEditorPane();
            p.setContentType("text/rtf");

            EditorKit kitRtf = p.getEditorKitForContentType("text/rtf");
            kitRtf.read(new FileReader(file), p.getDocument(), 0);
            kitRtf = null;

            EditorKit kitHtml = p.getEditorKitForContentType("text/html");
            Writer writer = new StringWriter();
            kitHtml.write(writer, p.getDocument(), 0, p.getDocument().getLength());

            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getExplicationRTF(){
        String filePath = this.dossierChemin + "/explication.txt";
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    public Difficulte getDifficulte()         {return this.difficulte;}
    public Notion     getNotion()             {return this.notion;}
    public int        getTemps()              {return this.temps;}
    public int        getPoint()              {return this.point;}
    public List<String> getListeFichiers()    {return this.listeFichiers;}
    public String     getImageChemin()        {return this.imageChemin;}
    public int        getId()                 {return this.id;}

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