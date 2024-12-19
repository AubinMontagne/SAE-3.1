package src.Vue;

import javax.swing.*;
import src.Controleur;

import java.awt.event.WindowListener;

public class FrameBanque extends JFrame implements WindowListener {
    private static int nbFrame = 0;

    // Constructeur
    /**
     * Constructeur de la class FrameBanque
     */
    public FrameBanque(Controleur ctrl )
    {
        System.out.println("Création de la frame Banque");

        this.setTitle("QCM Builder - Banque de question ");
        this.setSize(850,200);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelBanque(ctrl) );

        setVisible(true);
    }

    public static FrameBanque creerFrameBanque(Controleur ctrl){
        if(FrameBanque.nbFrame == 0){
            FrameBanque.nbFrame++;
            return new FrameBanque(ctrl);
        }
        return null;
        }




    public void windowOpened     (WindowEvent e) {}
    public void windowClosing    (WindowEvent e)  {FrameBanque.nbFrame--;}
    public void windowClosed     (WindowEvent e) {}
    public void windowIconified  (WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated  (WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
}