package src.Vue;

import src.Controleur;
import src.Metier.Question;
import javax.swing.*;
import java.awt.event.WindowListener;

public class FrameModifQuestion extends JFrame implements WindowListener {
    private static int nbFrame = 0;

    private PanelBanque panelBanque;


    // Constructeur
    /**
     * Constructeur de la class FrameCreationQuestion
     * @param ctrl          Le contrôleur
     */
    public FrameModifQuestion( Controleur ctrl, Question q){
        this.setTitle             ("QCM Builder - Modifi de la question");
        this.setSize              (900,500);
        this.setMinimumSize       (this.getSize());
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());


        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelModifQuestion(ctrl, q) );
        this.addWindowListener(this);

        setVisible(true);
    }

    public static FrameModifQuestion creerFrameModifQuestion(Controleur ctrl, Question q){
        if(FrameModifQuestion.nbFrame == 0){
            FrameModifQuestion.nbFrame++;
            return new FrameModifQuestion(ctrl, q);
        }
        return null;
    }

    public void windowOpened     (java.awt.event.WindowEvent e) {}
    public void windowClosing    (java.awt.event.WindowEvent e) {FrameModifQuestion.nbFrame--;}
    public void windowClosed     (java.awt.event.WindowEvent e) {}
    public void windowIconified  (java.awt.event.WindowEvent e) {}
    public void windowDeiconified(java.awt.event.WindowEvent e) {}
    public void windowActivated  (java.awt.event.WindowEvent e) {}
    public void windowDeactivated(java.awt.event.WindowEvent e) {}
}