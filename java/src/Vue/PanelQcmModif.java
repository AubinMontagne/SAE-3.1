package src.Vue;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashMap;
import javax.swing.*;
import src.Controleur;
import src.Metier.Question;
import src.Metier.QCM;


public class PanelQcmModif extends JFrame implements ActionListener {

    private Controleur               ctrl;
    private JPanel                   panelReponses; // Panel pour les réponses
    private int                      nombreReponses = 0; // Nombre de réponses
    private JTextField               champQuestion;
    private JButton                  boutonAjoutReponse;
    private JButton                  boutonEnregistrer;
    private boolean                  modeReponseUnique; // Checkbox pour activer/désactiver le mode réponse unique
    private Question                 q;
    private HashMap<String, Boolean> reponses;

    private boolean     estModeUnique = false; // Par défaut, mode "plusieurs réponses correctes"
    private ButtonGroup groupReponses; // Utilisé pour le mode "réponse unique"

    int    difficulte;
    String notion;
    int    points;
    int    temps;

    PanelBanque panelBanque;

    public PanelQcmModif(Controleur ctrl, int difficulte, String notion, int points, int temps, boolean estModeUnique, Question q) {
        this.ctrl          = ctrl;
        this.panelBanque   = panelBanque;
        this.estModeUnique = estModeUnique;
        this.difficulte    = difficulte;
        this.notion        = notion;
        this.points        = points;
        this.temps         = temps;
        this.q             = q;
        //this.reponses = q.getReponses();

        setTitle("QCM Builder - Modificateur de QCM");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelQuestion = new JPanel(new BorderLayout());
        JLabel etiquetteQuestion = new JLabel("Question :");
        champQuestion = new JTextField();
        panelQuestion.add(etiquetteQuestion, BorderLayout.NORTH);
        panelQuestion.add(champQuestion, BorderLayout.CENTER);

        // Panel pour ajouter les réponses
        panelReponses = new JPanel();
        panelReponses.setLayout(new BoxLayout(panelReponses, BoxLayout.Y_AXIS));
        JScrollPane defilement = new JScrollPane(panelReponses);

        // Bouton pour ajouter une réponse
        boutonAjoutReponse = new JButton("Ajouter une réponse");
        boutonAjoutReponse.setActionCommand("ajouterReponse");

        // Bouton pour enregistrer
        boutonEnregistrer = new JButton("Enregistrer");
        boutonEnregistrer.setActionCommand("enregistrer");

        // Panel des boutons
        JPanel panelBoutons = new JPanel();
        panelBoutons.add(boutonAjoutReponse);
        panelBoutons.add(boutonEnregistrer);

        // Ajout des composants à la fenêtre
        add(panelQuestion, BorderLayout.NORTH);
        add(defilement   , BorderLayout.CENTER);
        add(panelBoutons , BorderLayout.SOUTH);

        // Activation des listeners
        boutonAjoutReponse.addActionListener(this);
        boutonEnregistrer .addActionListener(this);
    }

    private void ajouterReponse() {
        JPanel panelAjoutReponse = new JPanel();
        panelAjoutReponse.setLayout(new BoxLayout(panelAjoutReponse, BoxLayout.X_AXIS));
        JTextField champReponse = new JTextField("Réponse " + (++nombreReponses));

        JComponent caseCorrecte;
        if (estModeUnique) {
            JRadioButton boutonRadio = new JRadioButton();
            if (groupReponses == null) {
                groupReponses = new ButtonGroup();
            }
            groupReponses.add(boutonRadio);
            caseCorrecte = boutonRadio;
        } else {
            caseCorrecte = new JCheckBox("Correcte");
        }

        JButton boutonSupprimer = new JButton("Supprimer");
        boutonSupprimer.addActionListener(e -> {
            if (caseCorrecte instanceof JRadioButton && groupReponses != null) {
                groupReponses.remove((JRadioButton) caseCorrecte);
            }
            panelReponses.remove(panelAjoutReponse);
            panelReponses.revalidate();
            panelReponses.repaint();
        });

        panelAjoutReponse.add(champReponse);
        panelAjoutReponse.add(caseCorrecte);
        panelAjoutReponse.add(boutonSupprimer);

        panelReponses.add(panelAjoutReponse);
        panelReponses.revalidate();
        panelReponses.repaint();
    }


    private void enregistrerQCMAvecHashMap() {

        String question = champQuestion.getText().trim();

        if (question.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une question.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (panelReponses.getComponentCount() == 0) {
            JOptionPane.showMessageDialog(this, "Veuillez ajouter au moins une réponse.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        HashMap<String, Boolean> reponses = new HashMap<>();
        boolean auMoinsUneReponseCorrecte = false;

        for (Component composant : panelReponses.getComponents()) {
            if (composant instanceof JPanel) {
                JPanel panelReponse = (JPanel) composant;
                JTextField champReponse = (JTextField) panelReponse.getComponent(0);
                JComponent caseCorrecte = (JComponent) panelReponse.getComponent(1);

                String texteReponse = champReponse.getText().trim();
                boolean estCorrecte = caseCorrecte instanceof JCheckBox
                        ? ((JCheckBox) caseCorrecte).isSelected()
                        : ((JRadioButton) caseCorrecte).isSelected();

                if (texteReponse.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Une réponse ne peut pas être vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (reponses.containsKey(texteReponse)) {
                    JOptionPane.showMessageDialog(this, "Une réponse dupliquée a été détectée : " + texteReponse, "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                reponses.put(texteReponse, estCorrecte);
                if (estCorrecte) {
                    auMoinsUneReponseCorrecte = true;
                }
            }
        }

        if (!auMoinsUneReponseCorrecte) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner au moins une réponse correcte.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.ctrl.supprimerQuestion(q);

        this.ctrl.creerQuestionQCM(
                question,
                difficulte,
                notion,
                temps,
                points,
                false,
                reponses
        );

        if(this.panelBanque != null) {this.panelBanque.maj();}

        JOptionPane.showMessageDialog(this, "Question enregistrée avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String commande = e.getActionCommand();
        switch (commande) {
            case "ajouterReponse":
                ajouterReponse();
                break;

            case "enregistrer":
                enregistrerQCMAvecHashMap();
                break;

            default:
                System.out.println("Commande inconnue : " + commande);
                break;
        }
    }
}

