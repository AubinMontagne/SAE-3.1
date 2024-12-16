package src.Vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.*;

import src.Controleur;
import src.Metier.Notion;
import src.Metier.Ressource;

public class PanelCreationQuestion extends JPanel implements ActionListener, ItemListener {
	private Controleur ctrl;
	private JComboBox<Ressource> listeRessources;
	private JComboBox<Notion> listeNotions;
	private JButton boutonTresFacile, boutonFacile, boutonMoyen, boutonDifficile;
	private JButton boutonConfirmer;
	private JComboBox<String> listeTypes;
	private JLabel labelMessage, labelResultat;
	private ArrayList<Ressource> ressources;
	private ArrayList<Notion> notions;

	private static final ImageIcon[] IMAGES_DIFFICULTE = {
			new ImageIcon("../../../../data/Images/imgDif/D.png"),
			new ImageIcon("../../data.Image/carrebleuaveclettre.png"),
			new ImageIcon("../../data.Image/carrerougeaveclettre.png"),
			new ImageIcon("../../data.Image/carregrisaveclettre.png")
	};

	public PanelCreationQuestion(Controleur ctrl) {
		this.ctrl = ctrl;
		this.ressources = this.ctrl.getRessources();
		this.notions = this.ctrl.getNotions();

		setLayout(new BorderLayout());

		UIManager.put("Label.font", new Font("Arial", Font.BOLD, 11));

		// Section supérieure
		JPanel panelConfiguration = new JPanel(new GridLayout(2, 2, 5, 5));
		panelConfiguration.setBorder(BorderFactory.createTitledBorder("Configuration"));

		JLabel labelPoints = new JLabel("Nombre de points :");
		JTextField champPoints = new JTextField();
		JLabel labelTemps = new JLabel("Temps de réponse (min:sec) :");
		JTextField champTemps = new JTextField("00:30");

		panelConfiguration.add(labelPoints);
		panelConfiguration.add(champPoints);
		panelConfiguration.add(labelTemps);
		panelConfiguration.add(champTemps);

		add(panelConfiguration, BorderLayout.NORTH);

		// Section centrale
		JPanel panelSelection = new JPanel(new GridLayout(3, 2, 5, 5));
		panelSelection.setBorder(BorderFactory.createTitledBorder("Sélection"));

		JLabel labelRessource = new JLabel("Ressource :");
		listeRessources = new JComboBox<Ressource>();
		for(Ressource ressource : ressources)
		{
			listeRessources.addItem(ressource);
		}
		listeRessources.addItemListener(this);

		JLabel labelNotion = new JLabel("Notion :");
		listeNotions = new JComboBox<>();
		for(Notion notion : notions)
		{
			listeNotions.addItem(notion);
		}
		listeNotions.setEnabled(false);
		listeNotions.addItemListener(this);

		JLabel labelNiveau = new JLabel("Difficulté :");
		JPanel panelNiveau = new JPanel(new FlowLayout());

		boutonTresFacile = new JButton();
		boutonFacile = new JButton();
		boutonMoyen = new JButton();
		boutonDifficile = new JButton();

		boutonTresFacile.setPreferredSize(new Dimension(100, 100));
		boutonFacile.setPreferredSize(new Dimension(100, 100));
		boutonMoyen.setPreferredSize(new Dimension(100, 100));
		boutonDifficile.setPreferredSize(new Dimension(100, 100));

		boutonTresFacile.setEnabled(false);
		boutonFacile.setEnabled(false);
		boutonMoyen.setEnabled(false);
		boutonDifficile.setEnabled(false);

		boutonTresFacile.addActionListener(this);
		boutonFacile.addActionListener(this);
		boutonMoyen.addActionListener(this);
		boutonDifficile.addActionListener(this);

		panelNiveau.add(boutonTresFacile);
		panelNiveau.add(boutonFacile);
		panelNiveau.add(boutonMoyen);
		panelNiveau.add(boutonDifficile);

		panelSelection.add(labelRessource);
		panelSelection.add(listeRessources);
		panelSelection.add(labelNotion);
		panelSelection.add(listeNotions);
		panelSelection.add(labelNiveau);
		panelSelection.add(panelNiveau);

		add(panelSelection, BorderLayout.CENTER);

		// Section inférieure
		JPanel panelType = new JPanel(new GridLayout(1, 3, 5, 5));
		panelType.setBorder(BorderFactory.createTitledBorder("Type de Question"));

		JLabel labelType = new JLabel("Type :");
		listeTypes = new JComboBox<>(new String[] { "QCM", "EntiteAssociation","Elimination" });

		boutonConfirmer = new JButton("Confirmer");
		boutonConfirmer.addActionListener(this);

		panelType.add(labelType);
		panelType.add(listeTypes);
		panelType.add(boutonConfirmer);

		add(panelType, BorderLayout.SOUTH);
		setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == listeRessources && e.getStateChange() == ItemEvent.SELECTED) {
			int index = listeRessources.getSelectedIndex();
			listeNotions.removeAllItems();
			for (Notion notion : this.ctrl.getNotions()) {
				listeNotions.addItem(notion);
			}
			listeNotions.setEnabled(true);
		} else if (e.getSource() == listeNotions && e.getStateChange() == ItemEvent.SELECTED) {
			int indexRessource = listeRessources.getSelectedIndex();
			int indexNotion = listeNotions.getSelectedIndex();
			if (indexRessource >= 0 && indexNotion >= 0) {

				boutonTresFacile.setIcon(IMAGES_DIFFICULTE[0]);
				boutonFacile.setIcon(IMAGES_DIFFICULTE[1]);
				boutonMoyen.setIcon(IMAGES_DIFFICULTE[2]);
				boutonDifficile.setIcon(IMAGES_DIFFICULTE[3]);

				boutonTresFacile.setEnabled(true);
				boutonFacile.setEnabled(true);
				boutonMoyen.setEnabled(true);
				boutonDifficile.setEnabled(true);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == boutonTresFacile) {
			JOptionPane.showMessageDialog(this, "Difficulté : Très Facile");
		} else if (e.getSource() == boutonFacile) {
			JOptionPane.showMessageDialog(this, "Difficulté : Facile");
		} else if (e.getSource() == boutonMoyen) {
			JOptionPane.showMessageDialog(this, "Difficulté : Moyen");
		} else if (e.getSource() == boutonDifficile) {
			JOptionPane.showMessageDialog(this, "Difficulté : Difficile");
		}

		if (e.getSource() == boutonConfirmer) {

			String typeSelectionne = (String) listeTypes.getSelectedItem();

			if ("QCM".equals(typeSelectionne)) {
				System.out.println(typeSelectionne);
				PanelQCM panelQCM = new PanelQCM(this.ctrl);
				panelQCM.setVisible(true);
			} else if ("EntiteAssociation".equals(typeSelectionne)) {
				System.out.println(typeSelectionne);

				PanelEntiteAssociation panelEntiteAssociation = new PanelEntiteAssociation(this.ctrl);
				panelEntiteAssociation.setVisible(true);
			} else if ("Elimination".equals(typeSelectionne)){
				System.out.println(typeSelectionne);

				PanelElimination panelElimination = new PanelElimination(this.ctrl);
				panelElimination.setVisible(true);
			}

		}

	}

}
