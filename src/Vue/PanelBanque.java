package Vue;

import src.Controleur;
import Metier.Question;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;


public class PanelBanque extends JPanel
{
    private Controleur ctrl;
    private JPanel panelBanque;
    private JTable tbQuestion;


    public PanelBanque( Controleur ctrl){
        this.ctrl         = ctrl;
        this.panelAccueil = new JPanel();
        this.setLayout ( new BorderLayout);

        String[] tabEntetes

        this.setVisible(true);


    }
}