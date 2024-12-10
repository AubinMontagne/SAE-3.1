package Metier;

import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.rtf.RTFEditorKit;

public abstract class Question
{
    private Metier     metier;
    private String     intitule;
    private Difficulte difficulte;
    private Notion     notion;
    private int        temps;
    private int        point;
    private String     explication;
	
	/**
     * Constructeur de la classe Question.
     *
     * @param intitule      L'intitulé de la question.
     * @param difficulte    La difficulté de la question, qui peut être : très facile, facile, moyen, difficile.
     * @param notion        La notion concernée par la question.
     * @param temps         Le temps nécessaire pour répondre à la question en millisecondes.
     * @param point         Le nombre de points que rapporte la question.
	 * @param metier        Le métier concerné par la question.
     */
    public Question(String intitule, Difficulte difficulte,Notion notion,int temps, int point, Metier metier)
    {
        this.intitule   = intitule;
        this.difficulte = difficulte;
        this.notion     = notion;
        this.temps      = temps;
        this.point      = point;
        this.metier     = metier;
    }

    /**
     * Constructeur de la classe Question.
     *
     * @param intitule      L'intitulé de la question.
     * @param difficulte    La difficulté de la question, qui peut être : très facile, facile, moyen, difficile.
     * @param notion        La notion concernée par la question.
     * @param temps         Le temps nécessaire pour répondre à la question en secondes.
     * @param point         Le nombre de points que rapporte la question.
	 * @param metier        Le métier concerné par la question.
     * @param explication   Les explications fournies avec la réponse à la question.
     */

    public Question(String intitule, Difficulte difficulte,Notion notion,int temps, int point , Metier metier,String explication)
    {
        this.intitule    = intitule;
        this.difficulte  = difficulte;
        this.notion      = notion;
        this.temps       = temps;
        this.metier      = metier;
        this.explication = explication;
        this.point       = point;
    }

    public String getAsData() {
        String fileName = this.intitule.replaceAll("\\s+", "_") + ".rtf";
        try {
            DefaultStyledDocument doc = new DefaultStyledDocument();
            StyleContext context = new StyleContext();
            Style style = context.addStyle("Style", null);

            // Ajouter l'intitulé de la question
            style.addAttribute(StyleConstants.FontFamily, "Serif");
            style.addAttribute(StyleConstants.Bold, true);
            doc.insertString(doc.getLength(), "Intitulé: " + this.intitule + "\n", style);

            // Ajouter la difficulté
            style = context.addStyle("Style", null);
            style.addAttribute(StyleConstants.Italic, true);
            doc.insertString(doc.getLength(), "Difficulté: " + this.difficulte + "\n", style);

            // Ajouter la notion
            doc.insertString(doc.getLength(), "Notion: " + this.notion + "\n", style);

            // Ajouter le temps
            doc.insertString(doc.getLength(), "Temps: " + this.temps + " secondes\n", style);

            // Ajouter les points
            doc.insertString(doc.getLength(), "Points: " + this.point + "\n", style);

            // Ajouter l'explication
            doc.insertString(doc.getLength(), "Explication: " + this.explication + "\n", style);

            // Écrire le document dans un fichier RTF
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                RTFEditorKit rtfKit = new RTFEditorKit();
                rtfKit.write(fos, doc, 0, doc.getLength());
            }
        } catch (IOException | BadLocationException e) {
            e.printStackTrace();
        }
        return fileName;
    }/*
    public static Question getAsInstance(String fileName, Metier metier) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            DefaultStyledDocument doc = new DefaultStyledDocument();
            RTFEditorKit rtfKit = new RTFEditorKit();
            rtfKit.read(fis, doc, 0);

            String content = doc.getText(0, doc.getLength());
            String[] lines = content.split("\n");

            String intitule = lines[0].split(": ")[1];
            Difficulte difficulte = Difficulte.valueOf(lines[1].split(": ")[1]);
            Notion notion = metier.getNotionById(Integer.parseInt(lines[2].split(": ")[1]));
            int temps = Integer.parseInt(lines[3].split(": ")[1].split(" ")[0]);
            int point = Integer.parseInt(lines[4].split(": ")[1]);
            String explication = lines[5].split(": ")[1];

            return new Question(intitule, difficulte, notion, temps, point, metier, explication);
        } catch (IOException | BadLocationException e) {
            e.printStackTrace();
        }
        return null;
    }*/

	// Get
    public String     getIntitule()    {return intitule;}
    public Difficulte getDifficulte()  {return this.difficulte;}
    public Notion     getNotion()      {return this.notion;}
    public int        getTemps()       {return this.temps;}
    public int        getPoint()       {return this.point;}
    public String     getExplication() {return this.explication;}
    
	// Set
    public  void setIntitule(String intitule)          {this.intitule = intitule;}
    private void setDifficulte(Difficulte difficulte) {this.difficulte = difficulte;}
    private void setNotion(Notion notion)             {this.notion = notion;}
    private void setTemps(int temps)                  {this.temps = temps;}
    private void setPoint(int point)                  {this.point = point;}
    private void setExplication(String explication)   {this.explication = explication;}
}