package src.Metier;

import javax.swing.JEditorPane;
import javax.swing.text.EditorKit;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

        this.sauvegarderQuestion();
    }

    private  void sauvegarderQuestion()
    {
        File fileComplement = new File("java/data/"+ this.notion.getRessourceAssociee().getNom() + "/" + this.notion + "/Question " + this.id  + "/Compléments");

        fileComplement.mkdirs();
    }

    public boolean supprimerQuestionDossier()
    {
        File dossier = new File("java/data/"+ this.notion.getRessourceAssociee().getNom() + "/" + this.notion + "/Question " + this.id);

        if(dossier.exists())
            if(dossier.delete())
            {
                System.out.println("Test");
                return true;
            }
        return false;
    }

    public static boolean sauvegarderFichier(String chemin, JEditorPane editeur)
    {
        try{
            File fichier = new File(chemin);

            FileWriter writer = new FileWriter(fichier);

            if(editeur != null && editeur.getText() != null)
            {
                String txtVersRtf = "{\\rtf1\\ansi\\ansicpg1252\\deff0\\nouicompat\\deflang1033{\\fonttbl{\\f0\\fnil\\fcharset0 Arial;}}\\viewkind4\\uc1\\pard\\sa200\\sl276\\slmult1\\f0\\fs22" +
                        editeur.getText() + "\\par}";

                writer.write(txtVersRtf);
            }


            writer.close();
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

	// Get
    public String   getDossierChemin()       {return dossierChemin;}
    public String   getEnonce(){
        File file = new File(this.dossierChemin + "/enonce.rtf");

        try {
            JEditorPane p = new JEditorPane();
            p.setContentType("text/rtf");

            EditorKit kitRtf = p.getEditorKitForContentType("text/rtf");
            kitRtf.read(new FileReader(file), p.getDocument(), 0);

            EditorKit kitHtml = p.getEditorKitForContentType("text/txt");
            Writer writer = new StringWriter();
            kitHtml.write(writer, p.getDocument(), 0, p.getDocument().getLength());

            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getExplication(){
        File file = new File(this.dossierChemin + "/explication.rtf");

        try {
            JEditorPane p = new JEditorPane();
            p.setContentType("text/rtf");

            EditorKit kitRtf = p.getEditorKitForContentType("text/rtf");
            kitRtf.read(new FileReader(file), p.getDocument(), 0);

            EditorKit kitHtml = p.getEditorKitForContentType("text/txt");
            Writer writer = new StringWriter();
            kitHtml.write(writer, p.getDocument(), 0, p.getDocument().getLength());

            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getEnonceText() {
        File file = new File(this.dossierChemin + "/enonce.rtf");

        try {
            JEditorPane p = new JEditorPane();
            p.setContentType("text/rtf");

            EditorKit kitRtf = p.getEditorKitForContentType("text/rtf");
            kitRtf.read(new FileReader(file), p.getDocument(), 0);

            EditorKit kitHtml = p.getEditorKitForContentType("text/html");
            Writer writer = new StringWriter();
            kitHtml.write(writer, p.getDocument(), 0, p.getDocument().getLength());

            String res = writer.toString();

            res = res.substring(res.indexOf("<style>"), res.indexOf("</style>")+8) + res.substring(res.indexOf("<p"), res.indexOf("</p>") + 4);

            return res;
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

            String res = writer.toString();

            res = res.substring(res.indexOf("<style>"), res.indexOf("</style>")+8) + res.substring(res.indexOf("<p"), res.indexOf("</p>") + 4);
            res = res.replaceAll("\\r?\\n", " ");

            return res;
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
    public void ajouterFichier    (String fichier){this.listeFichiers.add(fichier);}
    public int  setId             (int id)                 {return this.id       = id;}

	public String toString(){
		return "Difficulté: " + this.difficulte + "\n" +
			   this.notion.toString() + "\n" +
			   "Temps: " + this.temps + " secondes\n" +
			   "Points: " + this.point + "\n";
	}
}