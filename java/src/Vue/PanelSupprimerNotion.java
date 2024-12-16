package src.Vue;

import src.Controleur;
import src.Metier.Notion;
import src.Metier.Ressource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class PanelSupprimerNotion extends JPanel implements ActionListener, ItemListener {
    private JPanel  panelSupprRess;
    private JButton boutonConfirmer;
    private Controleur ctrl;
    private Notion notionChoisie;
    private JComboBox<Notion> mdRessources;
    private FrameSuppressionNotion frameSuppressionNotion;
    private PanelNotion panelNotion;

    public PanelSupprimerNotion(Controleur crtl, Ressource r, PanelNotion panelNotion, FrameSuppressionNotion frameSuppressionNotion) {
        Ressource ressource = r;

        this.notionChoisie = null;
        this.panelNotion = panelNotion;
        this.frameSuppressionNotion = frameSuppressionNotion;
        this.panelSupprRess = new JPanel();
        this.panelSupprRess.setLayout(new BorderLayout());
        this.ctrl = crtl;

        UIManager.put("Label.font", new Font("Arial", Font.BOLD, 11));

        // Section des données
        JPanel panelConfiguration = new JPanel(new GridLayout(2, 1, 5, 5));
        panelConfiguration.setBorder(BorderFactory.createTitledBorder("Notion"));

        mdRessources = new JComboBox<>(ctrl.getNotionsParRessource(r).toArray(new Notion[0]));

        boutonConfirmer = new JButton("Confirmer");
        boutonConfirmer.setEnabled(true);
        panelConfiguration.add(mdRessources);

        add(panelConfiguration, BorderLayout.CENTER);
        add(boutonConfirmer,    BorderLayout.SOUTH );

        // Ajouter les ItemsListener
        mdRessources.addItemListener(this);

        // Ajouter un ActionListener au bouton Confirmer
        boutonConfirmer.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonConfirmer) {

            // Suppression de la notion
            this.ctrl.supprimerNotion(this.notionChoisie);

            // Afficher une pop-up avec les informations de la ressource
            JOptionPane.showMessageDialog(this, "Notion supprimer:\nNom : " + this.notionChoisie.getNom() + "\nRessource associée : " + this.notionChoisie.getRessourceAssociee(), "Confirmation", JOptionPane.INFORMATION_MESSAGE);

            this.panelNotion.maj();
            this.frameSuppressionNotion.dispose();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            this.notionChoisie = (Notion) mdRessources.getSelectedItem();
        }
    }
}