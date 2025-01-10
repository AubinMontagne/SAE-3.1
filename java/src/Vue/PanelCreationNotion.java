package src.Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import src.Controleur;
import src.Metier.Notion;
import src.Metier.Ressource;

public class PanelCreationNotion extends JPanel implements ActionListener
{

    private Controleur          ctrl;
    private JTextField          txtNom;
    private JPanel              panelCreaRess;
    private JButton             btnConfirmer;
    private FrameCreationNotion frameCreationNotion;
    private PanelNotion         panelNotion;
    private Ressource           r;


    /**
     * Contructeur de la class PanelCreationNotion
     * @param crtl                  Le contrôleur
     * @param r                     La ressource
     * @param panelNotion           Le panel notion
     * @param frameCreationNotion   La frame de création de question
     */
    public PanelCreationNotion(Controleur crtl, Ressource r,PanelNotion panelNotion, FrameCreationNotion frameCreationNotion)
    {
        this.ctrl                = crtl;
        this.r                   = r;
        this.panelNotion         = panelNotion;
        this.frameCreationNotion = frameCreationNotion;
        this.panelCreaRess       = new JPanel();
        this.panelCreaRess.setLayout(new BorderLayout());


        UIManager.put("Label.font", new Font("Arial", Font.BOLD, 11));

        // Section des données
        JPanel panelConfiguration = new JPanel(new GridLayout(2, 2, 5, 5));
        panelConfiguration.setBorder(BorderFactory.createTitledBorder("Notion"));

        JLabel labelTemps = new JLabel("Nom (Ex: Le Chiffrement):");
        this.txtNom     = new JTextField();

        this.btnConfirmer = new JButton("Confirmer");
        this.btnConfirmer.setEnabled(false);
        btnConfirmer.setBackground(new Color(163,206,250));
        btnConfirmer.setFont(new Font("Arial", Font.PLAIN, 22));
        panelConfiguration.add(labelTemps);
        panelConfiguration.add(txtNom);

        add(panelConfiguration  , BorderLayout.CENTER );
        add(this.btnConfirmer, BorderLayout.SOUTH  );

        // Ajout des écouteurs sur les champs de texte
        this.txtNom.getDocument ().addDocumentListener (new InputListener());

        // Ajouter un ActionListener au bouton Confirmer
        this.btnConfirmer.addActionListener(this);

        setVisible(true);
    }

    // Methode

    /**
     * Methode verifierChamps
     */
    private void verifierChamps()
    {
        String texteChampNom = this.txtNom.getText().trim();
        this.btnConfirmer.setEnabled(!texteChampNom.isEmpty() );
    }

    /**
     * Methode actionPerformed
     * @param e L'évènement à traiter
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnConfirmer)
        {
            String nom = this.txtNom.getText().trim();

            // Créez l'objet Ressource (assurez-vous que la classe Ressource existe déjà)
            Notion notion = new Notion(nom, this.r);

            this.ctrl.ajouterNotion(notion);

            // Afficher une po-pup avec les informations de la Notion
            JOptionPane.showMessageDialog(this, "Notion ajoutée", "Succès", JOptionPane.INFORMATION_MESSAGE);

            this.panelNotion.maj();
            this.frameCreationNotion.dispose();
        }
    }

    // Classe interne pour surveiller les changements dans les champs de texte
    private class InputListener implements DocumentListener
    {
        @Override
        public void insertUpdate(DocumentEvent e)  {verifierChamps();}
        @Override
        public void removeUpdate(DocumentEvent e)  {verifierChamps();}
        @Override
        public void changedUpdate(DocumentEvent e) {verifierChamps();}
    }
}
