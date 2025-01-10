package src.Vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import src.Metier.*;
import src.*;

public class PanelQuestionnaire extends JPanel implements ActionListener, ItemListener
{
	private boolean 			 videTitre;
	private boolean 			 videRessource;
	private JPanel 				 panelQuestionnaire;
	private JComboBox<Ressource> ddlstRessources;
	private JButton 			 btnConfirmer;
	private JButton 			 btnChrono;
	private boolean 			 chrono;
	private JTextField 			 txtTitre;
    private List<Ressource>      lstRessources;
	private Ressource            r;
	private Notion 	             n;
	private Controleur           ctrl;
	private FrameQuestionnaire   frame ;


	/**
	 * Constructeur de la class PanelQuestionnaire
	 * @param ctrl	Le contrôleur
	 */
    public PanelQuestionnaire(Controleur ctrl, FrameQuestionnaire frame)
	{
    	this.panelQuestionnaire = new JPanel(new BorderLayout());
		this.frame              = frame;
		this.ctrl               = ctrl;
		this.chrono             = false;
		this.videTitre          = true;
		this.videRessource      = true;                             

    	UIManager.put("Label.font", new Font("Arial", Font.BOLD, 11));

		this.lstRessources   = ctrl.getRessources();

    	// ------ Section supérieure ------
		JPanel panelInfo = new JPanel(new GridLayout(2, 2, 5, 5));
		TitledBorder titledBorder = BorderFactory.createTitledBorder("Information");
		titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 26));
		panelInfo.setBorder(titledBorder);


    	JLabel labelTitre = new JLabel("Titre du questionnaire :");
    	this.txtTitre   = new JTextField();

    	JLabel labelChrono = new JLabel("Chronomètre :");
    	this.btnChrono 	   = new JButton("NON");
		this.btnChrono.setBackground(new Color(163,206,250));
		this.btnChrono.setFont(new Font("Arial", Font.PLAIN, 22));


    	JLabel labelRessource = new JLabel("Ressource :");
		this.ddlstRessources  = new JComboBox<>(this.lstRessources.toArray(new Ressource[0]));

    	panelInfo.add(labelTitre);
    	panelInfo.add(labelRessource);
    	panelInfo.add(labelChrono);

    	panelInfo.add(this.txtTitre);
    	panelInfo.add(this.ddlstRessources);
    	panelInfo.add(this.btnChrono);

    	this.add(panelInfo, BorderLayout.NORTH);

		this.btnConfirmer = new JButton("Confirmer");
		btnConfirmer.setBackground(new Color(163,206,250));
		btnConfirmer.setFont(new Font("Arial", Font.PLAIN, 22));
		this.btnConfirmer.setEnabled(false);
		this.btnConfirmer.addActionListener(this);

    	this.add(this.panelQuestionnaire);

    // ------ Ajouter le DocumentListener au txtTitre ------
		this.txtTitre.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate(DocumentEvent e)  {verifierChamps(txtTitre.getText()); }
			@Override
			public void removeUpdate(DocumentEvent e)  {verifierChamps(txtTitre.getText()); }
			@Override
			public void changedUpdate(DocumentEvent e) {verifierChamps(txtTitre.getText()); }
		});

		this.btnChrono.addActionListener(this);
		this.txtTitre.addActionListener(this);

		this.ddlstRessources.addItemListener(this);
		this.ddlstRessources.setSelectedIndex(0);
		this.ddlstRessources.setEnabled(false);

		this.panelQuestionnaire.setVisible(true);
	}

	// Methode



	/**
	 * Methode verifierChamps
	 * @param texteTitre Champs à verifier
	 */
	private void verifierChamps(String texteTitre)
	{
		this.videTitre = texteTitre.trim().isEmpty();

		if (!this.videTitre && !this.videRessource) {this.btnConfirmer.setEnabled(true);}
		else {this.btnConfirmer.setEnabled(false);}

		if(!this.videTitre){this.ddlstRessources.setEnabled(true);}
		else {this.ddlstRessources.setEnabled(false);}
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
			if (this.ddlstRessources.getSelectedIndex() == 0 && this.lstRessources.get(0).getId().equals(" ")) {this.ddlstRessources.removeItemAt(0);}
            Ressource selectedRessource = (Ressource) this.ddlstRessources.getSelectedItem();

            if (selectedRessource != null) 
			{
				this.r = selectedRessource;
				this.videRessource = false;
				if( !this.videTitre )
					this.frame.majTab(this.ctrl, selectedRessource,this.txtTitre.getText(),this.chrono);
            }
        }
    }

	/**
	 * Methode actionPerformed
	 * @param e L'évènement à traiter
	 */
    @Override
    public void actionPerformed(ActionEvent e)
	{
        if (e.getSource() == this.btnConfirmer)
		{
            Ressource selectedRessource = (Ressource) this.ddlstRessources.getSelectedItem();
			String titre = this.txtTitre.getText();
            if (selectedRessource != null)
			{
				this.frame.majTab(this.ctrl, selectedRessource,titre,this.chrono);
            }
			this.frame.dispose();
        }

		if(e.getSource() == this.btnChrono)
		{
			if(this.btnChrono.getText() == "OUI")
			{
				this.btnChrono.setText("NON");
				this.chrono = false;
			}
			else if(this.btnChrono.getText() == "NON")
			{
				this.btnChrono.setText("OUI");	
				this.chrono = false;
			}
		}

		if(e.getSource() == this.txtTitre)
		{
			if( !this.txtTitre.getText().equals("") )
			{
				this.ddlstRessources.setEnabled(true);

				Ressource selectedRessource = (Ressource) this.ddlstRessources.getSelectedItem();
				String titre = this.txtTitre.getText();
				this.frame.majTab(this.ctrl, selectedRessource,titre,this.chrono);
			}


		}
    }
}