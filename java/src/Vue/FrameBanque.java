package src.Vue;

import javax.swing.*;
import src.Controleur;
import src.Metier.Notion;

import java.awt.event.WindowListener;

public class FrameBanque extends JFrame implements WindowListener {
    private static int nbFrame = 0;

    private Controleur ctrl;
    private Notion     notion;


    // Constructeur
    /**
     * Constructeur de la class FrameBanque
     * @param ctrl  Le contrôleur
     */
	public FrameBanque(Controleur ctrl )
    {
        this.ctrl = ctrl;

        this.setTitle("Banque de question ");
        this.setSize(850,200);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelBanque(this.ctrl) );
        this.addWindowListener(this);

        setVisible(true);
    }

    public static FrameBanque creerFrameBanque(Controleur ctrl){
        if(FrameBanque.nbFrame == 0){
            FrameBanque.nbFrame++;
            return new FrameBanque(ctrl);
        }
        return null;
    }

    /**
     * Constructeur de la class FrameBanque
     * @param notion La notion
     * @param ctrl   Le contrôleur
     */
    public FrameBanque( Controleur ctrl, Notion notion ){
        this.ctrl   = ctrl;
        this.notion = notion;

        System.out.println("Création de la frame Banque");

        this.setTitle("Banque de question de la notion "+ notion.getNom());
        this.setSize(850,200);
        this.setMinimumSize(this.getSize());
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelBanque(this.notion, this.ctrl) );

        setVisible(true);
    }

    public static FrameBanque creerFrameBanque(Controleur ctrl, Notion notion){
        if(FrameBanque.nbFrame == 0){
            FrameBanque.nbFrame++;
            return new FrameBanque(ctrl, notion);
        }
        return null;
    }

    public void windowOpened(java.awt.event.WindowEvent e) {
    }
    public void windowClosing(java.awt.event.WindowEvent e) {
        FrameBanque.nbFrame--;
    }
    public void windowClosed(java.awt.event.WindowEvent e) {
    }
    public void windowIconified(java.awt.event.WindowEvent e) {
    }
    public void windowDeiconified(java.awt.event.WindowEvent e) {
    }
    public void windowActivated(java.awt.event.WindowEvent e) {
    }
    public void windowDeactivated(java.awt.event.WindowEvent e) {
    }
}