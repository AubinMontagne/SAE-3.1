package src.Vue;

import src.Controleur;
import src.Metier.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;
import javax.swing.text.rtf.RTFEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PanelModifQuestion extends JPanel implements ActionListener, ItemListener
{
	private Controleur           ctrl;

	private JComboBox<Ressource> ddlstRessources;
	private JComboBox<Notion>    ddlstNotions;
	private JComboBox<String>    ddlstTypes;
	private JButton              btnTresFacile, btnFacile, btnMoyen, btnDifficile;
	private JButton              btnConfirmer;
	private JButton				 btnGras, btnItalique, btnAjouterImage, btnAjouterRessComp, btnSupprimerFichierComp;
	private JTextField           txtPoints, txtTemps;
	private JEditorPane          epEnonce;
	private JEditorPane          epExplication;

	private ArrayList<Ressource> lstRessources;
	private ArrayList<Notion>    lstNotions;
	private List<String>         lstLiens;
	private Question  			 q;
	private String				 imageFond;
	private String				 notion;
	private int     			 difficulte;
	private int					 temps;
	private int					 points;

	private Border               bordureDefaut;
	private Border               bordureSelect;

	private FrameModifQuestion frameModifQuestion;
	private PanelBanque        panelBanque;

	private String texteTxtEnonce;
	private String texteTxtExplication;


	private static final ImageIcon[] IMAGES_DIFFICULTE =
			{
			new ImageIcon("java/data/Images/imgDif/TF.png"),
			new ImageIcon("java/data/Images/imgDif/F.png"),
			new ImageIcon("java/data/Images/imgDif/M.png"),
			new ImageIcon("java/data/Images/imgDif/D.png")
	};

	public PanelModifQuestion(FrameModifQuestion frameModifQuestion, Controleur ctrl , PanelBanque panelBanque, Question q)
	{
		this.ctrl               = ctrl;
		this.panelBanque        = panelBanque;

		this.frameModifQuestion = frameModifQuestion;
		this.lstRessources      = this.ctrl.getRessources();
		this.lstNotions         = this.ctrl.getNotions();
		this.lstLiens           = new ArrayList<>(5);

		this.q          = q;
		this.notion     = q.getNotion().getNom();
		this.difficulte = q.getDifficulte().getIndice();
		this.temps      = q.getTemps();
		this.points     = q.getPoint();
		System.out.println(this.points);

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
		this.txtPoints.setText(q.getPoint()+"");

		JLabel labelTemps = new JLabel("Temps de réponse (min:sec) :");
		try
		{
			MaskFormatter timeFormatter = new MaskFormatter("##:##");
			timeFormatter.setPlaceholderCharacter('0');
			this.txtTemps = new JFormattedTextField(timeFormatter);
			this.txtTemps.setText((q.getTemps()/60)+""+(q.getTemps()%60));

			this.txtTemps.addPropertyChangeListener("value", evt -> secondeValide());
		}
		catch (ParseException e)
		{
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
		JPanel panelText = new JPanel(new GridLayout(4,1));

		JLabel lblIntituleQuestion = new JLabel("Question : ");
		this.epEnonce = new JEditorPane();
		this.epEnonce.setEditorKit(new StyledEditorKit()); // Permet la gestion des styles
		this.epEnonce.setEditable(true); // Rendre éditable

		JLabel lblExpliquationQuestion = new JLabel("Explication : ");
		this.epExplication = new JEditorPane();
		this.epExplication.setEditorKit(new StyledEditorKit()); // Permet la gestion des styles
		this.epExplication.setEditable(true); // Rendre éditable

		JPanel panelCentrale = new JPanel(new GridLayout(1, 2, 5, 5));
		panelCentrale.setBorder(BorderFactory.createTitledBorder("Sélection"));

		JPanel panelSelection = new JPanel(new BorderLayout());
		JPanel panelLabels    = new JPanel(new GridLayout(3, 1, 5, 5));
		JPanel panelDonnées   = new JPanel(new GridLayout(3, 1, 5, 5));

		// ComboBox de Ressource
		JLabel labelRessource = new JLabel("Ressource :");
		this.ddlstRessources  = new JComboBox<Ressource>();
		for(Ressource ressource : this.lstRessources)
		{
			this.ddlstRessources.addItem(ressource);
		}
		this.ddlstRessources.setSelectedItem(q.getNotion().getRessourceAssociee());
		this.ddlstRessources.addItemListener(this);

		// ComboBox de Notion
		JLabel labelNotion = new JLabel("Notion :");
		this.ddlstNotions  = new JComboBox<>();
		this.lstNotions = this.ctrl.getNotionsParRessource(q.getNotion().getRessourceAssociee());
		for(Notion notion : this.lstNotions)
		{
			this.ddlstNotions.addItem(notion);
		}
		this.ddlstNotions.setSelectedItem(q.getNotion());
		this.ddlstNotions.addItemListener(this);

		JLabel labelNiveau = new JLabel("Difficulté :");
		JPanel panelNiveau = new JPanel(new FlowLayout());

		this.btnTresFacile		     = new JButton(IMAGES_DIFFICULTE[0]);
		this.btnFacile     		     = new JButton(IMAGES_DIFFICULTE[1]);
		this.btnMoyen      		     = new JButton(IMAGES_DIFFICULTE[2]);
		this.btnDifficile  		     = new JButton(IMAGES_DIFFICULTE[3]);

		this.btnGras	   		     = new JButton("Gras");
		this.btnItalique  		     = new JButton("Italic");
		this.btnAjouterImage 	     = new JButton("Ajouter une image");
		this.btnAjouterRessComp      = new JButton("Ajouter des fichiers");
		this.btnSupprimerFichierComp = new JButton("Supprimer les fichiers");


		Dimension d1 = new Dimension( 75,  75);
		Dimension d2 = new Dimension(100, 100);
		this.btnTresFacile		.setPreferredSize(d1);
		this.btnFacile    		.setPreferredSize(d1);
		this.btnMoyen     		.setPreferredSize(d1);
		this.btnDifficile 		.setPreferredSize(d1);

		this.btnGras	  		    .setPreferredSize(d2);
		this.btnItalique 		    .setPreferredSize(d2);
		this.btnAjouterImage	    .setPreferredSize(d2);
		this.btnAjouterRessComp     .setPreferredSize(d2);
		this.btnSupprimerFichierComp.setPreferredSize(d2);

		this.btnTresFacile.setBorder(this.bordureDefaut);
		this.btnFacile    .setBorder(this.bordureDefaut);
		this.btnMoyen     .setBorder(this.bordureDefaut);
		this.btnDifficile .setBorder(this.bordureDefaut);

		switch (this.difficulte)
		{
			case 1 -> this.btnTresFacile.setBorder(this.bordureSelect);
			case 2 -> this.btnFacile    .setBorder(this.bordureSelect);
			case 3 -> this.btnMoyen     .setBorder(this.bordureSelect);
			case 4 -> this.btnDifficile .setBorder(this.bordureSelect);
		}

		this.btnTresFacile.addActionListener(this);
		this.btnFacile    .addActionListener(this);
		this.btnMoyen     .addActionListener(this);
		this.btnDifficile .addActionListener(this);

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
		this.btnAjouterImage		.setFont(new Font("Arial", Font.PLAIN, 10));
		this.btnAjouterRessComp		.setFont(new Font("Arial", Font.PLAIN, 10));
		this.btnSupprimerFichierComp.setFont(new Font("Arial", Font.PLAIN, 10));

		panelNiveau.add(this.btnTresFacile);
		panelNiveau.add(this.btnFacile);
		panelNiveau.add(this.btnMoyen);
		panelNiveau.add(this.btnDifficile);
		
		JPanel panelStyle = new JPanel(new GridLayout(1,2));
		panelStyle.add  (this.btnGras);
		panelStyle.add  (this.btnItalique);

		Border bordPanel =  BorderFactory.createLineBorder(Color.GRAY, 1);

		JPanel panelEnonce = new JPanel(new BorderLayout());
		panelEnonce.setBorder(bordPanel);
		panelEnonce.add(lblIntituleQuestion, BorderLayout.WEST);
		panelEnonce.add(this.epEnonce, BorderLayout.CENTER);

		JPanel panelExplication = new JPanel(new BorderLayout());
		panelExplication.setBorder(bordPanel);
		panelExplication.add(lblExpliquationQuestion, BorderLayout.WEST);
		panelExplication.add(this.epExplication, BorderLayout.CENTER);

		JPanel panelBoutons = new JPanel(new GridLayout(1,3));
		panelBoutons.add(this.btnAjouterImage);
		panelBoutons.add(this.btnAjouterRessComp);
		panelBoutons.add(this.btnSupprimerFichierComp);

		panelText.add(panelStyle);
		panelText.add(panelEnonce);
		panelText.add(panelExplication);
		panelText.add(panelBoutons);

		panelLabels.add (labelRessource);
		panelDonnées.add(this.ddlstRessources);
		panelLabels.add (labelNotion);
		panelDonnées.add(this.ddlstNotions);
		panelLabels.add (labelNiveau);
		panelDonnées.add(panelNiveau);

		panelSelection.add(panelLabels , BorderLayout.WEST);
		panelSelection.add(panelDonnées, BorderLayout.CENTER);

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
		if (q instanceof QCM)
		{
			if (((QCM)(q)).estVraiouFaux())
			{
				this.ddlstTypes.setSelectedIndex(0);
			}
			else
			{
				this.ddlstTypes.setSelectedIndex(1);
			}
		}
		else
		{
			if ( q instanceof AssociationElement)
			{
				this.ddlstTypes.setSelectedIndex(2);
			}
			else
			{
				this.ddlstTypes.setSelectedIndex(3);
			}
		}

		this.btnConfirmer = new JButton("Confirmer");
		this.btnConfirmer.addActionListener(this);
		
		panelType.add(labelType);
		panelType.add(this.ddlstTypes);
		panelType.add(this.btnConfirmer);

		add(panelType, BorderLayout.SOUTH);
		setVisible(true);
	}

	// Methode
	private void secondeValide()
	{
		String timeText = this.txtTemps.getText();

		if (timeText.matches("\\d{2}:\\d{2}"))
		{
			int minutes  = Integer.parseInt(timeText.substring(0, 2));
			int secondes = Integer.parseInt(timeText.substring(3, 5));

			// Vérifie que les secondes ne dépassent pas 59
			if (secondes >= 60)
			{
				secondes  = 59;
				this.txtTemps.setText(String.format("%02d:%02d", minutes, secondes));
				JOptionPane.showMessageDialog(
						this, "Les secondes ne peuvent pas dépasser 59. Ajustement automatique effectué."
				);

			}

			// (Optionnel) Vérifie que les minutes ne dépassent pas une certaine limite
			if (minutes > 99)
			{
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
	public void itemStateChanged(ItemEvent e)
	{
		if (e.getSource() == this.ddlstRessources && e.getStateChange() == ItemEvent.SELECTED)
		{

			int index = ddlstRessources.getSelectedIndex();
			this.ddlstNotions.removeAllItems();
			for (Notion notion : this.ctrl.getNotionsParRessource(this.lstRessources.get(index)))
			{
				this.ddlstNotions.addItem(notion);
			}

		}
		else if (e.getSource() == this.ddlstNotions && e.getStateChange() == ItemEvent.SELECTED)
		{
			int indexRessource  = this.ddlstRessources.getSelectedIndex();
			int indexNotion     = this.ddlstNotions.getSelectedIndex();
			this.notion = ((Notion)(this.ddlstNotions.getSelectedItem() )).getNom();
			if (indexRessource >= 0 && indexNotion >= 0)
			{
				this.btnTresFacile.setIcon(IMAGES_DIFFICULTE[0]);
				this.btnFacile    .setIcon(IMAGES_DIFFICULTE[1]);
				this.btnMoyen     .setIcon(IMAGES_DIFFICULTE[2]);
				this.btnDifficile .setIcon(IMAGES_DIFFICULTE[3]);
			}
		}
	}

	public void fermer()
	{
		if(this.panelBanque != null)
		{
			this.panelBanque.maj();
		}
		this.frameModifQuestion.dispose();
	}

	/**
	 * Methode actionPerformed
	 * @param e L'évènement à traiter
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.btnTresFacile)
		{
			JOptionPane.showMessageDialog(this, "Difficulté : Très Facile");
			this.difficulte = 1;

			this.btnTresFacile.setBorder(this.bordureSelect);
			this.btnFacile    .setBorder(this.bordureDefaut);
			this.btnMoyen     .setBorder(this.bordureDefaut);
			this.btnDifficile .setBorder(this.bordureDefaut);

		}
		else if (e.getSource() == this.btnFacile)
		{
			JOptionPane.showMessageDialog(this, "Difficulté : Facile");
			this.difficulte = 2;
			this.btnTresFacile.setBorder(this.bordureDefaut);
			this.btnFacile    .setBorder(this.bordureSelect);
			this.btnMoyen     .setBorder(this.bordureDefaut);
			this.btnDifficile .setBorder(this.bordureDefaut);

		}
		else if (e.getSource() == this.btnMoyen)
		{
			JOptionPane.showMessageDialog(this, "Difficulté : Moyen");
			this.difficulte = 3;
			this.btnTresFacile.setBorder(this.bordureDefaut);
			this.btnFacile    .setBorder(this.bordureDefaut);
			this.btnMoyen     .setBorder(this.bordureSelect);
			this.btnDifficile .setBorder(this.bordureDefaut);

		}
		else if (e.getSource() == this.btnDifficile)
		{
			JOptionPane.showMessageDialog(this, "Difficulté : Difficile");
			this.difficulte = 4;
			this.btnTresFacile.setBorder(this.bordureDefaut);
			this.btnFacile    .setBorder(this.bordureDefaut);
			this.btnMoyen     .setBorder(this.bordureDefaut);
			this.btnDifficile .setBorder(this.bordureSelect);
		}

		if (e.getSource() == this.btnConfirmer)
		{
			String cheminDossier = "java/data/" + this.ctrl.getNotionByNom(this.notion).getRessourceAssociee().getNom()
					+ "/" + this.ctrl.getNotionByNom(notion).getNom() + "/Question " + this.q.getId();

			if (this.txtPoints.getText().equals(""))
			{
				JOptionPane.showMessageDialog(
						this,
						"Attribuez des points à la question"
				);
				return;
			}
			if (this.epEnonce.getText() == null)
			{
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

			this.epEnonce. setText( this.passageRtf(this.epEnonce) );
			this.epExplication.setText( this.passageRtf(this.epExplication) );

			this.q.setPoint(Integer.parseInt(this.txtPoints.getText()));
			this.q.setTemps(Integer.parseInt(
					this.txtTemps.getText().substring(0,this.txtTemps.getText().indexOf(":"))
			)
					*60 + Integer.parseInt(
					this.txtTemps.getText().substring(
							this.txtTemps.getText().indexOf(":")+1
					)
			));
			this.points = Integer.parseInt(this.txtPoints.getText());

			this.q.setDifficulte(Difficulte.getDifficulteByIndice(this.difficulte));
			this.q.setDossierChemin(cheminDossier);

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

				PanelModifQCM panelModifQCM = new PanelModifQCM(
						this,
						this.ctrl,
						this.imageFond,
						this.lstLiens,
						this.panelBanque,
						this.epEnonce,
						this.epExplication,
						this.q,
						true

				);
				panelModifQCM.setVisible(true);
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

				PanelModifQCM panelModifQCM = new PanelModifQCM(
						this,
						this.ctrl,
						this.imageFond,
						this.lstLiens,
						this.panelBanque,
						this.epEnonce,
						this.epExplication,
						this.q,
						false
				);

				panelModifQCM.setVisible(true);
			} else if("EntiteAssociation".equals(typeSelectionne)) {
				PanelModifEntiteAssociation panelModifEntiteAssociation = new PanelModifEntiteAssociation(
						this,
						this.ctrl,
						this.imageFond,
						this.lstLiens,
						this.panelBanque,
						this.epEnonce,
						this.epExplication,
						this.q
				);

				panelModifEntiteAssociation.setVisible(true);
			} else if ("Elimination".equals(typeSelectionne)){
				PanelModifElimination panelModifElimination = new PanelModifElimination(
						this,
						this.ctrl,
						this.imageFond,
						this.lstLiens,
						this.panelBanque,
						this.epEnonce,
						this.epExplication,
						this.q
				);
				panelModifElimination.setVisible(true);
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
					String nomFichier = tmpVerif.substring(tmpVerif.lastIndexOf("/") + 1);
					this.btnAjouterImage.setText("Ajoutez une image \n" + nomFichier);
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


	public String getTexteTxtEnonce     ()
	{
		return this.texteTxtEnonce;
	}
	public String getTexteTxtExplication()
	{
		return this.texteTxtEnonce;
	}

	public void setTexteTxtEnonce     (String s)
	{
		this.texteTxtEnonce = s;
	}
	public void setTexteTxtExplication(String s)
	{
		this.texteTxtExplication = s;
	}

	public JEditorPane getEditeurEnonce     ()
	{
		return this.epEnonce;
	}
	public JEditorPane getEditeurExplication()
	{
		return this.epExplication;
	}


	/*Methode*/

	public String passageRtf(JEditorPane editeur)
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