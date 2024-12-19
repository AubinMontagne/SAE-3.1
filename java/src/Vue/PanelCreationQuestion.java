package src.Vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import src.Controleur;
import src.Metier.Notion;
import src.Metier.Ressource;

public class PanelCreationQuestion extends JPanel implements ActionListener, ItemListener {
	private Controleur           ctrl;
	private JComboBox<Ressource> listeRessources;
	private JComboBox<Notion>    listeNotions;
	private JButton              btnTresFacile, btnFacile, btnMoyen, btnDifficile;
	private JButton              btnConfirmer;
	private JComboBox<String>    listeTypes;
	private JLabel               labelMessage, labelResultat;
	private ArrayList<Ressource> ressources;
	private ArrayList<Notion>    notions;

	private JTextField champPoints;
	private JTextField champTemps;
	// A envoyer aux 3 autres panels

	private int    difficulte;
	private String notion;
	private int    temps;
	private int    points;

	PanelBanque panelBanque;

	private static final ImageIcon[] IMAGES_DIFFICULTE = {
			new ImageIcon("java/data/Images/imgDif/TF.png"),
			new ImageIcon("java/data/Images/imgDif/F.png"),
			new ImageIcon("java/data/Images/imgDif/M.png"),
			new ImageIcon("java/data/Images/imgDif/D.png")
	};

