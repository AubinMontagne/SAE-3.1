package src.Vue;

import src.Metier.Notion;
import src.Metier.Ressource;
import src.Controleur;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;

public class PanelNotion extends JPanel implements ActionListener , ListSelectionListener
{	
    private JPanel panelNotion;
    private JButton btNouvNotion;
    private JList<Notion> list;
    private Ressource ressource;
    private ArrayList<Notion> notions;
    private Controleur ctrl;
  
    
    public PanelNotion( Controleur ctrl,Ressource r){
        this.ctrl        = ctrl;
        this.ressource = r;
        this.notions = ctrl.getNotions();
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
        this.btNouvNotion = new JButton("Nouvelle Notion");
        this.list = new JList<>(listModel);
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajout des écouteurs
        this.list.addListSelectionListener(this);
        this.btNouvNotion.addActionListener(this);

        // Ajout de la liste dans un JScrollPane
        JScrollPane scrollPane = new JScrollPane(this.list);
        this.panelNotion.add(scrollPane, BorderLayout.CENTER);
        this.panelNotion.add(btNouvNotion, BorderLayout.SOUTH);
        this.panelNotion.add(labTitre, BorderLayout.NORTH);

        // Ajout du panel à la fenêtre
        this.add(this.panelNotion);
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Notion selectedNotion = this.list.getSelectedValue();
            if (selectedNotion != null) {
                new FrameBanque(selectedNotion, ctrl);
            }
        }
    }
    public void actionPerformed(ActionEvent e) {
        if (btNouvNotion == e.getSource()) {
            new FrameCreationNotion(this.ctrl, this.ressource);
        }
    }
        
}
    