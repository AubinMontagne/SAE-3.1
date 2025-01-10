package src.Vue;

import src.Controleur;
import src.Metier.Ressource;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class PanelRessource extends JPanel implements ActionListener, ListSelectionListener
{
    private Controleur       ctrl;
    private JPanel           panelRessource;
    private JButton          btnNouvRess;
    private JButton          btnSupprRess;
    private JList<Ressource> lstRessources;
    private FrameParametrage frameParametrage;

    /**
     * Constructeur de la class PanelRessource
     * @param ctrl Le contrôleur
     */
    public PanelRessource(Controleur ctrl, FrameParametrage frameParametrage)
    {
        this.ctrl             = ctrl;
        this.frameParametrage = frameParametrage;

        // Initialisation du panel principal
        this.panelRessource = new JPanel();
        this.panelRessource.setLayout(new BorderLayout());

        // Création d'un modèle de liste
        DefaultListModel<Ressource> listModel = new DefaultListModel<>();
        for (Ressource ressource : this.ctrl.getRessources()){
            listModel.addElement(ressource);
        }

        // Création des composants
        this.btnNouvRess   = new JButton("Nouvelle Ressource");
        this.btnSupprRess  = new JButton("Supprimer Ressource");
        this.lstRessources = new JList<>(listModel);
        this.lstRessources.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajout des écouteurs
        this.lstRessources.addListSelectionListener(this);
        this.btnNouvRess  .addActionListener(this);
        this.btnSupprRess .addActionListener(this);

        // Ajout de la liste dans un JScrollPane
        JScrollPane scrollPane = new JScrollPane(this.lstRessources);
        this.panelRessource.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBouton = new JPanel(new GridLayout(1, 2));

        panelBouton.add(this.btnNouvRess);
        panelBouton.add(this.btnSupprRess);

        this.panelRessource.add(panelBouton, BorderLayout.SOUTH);
        this.add(this.panelRessource);
    }

    // Methode
    /**
     * Methode valueChanged
     * @param e L'évènement qui caractérise le changement
     */
    public void valueChanged(ListSelectionEvent e)
    {
        if (!e.getValueIsAdjusting())
        {
            Ressource selectedRessource = this.lstRessources.getSelectedValue();
            if (selectedRessource != null)
            {
                this.frameParametrage.majPanelNotion();
            }
        }
    }

    /**
     * Methode actionPerformed
     * @param e L'évènement à traiter
     */
    public void actionPerformed(ActionEvent e)
    {
        if (this.btnNouvRess == e.getSource())
        {
            FrameCreationRessource.creerFrameCreationRessource(this.ctrl,this);
            this.maj();
        }
        if (this.btnSupprRess == e.getSource())
        {
            Ressource ressource = this.lstRessources.getSelectedValue();
            if (ressource != null)
            {
                this.ctrl.supprimerRessource(ressource);
                this.maj();
            }
        }
    }

    /**
     * Methode maj
     */
    public void maj()
    {
        DefaultListModel<Ressource> listModel = new DefaultListModel<>();
        for (Ressource ressource : this.ctrl.getRessources() )
        {
            listModel.addElement(ressource);
        }
        this.lstRessources.setModel(listModel);
    }
    public Ressource getRessourceSelectionnee(){
        return this.lstRessources.getSelectedValue();
    }
}