package src.Vue;

import javax.swing.*;
import src.Controleur;
import src.Metier.Notion;

import java.awt.event.WindowListener;

public class FrameBanque extends JFrame implements WindowListener {
    private static int nbFrame = 0;

    private Controleur ctrl;

    // Constructeur
    /**
     * Constructeur de la class FrameBanque
     * @param ctrl  Le contrôleur
     */
    public FrameBanque(Controleur ctrl )
    {
        this.ctrl = ctrl;

        System.out.println("Création de la frame Banque");

        this.setTitle("QCM Builder - Banque de question ");
        this.setSize(850,200);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getResource("java/data/Images/icon.png")).getImage());

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelBanque(this.ctrl) );

        setVisible(true);
    }

    public static FrameBanque creerFrameBanque(Controleur ctrl){
        if(FrameBanque.nbFrame == 0){
            FrameBanque.nbFrame++;
            return new FrameBanque(ctrl);
        }
        return null;
    }

    public void windowOpened     (java.awt.event.WindowEvent e) {}
    public void windowClosing    (java.awt.event.WindowEvent e) {FrameBanque.nbFrame--;}
    public void windowClosed     (java.awt.event.WindowEvent e) {}
    public void windowIconified  (java.awt.event.WindowEvent e) {}
    public void windowDeiconified(java.awt.event.WindowEvent e) {}
    public void windowActivated  (java.awt.event.WindowEvent e) {}
    public void windowDeactivated(java.awt.event.WindowEvent e) {}
}