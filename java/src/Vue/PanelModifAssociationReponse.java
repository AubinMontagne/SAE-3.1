package src.Vue;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import src.Controleur;
import src.Metier.*;

public class PanelModifAssociationReponse extends JFrame implements ActionListener {

    private JPanel panelAssociations; // Panel pour les associations
    private int nombreAssociations = 0; // Nombre d'associations
    private JTextField champQuestion;
    private JButton boutonAjoutAssociation;
    private JButton boutonEnregistrer;
    private Question q ;
    private HashMap<String, String> hmReponses;

    private PanelBanque panelBanque;

    int 	difficulte;
    String 	notion;
    int 	points;
    int 	temps;

    private Controleur ctrl;

    // Constructeur

    /**
     * Constructeur de la class PanelEntiteAssociation
     * @param ctrl	Le contrôleur
     */
    public PanelModifAssociationReponse(Controleur ctrl,Question q, HashMap<String, String> hmResponses)
    {
        this.ctrl       = ctrl;
        this.hmReponses = hmResponses;

        this.difficulte = q.getDifficulte().getIndice();
        this.notion     = q.getNotion().getNom();
        this.points     = q.getPoint();
        this.temps      = q.getTemps();
        this.q          = q ;

        setTitle("Créateur de Questions Entité-Association");
        setSize(600,400);
        setMinimumSize(this.getSize());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());

        // Panel pour la question
        JPanel panelQuestion = new JPanel(new BorderLayout());
        JLabel etiquetteQuestion = new JLabel("Question :");
        champQuestion = new JTextField();
        panelQuestion.add(etiquetteQuestion, BorderLayout.NORTH);
        panelQuestion.add(champQuestion, BorderLayout.CENTER);

        // Panel pour les associations
        panelAssociations = new JPanel();
        panelAssociations.setLayout(new BoxLayout(panelAssociations, BoxLayout.Y_AXIS));
        JScrollPane defilement = new JScrollPane(panelAssociations);

        // Bouton pour ajouter une association
        boutonAjoutAssociation = new JButton("Ajouter une association");
        boutonAjoutAssociation.setActionCommand("ajouterAssociation");

        // Bouton pour enregistrer
        boutonEnregistrer = new JButton("Enregistrer");
        boutonEnregistrer.setActionCommand("enregistrer");

        // Panel des boutons
        JPanel panelBoutons = new JPanel();
        panelBoutons.add(boutonAjoutAssociation);
        panelBoutons.add(boutonEnregistrer);

        // Ajout des composants à la fenêtre
        add(panelQuestion, BorderLayout.NORTH);
        add(defilement, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);

        // Activation des listeners
        boutonAjoutAssociation.addActionListener(this);
        boutonEnregistrer.addActionListener(this);

        //Ajout de deux réponses pour rendre plus beau
        this.ajouterAssociation();
        this.ajouterAssociation();

        // Initialisation des réponse déjà écrite
        for (HashMap.Entry<String, String> entry : this.hmReponses.entrySet()) {
            String  key   = entry.getKey();
            String value = entry.getValue();

            initAssociation(key, value);
        }
    }

    // Methode
    /**
     * Methode ajouterAssociation
     */
    private void ajouterAssociation()
    {
        JPanel panelAjoutAssociation = new JPanel();
        panelAjoutAssociation.setLayout(new BoxLayout(panelAjoutAssociation, BoxLayout.X_AXIS));

        // Champs pour les entités et les associations
        JTextField champEntite = new JTextField("Entité " + (++nombreAssociations), 10);
        JTextField champAssociation = new JTextField("Association " + nombreAssociations, 10);
        JButton boutonSupprimer = new JButton("Supprimer");

        // Listener pour supprimer une association
        boutonSupprimer.addActionListener(e -> {
            panelAssociations.remove(panelAjoutAssociation);
            panelAssociations.revalidate();
            panelAssociations.repaint();
        });

        panelAjoutAssociation.add(champEntite);
        panelAjoutAssociation.add(new JLabel(" -> "));
        panelAjoutAssociation.add(champAssociation);
        panelAjoutAssociation.add(boutonSupprimer);

        panelAssociations.add(panelAjoutAssociation);
        panelAssociations.revalidate();
        panelAssociations.repaint();
    }

    private void initAssociation(String entite, String asso)
    {
        JPanel panelAjoutAssociation = new JPanel();
        panelAjoutAssociation.setLayout(new BoxLayout(panelAjoutAssociation, BoxLayout.X_AXIS));

        // Champs pour les entités et les associations
        JTextField champEntite      = new JTextField( entite + (++nombreAssociations), 10);
        JTextField champAssociation = new JTextField( asso   + nombreAssociations, 10);
        JButton boutonSupprimer     = new JButton   ("Supprimer");

        // Listener pour supprimer une association
        boutonSupprimer.addActionListener(e -> {
            panelAssociations.remove(panelAjoutAssociation);
            panelAssociations.revalidate();
            panelAssociations.repaint();
        });

        panelAjoutAssociation.add(champEntite);
        panelAjoutAssociation.add(new JLabel(" -> "));
        panelAjoutAssociation.add(champAssociation);
        panelAjoutAssociation.add(boutonSupprimer);

        panelAssociations.add(panelAjoutAssociation);
        panelAssociations.revalidate();
        panelAssociations.repaint();
    }

    /**
     * Methode enregistrerAssociations
     */
    private void enregistrerAssociations()
    {
        String question = champQuestion.getText();
        if (question.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une question.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Récupérer les entités et associations
        List<String> associations = new ArrayList<>();
        Component[] composants = panelAssociations.getComponents();
        for (Component composant : composants)
        {
            if (composant instanceof JPanel)
            {
                JPanel associationPanel = (JPanel) composant;
                JTextField champEntite = (JTextField) associationPanel.getComponent(0);
                JTextField champAssociation = (JTextField) associationPanel.getComponent(2);
                associations.add(champEntite.getText() + " -> " + champAssociation.getText());
            }
        }

        // Résumé des données
        StringBuilder resultats = new StringBuilder("Question : " + question + "\nAssociations :\n");
        for (String association : associations)
        {
            resultats.append(association).append("\n");
        }



        HashMap<String, String> entiteAssociation = new HashMap<>();

        for (String association : associations)
        {
            String[] entiteAssociationSplit = association.split(" -> ");
            entiteAssociation.put(entiteAssociationSplit[0], entiteAssociationSplit[1]);
        }

        int idMax = 0;

        for(Question q : ctrl.getQuestions())
        {
            if(q.getId() > idMax)
            {
                idMax = q.getId();
            }
        }


        this.ctrl.creerQuestionEntiteAssociation(question, difficulte, notion, temps, points, entiteAssociation, idMax);

        if(this.panelBanque != null) {this.panelBanque.maj();}

        JOptionPane.showMessageDialog(this, resultats.toString(), "Résumé", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Methode actionPerformed
     * @param e L'évènement à traiter
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String commande = e.getActionCommand();
        switch (commande)
        {
            case "ajouterAssociation":
                ajouterAssociation();
                break;
            case "enregistrer":
                enregistrerAssociations();
        }
    }
}