package src.Vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;
import javax.swing.text.rtf.RTFEditorKit;

import src.Controleur;
import src.Metier.Notion;
import src.Metier.Question;
import src.Metier.Ressource;
import java.util.HashMap;
import java.util.List;


public class PanelModifElimination extends JPanel implements ActionListener, java.awt.event.ItemListener {
    private Controleur           ctrl;
    private JComboBox<Ressource> ddlstRessources;
    private JComboBox<Notion>    ddlstNotions;
    private JComboBox<String>    ddlstTypes;
    private JButton              btnTresFacile, btnFacile, btnMoyen, btnDifficile;
    private JButton              btnConfirmer;
    private JButton				 btnGras, btnItalique, btnAjouterImage, btnAjouterRessComp;
    private Border               bordureDefaut;
    private Border               bordureSelect;

    private JTextField           champPoints;
    private JTextField           champTemps;

    private JEditorPane epQuestion;
    private JEditorPane epExplication;

    private HashMap<String, Double []> hmReponses;
    private ArrayList<Ressource>       lstRessources;
    private ArrayList<Notion>          lstNotions;
    private String                     notion;
    private String                     imageFond;
    private int                        difficulte;
    private int                        temps;
    private int                        points;
    private List<String>               lstLiens;

    private Question q;

    private static final ImageIcon[] IMAGES_DIFFICULTE = {
            new ImageIcon("java/data/Images/imgDif/TF.png"),
            new ImageIcon("java/data/Images/imgDif/F.png" ),
            new ImageIcon("java/data/Images/imgDif/M.png" ),
            new ImageIcon("java/data/Images/imgDif/D.png" )
    };

