package src.Vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class PanelEntiteAssociation extends JFrame implements ActionListener {

    private JPanel panelEntites; // Panel pour les entités et leurs associations
    private int nombreEntites = 0; // Nombre d'entités créées
    private JTextField champQuestion;
    private JButton boutonAjoutEntite;
    private JButton boutonEnregistrer;

    // Stockage des entités et de leurs associations
    private Map<String, List<String>> entitesAssociations;

    public PanelEntiteAssociation() {
        setTitle("Créateur de Questions Entité-Association");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        entitesAssociations = new HashMap<>();

        // Panel pour la question
        JPanel panelQuestion = new JPanel(new BorderLayout());
        JLabel etiquetteQuestion = new JLabel("Question :");
        champQuestion = new JTextField();
        panelQuestion.add(etiquetteQuestion, BorderLayout.NORTH);
        panelQuestion.add(champQuestion, BorderLayout.CENTER);

        // Panel pour les entités et leurs associations
        panelEntites = new JPanel();
        panelEntites.setLayout(new BoxLayout(panelEntites, BoxLayout.Y_AXIS));
        JScrollPane defilement = new JScrollPane(panelEntites);

        // Bouton pour ajouter une entité
        boutonAjoutEntite = new JButton("Ajouter une entité");
        boutonAjoutEntite.setActionCommand("ajouterEntite");

        // Bouton pour enregistrer
        boutonEnregistrer = new JButton("Enregistrer");
        boutonEnregistrer.setActionCommand("enregistrer");

        // Panel des boutons
        JPanel panelBoutons = new JPanel();
        panelBoutons.add(boutonAjoutEntite);
        panelBoutons.add(boutonEnregistrer);

        // Ajout des composants à la fenêtre
        add(panelQuestion, BorderLayout.NORTH);
        add(defilement, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);

        // Activation des listeners
        boutonAjoutEntite.addActionListener(this);
        boutonEnregistrer.addActionListener(this);
    }

    private void ajouterEntite() {
        JPanel panelEntite = new JPanel();
        panelEntite.setLayout(new BoxLayout(panelEntite, BoxLayout.Y_AXIS));

        // Champ pour l'entité
        JTextField champEntite = new JTextField("Entité " + (++nombreEntites), 15);
        JButton boutonAjouterAssociation = new JButton("Ajouter une association");
        JPanel panelHautEntite = new JPanel(new BorderLayout());
        panelHautEntite.add(champEntite, BorderLayout.CENTER);
        panelHautEntite.add(boutonAjouterAssociation, BorderLayout.EAST);

        // Panel pour les associations de cette entité
        JPanel panelAssociations = new JPanel();
        panelAssociations.setLayout(new BoxLayout(panelAssociations, BoxLayout.Y_AXIS));

        boutonAjouterAssociation.addActionListener(e -> ajouterAssociation(champEntite.getText(), panelAssociations));

        panelEntite.add(panelHautEntite);
        panelEntite.add(panelAssociations);

        panelEntites.add(panelEntite);
        panelEntites.revalidate();
        panelEntites.repaint();
    }

    private void ajouterAssociation(String entite, JPanel panelAssociations) {
        JPanel panelAssociation = new JPanel();
        panelAssociation.setLayout(new BoxLayout(panelAssociation, BoxLayout.X_AXIS));

        JTextField champAssociation = new JTextField("Association", 15);
        JButton boutonSupprimer = new JButton("Supprimer");

        boutonSupprimer.addActionListener(e -> {
            panelAssociations.remove(panelAssociation);
            panelAssociations.revalidate();
            panelAssociations.repaint();
        });

        panelAssociation.add(champAssociation);
        panelAssociation.add(boutonSupprimer);

        panelAssociations.add(panelAssociation);
        panelAssociations.revalidate();
        panelAssociations.repaint();

        // Ajouter au stockage
        entitesAssociations.computeIfAbsent(entite, k -> new ArrayList<>()).add(champAssociation.getText());
    }

    private void enregistrerAssociations() {
        String question = champQuestion.getText();
        if (question.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une question.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Récupérer les données des entités et associations
        entitesAssociations.clear();
        for (Component entiteComponent : panelEntites.getComponents()) {
            if (entiteComponent instanceof JPanel) {
                JPanel entitePanel = (JPanel) entiteComponent;
                JPanel panelHautEntite = (JPanel) entitePanel.getComponent(0);
                JTextField champEntite = (JTextField) panelHautEntite.getComponent(0);

                String entite = champEntite.getText();
                List<String> associations = new ArrayList<>();

                JPanel panelAssociations = (JPanel) entitePanel.getComponent(1);
                for (Component associationComponent : panelAssociations.getComponents()) {
                    if (associationComponent instanceof JPanel) {
                        JPanel associationPanel = (JPanel) associationComponent;
                        JTextField champAssociation = (JTextField) associationPanel.getComponent(0);
                        associations.add(champAssociation.getText());
                    }
                }

                entitesAssociations.put(entite, associations);
            }
        }

        // Résumé des données
        StringBuilder resultats = new StringBuilder("Question : " + question + "\nEntités et Associations :\n");
        for (Map.Entry<String, List<String>> entry : entitesAssociations.entrySet()) {
            resultats.append(entry.getKey()).append(" : ").append(String.join(", ", entry.getValue())).append("\n");
        }

        JOptionPane.showMessageDialog(this, resultats.toString(), "Résumé", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String commande = e.getActionCommand();
        switch (commande) {
            case "ajouterEntite":
                ajouterEntite();
                break;
            case "enregistrer":
                enregistrerAssociations();
                break;
            default:
                break;
        }
    }
}
