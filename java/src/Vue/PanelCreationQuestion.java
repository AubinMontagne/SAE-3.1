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
import java.awt.event.ItemListener;

import java.io.ByteArrayOutputStream;

import java.text.NumberFormat;
import java.text.ParseException;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.text.*;
import javax.swing.text.rtf.RTFEditorKit;
import java.awt.Color;

import src.Controleur;
import src.Metier.Notion;
import src.Metier.Question;
import src.Metier.Ressource;
import java.util.List;

public class PanelCreationQuestion extends JPanel implements ActionListener, ItemListener {
	private Controleur           ctrl;

	private JComboBox<Ressource> ddlstRessources;
	private JComboBox<Notion>    ddlstNotions;
	private JComboBox<String>    ddlstTypes;
	private JButton              btnTresFacile, btnFacile, btnMoyen, btnDifficile;
	private JButton              btnConfirmer;
	private JButton				 btnGras, btnItalique, btnAjouterImage, btnAjouterRessComp, btnSupprimerFichierComp;
	private JTextField           txtPoints, txtTemps;
	private JEditorPane epEnonce;
	private JEditorPane epExplication;

	private ArrayList<Ressource> lstRessources;
	private ArrayList<Notion>    lstNotions;
	private List<String>         lstLiens;
	private String				 imageFond;
	private String 		 		 notion;
	private int    		 		 difficulte;
	private int    		 		 temps;
	private int    		 		 points;
	private Border               bordureDefaut;
	private Border               bordureSelect;

	private FrameCreationQuestion frameCreationQuestion;
	private PanelBanque           panelBanque;

	private String texteTxtEnonce;
	private String texteTxtExplication;

	private static final ImageIcon[] IMAGES_DIFFICULTE = {
			new ImageIcon("java/data/Images/imgDif/TF.png"),
			new ImageIcon("java/data/Images/imgDif/F.png"),
			new ImageIcon("java/data/Images/imgDif/M.png"),
			new ImageIcon("java/data/Images/imgDif/D.png")
	};

