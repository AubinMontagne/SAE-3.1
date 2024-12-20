package src.Vue;

import src.Controleur;

import javax.swing.*;
import java.awt.event.WindowListener;

public class FrameCreationQuestion extends JFrame implements WindowListener {
    private static int nbFrame = 0;

    private PanelBanque panelBanque;

    private Controleur ctrl;

    // Constructeur
    /**
     * Constructeur de la class FrameCreationQuestion
     * @param ctrl          Le contrôleur
     */
    public FrameCreationQuestion( Controleur ctrl){
        this.ctrl = ctrl;

        this.setTitle             ("QCM Builder - Création de la question");
        this.setSize              (900,500);
        this.setMinimumSize       (this.getSize());
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());


        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelCreationQuestion(ctrl,null) );
        this.addWindowListener(this);

        setVisible(true);
    }

    public static FrameCreationQuestion creerFrameCreationQuestion(Controleur ctrl){
        if(FrameCreationQuestion.nbFrame == 0){
            FrameCreationQuestion.nbFrame++;
            return new FrameCreationQuestion(ctrl);
        }
        return null;
    }

    public FrameCreationQuestion( Controleur ctrl, PanelBanque panelBanque){
        this.ctrl = ctrl;
        this.panelBanque = panelBanque;

        this.setTitle             ("QCM Builder - Création de la question");
        this.setSize              (900,500);
        this.setMinimumSize       (this.getSize());
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());


        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelCreationQuestion(ctrl,this.panelBanque) );
        this.addWindowListener(this);

        setVisible(true);
    }

    public static FrameCreationQuestion creerFrameCreationQuestion(Controleur ctrl, PanelBanque panelBanque){
        if(FrameCreationQuestion.nbFrame == 0){
            FrameCreationQuestion.nbFrame++;
            return new FrameCreationQuestion(ctrl,panelBanque);
        }
        return null;
    }

    public void windowOpened     (java.awt.event.WindowEvent e) {}
    public void windowClosing    (java.awt.event.WindowEvent e) {FrameCreationQuestion.nbFrame--;}
    public void windowClosed     (java.awt.event.WindowEvent e) {}
    public void windowIconified  (java.awt.event.WindowEvent e) {}
    public void windowDeiconified(java.awt.event.WindowEvent e) {}
    public void windowActivated  (java.awt.event.WindowEvent e) {}
    public void windowDeactivated(java.awt.event.WindowEvent e) {}
}