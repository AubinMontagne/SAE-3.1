package src.Vue;

import src.Controleur;
import src.Metier.Ressource;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class PanelRessource extends JPanel implements ActionListener, ListSelectionListener {
    private Controleur       ctrl;
    private JPanel           panelRessource;
    private JButton          btNouvRess;
    private JButton          btSupprRess;
    private JList<Ressource> list;

    public PanelRessource(Controleur ctrl) {
        this.ctrl           = ctrl;
        // Initialisation du panel principal
        this.panelRessource = new JPanel();
        this.panelRessource.setLayout(new BorderLayout());

        // Création d'un modèle de liste
        DefaultListModel<Ressource> listModel = new DefaultListModel<>();
        for (Ressource ressource : this.ctrl.getRessources()){
            listModel.addElement(ressource);
        }

        // Création des composants
        this.btNouvRess = new JButton("Nouvelle Ressource");
        this.list       = new JList<>(listModel);
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajout des écouteurs
        this.list.addListSelectionListener(this);
        this.btNouvRess.addActionListener(this);

        // Ajout de la liste dans un JScrollPane
        JScrollPane scrollPane = new JScrollPane(this.list);
        this.panelRessource.add(scrollPane, BorderLayout.CENTER);
        this.panelRessource.add(btNouvRess, BorderLayout.SOUTH);

        // Ajout du panel à la fenêtre
        this.add(this.panelRessource);
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Ressource selectedRessource = this.list.getSelectedValue();
            if (selectedRessource != null) {
                new FrameNotion(this.ctrl, selectedRessource);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (btNouvRess == e.getSource()) {
            System.out.println("La frame CreaRessource s'ouvre");
            new FrameCreationRessource(this.ctrl, this);
        }
    }

    public void maj(){
        DefaultListModel<Ressource> listModel = new DefaultListModel<>();
        for (Ressource ressource : this.ctrl.getRessources()){
            listModel.addElement(ressource);
        }
        this.list.setModel(listModel);
    }
}