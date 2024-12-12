import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PanelCreaRess extends JPanel implements ActionListener 
{
	private JTextField champAcronyme;
    private JTextField champNom;
	private JPanel  panelCreaRess;
	private JButton boutonConfirmer;

	public PanelCreaRess() {
		this.panelCreaRess = new JPanel();
        this.panelCreaRess.setLayout(new BorderLayout());

		

		UIManager.put("Label.font", new Font("Arial", Font.BOLD, 11));

		// Section des donnée
		JPanel panelConfiguration = new JPanel(new GridLayout(2, 2, 5, 5));
		panelConfiguration.setBorder(BorderFactory.createTitledBorder("Ressource"));

		JLabel labelAcronyme = new JLabel("Acronyme (Ex: R3.03):");
		this.champAcronyme = new JTextField();
		JLabel labelNom = new JLabel("Nom (Ex: Analyse):");
		this.champNom = new JTextField();
		
		boutonConfirmer = new JButton("Confirmer");
		boutonConfirmer.setEnabled(false);
		panelConfiguration.add(labelAcronyme);
		panelConfiguration.add(champAcronyme);
		panelConfiguration.add(labelNom);
		panelConfiguration.add(champNom);

		add(panelConfiguration, BorderLayout.CENTER);
		add(boutonConfirmer,    BorderLayout.SOUTH );
 
		// Ajout des écouteurs sur les champs de texte
        champAcronyme.getDocument ().addDocumentListener (new InputListener());
        champNom.getDocument().addDocumentListener(new InputListener());

        // Ajouter un ActionListener au bouton Confirmer
        boutonConfirmer.addActionListener(this);

        setVisible(true);
    }

    // Méthode pour vérifier si les champs sont remplis
    private void verifierChamps() {
        String texteChampAcronyme = champAcronyme.getText().trim();
        String texteChampNom = champNom.getText().trim();
        boutonConfirmer.setEnabled(!texteChampAcronyme.isEmpty() && !texteChampNom.isEmpty());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonConfirmer) {
            String acronyme = champAcronyme.getText().trim();
            String nom = champNom.getText().trim();

            // Créez l'objet Ressource (assurez-vous que la classe Ressource existe déjà)
            Ressource ressource = new Ressource(acronyme, nom);
			//----------------------Ajouter la ressource aux métier----------------------

			// Réinitialiser les champs si nécessaire
			champAcronyme.setText("");
            champNom.setText("");

            // Afficher une popup avec les informations de la ressource
            JOptionPane.showMessageDialog(this, "Ressource créée:\n " + nom + "  " + acronyme, "Confirmation", JOptionPane.INFORMATION_MESSAGE);

            
            
        }
    }

    // Classe interne pour surveiller les changements dans les champs de texte
    private class InputListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            verifierChamps();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            verifierChamps();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            verifierChamps();
        }
    }

}

