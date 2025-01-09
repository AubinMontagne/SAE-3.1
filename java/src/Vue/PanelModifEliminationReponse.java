package src.Vue;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;
import src.Controleur;
import src.Metier.Question;

public class PanelModifEliminationReponse extends JFrame implements ActionListener {

    private Controleur  ctrl;
    private JPanel 	    panelReponses; // Panel pour les répons
    private PanelBanque panelBanque;
    private JTextField  txtQuestion;
    private JButton     btnAjoutReponse, btnEnregistrer;

    private Question                  q;
    private HashMap<String, Double[]> hmReponses;
    private String                    notion;
    private int                       difficulte, points, temps;
    private int 	                  nombreReponses = 0;


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
        this.txtQuestion            = new JTextField();
        panelQuestion.add(etiquetteQuestion, BorderLayout.NORTH);
        panelQuestion.add(txtQuestion    , BorderLayout.CENTER);

        // Panel pour ajouter les réponses
        this.panelReponses 		   = new JPanel();
        this.panelReponses.setLayout(new BoxLayout(panelReponses, BoxLayout.Y_AXIS));
        JScrollPane defilement = new JScrollPane(panelReponses);

        // Bouton pour ajouter une réponse
        this.btnAjoutReponse = new JButton("Ajouter une réponse");
        this.btnAjoutReponse.setActionCommand("ajouterReponse");

        // Bouton pour enregistrer
        this.btnEnregistrer = new JButton("Enregistrer");
        this.btnEnregistrer.setActionCommand("enregistrer");

        // Panel des boutons
        JPanel panelBoutons = new JPanel();
        panelBoutons.add(this.btnAjoutReponse);
        panelBoutons.add(this.btnEnregistrer);

        // Ajout des composants à la fenêtre
        this.add(panelQuestion, BorderLayout.NORTH);
        this.add(defilement   , BorderLayout.CENTER);
        this.add(panelBoutons , BorderLayout.SOUTH);

        // Activation des listeners
        this.btnAjoutReponse.addActionListener(this);
        this.btnEnregistrer .addActionListener(this);

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
        JTextField champReponse    = new JTextField("Réponse " + (++this.nombreReponses));
        JCheckBox caseBonneReponse = new JCheckBox("Correcte");
        JButton boutonSupprimer    = new JButton("Supprimer");

        boutonSupprimer.addActionListener(e -> {
            this.panelReponses.remove(panelAjoutReponse);
            this.panelReponses.revalidate();
            this.panelReponses.repaint();
        });

        JTextField txtOrdreElim    = new JTextField("ordre elim");
        JTextField txtPointNegatif = new JTextField("pnt-");

        panelAjoutReponse.add(champReponse);
        panelAjoutReponse.add(txtOrdreElim);
        panelAjoutReponse.add(txtPointNegatif);

        panelAjoutReponse.add(caseBonneReponse);
        panelAjoutReponse.add(boutonSupprimer);

        this.panelReponses.add(panelAjoutReponse);
        this.panelReponses.revalidate();
        this.panelReponses.repaint();
    }

    private void initReponse(String rep, Double[] tabD){
        JPanel panelAjoutReponse   = new JPanel();
        panelAjoutReponse.setLayout(new BoxLayout(panelAjoutReponse, BoxLayout.X_AXIS));
        JTextField champReponse    = new JTextField( rep + (++this.nombreReponses));
        JCheckBox caseBonneReponse = new JCheckBox("Correcte");
        JButton boutonSupprimer    = new JButton  ("Supprimer");

        boutonSupprimer.addActionListener(e -> {
            this.panelReponses.remove(panelAjoutReponse);
            this.panelReponses.revalidate();
            this.panelReponses.repaint();
        });

        JTextField txtOrdreElim    = new JTextField("" + tabD[0]);
        JTextField txtPointNegatif = new JTextField("" + tabD[1]);

        panelAjoutReponse.add(champReponse);
        panelAjoutReponse.add(txtOrdreElim);
        panelAjoutReponse.add(txtPointNegatif);

        panelAjoutReponse.add(caseBonneReponse);
        panelAjoutReponse.add(boutonSupprimer);

        this.panelReponses.add(panelAjoutReponse);
        this.panelReponses.revalidate();
        this.panelReponses.repaint();
    }


    /**
     * Methode enregistrerElimination
     */
    private void enregistrerElimination(){
        String question = this.txtQuestion.getText();
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

        for(Question q : this.ctrl.getQuestions())
        {
            if(q.getId() > idMax)
            {
                idMax = q.getId();
            }
        }

        // Création de la question
        this.ctrl.creerQuestionElimination(
                question,
                this.difficulte,
                this.notion,
                this.temps,
                this.points,
                reponses,
                reponseCorrecte,
                idMax
        );

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