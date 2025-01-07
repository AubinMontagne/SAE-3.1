package src.Vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import src.Controleur;
import src.Metier.Notion;
import src.Metier.Question;
import src.Metier.Ressource;
import java.util.HashMap;
import java.util.List;


public class PanelModifQCM extends JPanel implements ActionListener, java.awt.event.ItemListener {
    private Controleur           ctrl;
    private JComboBox<Ressource> listeRessources;
    private JComboBox<Notion>    listeNotions;
    private JComboBox<String>    listeTypes;
    private JButton              btnTresFacile, btnFacile, btnMoyen, btnDifficile;
    private JButton              btnConfirmer;
    private JButton				 btnGras, btnSouligner, btnAjouterImage, btnAjouterRessComp;
    private JLabel               labelMessage, labelResultat;
    private JTextField           champPoints;
    private JTextField           champTemps;

    private JEditorPane txtaQuestion;
    private JEditorPane txtexQuestion;

    private HashMap<String, Boolean> hmReponses;
    private ArrayList<Ressource>     ressources;
    private ArrayList<Notion>        notions;
    private int                      difficulte;
    private String                   notion;
    private int                      temps;
    private int                      points;
    private List<String>             lstLiens;

    private Question q;

    private static final ImageIcon[] IMAGES_DIFFICULTE = {
            new ImageIcon("java/data/Images/imgDif/TF.png"),
            new ImageIcon("java/data/Images/imgDif/F.png" ),
            new ImageIcon("java/data/Images/imgDif/M.png" ),
            new ImageIcon("java/data/Images/imgDif/D.png" )
    };

    public PanelModifQCM(Controleur ctrl ,Question q, HashMap<String, Boolean> hmReponses) {
        this.ctrl             = ctrl;
        this.q                = q;
        this.difficulte       = this.q.getDifficulte().getIndice();
        this.ressources       = this.ctrl.getRessources();
        this.notions          = this.ctrl.getNotions();
        Ressource placeHolder = this.q.getNotion().getRessourceAssociee();
        this.hmReponses       = hmReponses;
        this.lstLiens         = new ArrayList<>(5);

        this.ressources.add(0,placeHolder);
        this.notions   .add(0,this.q.getNotion());

        setLayout(new BorderLayout());

        UIManager.put("Label.font", new Font("Arial", Font.BOLD, 11));

        // Section supérieure
        JPanel panelConfiguration = new JPanel(new GridLayout(2, 2, 5, 5));
        panelConfiguration.setBorder(BorderFactory.createTitledBorder("Configuration"));


        // Relier donnée
        JLabel labelPoints = new JLabel("Nombre de points :");

        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);

        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setAllowsInvalid(true);
        numberFormatter.setMinimum(0);
        numberFormatter.setOverwriteMode(true);

        this.champPoints  = new JFormattedTextField(numberFormatter);
        this.champPoints.setColumns(10);
        this.champPoints.setText("" + this.q.getPoint());
        JLabel labelTemps = new JLabel("Temps de réponse (min:sec) :");

