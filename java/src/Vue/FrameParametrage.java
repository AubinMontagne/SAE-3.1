package src.Vue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import src.Controleur;

import java.awt.BorderLayout;
import java.awt.event.WindowListener;

public class  FrameParametrage extends JFrame implements WindowListener
{
    private static int nbFrame = 0;
	private Controleur     ctrl;
    private PanelNotion    panelNotion;
    private PanelRessource panelRessource;

    /**
     * Contructeur de la class FrameRessource
     * @param ctrl  Le contrôleur
     */
	public FrameParametrage(Controleur ctrl)
    {
        this.ctrl = ctrl;

        this.setTitle             ("Les Ressources");
        this.setSize              (650,275);
        this.setMinimumSize       (this.getSize());
        this.setLocationRelativeTo(null);
        this.setLayout            (new BorderLayout());
        this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.panelRessource = new PanelRessource(ctrl,this);
        this.panelNotion    = new PanelNotion(ctrl   ,null);

        this.add(this.panelRessource, BorderLayout.WEST );
        this.add(this.panelNotion   , BorderLayout.EAST );

        this.addWindowListener(this);

        setVisible(true);
    }
    public void majPanelNotion()
    {
        this.panelNotion.setRessourceSelectionnee(this.panelRessource.getRessourceSelectionnee());
        this.panelNotion.maj();
    }

    public static FrameParametrage creerFrameFrameParametrage(Controleur ctrl)
    {
        if(FrameParametrage.nbFrame == 0){
            FrameParametrage.nbFrame++;
            System.out.print(" ");
            return new FrameParametrage(ctrl);
        }
        return null;
    }

    public void windowOpened     (java.awt.event.WindowEvent e) {}
    public void windowClosing    (java.awt.event.WindowEvent e)
    {
        FrameParametrage.nbFrame--;
        System.out.print(" ");
    }
    public void windowClosed     (java.awt.event.WindowEvent e) {}
    public void windowIconified  (java.awt.event.WindowEvent e) {}
    public void windowDeiconified(java.awt.event.WindowEvent e) {}
    public void windowActivated  (java.awt.event.WindowEvent e) {}
    public void windowDeactivated(java.awt.event.WindowEvent e) {}
}