package src.Vue;

import src.Controleur;
import src.Metier.Question;
import javax.swing.*;
import java.awt.event.WindowListener;
import java.util.HashMap;

public class FrameModifQuestion extends JFrame implements WindowListener {
    private static int nbFrame = 0;
    private static HashMap<String, Boolean>  hmReponsesQCM     = null;
    private static HashMap<String, Double[]> hmReponsesElimRep = null;
    private static HashMap<String, String>   hmReponsesAssoElt = null;

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

        if(hmReponsesQCM != null){
            HashMap<String, Boolean> hmReponses = hmReponsesQCM;
            this.add(new PanelModifQCM(ctrl, q, hmReponses) );
        } else if ( hmReponsesElimRep != null ){
            HashMap<String, Double[]> hmReponses = hmReponsesElimRep;
            this.add(new PanelModifElimination(ctrl, q, hmReponses) );
        } else {
            HashMap<String, String> hmReponses = hmReponsesAssoElt;
            this.add(new PanelModifAssociation(ctrl, q, hmReponses) );
        }
        this.addWindowListener(this);

        setVisible(true);
    }

    public static FrameModifQuestion creerFrameModifQCM(Controleur ctrl, Question q, HashMap<String, Boolean> hmReponses){
        if(FrameModifQuestion.nbFrame == 0){
            FrameModifQuestion.hmReponsesQCM = hmReponses;
            FrameModifQuestion.nbFrame++;
            return new FrameModifQuestion(ctrl, q);
        }
        return null;
    }

    public static FrameModifQuestion creerFrameModifElimRep(Controleur ctrl, Question q, HashMap<String, Double[]> hmReponses){
        if(FrameModifQuestion.nbFrame == 0){
            FrameModifQuestion.hmReponsesElimRep = hmReponses;
            FrameModifQuestion.nbFrame++;
            return new FrameModifQuestion(ctrl, q);
        }
        return null;
    }

    public static FrameModifQuestion creerFrameModifAssoElt(Controleur ctrl, Question q, HashMap<String, String> hmReponses){
        if(FrameModifQuestion.nbFrame == 0){
            FrameModifQuestion.hmReponsesAssoElt = hmReponses;
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