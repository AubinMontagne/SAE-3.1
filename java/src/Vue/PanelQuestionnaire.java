package src.Vue;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import src.Metier.*;
import src.*;

public class PanelQuestionnaire extends JPanel implements ActionListener, ItemListener {
	private boolean 			 videTitre;
	private boolean 			 videRessource;
	private JPanel 				 panelQuestionnaire;
	private JComboBox<Ressource> mdRessources;
	private JButton 			 btConfirmer;
	private JButton 			 btChrono;
	private boolean 			 chrono;
	private JTextField 			 champTitre;

    private List<Ressource> listRessources;
	private Ressource  r;
	private Notion 	   n;
	private Controleur ctrl;
	private FrameQuestionnaire frame ;

	// Constructeur

	/**
	 * Constructeur de la class PanelQuestionnaire
	 * @param ctrl	Le contrôleur
	 */
    public PanelQuestionnaire(Controleur ctrl, FrameQuestionnaire frame) {
		this.frame = frame;
    	this.panelQuestionnaire = new JPanel(new BorderLayout());
    	this.panelQuestionnaire.setVisible(true);

		this.ctrl = ctrl;
		this.chrono = false;
		this.videTitre = true;
		this.videRessource = true;

    	UIManager.put("Label.font", new Font("Arial", Font.BOLD, 11));

		this.listRessources = ctrl.getRessources();
		Ressource placeHolder = new Ressource("PlaceHolder","PlaceHolder");
		this.listRessources.add(0,placeHolder);

    	// ------ Section supérieure ------
    	JPanel panelInfo = new JPanel(new GridLayout(2, 2, 5, 5));
    	panelInfo.setBorder(BorderFactory.createTitledBorder("Information"));

    	JLabel labelTitre = new JLabel("Titre du questionnaire :");
    	this.champTitre   = new JTextField();

    	JLabel labelChrono = new JLabel("Chronomètre :");
    	this.btChrono 	   = new JButton("NON");
    	this.btChrono.addActionListener(this);

    	JLabel labelRessource = new JLabel("Ressource :");
		this.mdRessources 		  = new JComboBox<>(this.listRessources.toArray(new Ressource[0]));
		this.mdRessources.addItemListener(this);
		this.mdRessources.setSelectedIndex(0);

    	panelInfo.add(labelTitre);
    	panelInfo.add(labelRessource);
    	panelInfo.add(labelChrono);

    	panelInfo.add(this.champTitre);
    	panelInfo.add(this.mdRessources);
    	panelInfo.add(this.btChrono);

    	this.add(panelInfo, BorderLayout.NORTH);

		this.btConfirmer = new JButton("Confirmer");
		this.btConfirmer.setEnabled(false);
		this.btConfirmer.addActionListener(this);
    	this.add(this.btConfirmer, BorderLayout.SOUTH);

    	this.add(this.panelQuestionnaire);

    // ------ Ajouter le DocumentListener au champTitre ------
		this.champTitre.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {verifierChamps(champTitre.getText()); }

			@Override
			public void removeUpdate(DocumentEvent e) {verifierChamps(champTitre.getText()); }

			@Override
			public void changedUpdate(DocumentEvent e) {verifierChamps(champTitre.getText()); }
		});
	}

	// Methode



	/**
	 * Methode verifierChamps
	 * @param texteTitre Champs à verifier
	 */
	private void verifierChamps(String texteTitre) {
		this.videTitre = texteTitre.trim().isEmpty();
		if (!this.videTitre && !this.videRessource) {
			this.btConfirmer.setEnabled(true);
		} else {
			this.btConfirmer.setEnabled(false);
		}
	}

	/**
	 * Methode itemStateChanged
	 * @param e L'évènement à traiter
	 */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == this.mdRessources && e.getStateChange() == ItemEvent.SELECTED) {
			if (this.mdRessources.getSelectedIndex() == 0 && this.listRessources.get(0).getId().equals("PlaceHolder")) {
				this.mdRessources.removeItemAt(0);
				this.listRessources.remove(0);
			}
            Ressource selectedRessource = (Ressource) this.mdRessources.getSelectedItem();

            if (selectedRessource != null) 
			{
				this.r = selectedRessource;
				System.out.println("Ressource sélectionnée : " + this.r);
				this.videRessource = false;
				if( !this.videTitre )
					this.frame.majTab(this.ctrl, selectedRessource,this.champTitre.getText(),this.chrono);
            }
        }
    }

	/**
	 * Methode actionPerformed
	 * @param e L'évènement à traiter
	 */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btConfirmer) {
            Ressource selectedRessource = (Ressource) this.mdRessources.getSelectedItem();
			String titre = this.champTitre.getText();
            if (selectedRessource != null) {
				this.frame.majTab(this.ctrl, selectedRessource,titre,this.chrono);
            }
        }

		if(e.getSource() == this.btChrono)
		{
			if(this.btChrono.getText() == "OUI"){
				this.btChrono.setText("NON");
				this.chrono = false;
			}
			else if(this.btChrono.getText() == "NON"){
				this.btChrono.setText("OUI");	
				this.chrono = false;
			}
		}
    }
}