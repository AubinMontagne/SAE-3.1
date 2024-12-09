package Vue;

import src.Controleur;
import Metier;
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
        this.setLayout ( new BorderLayout() );

        String[] tabEntetes = {"Question", "Ressource", "Notion"};

        /*Mettre faire un for each qau&nd on aura une liste de questions dans controleur
        * et creer une JTable de avec intitulée, la resssource et la notion*/



        this.setVisible(true);


    }
}