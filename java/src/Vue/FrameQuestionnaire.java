package src.Vue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import src.Controleur;
import src.Metier.Ressource;

import java.awt.GridLayout;
import java.awt.event.WindowListener;

public class FrameQuestionnaire extends JFrame implements WindowListener
{
    private static int nbFrame = 0;
    private Controleur ctrl;
    private PanelQuestionnaireTab questionnaireTab;

    /**
     * Constructeur de la class FrameQuestionnaire
     * @param ctrl          Le contrôleur
     */
    public FrameQuestionnaire( Controleur ctrl )
    {
        this.ctrl = ctrl;
        this.questionnaireTab = null;

        this.setTitle             ("QCM Builder - Nouveau Questionnaire");
        this.setSize              (1200,400);
        this.setMinimumSize       (this.getSize());
        this.setLayout            (new GridLayout(2,1));
        this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelQuestionnaire(ctrl, this));

        this.addWindowListener(this);

        setVisible(true);
    }

    public void majTab(Controleur ctrl, Ressource r, String titre, Boolean estChrono)
    {
        if (this.questionnaireTab != null)      { this.remove(this.questionnaireTab); }
        if (r != null && r.getId().equals(" ")) { r = null; }

        this.questionnaireTab = new PanelQuestionnaireTab(this,ctrl,r,titre,estChrono);
        this.add(questionnaireTab);
        this.revalidate();
    }

    public static FrameQuestionnaire creerFrameQuestionnaire(Controleur ctrl)
    {
        System.out.print(FrameQuestionnaire.nbFrame+" : FrameQuestionnaire.nbFrame");
        if(FrameQuestionnaire.nbFrame <= 0){
            FrameQuestionnaire.nbFrame += 1;
            System.out.print(FrameQuestionnaire.nbFrame+" : FrameQuestionnaire.nbFrame");
            return new FrameQuestionnaire(ctrl);
        }
        return null;
    }

    public void windowOpened     (java.awt.event.WindowEvent e) {}
    public void windowClosing    (java.awt.event.WindowEvent e)
    {
        FrameQuestionnaire.nbFrame -= 1;
        System.out.print(" ");
    }
    public void windowClosed     (java.awt.event.WindowEvent e)
    {
        FrameQuestionnaire.nbFrame -= 1;
        System.out.print(" ");
    }
    public void windowIconified  (java.awt.event.WindowEvent e) {}
    public void windowDeiconified(java.awt.event.WindowEvent e) {}
    public void windowActivated  (java.awt.event.WindowEvent e) {}
    public void windowDeactivated(java.awt.event.WindowEvent e) {}
}