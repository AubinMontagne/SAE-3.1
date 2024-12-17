package src.Vue;

import src.Metier.Notion;
import src.Metier.Ressource;
import src.Controleur;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;

public class PanelNotion extends JPanel implements ActionListener , ListSelectionListener
{	
    private JPanel            panelNotion;
    private JButton           btnNouvNotion;
    private JButton           btnSupprNotion;
    private JList<Notion>     list;
    private Ressource         ressource;
    private ArrayList<Notion> notions;
    private Controleur        ctrl;
  
    // Constructeur

    /**
     * Constructeur de la class PanelNotion
     * @param ctrl  Le controleur
     * @param r     La ressource
     */
    public PanelNotion( Controleur ctrl,Ressource r){
        this.ctrl        = ctrl;
        this.ressource   = r;
        this.notions     = ctrl.getNotions();
        this.panelNotion = new JPanel();
        this.panelNotion.setLayout(new BorderLayout());

        // Création d'un modèle de liste
        DefaultListModel<Notion> listModel = new DefaultListModel<>();
        for (Notion notion : this.notions) {
            if( notion.getRessourceAssociee() == this.ressource)
                listModel.addElement(notion);
        }

        // Création des composants
        JLabel labTitre = new JLabel (this.ressource.toString());

        this.btnNouvNotion = new JButton("Nouvelle Notion");
        this.btnSupprNotion= new JButton("Supprimer Notion");

        this.list = new JList<>(listModel);
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajout des écouteurs
        this.list.addListSelectionListener   (this);
        this.btnNouvNotion.addActionListener (this);
        this.btnSupprNotion.addActionListener(this);

        // Ajout de la liste dans un JScrollPane
        JScrollPane scrollPane = new JScrollPane(this.list);
        this.panelNotion.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBouton = new JPanel();
        panelNotion.add(panelBouton, BorderLayout.SOUTH);
        panelBouton.add(this.btnNouvNotion );
        panelBouton.add(this.btnSupprNotion);

        this.panelNotion.add(labTitre, BorderLayout.NORTH);

        // Ajout du panel à la fenêtre
        this.setVisible(true);
        this.add(this.panelNotion);
    }

    // Methode

    /**
     * Methode maj
     */
    public void maj(){
        DefaultListModel<Notion> listModel = new DefaultListModel<>();
        for (Notion notion : this.notions) {
            if( notion.getRessourceAssociee() == this.ressource)
                listModel.addElement(notion);
        }
        this.list.setModel(listModel);
    }

    /**
     * Methode valueChanged
     * @param e L'évènement qui caractérise le changement
     */
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Notion selectedNotion = this.list.getSelectedValue();
            if (selectedNotion != null) {
                new FrameBanque(selectedNotion, ctrl);
            }
        }
    }

    /**
     * Methode actionPerformed
     * @param e L'évènement à traiter
     */
    public void actionPerformed(ActionEvent e) {
        if (btnNouvNotion == e.getSource()) {
            new FrameCreationNotion(this.ctrl, this.ressource, this);
        }
        if (btnSupprNotion == e.getSource()) {
            new FrameSuppressionNotion(this.ctrl, this.ressource, this);
        }
    }
        
}
    