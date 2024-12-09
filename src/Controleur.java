import Metier.*;
import Vue.*;
public class Controleur
{
    private Metier metier;
    private FrameBanque frameBanque;
    private FrameParam frameParam;


    public Controleur()
    {
        this.metier = new Metier();
    }

    // Get

    public Metier getMetier()
    {
        return this.metier;
    }

    // Set

    public void setMetier(Metier metier)
    {
        this.metier = metier;
    }
}
private class RTFWriterExample {
    public static void main(String[] args) {
        // Créer un document
        DefaultStyledDocument doc = new DefaultStyledDocument();
        StyleContext context = new StyleContext();
        Style style = context.addStyle("Style", null);

        // Ajouter du texte avec style
        try {
            style.addAttribute(StyleConstants.FontFamily, "Serif");
            style.addAttribute(StyleConstants.Bold, true);
            doc.insertString(doc.getLength(), "Texte en gras\n", style);

            style = context.addStyle("Style", null);
            style.addAttribute(StyleConstants.Italic, true);
            doc.insertString(doc.getLength(), "Texte en italique\n", style);

            style = context.addStyle("Style", null);
            style.addAttribute(StyleConstants.Foreground, java.awt.Color.RED);
            doc.insertString(doc.getLength(), "Texte en rouge\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        // Écrire le document dans un fichier RTF
        try (FileOutputStream fos = new FileOutputStream("output.rtf")) {
            RTFEditorKit rtfKit = new RTFEditorKit();
            rtfKit.write(fos, doc, 0, doc.getLength());
        } catch (IOException | BadLocationException e) {
            e.printStackTrace();
        }
    }
}