	public PanelCreationQuestion(FrameCreationQuestion frameCreationQuestion, Controleur ctrl ,PanelBanque panelBanque, Ressource resChoisie, Notion notionChoisie) {
		this.ctrl                  = ctrl;
		this.panelBanque           = panelBanque;
		this.frameCreationQuestion = frameCreationQuestion;
		this.lstRessources         = this.ctrl.getRessources();
		this.lstNotions            = this.ctrl.getNotions();
		this.lstLiens              = new ArrayList<>(5);

		this.bordureDefaut = BorderFactory.createLineBorder(Color.BLACK, 5);
		this.bordureSelect = BorderFactory.createLineBorder(Color.RED  , 5);

		this.texteTxtEnonce 	 = "";
        this.texteTxtExplication = "";

		setLayout(new BorderLayout());

		UIManager.put("Label.font", new Font("Arial", Font.BOLD, 11));

		// Section supérieure
		JPanel panelConfiguration = new JPanel(new GridLayout(2, 2, 5, 5));
		panelConfiguration.setBorder(BorderFactory.createTitledBorder("Configuration"));

		JLabel labelPoints = new JLabel("Nombre de points :");

		NumberFormat format = NumberFormat.getIntegerInstance();
		format.setGroupingUsed(false);

		NumberFormatter numberFormatter = new NumberFormatter(format);
		numberFormatter.setAllowsInvalid(true);
		numberFormatter.setMinimum(0);
		numberFormatter.setOverwriteMode(true);

		this.txtPoints  = new JFormattedTextField(numberFormatter);
		this.txtPoints.setColumns(10);

		JLabel labelTemps = new JLabel("Temps de réponse (min:sec) :");
		try {
			MaskFormatter timeFormatter = new MaskFormatter("##:##");
			timeFormatter.setPlaceholderCharacter('0');
			this.txtTemps = new JFormattedTextField(timeFormatter);
			this.txtTemps.setText("00:30"); // Valeur initiale

			this.txtTemps.addPropertyChangeListener("value", evt -> secondeValide());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		this.txtTemps.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e) {
				secondeValide();
			}
		});

		panelConfiguration.add(labelPoints);
		panelConfiguration.add(this.txtPoints);
		panelConfiguration.add(labelTemps);
		panelConfiguration.add(this.txtTemps);

		this.add(panelConfiguration, BorderLayout.NORTH);

		// Section centrale
		JPanel panelText = new JPanel(new GridLayout(5,1));

		JLabel lblIntituleQuestion = new JLabel("Question : ");
		this.epEnonce = new JEditorPane();
		this.epEnonce.setEditorKit(new StyledEditorKit()); // Permet la gestion des styles
		this.epEnonce.setEditable(true); // Rendre éditable
		this.epEnonce.setFont(new Font("Arial", Font.PLAIN, 12));

		JLabel lblExpliquationQuestion = new JLabel("Explication : ");
		this.epExplication = new JEditorPane();
		this.epExplication.setEditorKit(new StyledEditorKit()); // Permet la gestion des styles
		this.epExplication.setEditable(true); // Rendre éditable
		this.epExplication.setFont(new Font("Arial", Font.PLAIN, 12));


		JPanel panelCentrale = new JPanel(new GridLayout(1, 2, 5, 5));
		panelCentrale.setBorder(BorderFactory.createTitledBorder("Sélection"));

		JPanel panelSelection = new JPanel(new BorderLayout());
		JPanel panelLabels    = new JPanel(new GridLayout(3, 1, 5, 5));
		JPanel panelDonnees   = new JPanel(new GridLayout(3, 1, 5, 5));

		// ComboBox de Ressource
		Ressource placeHolder = new Ressource(" "," ");

		JLabel labelRessource = new JLabel("Ressource :");
		this.ddlstRessources  = new JComboBox<Ressource>();
		for(Ressource ressource : this.lstRessources)
		{
			this.ddlstRessources.addItem(ressource);
		}
		this.ddlstRessources.insertItemAt(placeHolder, 0);
		this.ddlstRessources.setSelectedIndex(0);
		this.ddlstRessources.addItemListener(this);

		// ComboBox de Notion
		JLabel labelNotion = new JLabel("Notion :");
		this.ddlstNotions  = new JComboBox<>();
		this.lstNotions = this.ctrl.getNotionsParRessource(this.lstRessources.getFirst());
		for(Notion notion : this.lstNotions)
		{
			this.ddlstNotions.addItem(notion);
		}
		this.ddlstNotions.insertItemAt(new Notion(" ",placeHolder), 0);
		this.ddlstNotions.setSelectedIndex(0);
		this.ddlstNotions.setEnabled(false);
		this.ddlstNotions.addItemListener(this);

		JLabel labelNiveau = new JLabel("Difficulté :");
		JPanel panelNiveau = new JPanel(new FlowLayout());

		this.btnTresFacile			 = new JButton(IMAGES_DIFFICULTE[0]);
		this.btnFacile     			 = new JButton(IMAGES_DIFFICULTE[1]);
		this.btnMoyen      			 = new JButton(IMAGES_DIFFICULTE[2]);
		this.btnDifficile  			 = new JButton(IMAGES_DIFFICULTE[3]);
		this.btnGras	   			 = new JButton("Gras");
		this.btnItalique  			 = new JButton("Italique");
		this.btnAjouterImage 		 = new JButton("Ajouter une image");
		this.btnAjouterRessComp 	 = new JButton("Ajouter des fichiers complémentaires");
		this.btnSupprimerFichierComp = new JButton("Supprimer les fichiers complémentaires");

		Dimension d1 = new Dimension( 75,  75);
		Dimension d2 = new Dimension(100, 100);
		this.btnTresFacile			.setPreferredSize(d1);
		this.btnFacile    			.setPreferredSize(d1);
		this.btnMoyen     			.setPreferredSize(d1);
		this.btnDifficile 			.setPreferredSize(d1);
		this.btnGras	  			.setPreferredSize(d2);
		this.btnItalique 			.setPreferredSize(d2);
		this.btnAjouterImage		.setPreferredSize(d2);
		this.btnAjouterRessComp 	.setPreferredSize(d2);
		this.btnSupprimerFichierComp.setPreferredSize(d2);

		this.btnTresFacile.setBorder(this.bordureDefaut);
		this.btnFacile    .setBorder(this.bordureDefaut);
		this.btnMoyen     .setBorder(this.bordureDefaut);
		this.btnDifficile .setBorder(this.bordureDefaut);

		this.btnTresFacile.setEnabled(false);
		this.btnFacile    .setEnabled(false);
		this.btnMoyen     .setEnabled(false);
		this.btnDifficile .setEnabled(false);

		this.btnTresFacile			.addActionListener(this);
		this.btnFacile    			.addActionListener(this);
		this.btnMoyen     			.addActionListener(this);
		this.btnDifficile 			.addActionListener(this);
		this.btnGras	  			.addActionListener(this);
		this.btnItalique 			.addActionListener(this);
		this.btnAjouterImage		.addActionListener(this);
		this.btnAjouterRessComp 	.addActionListener(this);
		this.btnSupprimerFichierComp.addActionListener(this);

		this.btnGras				.setBackground(new Color(163,206,250));
		this.btnItalique			.setBackground(new Color(163,206,250));
		this.btnAjouterImage		.setBackground(new Color(163,206,250));
		this.btnAjouterRessComp		.setBackground(new Color(163,206,250));
		this.btnSupprimerFichierComp.setBackground(new Color(163,206,250));


		this.btnGras				.setFont(new Font("Arial", Font.PLAIN, 22));
		this.btnItalique			.setFont(new Font("Arial", Font.PLAIN, 22));
		this.btnAjouterImage		.setFont(new Font("Arial", Font.PLAIN, 22));
		this.btnAjouterRessComp		.setFont(new Font("Arial", Font.PLAIN, 22));
		this.btnSupprimerFichierComp.setFont(new Font("Arial", Font.PLAIN, 22));

		panelNiveau.add(this.btnTresFacile);
		panelNiveau.add(this.btnFacile);
		panelNiveau.add(this.btnMoyen);
		panelNiveau.add(this.btnDifficile);

		JPanel panelStyle = new JPanel(new GridLayout(1,2));
		panelStyle.add  (this.btnGras);
		panelStyle.add  (this.btnItalique);

		JPanel panelEnonce = new JPanel(new GridLayout(1,2));
		panelEnonce.add(lblIntituleQuestion);
		panelEnonce.add(this.epEnonce);

		JPanel panelExplication = new JPanel(new GridLayout(1,2));
		panelExplication.add(lblExpliquationQuestion);
		panelExplication.add(this.epExplication);

		JPanel panelBoutons = new JPanel(new GridLayout(1,3));
		panelBoutons.add(this.btnAjouterImage);
		panelBoutons.add(this.btnAjouterRessComp);
		panelBoutons.add(this.btnSupprimerFichierComp);

		panelText.add(panelStyle);
		panelText.add(panelEnonce);
		panelText.add(new JSeparator());
		panelText.add(panelExplication);
		panelText.add(panelBoutons);

		panelLabels.add (labelRessource);
		panelDonnees.add(this.ddlstRessources);
		panelLabels.add (labelNotion);
		panelDonnees.add(this.ddlstNotions);
		panelLabels.add (labelNiveau);
		panelDonnees.add(panelNiveau);

		panelSelection.add(panelLabels, BorderLayout.WEST);
		panelSelection.add(panelDonnees, BorderLayout.CENTER);

		panelCentrale.add(panelSelection);
		panelCentrale.add(panelText);

		add(panelCentrale, BorderLayout.CENTER);

		// Section inférieure
		JPanel panelType = new JPanel(new GridLayout(1, 3, 5, 5));
		panelType.setBorder(BorderFactory.createTitledBorder("Type de Question"));

		JLabel labelType = new JLabel("Type :");
		this.ddlstTypes  = new JComboBox<>(new String[]
				{ "QCM REP. UNIQUE","QCM REP. MULTIPLE", "EntiteAssociation","Elimination" }
		);

		this.btnConfirmer = new JButton("Confirmer");
		this.btnConfirmer.addActionListener(this);

		panelType.add(labelType);
		panelType.add(this.ddlstTypes);
		panelType.add(this.btnConfirmer);

		add(panelType, BorderLayout.SOUTH);
		setVisible(true);

		if (resChoisie != null && !resChoisie.getNom().equals(" ")) {
			this.ddlstRessources.removeItemAt(0);
			this.lstRessources  .removeFirst();
			
			this.ddlstRessources.setSelectedItem(resChoisie);
			
			this.ddlstNotions.removeAllItems();
			for (Notion notion : this.ctrl.getNotionsParRessource(resChoisie)) {
				this.ddlstNotions.addItem(notion);
			}
			this.ddlstNotions.setSelectedItem(notionChoisie);
			this.ddlstNotions.setEnabled(true);
		}
	}

	// Methode
	private void secondeValide() {
		String timeText = this.txtTemps.getText();

		if (timeText.matches("\\d{2}:\\d{2}")) {
			int minutes  = Integer.parseInt(timeText.substring(0, 2));
			int secondes = Integer.parseInt(timeText.substring(3, 5));

			// Vérifie que les secondes ne dépassent pas 59
			if (secondes >= 60) {
				secondes  = 59;
				this.txtTemps.setText(String.format("%02d:%02d", minutes, secondes));
				JOptionPane.showMessageDialog(
						this, "Les secondes ne peuvent pas dépasser 59. Ajustement automatique effectué."
				);

			}

			// (Optionnel) Vérifie que les minutes ne dépassent pas une certaine limite
			if (minutes > 99) {
				minutes = 99;

				this.txtTemps.setText(String.format("%02d:%02d", minutes, secondes));
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
		if (e.getSource() == this.ddlstRessources && e.getStateChange() == ItemEvent.SELECTED) {
			if (this.ddlstRessources.getItemAt(0).getNom().equals(" ")){
				this.ddlstRessources.removeItemAt(0);
				this.lstRessources     .remove(0);
				this.lstNotions        .remove(0);
			}

			int index = ddlstRessources.getSelectedIndex();
			this.ddlstNotions.removeAllItems();
			for (Notion notion : this.ctrl.getNotionsParRessource(this.lstRessources.get(index))) {
				this.ddlstNotions.addItem(notion);
			}
			ddlstNotions.setEnabled(true);
		} else if (e.getSource() == this.ddlstNotions && e.getStateChange() == ItemEvent.SELECTED) {
			int indexRessource  = this.ddlstRessources.getSelectedIndex();
			int indexNotion     = this.ddlstNotions.getSelectedIndex();
			this.notion = ( (Notion)(this.ddlstNotions.getSelectedItem() )).getNom();
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

	public void actualiser(){
		if(this.panelBanque != null) {
			this.panelBanque.maj();
			new FrameCreationQuestion(
					this.ctrl,
					this.panelBanque,
					(Ressource) (this.ddlstRessources.getSelectedItem()),
					(Notion) (this.ddlstNotions.getSelectedItem() )
			);
		} else {
			new FrameCreationQuestion(this.ctrl, null, null, null);
		}

		this.frameCreationQuestion.dispose();
	}

	/**
	 * Methode actionPerformed
	 * @param e L'évènement à traiter
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnTresFacile) {
			this.difficulte = 1;
			this.btnTresFacile.setBorder(this.bordureSelect);
			this.btnFacile    .setBorder(this.bordureDefaut);
			this.btnMoyen     .setBorder(this.bordureDefaut);
			this.btnDifficile .setBorder(this.bordureDefaut);

		} else if (e.getSource() == this.btnFacile) {
			this.difficulte = 2;
			this.btnTresFacile.setBorder(this.bordureDefaut);
			this.btnFacile    .setBorder(this.bordureSelect);
			this.btnMoyen     .setBorder(this.bordureDefaut);
			this.btnDifficile .setBorder(this.bordureDefaut);

		} else if (e.getSource() == this.btnMoyen) {
			this.difficulte = 3;
			this.btnTresFacile.setBorder(this.bordureDefaut);
			this.btnFacile    .setBorder(this.bordureDefaut);
			this.btnMoyen     .setBorder(this.bordureSelect);
			this.btnDifficile .setBorder(this.bordureDefaut);

		} else if (e.getSource() == this.btnDifficile) {
			this.difficulte = 4;
			this.btnTresFacile.setBorder(this.bordureDefaut);
			this.btnFacile    .setBorder(this.bordureDefaut);
			this.btnMoyen     .setBorder(this.bordureDefaut);
			this.btnDifficile .setBorder(this.bordureSelect);
		}

		if (e.getSource() == this.btnConfirmer) {
			int idMax = 0;

			for(Question q : ctrl.getQuestionsParRessource( (Ressource)(this.ddlstRessources.getSelectedItem()) ))
			{
				if(q.getId() > idMax)
				{
					idMax = q.getId();
				}
			}
			idMax++;


			String cheminDossier = "java/data/" + this.ctrl.getNotionByNom(this.notion).getRessourceAssociee().getNom()
					+ "/" + this.ctrl.getNotionByNom(notion).getNom() + "/Question " + idMax;

			if (this.txtPoints.getText().equals(""))
			{
				JOptionPane.showMessageDialog(
						this,
						"Attribuez des points à la question"
				);
				return;
			}
			if (this.epEnonce.getText() == null){
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

			if(this.epEnonce.getText().contains(";"))
			{
				JOptionPane.showMessageDialog(
						this,
						"Les ; ne sont pas autorisés."
				);
				return;
			}

			String typeSelectionne = (String) this.ddlstTypes.getSelectedItem();


			if ("QCM REP. UNIQUE".equals(typeSelectionne)) {
				Notion n = (Notion)(this.ddlstNotions.getSelectedItem());
				this.notion = n.getNom();
				this.temps  = Integer.parseInt(
						this.txtTemps.getText().substring(
								0,this.txtTemps.getText().indexOf(":"))
						)*60
						+ Integer.parseInt(
								this.txtTemps.getText().substring(
										this.txtTemps.getText().indexOf(":")+1
								)
				);
				this.points = Integer.parseInt(this.txtPoints.getText());

				PanelQCM panelQCM = new PanelQCM(
						this,
						this.ctrl,
						cheminDossier,
						this.imageFond,
						this.lstLiens,
						this.difficulte,
						this.notion,
						this.points,
						this.temps,
						this.panelBanque,
						true,
						this.epEnonce,
						this.epExplication,
						idMax

				);
				panelQCM.setVisible(true);
			} else if ("QCM REP. MULTIPLE".equals(typeSelectionne)) {
				Notion n = (Notion)(this.ddlstNotions.getSelectedItem());
				this.notion = n.getNom();
				this.temps  = Integer.parseInt(
						this.txtTemps.getText().substring(0,this.txtTemps.getText().indexOf(":"))
						)
						*60 + Integer.parseInt(
								this.txtTemps.getText().substring(
										this.txtTemps.getText().indexOf(":")+1
								)
				);
				this.points = Integer.parseInt(this.txtPoints.getText());

				PanelQCM panelQCM = new PanelQCM(
						this,
						this.ctrl,
						cheminDossier,
						this.imageFond,
						this.lstLiens,
						this.difficulte,
						this.notion,
						this.points,
						this.temps,
						this.panelBanque,
						false,
						this.epEnonce,
						this.epExplication,
						idMax
				);

				panelQCM.setVisible(true);
			} else if("EntiteAssociation".equals(typeSelectionne)) {

				PanelEntiteAssociation panelEntiteAssociation = new PanelEntiteAssociation(
						this,
						this.ctrl,
						cheminDossier,
						this.imageFond,
						this.lstLiens,
						this.difficulte,
						this.notion,
						this.points,
						this.temps,
						this.panelBanque,
						this.epEnonce,
						this.epExplication,
						idMax
				);

				panelEntiteAssociation.setVisible(true);
			} else if ("Elimination".equals(typeSelectionne)){

				PanelElimination panelElimination = new PanelElimination(
						this,
						this.ctrl,
						cheminDossier,
						this.imageFond,
						this.lstLiens,
						this.difficulte,
						this.notion,
						this.points,
						this.temps,
						this.panelBanque,
						this.epEnonce,
						this.epExplication,
						idMax
				);
				panelElimination.setVisible(true);
			}
		}


		if(e.getSource() == this.btnItalique)
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
				if(this.epEnonce.getSelectedText() != null &&
						!this.epEnonce.getSelectedText().isEmpty())
				{
					doc = (StyledDocument) this.epEnonce.getDocument();
					start = this.epEnonce.getSelectionStart();
					end = this.epEnonce.getSelectionEnd();
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
				JOptionPane.showMessageDialog(
						null, "Sélectionnez un texte pour le mettre en Italic."
				);
			}
            this.epExplication.setSelectionStart(0);
            this.epExplication.setSelectionEnd(0);
            this.epEnonce.setSelectionStart(0);
            this.epEnonce.setSelectionEnd(0);
		}


		if(e.getSource() == this.btnGras)
		{
			StyledDocument doc;
			int start;
			int end;

			if(this.epExplication.getSelectedText() != null &&
					!this.epExplication.getSelectedText().isEmpty())
			{
				doc = (StyledDocument) this.epExplication.getDocument();
				start = this.epExplication.getSelectionStart();
				end = this.epExplication.getSelectionEnd();

			}else{
				if(this.epEnonce.getSelectedText() != null &&
						(!this.epEnonce.getSelectedText().isEmpty()))
				{
					doc = (StyledDocument) this.epEnonce.getDocument();
					start = this.epEnonce.getSelectionStart();
					end = this.epEnonce.getSelectionEnd();
				}else{
					return;
				}
			}

			if (start != end) {

				Element element = doc.getCharacterElement(start);
				AttributeSet attributes = element.getAttributes();
				boolean isBold = StyleConstants.isBold(attributes);

				StyleContext context = new StyleContext();
				Style style = context.addStyle("Bold", null);
				StyleConstants.setBold(style, !isBold);

				doc.setCharacterAttributes(start, end - start, style, false);
			} else {
				JOptionPane.showMessageDialog(
						null, "Sélectionnez un texte pour le mettre en Gras."
				);
			}
			this.epExplication.setSelectionStart(0);
			this.epExplication.setSelectionEnd(0);
            this.epEnonce.setSelectionStart(0);
            this.epEnonce.setSelectionEnd(0);
		}


		if(e.getSource() == this.btnAjouterImage)  {
			JFileChooser fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(null);

			if(returnValue == JFileChooser.APPROVE_OPTION)  {
				String tmpVerif = fileChooser.getSelectedFile().getAbsolutePath();

				if(tmpVerif.endsWith(".png") ||
						tmpVerif.endsWith(".jpg") ||
						tmpVerif.endsWith(".jpeg") ) {

					this.imageFond = fileChooser.getSelectedFile().getAbsolutePath();
					String nomFichier = tmpVerif.substring(tmpVerif.lastIndexOf("\\") + 1);
					this.btnAjouterImage.setText("Ajoutez une image : \n" + nomFichier);
				} else {
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
				String tmpChemin  = fileChooser.getSelectedFile().getAbsolutePath();
				String nomFichier = tmpChemin.substring(tmpChemin.lastIndexOf("/") + 1);
				this.btnAjouterImage.setText("Ajouter des fichiers complémentaires\n" + nomFichier);
			}
		}


		if(e.getSource() == this.btnSupprimerFichierComp)
		{
			this.imageFond  = "";
			this.lstLiens = new ArrayList<String>();

			this.btnAjouterImage   .setText("Ajouter une image");
			this.btnAjouterRessComp.setText("Ajouter des fichiers complémentaires");
		}
	}

	public String getTexteTxtEnonce()
	{
		return this.texteTxtEnonce;
	}

	public String getTexteTxtExplication()
	{
		return this.texteTxtEnonce;
	}

	public void setTexteTxtEnonce(String s)
	{
		this.texteTxtEnonce = s;
	}

	public void setTexteTxtExplication(String s)
	{
		this.texteTxtExplication = s;
	}

	public JEditorPane getEditeurEnonce()
	{
		return this.epEnonce;
	}

	public JEditorPane getEditeurExplication()
	{
		return this.epExplication;
	}


	public String passageRtf(JEditorPane editeur)
	{
		//Mise en place du Rtf
		RTFEditorKit rtfKit = new RTFEditorKit();
		StyledDocument doc = (StyledDocument) editeur.getDocument();

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		{
			rtfKit.write(outputStream, doc, 0, doc.getLength());
			return outputStream.toString();
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}