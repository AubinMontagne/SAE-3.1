package Vue;

import src.Controleur;
import Metier;
import Metier.Question;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;


public class PanelBanque extends JPanel
{
    //private Controleur ctrl;
    private ArrayList<String> questions;
    private JPanel panelBanque;
    private JTable tbQuestion;


    public PanelBanque( /*Controleur ctrl*/){
        //this.ctrl         = ctrl;
        this.panelAccueil = new JPanel();
        this.setLayout ( new BorderLayout() );

        // En attendant d'avoir la liste des questions
        this.question = new ArrayList<Question>();


        String[] tabEntetes = {"Question", "Ressource", "Notion"};

        /*Mettre faire un for each quand on aura une liste de questions dans controleur
        * et creer une JTable de avec intitulée, la resssource et la notion*/



        this.setVisible(true);


    }
}