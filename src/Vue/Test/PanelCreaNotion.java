import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PanelCreaNotion extends JPanel implements ActionListener 
{
	private JTextField champNom;
	private JPanel  panelCreaRess;
	private JButton boutonConfirmer;
	private Ressource r;

	public PanelCreaNotion(Ressource r) {
		this.r = r;
		this.panelCreaRess = new JPanel();
        this.panelCreaRess.setLayout(new BorderLayout());

		

		UIManager.put("Label.font", new Font("Arial", Font.BOLD, 11));

		// Section des donnée
		JPanel panelConfiguration = new JPanel(new GridLayout(2, 2, 5, 5));
		panelConfiguration.setBorder(BorderFactory.createTitledBorder("Notion"));

		JLabel labelTemps = new JLabel("Nom (Ex: Le Chiffrement):");
		this.champNom = new JTextField();
		
		boutonConfirmer = new JButton("Confirmer");
		boutonConfirmer.setEnabled(false);
		panelConfiguration.add(labelTemps);
		panelConfiguration.add(champNom);


		add(panelConfiguration, BorderLayout.CENTER);
		add(boutonConfirmer,    BorderLayout.SOUTH );
 
		// Ajout des écouteurs sur les champs de texte
        champNom.getDocument ().addDocumentListener (new InputListener());


        // Ajouter un ActionListener au bouton Confirmer
        boutonConfirmer.addActionListener(this);

        setVisible(true);
    }

    // Méthode pour vérifier si les champs sont remplis
    private void verifierChamps() {
        String texteChampNom = champNom.getText().trim();
        boutonConfirmer.setEnabled(!texteChampNom.isEmpty() );
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boutonConfirmer) {
            String nom = champNom.getText().trim();

            // Créez l'objet Ressource (assurez-vous que la classe Ressource existe déjà)
            Notion notion = new Notion(nom, this.r);
			//Ajouter la ressoruce aux métier

            // Afficher une popup avec les informations de la ressource
            JOptionPane.showMessageDialog(this, "Notion créée:\nNom : " + nom + "\nRessource associée : " + this.r, "Confirmation", JOptionPane.INFORMATION_MESSAGE);

            // Réinitialiser les champs si nécessaire
            champNom.setText("");
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