    public PanelModifElimination(Controleur ctrl ,Question q, HashMap<String, Double[]> hmReponses) {
        this.ctrl             = ctrl;
        this.q                = q;
        this.difficulte       = this.q.getDifficulte().getIndice();
        this.lstRessources    = this.ctrl.getRessources();
        this.lstNotions       = this.ctrl.getNotions();
        this.hmReponses       = hmReponses;
        this.lstLiens         = new ArrayList<>(5);

        Ressource placeHolder = this.q.getNotion().getRessourceAssociee();

        this.bordureDefaut = BorderFactory.createLineBorder(Color.BLACK, 5);
        this.bordureSelect = BorderFactory.createLineBorder(Color.RED  , 5);

        this.lstRessources.add(0,placeHolder);
        this.lstNotions   .add(0,this.q.getNotion());

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

        this.champTemps.addFocusListener(new FocusAdapter() {
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
        this.epQuestion            = new JEditorPane();
        this.epQuestion.setEditorKit(new StyledEditorKit());
        this.epQuestion.setText     (this.q.getEnonce());

        JLabel lblExpliquationQuestion = new JLabel("Explication : ");
        this.epExplication             = new JEditorPane();
        this.epQuestion .setEditorKit(new StyledEditorKit());
        this.epExplication.setText   (this.q.getExplication() );

        JPanel panelCentrale = new JPanel(new GridLayout(1, 2, 5, 5));
        panelCentrale.setBorder(BorderFactory.createTitledBorder("Sélection"));

        JPanel panelSelection = new JPanel(new BorderLayout());
        JPanel panelLabels    = new JPanel(new GridLayout(3, 1, 5, 5));
        JPanel panelDonnees   = new JPanel(new GridLayout(3, 1, 5, 5));

        // ComboBox de Ressource
        JLabel lblRessource  = new JLabel("Ressource :");
        this.ddlstRessources = new JComboBox<Ressource>();
        for(Ressource ressource : this.lstRessources)
        {
            this.ddlstRessources.addItem(ressource);
        }
        for(int i =0; i < this.lstRessources.size(); i++) {
            if(this.lstRessources.get(i) == this.q.getNotion().getRessourceAssociee() ){
                this.ddlstRessources.setSelectedIndex(i);
            }
        }
        this.ddlstRessources.addItemListener(this);

        // ComboBox de Notion
        JLabel lblNotion  = new JLabel("Notion :");
        this.ddlstNotions = new JComboBox<>();

        this.lstNotions = this.ctrl.getNotionsParRessource(this.lstRessources.get(0));
        for(Notion notion : this.lstNotions)
        {
            this.ddlstNotions.addItem(notion);
        }
        for(int i =0; i < this.lstNotions.size(); i++) {
            if(this.lstNotions.get(i) == this.q.getNotion() ){
                this.ddlstNotions.setSelectedIndex(i);
            }
        }

        this.ddlstNotions.addItemListener(this);

        JLabel lblNiveau   = new JLabel("Difficulté :");
        JPanel panelNiveau = new JPanel(new FlowLayout());

        this.btnTresFacile		= new JButton(IMAGES_DIFFICULTE[0]);
        this.btnFacile     		= new JButton(IMAGES_DIFFICULTE[1]);
        this.btnMoyen      		= new JButton(IMAGES_DIFFICULTE[2]);
        this.btnDifficile  		= new JButton(IMAGES_DIFFICULTE[3]);
        this.btnGras	   		= new JButton("Gras");
        this.btnItalique  		= new JButton("Italique");
        this.btnAjouterImage 	= new JButton("Ajouter image au texte");
        this.btnAjouterRessComp = new JButton("Ajouter des fichiers complémentaires");

        Dimension d1 = new Dimension( 75,  75);
        Dimension d2 = new Dimension(100, 100);
        this.btnTresFacile	   .setPreferredSize(d1);
        this.btnFacile    	   .setPreferredSize(d1);
        this.btnMoyen     	   .setPreferredSize(d1);
        this.btnDifficile	   .setPreferredSize(d1);
        this.btnGras	  	   .setPreferredSize(d2);
        this.btnItalique 	   .setPreferredSize(d2);
        this.btnAjouterImage   .setPreferredSize(d2);
        this.btnAjouterRessComp.setPreferredSize(d2);

        this.btnTresFacile.setBorder(this.bordureDefaut);
        this.btnFacile    .setBorder(this.bordureDefaut);
        this.btnMoyen     .setBorder(this.bordureDefaut);
        this.btnDifficile .setBorder(this.bordureDefaut);

        this.btnTresFacile	   .addActionListener(this);
        this.btnFacile    	   .addActionListener(this);
        this.btnMoyen     	   .addActionListener(this);
        this.btnDifficile 	   .addActionListener(this);
        this.btnGras	  	   .addActionListener(this);
        this.btnItalique 	   .addActionListener(this);
        this.btnAjouterImage   .addActionListener(this);
        this.btnAjouterRessComp.addActionListener(this);

        switch (q.getDifficulte().getIndice()) {
            case 1 :
                this.btnTresFacile.setBorder ( BorderFactory.createLineBorder(Color.RED  ) );
                break;
            case 2 :
                this.btnFacile    .setBorder ( BorderFactory.createLineBorder(Color.RED) );
                break;
            case 3 :
                this.btnMoyen     .setBorder ( BorderFactory.createLineBorder(Color.RED) );
                break;
            case 4:
                this.btnDifficile .setBorder ( BorderFactory.createLineBorder(Color.RED) );
                break;
        }

        panelNiveau.add(this.btnTresFacile);
        panelNiveau.add(this.btnFacile);
        panelNiveau.add(this.btnMoyen);
        panelNiveau.add(this.btnDifficile);
        panelText  .add(this.btnGras);
        panelText  .add(this.btnItalique);

        panelText.add(lblIntituleQuestion);
        panelText.add(epQuestion);
        panelText.add(lblExpliquationQuestion);
        panelText.add(epExplication);

        panelText.add(this.btnAjouterImage);
        panelText.add(this.btnAjouterRessComp);

        panelLabels .add(lblRessource);
        panelDonnees.add(this.ddlstRessources);
        panelLabels .add(lblNotion);
        panelDonnees.add(this.ddlstNotions);
        panelLabels .add(lblNiveau);
        panelDonnees.add(panelNiveau);

        panelSelection.add(panelLabels , BorderLayout.WEST);
        panelSelection.add(panelDonnees, BorderLayout.CENTER);

        panelCentrale.add(panelSelection);
        panelCentrale.add(panelText);

        add(panelCentrale, BorderLayout.CENTER);

        // Section inférieure
        JPanel panelType = new JPanel(new GridLayout(1, 3, 5, 5));
        panelType.setBorder(BorderFactory.createTitledBorder("Type de Question"));

        JLabel lblType = new JLabel("Type :");
        this.ddlstTypes  = new JComboBox<>(new String[]
                { "Elimination" }
        );

        this.btnConfirmer = new JButton("Confirmer");
        this.btnConfirmer.addActionListener(this);

        panelType.add(lblType);
        panelType.add(this.ddlstTypes);
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
    public void itemStateChanged(ItemEvent e) {}

    /**
     * Methode actionPerformed
     * @param e L'évènement à traiter
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnTresFacile) {
            JOptionPane.showMessageDialog(this, "Difficulté : Très Facile");
            this.difficulte = 1;

            this.btnTresFacile.setBorder ( BorderFactory.createLineBorder(Color.RED  ) );
            this.btnFacile    .setBorder ( BorderFactory.createLineBorder(Color.BLACK) );
            this.btnMoyen     .setBorder ( BorderFactory.createLineBorder(Color.BLACK) );
            this.btnDifficile .setBorder ( BorderFactory.createLineBorder(Color.BLACK) );

        } else if (e.getSource() == this.btnFacile) {
            JOptionPane.showMessageDialog(this, "Difficulté : Facile");
            this.difficulte = 2;
            this.btnTresFacile.setBorder ( BorderFactory.createLineBorder(Color.BLACK) );
            this.btnFacile    .setBorder ( BorderFactory.createLineBorder(Color.RED  ) );
            this.btnMoyen     .setBorder ( BorderFactory.createLineBorder(Color.BLACK) );
            this.btnDifficile .setBorder ( BorderFactory.createLineBorder(Color.BLACK) );

        } else if (e.getSource() == this.btnMoyen) {
            JOptionPane.showMessageDialog(this, "Difficulté : Moyen");
            this.difficulte = 3;
            this.btnTresFacile.setBorder ( BorderFactory.createLineBorder(Color.BLACK) );
            this.btnFacile    .setBorder ( BorderFactory.createLineBorder(Color.BLACK) );
            this.btnMoyen     .setBorder ( BorderFactory.createLineBorder(Color.RED  ) );
            this.btnDifficile .setBorder ( BorderFactory.createLineBorder(Color.BLACK) );

        } else if (e.getSource() == this.btnDifficile) {
            JOptionPane.showMessageDialog(this, "Difficulté : Difficile");
            this.difficulte = 4;
            this.btnTresFacile.setBorder ( BorderFactory.createLineBorder(Color.BLACK) );
            this.btnFacile    .setBorder ( BorderFactory.createLineBorder(Color.BLACK) );
            this.btnMoyen     .setBorder ( BorderFactory.createLineBorder(Color.BLACK) );
            this.btnDifficile .setBorder ( BorderFactory.createLineBorder(Color.RED  ) );
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
                JOptionPane.showMessageDialog(
                        this,
                        "Attribuez des points à la question"
                );
                return;
            }
            if (this.epQuestion.getText() == null){
                JOptionPane.showMessageDialog(
                        this,
                        "Veuillez entrer un énoncer"
                );
                return;
            }
            if (this.difficulte == 0)
            {
                JOptionPane.showMessageDialog(
                        this,
                        "Choisissez une difficulté"
                );
                return;
            }
            String typeSelectionne = (String) this.ddlstTypes.getSelectedItem();

            this.epQuestion. setText( this.passageRtf(this.epQuestion) );
            this.epExplication.setText( this.passageRtf(this.epExplication) );

            if ("Elimination".equals(typeSelectionne)) {
                System.out.println(typeSelectionne);
                Notion n = (Notion)(this.ddlstNotions.getSelectedItem());
                this.q.setNotion(n);
                this.temps  = Integer.parseInt(
                        this.champTemps.getText().substring(0,this.champTemps.getText().indexOf(":")))*60
                        + Integer.parseInt(this.champTemps.getText().substring(this.champTemps.getText().indexOf(":")+1)
                );
                this.q.setTemps(this.temps);
                this.q.setPoint(Integer.parseInt(this.champPoints.getText()));

                PanelModifEliminationReponse panelModifElimRep = new PanelModifEliminationReponse(
                        this.ctrl,this.q, this.hmReponses
                );
                panelModifElimRep.setVisible(true);}

        } if(e.getSource() == this.btnItalique)
        {
            StyledDocument doc;
            int start;
            int end;


            if(this.epExplication.getSelectedText() != null && !this.epExplication.getSelectedText().isEmpty())
            {
                doc = (StyledDocument) this.epExplication.getDocument();
                start = this.epExplication.getSelectionStart();
                end = this.epExplication.getSelectionEnd();

            }else{
                if(this.epQuestion.getSelectedText() != null && !this.epQuestion.getSelectedText().isEmpty())
                {
                    doc = (StyledDocument) this.epQuestion.getDocument();
                    start = this.epQuestion.getSelectionStart();
                    end = this.epQuestion.getSelectionEnd();
                }else{
                    return;
                }
            }

            if (start != end) {

                Element element = doc.getCharacterElement(start);
                AttributeSet attributes = element.getAttributes();
                boolean isItalic = StyleConstants.isItalic(attributes);

                StyleContext context = new StyleContext();
                Style italicStyle = context.addStyle("Italic", null);
                StyleConstants.setItalic(italicStyle, !isItalic);

                doc.setCharacterAttributes(start, end - start, italicStyle, false);
            } else {
                JOptionPane.showMessageDialog(null, "Sélectionnez un texte pour le mettre en Italic.");
            }
            this.epExplication.setSelectionStart(0);
            this.epExplication.setSelectionEnd(0);
            this.epQuestion   .setSelectionStart(0);
            this.epQuestion   .setSelectionEnd(0);
        }

        if(e.getSource() == this.btnGras)
        {
            StyledDocument doc;
            int start;
            int end;

            if(this.epExplication.getSelectedText() != null && !this.epExplication.getSelectedText().isEmpty())
            {
                doc   = (StyledDocument) this.epExplication.getDocument();
                start = this.epExplication.getSelectionStart();
                end   = this.epExplication.getSelectionEnd();

            }else{
                if(this.epQuestion.getSelectedText() != null && (!this.epQuestion.getSelectedText().isEmpty()))
                {
                    doc   = (StyledDocument) this.epQuestion.getDocument();
                    start = this.epQuestion.getSelectionStart();
                    end   = this.epQuestion.getSelectionEnd();
                }else{
                    return;
                }
            }

            if (start != end) {

                Element      element    = doc.getCharacterElement(start);
                AttributeSet attributes = element.getAttributes();
                boolean      isBold     = StyleConstants.isBold(attributes);

                StyleContext context = new StyleContext();
                Style        style   = context.addStyle("Bold", null);
                StyleConstants.setBold(style, !isBold);

                doc.setCharacterAttributes(start, end - start, style, false);
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Sélectionnez un texte pour le mettre en Gras."
                );
            }
            this.epExplication.setSelectionStart(0);
            this.epExplication.setSelectionEnd(0);
            this.epQuestion   .setSelectionStart(0);
            this.epQuestion   .setSelectionEnd(0);
        }

        if(e.getSource() == this.btnAjouterImage)
        {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if(returnValue == JFileChooser.APPROVE_OPTION)
            {
                String tmpVerif = fileChooser.getSelectedFile().getAbsolutePath();

                if(tmpVerif.endsWith(".png") || tmpVerif.endsWith(".jpg") || tmpVerif.endsWith(".jpeg"))
                {
                    this.imageFond = fileChooser.getSelectedFile().getAbsolutePath();
                }else{
                    JOptionPane.showMessageDialog(null, "Vous ne devez choisir que des images en .jpg, .png ou .jpeg", "Information", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        if(e.getSource() == this.btnAjouterRessComp)
        {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if(returnValue == JFileChooser.APPROVE_OPTION)
            {
                this.lstLiens.add(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
    }

    private String passageRtf(JEditorPane editeur)
    {
        //Mise en place du Rtf
        RTFEditorKit rtfKit = new RTFEditorKit();
        StyledDocument doc = (StyledDocument) editeur.getDocument();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
        {
            rtfKit.write(outputStream, doc, 0, doc.getLength());
            return outputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}