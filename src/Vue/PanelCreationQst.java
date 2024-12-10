import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelCreationQst
{
    public static void main(String[] args)
    {
        /*
        JFrame frame = new JFrame("Création de Question");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

         */

        // Créer le panel principal avec BorderLayout
        JPanel PanelPrin = new JPanel(new BorderLayout());

        JPanel PanelHaut = new JPanel(new GridLayout(2, 2, 5, 5));
        PanelHaut.setBorder(BorderFactory.createTitledBorder("Configuration"));

        JLabel lblPoints = new JLabel("Nombre de points :");
        JTextField txtPoints = new JTextField();
        JLabel lblTemps = new JLabel("Temps de réponse (min:sec) :");
        JTextField txtTemps = new JTextField("00:30");

        PanelHaut.add(lblPoints);
        PanelHaut.add(txtPoints);
        PanelHaut.add(lblTemps);
        PanelHaut.add(txtTemps);

        PanelPrin.add(PanelHaut, BorderLayout.NORTH);

        JPanel PanelCentre = new JPanel(new GridLayout(3, 2, 5, 5));
        PanelCentre.setBorder(BorderFactory.createTitledBorder("Sélection"));

        JLabel ressourceLabel = new JLabel("Ressource :");
        JComboBox<String> ressourceComboBox = new JComboBox<>(
                new String[] { /* Recup donnée */ });
        JLabel notionLabel = new JLabel("Notion :");

        JComboBox<String> notionComboBox = new JComboBox<>();
        notionComboBox.setEnabled(false); // Désactivé au début

        JLabel niveauLabel = new JLabel("Difficulté :");
        // IMAGE
        JPanel niveauPanel = new JPanel(new FlowLayout());

        ImageIcon tresFacileIcon = new ImageIcon(
                new ImageIcon("carrevertsanslettre.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        ImageIcon facileIcon = new ImageIcon(
                new ImageIcon("carrebleusanslettre.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        ImageIcon moyenIcon = new ImageIcon(
                new ImageIcon("carrerougesanslettre.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        ImageIcon difficileIcon = new ImageIcon(
                new ImageIcon("carregrissanslettre.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        JButton tresFacileButton = new JButton(tresFacileIcon);
        JButton facileButton = new JButton(facileIcon);
        JButton moyenButton = new JButton(moyenIcon);
        JButton difficileButton = new JButton(difficileIcon);

        // Ajouter des actions pour les boutons
        /*
         * tresFacileButton.addActionListener();
         * facileButton.addActionListener(); moyenButton.addActionListener();
         * difficileButton.addActionListener();
         */

        // Ajouter les boutons au panneau
        niveauPanel.add(tresFacileButton);
        niveauPanel.add(facileButton);
        niveauPanel.add(moyenButton);
        niveauPanel.add(difficileButton);

        PanelCentre.add(ressourceLabel);
        PanelCentre.add(ressourceComboBox);
        PanelCentre.add(notionLabel);
        PanelCentre.add(notionComboBox);
        PanelCentre.add(niveauLabel);
        PanelCentre.add(niveauPanel);

        PanelPrin.add(PanelCentre, BorderLayout.CENTER);

        JPanel PanelBas = new JPanel(new GridLayout(1, 2, 5, 5));
        PanelBas.setBorder(BorderFactory.createTitledBorder("Type de Question"));

        JLabel typeLabel = new JLabel("Type :");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[] { /* */ });

        PanelBas.add(typeLabel);
        PanelBas.add(typeComboBox);

        PanelPrin.add(PanelBas, BorderLayout.SOUTH);

        ressourceComboBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                notionComboBox.setEnabled(true);
                notionComboBox.removeAllItems();
                // Charger des notions en fonction de la ressource sélectionnée
                // Faut récup les données
            }
        });

        notionComboBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Activer le choix de difficulté une fois une notion choisie
                if (notionComboBox.getSelectedItem() != null)
                {

                }
            }
        });
        frame.add(PanelPrin);
        frame.setVisible(true);

    }
}