        try {
            MaskFormatter timeFormatter = new MaskFormatter("##:##");
            timeFormatter.setPlaceholderCharacter('0');
            this.champTemps = new JFormattedTextField(timeFormatter);
            this.champTemps.setText(""+ this.q.getTemps()); // Valeur initiale

            this.champTemps.addPropertyChangeListener("value", evt -> secondeValide());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        this.champTemps.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusLost(FocusEvent e) {
                secondeValide();
            }
        });

        panelConfiguration.add(labelPoints);
        panelConfiguration.add(this.champPoints);
        panelConfiguration.add(labelTemps);
        panelConfiguration.add(this.champTemps);

        this.add(panelConfiguration, BorderLayout.NORTH);

        // Section centrale
        JPanel panelText = new JPanel(new GridLayout(4,1));

        JLabel lblIntituleQuestion = new JLabel("Question : ");
        this.txtaQuestion = new JEditorPane();
        this.txtaQuestion.setText( this.q.getEnonce());
        JLabel lblExpliquationQuestion = new JLabel("Explication : ");
        this.txtexQuestion = new JEditorPane();
        this.txtexQuestion.setText( this.q.getExplicationRTF() );

        JPanel panelCentrale = new JPanel(new GridLayout(3, 2, 5, 5));
        panelCentrale.setBorder(BorderFactory.createTitledBorder("Sélection"));

        JPanel panelSelection = new JPanel(new BorderLayout());
        JPanel panelLabels    = new JPanel(new GridLayout(3, 1, 5, 5));
        JPanel panelDonnées   = new JPanel(new GridLayout(3, 1, 5, 5));

        // ComboBox de Ressource
        JLabel labelRessource = new JLabel("Ressource :");
        this.listeRessources  = new JComboBox<Ressource>();
        for(Ressource ressource : this.ressources)
        {
            this.listeRessources.addItem(ressource);
        }
        for(int i =0; i < this.ressources.size(); i++) {
            if(this.ressources.get(i) == this.q.getNotion().getRessourceAssociee() ){
                this.listeRessources.setSelectedIndex(i);
            }
        }

        this.listeRessources.addItemListener(this);

        // ComboBox de Notion
        JLabel labelNotion = new JLabel("Notion :");
        this.listeNotions  = new JComboBox<>();

        this.notions = this.ctrl.getNotionsParRessource(this.ressources.get(0));
        for(Notion notion : this.notions)
        {
            this.listeNotions.addItem(notion);
        }
        for(int i =0; i < this.notions.size(); i++) {
            if(this.notions.get(i) == this.q.getNotion() ){
                this.listeNotions.setSelectedIndex(i);
            }
        }

        this.listeNotions.addItemListener(this);

        JLabel labelNiveau = new JLabel("Difficulté :");
        JPanel panelNiveau = new JPanel(new FlowLayout());

        this.btnTresFacile		= new JButton(IMAGES_DIFFICULTE[0]);
        this.btnFacile     		= new JButton(IMAGES_DIFFICULTE[1]);
        this.btnMoyen      		= new JButton(IMAGES_DIFFICULTE[2]);
        this.btnDifficile  		= new JButton(IMAGES_DIFFICULTE[3]);
        this.btnGras	   		= new JButton("Gras");
        this.btnSouligner  		= new JButton("Souligner");
        this.btnAjouterImage 	= new JButton("Ajouter image au texte");
        this.btnAjouterRessComp = new JButton("Ajouter des fichiers complémentaires");

        Dimension d1 = new Dimension( 75,  75);
        Dimension d2 = new Dimension(100, 100);
        this.btnTresFacile		.setPreferredSize(d1);
        this.btnFacile    		.setPreferredSize(d1);
        this.btnMoyen     		.setPreferredSize(d1);
        this.btnDifficile 		.setPreferredSize(d1);
        this.btnGras	  		.setPreferredSize(d2);
        this.btnSouligner 		.setPreferredSize(d2);
        this.btnAjouterImage	.setPreferredSize(d2);
        this.btnAjouterRessComp .setPreferredSize(d2);

        this.btnTresFacile		.addActionListener(this);
        this.btnFacile    		.addActionListener(this);
        this.btnMoyen     		.addActionListener(this);
        this.btnDifficile 		.addActionListener(this);
        this.btnGras	  		.addActionListener(this);
        this.btnSouligner 		.addActionListener(this);
        this.btnAjouterImage	.addActionListener(this);
        this.btnAjouterRessComp .addActionListener(this);

        panelNiveau.add(this.btnTresFacile);
        panelNiveau.add(this.btnFacile);
        panelNiveau.add(this.btnMoyen);
        panelNiveau.add(this.btnDifficile);
        panelText.add  (this.btnGras);
        panelText.add  (this.btnSouligner);

        panelText.add(lblIntituleQuestion);
        panelText.add(txtaQuestion);
        panelText.add(lblExpliquationQuestion);
        panelText.add(txtexQuestion);

        panelText.add(this.btnAjouterImage);
        panelText.add(this.btnAjouterRessComp);

        panelLabels.add (labelRessource);
        panelDonnées.add(this.listeRessources);
        panelLabels.add (labelNotion);
        panelDonnées.add(this.listeNotions);
        panelLabels.add (labelNiveau);
        panelDonnées.add(panelNiveau);

        panelSelection.add(panelLabels, BorderLayout.WEST);
        panelSelection.add(panelDonnées, BorderLayout.CENTER);

        panelCentrale.add(panelSelection);
        panelCentrale.add(panelText);

        add(panelCentrale, BorderLayout.CENTER);

        // Section inférieure
        JPanel panelType = new JPanel(new GridLayout(1, 3, 5, 5));
        panelType.setBorder(BorderFactory.createTitledBorder("Type de Question"));

        JLabel labelType = new JLabel("Type :");
        this.listeTypes  = new JComboBox<>(new String[]
                { "QCM REP. UNIQUE","QCM REP. MULTIPLE" }
        );

        this.btnConfirmer = new JButton("Confirmer");
        this.btnConfirmer.addActionListener(this);

        panelType.add(labelType);
        panelType.add(this.listeTypes);
        panelType.add(this.btnConfirmer);

        add(panelType, BorderLayout.SOUTH);
        setVisible(true);

    }

    // Methode
    private void secondeValide() {
        String timeText = this.champTemps.getText();

        if (timeText.matches("\\d{2}:\\d{2}")) {
            int minutes  = Integer.parseInt(timeText.substring(0, 2));
            int secondes = Integer.parseInt(timeText.substring(3, 5));

            // Vérifie que les secondes ne dépassent pas 59
            if (secondes >= 60) {
                secondes  = 59;
                this.champTemps.setText(String.format("%02d:%02d", minutes, secondes));
                JOptionPane.showMessageDialog(
                        this, "Les secondes ne peuvent pas dépasser 59. Ajustement automatique effectué."
                );

            }

            // (Optionnel) Vérifie que les minutes ne dépassent pas une certaine limite
            if (minutes > 99) {
                minutes = 99;

                this.champTemps.setText(String.format("%02d:%02d", minutes, secondes));
                JOptionPane.showMessageDialog(
                        this, "Les minutes ne peuvent pas dépasser 99. Ajustement automatique effectué."
                );

            }
        }
    }

    /**
     * Methode itemStateChanged
     * @param e L'évènement à traiter
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == this.listeRessources && e.getStateChange() == ItemEvent.SELECTED) {
            if (this.listeRessources.getItemAt(0).getNom().equals("PlaceHolder")){
                this.listeRessources.removeItemAt(0);
                this.ressources     .remove(0);
                this.notions        .remove(0);
            }

            int index = listeRessources.getSelectedIndex();
            this.listeNotions.removeAllItems();
            for (Notion notion : this.ctrl.getNotionsParRessource(this.ressources.get(index))) {
                this.listeNotions.addItem(notion);
            }
            listeNotions.setEnabled(true);
        } else if (e.getSource() == this.listeNotions && e.getStateChange() == ItemEvent.SELECTED) {
            int indexRessource  = this.listeRessources.getSelectedIndex();
            int indexNotion     = this.listeNotions.getSelectedIndex();
            this.notion = ( (Notion)(this.listeNotions.getSelectedItem() )).getNom();
            if (indexRessource >= 0 && indexNotion >= 0) {

                this.btnTresFacile.setIcon(IMAGES_DIFFICULTE[0]);
                this.btnFacile    .setIcon(IMAGES_DIFFICULTE[1]);
                this.btnMoyen     .setIcon(IMAGES_DIFFICULTE[2]);
                this.btnDifficile .setIcon(IMAGES_DIFFICULTE[3]);

                this.btnTresFacile.setEnabled(true);
                this.btnFacile    .setEnabled(true);
                this.btnMoyen     .setEnabled(true);
                this.btnDifficile .setEnabled(true);
            }
        }
    }

    /**
     * Methode actionPerformed
     * @param e L'évènement à traiter
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnTresFacile) {
            JOptionPane.showMessageDialog(this, "Difficulté : Très Facile");
            this.difficulte = 1;
        } else if (e.getSource() == this.btnFacile) {
            JOptionPane.showMessageDialog(this, "Difficulté : Facile");
            this.difficulte = 2;
        } else if (e.getSource() == this.btnMoyen) {
            JOptionPane.showMessageDialog(this, "Difficulté : Moyen");
            this.difficulte = 3;
        } else if (e.getSource() == this.btnDifficile) {
            JOptionPane.showMessageDialog(this, "Difficulté : Difficile");
            this.difficulte = 4;
        }

        if (e.getSource() == this.btnConfirmer) {
            int idMax = 0;

            for(Question q : ctrl.getQuestions())
            {
                if(q.getId() > idMax)
                {
                    idMax = q.getId();
                }
            }
            idMax++;

            System.out.println(this.notion);
            String cheminDossier = "java/data/" + this.ctrl.getNotionByNom(this.notion).getRessourceAssociee().getNom() + "/" + this.ctrl.getNotionByNom(notion).getNom() + "/Question " + idMax;

            if (this.champPoints.getText().equals(""))
            {
                JOptionPane.showMessageDialog(this, "Attribuez des points à la question");
                return;
            }
            if (this.txtaQuestion.getText() == null){
                JOptionPane.showMessageDialog(this,"Veuillez entrer un énoncer");
                return;
            }
            if (this.difficulte == 0)
            {
                JOptionPane.showMessageDialog(this, "Choisissez une difficulté");
                return;
            }
            String typeSelectionne = (String) this.listeTypes.getSelectedItem();

            if ("QCM REP. UNIQUE".equals(typeSelectionne)) {
                System.out.println(typeSelectionne);
                Notion n = (Notion)(this.listeNotions.getSelectedItem());
                this.q.setNotion(n);
                this.temps  = Integer.parseInt(
                        this.champTemps.getText().substring(0,this.champTemps.getText().indexOf(":")))*60
                        + Integer.parseInt(this.champTemps.getText().substring(this.champTemps.getText().indexOf(":")+1)
                );
                this.q.setTemps(this.temps);
                this.q.setPoint(Integer.parseInt(this.champPoints.getText()));

                PanelModifQCMReponse panelModifQCM = new PanelModifQCMReponse(
                        this.ctrl,this.q, this.hmReponses, true
                );
                panelModifQCM.setVisible(true);

            } else if ("QCM REP. MULTIPLE".equals(typeSelectionne)) {
                System.out.println(typeSelectionne);
                Notion n = (Notion) (this.listeNotions.getSelectedItem());
                this.q.setNotion(n);
                this.temps = Integer.parseInt(
                        this.champTemps.getText().substring(0, this.champTemps.getText().indexOf(":"))) * 60
                        + Integer.parseInt(this.champTemps.getText().substring(this.champTemps.getText().indexOf(":") + 1)
                );
                this.q.setTemps(this.temps);
                this.q.setPoint(Integer.parseInt(this.champPoints.getText()));
                PanelModifQCMReponse panelModifQCM = new PanelModifQCMReponse (
                        this.ctrl, this.q, this.hmReponses, false
                );
                panelModifQCM.setVisible(true);
            }
        }
    }
}