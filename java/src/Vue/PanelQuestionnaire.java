import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PanelQuestionnaire extends JPanel implements ActionListener, ItemListener {
	private boolean videTitre = true;
	private boolean videRessource = true;
	private JPanel panelQuestionnaire;
	private JComboBox<Ressource> mdRessources;
	private JButton btConfirmer;
	private JButton btChrono;
	private boolean chrono = true;

    private List<Ressource> listRessources;
    private List<Notion> listNotions;

	private Ressource r;
	private Notion n;

    public PanelQuestionnaire() {
    	this.panelQuestionnaire = new JPanel(new BorderLayout());
    	this.panelQuestionnaire.setVisible(true);

    	UIManager.put("Label.font", new Font("Arial", Font.BOLD, 11));

   		// ------ Données brutes ------
    	initRessourcesAndNotions();

    	// ------ Section supérieure ------
    	JPanel panelInfo = new JPanel(new GridLayout(2, 2, 5, 5));
    	panelInfo.setBorder(BorderFactory.createTitledBorder("Information"));

    	JLabel labelTitre = new JLabel("Titre du questionnaire :");
    	JTextField champTitre = new JTextField();

    	JLabel labelChrono = new JLabel("Chronomètre :");
    	this.btChrono = new JButton("NON");
    	this.btChrono.addActionListener(this);

    	JLabel labelRessource = new JLabel("Ressource :");
    	mdRessources = new JComboBox<>(listRessources.toArray(new Ressource[0]));
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
        public void insertUpdate(DocumentEvent e) {
            verifierChamps(champTitre.getText());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            verifierChamps(champTitre.getText());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            verifierChamps(champTitre.getText());
        }
    });
}

// Méthode pour valider les champs et activer le bouton
private void verifierChamps(String texteTitre) {
    this.videTitre = texteTitre.trim().isEmpty();
    if (!this.videTitre && !this.videRessource) {
        btConfirmer.setEnabled(true);
    } else {
        btConfirmer.setEnabled(false);
    }
}

    private void initRessourcesAndNotions() {
        this.listRessources = new ArrayList<>();
        Ressource ress1 = new Ressource("Probabilitée", "R3.08");
        Ressource ress2 = new Ressource("DevEfficace", "R3.02");
        Ressource ress3 = new Ressource("Cryptomonaie", "R3.09");

        listRessources.add(ress1);
        listRessources.add(ress2);
        listRessources.add(ress3);

        this.listNotions = new ArrayList<>();
        listNotions.add(new Notion("Truk", ress1));
        listNotions.add(new Notion("Machin", ress1));
        listNotions.add(new Notion("Miche", ress2));
        listNotions.add(new Notion("Bidule", ress2));
        listNotions.add(new Notion("JeSaIsPaS", ress3));
        listNotions.add(new Notion("PlUsAcUnEiDeE", ress3));
    }

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btConfirmer) {
            Ressource selectedRessource = (Ressource) mdRessources.getSelectedItem();
            if (selectedRessource != null) {
				new FrameQuestionnaireTab(selectedRessource);
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