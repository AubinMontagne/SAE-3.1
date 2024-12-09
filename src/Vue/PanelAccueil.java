package Vue;

import src.Controleur;

import javax.swing;

public class PanelAccueil extends JPanel
{
    private Controleur ctrl;
    private JPanel panelAccueil;

    public PanelAccueil( Controleur ctrl){
        this.ctrl = ctrl;
        this.panelAccueil = new JPanel();
        this.setVisible(true);
    }

}