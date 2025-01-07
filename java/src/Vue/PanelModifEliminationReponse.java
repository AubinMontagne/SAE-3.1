package src.Vue;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;
import src.Controleur;
import src.Metier.Question;

public class PanelModifEliminationReponse extends JFrame implements ActionListener
{

    // FONCTION EXPLICATION A RAJOUTER
    private JPanel 	   panelReponses; // Panel pour les réponses
    private int 	   nombreReponses = 0; // Nombre de réponses
    private JTextField champQuestion;
    private JButton    boutonAjoutReponse;
    private JButton    boutonEnregistrer;
    private Controleur ctrl;
    private HashMap<String, Double[]> hmReponses;

    private PanelBanque panelBanque;

    private Question q;
    private int difficulte;
    private String notion;
    private int points;
    private int temps;


    // Constructeur
    /**
     * Constructeur de la class PanelElimination
     * @param ctrl	Le contrôleur
     */
    public PanelModifEliminationReponse(Controleur ctrl, Question q, HashMap<String, Double[]> hmReponses){
        this.ctrl        = ctrl;
        this.hmReponses  = hmReponses;

        this.difficulte  = q.getDifficulte().getIndice();
        this.notion      = q.getNotion().getNom();
        this.points      = q.getPoint();
        this.temps       = q.getTemps();
        this.q           = q;

        setTitle("QCM Builder - Modifier-Question élimination");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        this.setIconImage(new ImageIcon("java/data/Images/icon.png").getImage());

        JPanel panelQuestion     = new JPanel(new BorderLayout());
        JLabel etiquetteQuestion = new JLabel("Question :");
        champQuestion            = new JTextField();
        panelQuestion.add(etiquetteQuestion, BorderLayout.NORTH);
        panelQuestion.add(champQuestion, BorderLayout.CENTER);

        // Panel pour ajouter les réponses
        panelReponses 		   = new JPanel();
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
        add(defilement, BorderLayout.CENTER);
        add(panelBoutons, BorderLayout.SOUTH);

        // Activation des listeners
        boutonAjoutReponse.addActionListener(this);
        boutonEnregistrer.addActionListener(this);

        // Initialisation des réponse déjà écrite
        for (HashMap.Entry<String, Double[]> entry : this.hmReponses.entrySet()) {
            String  key   = entry.getKey();
            Double[] value = entry.getValue();

            initReponse(key, value);
        }
    }

    // Methode

    /**
     * Methode ajouterReponse
     */
    private void ajouterReponse(){
        JPanel panelAjoutReponse   = new JPanel();
        panelAjoutReponse.setLayout(new BoxLayout(panelAjoutReponse, BoxLayout.X_AXIS));
        JTextField champReponse    = new JTextField("Réponse " + (++nombreReponses));
        JCheckBox caseBonneReponse = new JCheckBox("Correcte");
        JButton boutonSupprimer    = new JButton("Supprimer");

        boutonSupprimer.addActionListener(e -> {
            panelReponses.remove(panelAjoutReponse);
            panelReponses.revalidate();
            panelReponses.repaint();
        });

        JTextField txtOrdreElim    = new JTextField("ordre elim");
        JTextField txtPointNegatif = new JTextField("pnt-");

        panelAjoutReponse.add(champReponse);
        panelAjoutReponse.add(txtOrdreElim);
        panelAjoutReponse.add(txtPointNegatif);

        panelAjoutReponse.add(caseBonneReponse);
        panelAjoutReponse.add(boutonSupprimer);

        panelReponses.add(panelAjoutReponse);
        panelReponses.revalidate();
        panelReponses.repaint();
    }

    private void initReponse(String rep, Double[] tabD){
        JPanel panelAjoutReponse   = new JPanel();
        panelAjoutReponse.setLayout(new BoxLayout(panelAjoutReponse, BoxLayout.X_AXIS));
        JTextField champReponse    = new JTextField( rep + (++nombreReponses));
        JCheckBox caseBonneReponse = new JCheckBox("Correcte");
        JButton boutonSupprimer    = new JButton  ("Supprimer");

        boutonSupprimer.addActionListener(e -> {
            panelReponses.remove(panelAjoutReponse);
            panelReponses.revalidate();
            panelReponses.repaint();
        });

        JTextField txtOrdreElim    = new JTextField("" + tabD[0]);
        JTextField txtPointNegatif = new JTextField("" + tabD[1]);

        panelAjoutReponse.add(champReponse);
        panelAjoutReponse.add(txtOrdreElim);
        panelAjoutReponse.add(txtPointNegatif);

        panelAjoutReponse.add(caseBonneReponse);
        panelAjoutReponse.add(boutonSupprimer);

        panelReponses.add(panelAjoutReponse);
        panelReponses.revalidate();
        panelReponses.repaint();
    }


    /**
     * Methode enregistrerElimination
     */
    private void enregistrerElimination(){
        String question = champQuestion.getText();
        if (question.isEmpty()){
            JOptionPane.showMessageDialog(this, "Veuillez entrer une question.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Résumé de la question faite (debug)
        StringBuilder resultats = new StringBuilder("Question : " + question + "\nRéponses :\n");
        Component[] composants  = panelReponses.getComponents();
        for (Component composant : composants){
            if (composant instanceof JPanel){
                JPanel reponse = (JPanel) composant;
                JTextField champReponse = (JTextField) reponse.getComponent(0);
                JCheckBox estCorrecte = (JCheckBox) reponse.getComponent(1);
                resultats.append("- ").append(champReponse.getText())
                        .append(estCorrecte.isSelected() ? " (correcte)\n" : " (incorrecte)\n");
            }
        }

        HashMap<String, Double[]> reponses = new HashMap<>();
        String reponseCorrecte = "";
        for (Component composant : composants){
            if (composant instanceof JPanel){
                JPanel reponse = (JPanel) composant;
                JTextField champReponse = (JTextField) reponse.getComponent(0);
                JTextField txtOrdreElim = (JTextField) reponse.getComponent(1);
                JTextField txtPointNegatif = (JTextField) reponse.getComponent(2);
                JCheckBox estCorrecte = (JCheckBox) reponse.getComponent(3);

                reponses.put(champReponse.getText(), new Double[]{Double.parseDouble(txtPointNegatif.getText()), Double.parseDouble(txtOrdreElim.getText())});
                if (estCorrecte.isSelected()){
                    reponseCorrecte = champReponse.getText();
                }
            }
        }

        int idMax = 0;

        for(Question q : ctrl.getQuestions())
        {
            if(q.getId() > idMax)
            {
                idMax = q.getId();
            }
        }

        // Création de la question
        this.ctrl.creerQuestionElimination(question, difficulte, notion, temps, points, reponses, reponseCorrecte, idMax);

        if(this.panelBanque != null) {this.panelBanque.maj();}

        JOptionPane.showMessageDialog(this, resultats.toString(), "Résumé", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Methode actionPerformed
     * @param e L'évènement à traiter
     */
    @Override
    public void actionPerformed(ActionEvent e){
        String commande = e.getActionCommand();
        switch (commande) {
            case "ajouterReponse":
                ajouterReponse();
                break;
            case "enregistrer":
                enregistrerElimination();
                break;
            default:
                break;
        }
    }
}