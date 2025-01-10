package src.Vue;

import src.Controleur;
import src.Metier.Notion;
import src.Metier.Ressource;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.event.WindowListener;

public class FrameCreationQuestion extends JFrame implements WindowListener
{
    private static int nbFrame = 0;


    private PanelBanque panelBanque;

    private PanelCreationQuestion pnlCreationQuestion;

    private Controleur ctrl;

    /**
     * Constructeur de la class FrameCreationQuestion
     * @param ctrl          Le contrôleur
     */
    public FrameCreationQuestion( Controleur ctrl)
    {
        this.ctrl = ctrl;

        this.setTitle             ("QCM Builder - Création de la question");
        this.setSize              (1100,500);
        this.setMinimumSize       (this.getSize());
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());


        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.pnlCreationQuestion = new PanelCreationQuestion(this, ctrl, null, null, null);

        this.add(this.pnlCreationQuestion);
        this.addWindowListener(this);

        setVisible(true);
    }

    public static FrameCreationQuestion creerFrameCreationQuestion(Controleur ctrl)
    {
        if(FrameCreationQuestion.nbFrame == 0){
            FrameCreationQuestion.nbFrame++;
            return new FrameCreationQuestion( ctrl );
        }
        return null;
    }

    public FrameCreationQuestion( Controleur ctrl, PanelBanque panelBanque, Ressource ressource, Notion notion)
    {
        this.ctrl = ctrl;
        this.panelBanque = panelBanque;

        this.setTitle             ("QCM Builder - Création de la question");
        this.setSize              (900,500);
        this.setMinimumSize       (this.getSize());
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());


        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.pnlCreationQuestion = new PanelCreationQuestion(this, ctrl,this.panelBanque, ressource, notion);

        this.add(this.pnlCreationQuestion);

        this.addWindowListener(this);

        setVisible(true);
    }

    public static FrameCreationQuestion creerFrameCreationQuestion(Controleur ctrl, PanelBanque panelBanque, Ressource ressource, Notion notion)
    {
        if(FrameCreationQuestion.nbFrame == 0)
        {
            FrameCreationQuestion.nbFrame += 1;
            System.out.print(" ");
            return new FrameCreationQuestion( ctrl, panelBanque, ressource, notion);
        }
        return null;
    }

    public void windowOpened     (java.awt.event.WindowEvent e) {}
    public void windowClosing    (java.awt.event.WindowEvent e)
    {
        FrameCreationQuestion.nbFrame -= 1;
        System.out.print(" ");
    }
    public void windowClosed     (java.awt.event.WindowEvent e) {}
    public void windowIconified  (java.awt.event.WindowEvent e) {}
    public void windowDeiconified(java.awt.event.WindowEvent e) {}

    public void windowActivated  (java.awt.event.WindowEvent e)
    {
        this.pnlCreationQuestion.getEditeurEnonce()     .setText(this.pnlCreationQuestion.getTexteTxtEnonce());
        this.pnlCreationQuestion.getEditeurExplication().setText(this.pnlCreationQuestion.getTexteTxtExplication());
    }
    public void windowDeactivated(java.awt.event.WindowEvent e)
    {
        this.pnlCreationQuestion.setTexteTxtEnonce     (this.pnlCreationQuestion.getEditeurEnonce()     .getText());
        this.pnlCreationQuestion.setTexteTxtExplication(this.pnlCreationQuestion.getEditeurExplication().getText());

        this.pnlCreationQuestion.getEditeurEnonce()     .setText(this.pnlCreationQuestion.passageRtf(this.pnlCreationQuestion.getEditeurEnonce()));
        this.pnlCreationQuestion.getEditeurExplication().setText(this.pnlCreationQuestion.passageRtf(this.pnlCreationQuestion.getEditeurExplication()));
    }
}