	// Constructeur
	/**
	 * Constructeur de la class PanelCreationQuestion
	 * @param ctrl	Le contrôleur
	 */
	public PanelCreationQuestion(Controleur ctrl, PanelBanque panelBanque){
		this.ctrl             = ctrl;
		this.panelBanque      = panelBanque;
		this.ressources       = this.ctrl.getRessources();
		this.notions          = this.ctrl.getNotions();
		Ressource placeHolder = new Ressource("PlaceHolder","PlaceHolder");

		this.ressources.add(0,placeHolder);
		this.notions   .add(0,new Notion("PlaceHolder",placeHolder));

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

		this.champPoints  = new JFormattedTextField(numberFormatter);
		this.champPoints.setColumns(10);
		JLabel labelTemps = new JLabel("Temps de réponse (min:sec) :");

		try {
			MaskFormatter timeFormatter = new MaskFormatter("##:##");
			timeFormatter.setPlaceholderCharacter('0');
			this.champTemps = new JFormattedTextField(timeFormatter);
			this.champTemps.setText("00:30"); // Valeur initiale

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

		add(panelConfiguration, BorderLayout.NORTH);

		// Section centrale
		JPanel panelSelection = new JPanel(new GridLayout(3, 2, 5, 5));
		panelSelection.setBorder(BorderFactory.createTitledBorder("Sélection"));

		JLabel labelRessource = new JLabel("Ressource :");
		this.listeRessources  = new JComboBox<Ressource>();
		for(Ressource ressource : this.ressources)
		{
			this.listeRessources.addItem(ressource);
		}
		this.listeRessources.setSelectedIndex(0);

		this.listeRessources.addItemListener(this);

		JLabel labelNotion = new JLabel("Notion :");
		this.listeNotions  = new JComboBox<>();

		this.notions = this.ctrl.getNotionsParRessource(this.ressources.get(0));
		for(Notion notion : this.notions)
		{
			this.listeNotions.addItem(notion);
		}
		this.listeNotions.setSelectedIndex(0);

		this.listeNotions.setEnabled(false);
		this.listeNotions.addItemListener(this);

		JLabel labelNiveau = new JLabel("Difficulté :");
		JPanel panelNiveau = new JPanel(new FlowLayout());

		this.btnTresFacile = new JButton(IMAGES_DIFFICULTE[0]);
		this.btnFacile     = new JButton(IMAGES_DIFFICULTE[1]);
		this.btnMoyen      = new JButton(IMAGES_DIFFICULTE[2]);
		this.btnDifficile  = new JButton(IMAGES_DIFFICULTE[3]);

		this.btnTresFacile.setPreferredSize(new Dimension(100, 100));
		this.btnFacile    .setPreferredSize(new Dimension(100, 100));
		this.btnMoyen     .setPreferredSize(new Dimension(100, 100));
		this.btnDifficile .setPreferredSize(new Dimension(100, 100));

		this.btnTresFacile.setEnabled(false);
		this.btnFacile    .setEnabled(false);
		this.btnMoyen     .setEnabled(false);
		this.btnDifficile .setEnabled(false);

		this.btnTresFacile.addActionListener(this);
		this.btnFacile    .addActionListener(this);
		this.btnMoyen     .addActionListener(this);
		this.btnDifficile .addActionListener(this);

		panelNiveau.add(this.btnTresFacile);
		panelNiveau.add(this.btnFacile);
		panelNiveau.add(this.btnMoyen);
		panelNiveau.add(this.btnDifficile);

		panelSelection.add(labelRessource);
		panelSelection.add(this.listeRessources);
		panelSelection.add(labelNotion);
		panelSelection.add(this.listeNotions);
		panelSelection.add(labelNiveau);
		panelSelection.add(panelNiveau);

		add(panelSelection, BorderLayout.CENTER);

		// Section inférieure
		JPanel panelType = new JPanel(new GridLayout(1, 3, 5, 5));
		panelType.setBorder(BorderFactory.createTitledBorder("Type de Question"));

		JLabel labelType = new JLabel("Type :");
		this.listeTypes  = new JComboBox<>(new String[]
				{ "QCM REP. UNIQUE","QCM REP. MULTIPLE", "EntiteAssociation","Elimination" }
		);

		this.btnConfirmer = new JButton("Confirmer");
		this.btnConfirmer.addActionListener(this);

		panelType.add(labelType);
		panelType.add(this.listeTypes);
		panelType.add(this.btnConfirmer);

		add(panelType, BorderLayout.SOUTH);
		setVisible(true);

	}

	public PanelCreationQuestion(Controleur ctrl ){
		this.ctrl             = ctrl;
		this.ressources       = this.ctrl.getRessources();
		this.notions          = this.ctrl.getNotions();
		Ressource placeHolder = new Ressource("PlaceHolder","PlaceHolder");

		this.ressources.add(0,placeHolder);
		this.notions   .add(0,new Notion("PlaceHolder",placeHolder));

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

		this.champPoints  = new JFormattedTextField(numberFormatter);
		this.champPoints.setColumns(10);
		JLabel labelTemps = new JLabel("Temps de réponse (min:sec) :");

		try {
			MaskFormatter timeFormatter = new MaskFormatter("##:##");
			timeFormatter.setPlaceholderCharacter('0');
			this.champTemps = new JFormattedTextField(timeFormatter);
			this.champTemps.setText("00:30"); // Valeur initiale

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

		add(panelConfiguration, BorderLayout.NORTH);

		// Section centrale
		JPanel panelSelection = new JPanel(new GridLayout(3, 2, 5, 5));
		panelSelection.setBorder(BorderFactory.createTitledBorder("Sélection"));

		JLabel labelRessource = new JLabel("Ressource :");
		this.listeRessources  = new JComboBox<Ressource>();
		for(Ressource ressource : this.ressources)
		{
			this.listeRessources.addItem(ressource);
		}
		this.listeRessources.setSelectedIndex(0);

		this.listeRessources.addItemListener(this);

		JLabel labelNotion = new JLabel("Notion :");
		this.listeNotions  = new JComboBox<>();

		this.notions = this.ctrl.getNotionsParRessource(this.ressources.get(0));
		for(Notion notion : this.notions)
		{
			this.listeNotions.addItem(notion);
		}
		this.listeNotions.setSelectedIndex(0);

		this.listeNotions.setEnabled(false);
		this.listeNotions.addItemListener(this);

		JLabel labelNiveau = new JLabel("Difficulté :");
		JPanel panelNiveau = new JPanel(new FlowLayout());

		this.btnTresFacile = new JButton(IMAGES_DIFFICULTE[0]);
		this.btnFacile     = new JButton(IMAGES_DIFFICULTE[1]);
		this.btnMoyen      = new JButton(IMAGES_DIFFICULTE[2]);
		this.btnDifficile  = new JButton(IMAGES_DIFFICULTE[3]);

		this.btnTresFacile.setPreferredSize(new Dimension(100, 100));
		this.btnFacile    .setPreferredSize(new Dimension(100, 100));
		this.btnMoyen     .setPreferredSize(new Dimension(100, 100));
		this.btnDifficile .setPreferredSize(new Dimension(100, 100));

		this.btnTresFacile.setEnabled(false);
		this.btnFacile    .setEnabled(false);
		this.btnMoyen     .setEnabled(false);
		this.btnDifficile .setEnabled(false);

		this.btnTresFacile.addActionListener(this);
		this.btnFacile    .addActionListener(this);
		this.btnMoyen     .addActionListener(this);
		this.btnDifficile .addActionListener(this);

		panelNiveau.add(this.btnTresFacile);
		panelNiveau.add(this.btnFacile);
		panelNiveau.add(this.btnMoyen);
		panelNiveau.add(this.btnDifficile);

		panelSelection.add(labelRessource);
		panelSelection.add(this.listeRessources);
		panelSelection.add(labelNotion);
		panelSelection.add(this.listeNotions);
		panelSelection.add(labelNiveau);
		panelSelection.add(panelNiveau);

		add(panelSelection, BorderLayout.CENTER);

		// Section inférieure
		JPanel panelType = new JPanel(new GridLayout(1, 3, 5, 5));
		panelType.setBorder(BorderFactory.createTitledBorder("Type de Question"));

		JLabel labelType = new JLabel("Type :");
		this.listeTypes  = new JComboBox<>(new String[]
				{ "QCM REP. UNIQUE","QCM REP. MULTIPLE", "EntiteAssociation","Elimination" }
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

			if (this.champPoints.getText().equals(""))
			{
				JOptionPane.showMessageDialog(this, "Attribuez des points à la question");
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
				this.notion = n.getNom();
				this.temps  = Integer.parseInt(
						this.champTemps.getText().substring(0,this.champTemps.getText().indexOf(":")))*60
						+ Integer.parseInt(this.champTemps.getText().substring(this.champTemps.getText().indexOf(":")+1)
				);
				this.points = Integer.parseInt(this.champPoints.getText());

				PanelQCM panelQCM = new PanelQCM(
						this.ctrl,this.difficulte,this.notion,this.points,this.temps,this.panelBanque,true
				);
				panelQCM.setVisible(true);
			} else if ("QCM REP. MULTIPLE".equals(typeSelectionne)) {
				System.out.println(typeSelectionne);
				Notion n = (Notion)(this.listeNotions.getSelectedItem());
				this.notion = n.getNom();
				this.temps  = Integer.parseInt(this.champTemps.getText().substring(0,this.champTemps.getText().indexOf(":")))*60 + Integer.parseInt(this.champTemps.getText().substring(this.champTemps.getText().indexOf(":")+1));
				this.points = Integer.parseInt(this.champPoints.getText());

				PanelQCM panelQCM = new PanelQCM(this.ctrl,this.difficulte,this.notion,this.points,this.temps,this.panelBanque,false);
				panelQCM.setVisible(true);
			} else if("EntiteAssociation".equals(typeSelectionne)) {
				System.out.println(typeSelectionne);

				//PanelEntiteAssociation panelEntiteAssociation = new PanelEntiteAssociation(this.ctrl,this.difficulté,this.notion,this.points,this.temps,this.panelBanque);
				//panelEntiteAssociation.setVisible(true);
			} else if ("Elimination".equals(typeSelectionne)){
				System.out.println(typeSelectionne);

				//PanelElimination panelElimination = new PanelElimination(this.ctrl,this.difficulté,this.notion,this.points,this.temps,this.panelBanque);
				//panelElimination.setVisible(true);
			}
		}
	}
}
