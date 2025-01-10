package src.Vue;

import src.Controleur;
import src.Metier.Question;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.event.WindowListener;

public class FrameModifQuestion extends JFrame implements WindowListener
{
    private static int nbFrame = 0;

    private PanelBanque panelBanque;
    private PanelModifQuestion pnlModifQuestion;
    private Controleur ctrl;
    private Question q;

    public FrameModifQuestion(Controleur ctrl, PanelBanque panelBanque, Question q)
    {
        this.ctrl = ctrl;
        this.panelBanque = panelBanque;

        this.setTitle             ("QCM Builder - Création de la question");
        this.setSize              (900,500);
        this.setMinimumSize       (this.getSize());
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());

        this.q = q;


        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.pnlModifQuestion = new PanelModifQuestion(this, ctrl,this.panelBanque, this.q);

        this.add(pnlModifQuestion);
        this.addWindowListener(this);

        setVisible(true);
    }

    public static FrameModifQuestion creerFrameModifQuestion(Controleur ctrl, PanelBanque panelBanque, Question q)
    {
        if(FrameModifQuestion.nbFrame == 0)
        {
            FrameModifQuestion.nbFrame++;
            return new FrameModifQuestion( ctrl, panelBanque, q);
        }
        return null;
    }

    public void windowOpened     (java.awt.event.WindowEvent e)
    {
        this.pnlModifQuestion.getEditeurEnonce().setText(this.q.getEnonce());
        this.pnlModifQuestion.getEditeurExplication().setText(this.q.getExplication());
    }
    public void windowClosing    (java.awt.event.WindowEvent e) {FrameModifQuestion.nbFrame--;}
    public void windowClosed     (java.awt.event.WindowEvent e) {}
    public void windowIconified  (java.awt.event.WindowEvent e) {}
    public void windowDeiconified(java.awt.event.WindowEvent e) {}

    public void windowActivated  (java.awt.event.WindowEvent e)
    {
        this.pnlModifQuestion.getEditeurEnonce()     .setText(this.pnlModifQuestion.getTexteTxtEnonce());
        this.pnlModifQuestion.getEditeurExplication().setText(this.pnlModifQuestion.getTexteTxtExplication());
    }
    public void windowDeactivated(java.awt.event.WindowEvent e)
    {
        this.pnlModifQuestion.setTexteTxtEnonce     (this.pnlModifQuestion.getEditeurEnonce().getText());
        this.pnlModifQuestion.setTexteTxtExplication(this.pnlModifQuestion.getEditeurExplication().getText());

        this.pnlModifQuestion.getEditeurEnonce()     .setText(this.pnlModifQuestion.passageRtf(this.pnlModifQuestion.getEditeurEnonce()));
        this.pnlModifQuestion.getEditeurExplication().setText(this.pnlModifQuestion.passageRtf(this.pnlModifQuestion.getEditeurExplication()));
    }
}