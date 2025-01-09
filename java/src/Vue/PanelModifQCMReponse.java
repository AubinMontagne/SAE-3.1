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


public class PanelModifQCMReponse extends JFrame implements ActionListener {

    private Controleur               ctrl;
    private JPanel                   panelReponses; // Panel pour les réponses
    private int                      nombreReponses = 0; // Nombre de réponses
    private JTextField               champQuestion;
    private JButton                  boutonAjoutReponse;
    private JButton                  boutonEnregistrer;
    private boolean                  modeReponseUnique; // Checkbox pour activer/désactiver le mode réponse unique
    private Question                 q;
    private HashMap<String, Boolean> hmReponses;

    private boolean     estModeUnique = false; // Par défaut, mode "plusieurs réponses correctes"
    private ButtonGroup groupReponses; // Utilisé pour le mode "réponse unique"

    int    difficulte;
    String notion;
    int    points;
    int    temps;

    PanelBanque panelBanque;

    public PanelModifQCMReponse(Controleur ctrl, Question q, HashMap<String, Boolean> hmReponses, boolean estModeUnique) {
        this.ctrl          = ctrl;
        this.hmReponses    = hmReponses;
        this.estModeUnique = estModeUnique;
        this.difficulte    = q.getDifficulte().getIndice();
        this.notion        = q.getNotion().getNom();
        this.points        = q.getPoint();
        this.temps         = q.getTemps();
        this.q             = q;

        setTitle("QCM Builder - Modificateur de QCM");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelQuestion = new JPanel(new BorderLayout());
        JLabel etiquetteQuestion = new JLabel("Question :");
        this.champQuestion = new JTextField();
        panelQuestion.add(etiquetteQuestion, BorderLayout.NORTH);
        panelQuestion.add(this.champQuestion, BorderLayout.CENTER);

        // Panel pour ajouter les réponses
        this.panelReponses = new JPanel();
        this.panelReponses.setLayout(new BoxLayout(panelReponses, BoxLayout.Y_AXIS));
        JScrollPane defilement = new JScrollPane(panelReponses);

        // Bouton pour ajouter une réponse
        this.boutonAjoutReponse = new JButton("Ajouter une réponse");
        this.boutonAjoutReponse.setActionCommand("ajouterReponse");

        // Bouton pour enregistrer
        this.boutonEnregistrer = new JButton("Enregistrer");
        this.boutonEnregistrer.setActionCommand("enregistrer");

        // Panel des boutons
        JPanel panelBoutons = new JPanel();
        panelBoutons.add(this.boutonAjoutReponse);
        panelBoutons.add(this.boutonEnregistrer);

        // Ajout des composants à la fenêtre
        add(panelQuestion, BorderLayout.NORTH);
        add(defilement   , BorderLayout.CENTER);
        add(panelBoutons , BorderLayout.SOUTH);

        // Activation des listeners
        boutonAjoutReponse.addActionListener(this);
        boutonEnregistrer .addActionListener(this);

        // Initialisation des réponse déjà écrite
        for (HashMap.Entry<String, Boolean> entry : this.hmReponses.entrySet()) {
            String  key   = entry.getKey();
            boolean value = entry.getValue();

            initReponse(key, value);
        }
    }

    private void initReponse(String rep, boolean vrai) {
        JPanel panelAjoutReponse = new JPanel();
        panelAjoutReponse.setLayout(new BoxLayout(panelAjoutReponse, BoxLayout.X_AXIS));
        JTextField champReponse = new JTextField( rep + (++this.nombreReponses));

        JComponent caseCorrecte;
        if (this.estModeUnique) {
            JRadioButton boutonRadio = new JRadioButton();
            if (this.groupReponses == null) {
                this.groupReponses = new ButtonGroup();
            }
            boutonRadio.setSelected(vrai);

            this.groupReponses.add(boutonRadio);
            caseCorrecte = boutonRadio;
        } else {
            caseCorrecte = new JCheckBox("Correcte", vrai);
        }


        JButton boutonSupprimer = new JButton("Supprimer");
        boutonSupprimer.addActionListener(e -> {
            if (caseCorrecte instanceof JRadioButton && this.groupReponses != null) {
                groupReponses.remove((JRadioButton) caseCorrecte);
            }
            this.panelReponses.remove(panelAjoutReponse);
            this.panelReponses.revalidate();
            this.panelReponses.repaint();
        });

        panelAjoutReponse.add(champReponse);
        panelAjoutReponse.add(caseCorrecte);
        panelAjoutReponse.add(boutonSupprimer);

        this.panelReponses.add(panelAjoutReponse);
        this.panelReponses.revalidate();
        this.panelReponses.repaint();
    }

    private void ajouterReponse() {
        JPanel panelAjoutReponse = new JPanel();
        panelAjoutReponse.setLayout(new BoxLayout(panelAjoutReponse, BoxLayout.X_AXIS));
        JTextField champReponse = new JTextField("Réponse " + (++this.nombreReponses));

        JComponent caseCorrecte;
        if (this.estModeUnique) {
            JRadioButton boutonRadio = new JRadioButton();
            if (this.groupReponses == null) {
                this.groupReponses = new ButtonGroup();
            }
            this.groupReponses.add(boutonRadio);
            caseCorrecte = boutonRadio;
        } else {
            caseCorrecte = new JCheckBox("Correcte");
        }

        JButton boutonSupprimer = new JButton("Supprimer");
        boutonSupprimer.addActionListener(e -> {
            if (caseCorrecte instanceof JRadioButton && this.groupReponses != null) {
                this.groupReponses.remove((JRadioButton) caseCorrecte);
            }
            this.panelReponses.remove(panelAjoutReponse);
            this.panelReponses.revalidate();
            this.panelReponses.repaint();
        });

        panelAjoutReponse.add(champReponse);
        panelAjoutReponse.add(caseCorrecte);
        panelAjoutReponse.add(boutonSupprimer);

        this.panelReponses.add(panelAjoutReponse);
        this.panelReponses.revalidate();
        this.panelReponses.repaint();
    }



    private void enregistrerQCMAvecHashMap() {

        String question = this.champQuestion.getText().trim();

        if (question.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer une question.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (this.panelReponses.getComponentCount() == 0) {
            JOptionPane.showMessageDialog(this, "Veuillez ajouter au moins une réponse.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        HashMap<String, Boolean> reponses = new HashMap<>();
        boolean auMoinsUneReponseCorrecte = false;

        for (Component composant : this.panelReponses.getComponents()) {
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

        int idMax = 0;

        for(Question q : this.ctrl.getQuestions())
        {
            if(q.getId() > idMax)
            {
                idMax = q.getId();
            }
        }

        this.ctrl.creerQuestionQCM(
                question,
                difficulte,
                notion,
                temps,
                points,
                false,
                reponses,
                q.getImageChemin(),
                q.getListeFichiers(),
                idMax
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

