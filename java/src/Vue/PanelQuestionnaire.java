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
	private boolean 			 videTitre = true;
	private boolean 			 videRessource = true;
	private JPanel 				 panelQuestionnaire;
	private JComboBox<Ressource> mdRessources;
	private JButton 			 btConfirmer;
	private JButton 			 btChrono;
	private boolean 			 chrono = true;
	private JTextField 			 champTitre;

    private List<Ressource> listRessources;
    private List<Notion> listNotions;

	private Ressource  r;
	private Notion 	   n;
	private Controleur ctrl;

	// Constructeur

	/**
	 * Constructeur de la class PanelQuestionnaire
	 * @param ctrl	Le contrôleur
	 */
    public PanelQuestionnaire(Controleur ctrl) {
    	this.panelQuestionnaire = new JPanel(new BorderLayout());
    	this.panelQuestionnaire.setVisible(true);

		this.ctrl = ctrl;

    	UIManager.put("Label.font", new Font("Arial", Font.BOLD, 11));

    	// ------ Section supérieure ------
    	JPanel panelInfo = new JPanel(new GridLayout(2, 2, 5, 5));
    	panelInfo.setBorder(BorderFactory.createTitledBorder("Information"));

    	JLabel labelTitre = new JLabel("Titre du questionnaire :");
    	this.champTitre   = new JTextField();

    	JLabel labelChrono = new JLabel("Chronomètre :");
    	this.btChrono 	   = new JButton("NON");
    	this.btChrono.addActionListener(this);

    	JLabel labelRessource = new JLabel("Ressource :");
    	mdRessources 		  = new JComboBox<>(ctrl.getRessources().toArray(new Ressource[0]));
    	mdRessources.addItemListener(this);

    	panelInfo.add(labelTitre);
    	panelInfo.add(labelRessource);
    	panelInfo.add(labelChrono);

    	panelInfo.add(champTitre);
    	panelInfo.add(mdRessources);
    	panelInfo.add(btChrono);

    	this.add(panelInfo, BorderLayout.NORTH);

   		btConfirmer = new JButton("Confirmer");
    	btConfirmer.setEnabled(false); 
    	btConfirmer.addActionListener(this);
    	this.add(btConfirmer, BorderLayout.SOUTH);

    	this.add(panelQuestionnaire);

    // ------ Ajouter le DocumentListener au champTitre ------
    	champTitre.getDocument().addDocumentListener(new DocumentListener() {
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
			btConfirmer.setEnabled(true);
			//this.titre = texteTitre;
		} else {
			btConfirmer.setEnabled(false);
		}
	}

	/**
	 * Methode itemStateChanged
	 * @param e L'évènement à traiter
	 */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == mdRessources && e.getStateChange() == ItemEvent.SELECTED) {
            Ressource selectedRessource = (Ressource) mdRessources.getSelectedItem();

            if (selectedRessource != null) 
			{
				this.r = selectedRessource;
				System.out.println("Ressource sélectionnée : " + this.r);
				this.videRessource = false;
				if( !this.videTitre )
					btConfirmer.setEnabled(true);
            }
        }
    }

	/**
	 * Methode actionPerformed
	 * @param e L'évènement à traiter
	 */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btConfirmer) {
            Ressource selectedRessource = (Ressource) mdRessources.getSelectedItem();
			String titre = champTitre.getText();
            if (selectedRessource != null) {
				new FrameQuestionnaireTab(ctrl,selectedRessource,titre, this.chrono);
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