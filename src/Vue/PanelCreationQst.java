import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class PanelCreationQst extends JPanel implements ActionListener, ItemListener
{

    private JComboBox<String> ressourceComboBox;
    private JComboBox<String> notionComboBox;
    private JButton tresFacileButton, facileButton, moyenButton, difficileButton;
    private JLabel lblMessage, lblResultat;

    // J'ai mis en Brut faut adapter
    private static final String[] RESSOURCES = { "Sélectionner une ressource", "Ressource 1", "Ressource 2" };
    private static final String[][] NOTIONS = { { "Notion A", "Notion B" }, // Pour
            // Ressource
            // 1
            { "Notion X", "Notion Y" } // Pour Ressource 2
    };

    private static final ImageIcon[] DIFFICULTE_IMAGES =

            { new ImageIcon("carrevertaveclettre.png"), new ImageIcon("carrebleuaveclettre.png"),
                    new ImageIcon("carrerougeaveclettre.png"), new ImageIcon("carregrisaveclettre.png") };

    public PanelCreationQst()
    {
        setLayout(new BorderLayout());

        // Section supérieure
        JPanel configPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        configPanel.setBorder(BorderFactory.createTitledBorder("Configuration"));

        JLabel lblPoints = new JLabel("Nombre de points :");
        JTextField txtPoints = new JTextField();
        JLabel lblTemps = new JLabel("Temps de réponse (min:sec) :");
        JTextField txtTemps = new JTextField("00:30");

        configPanel.add(lblPoints);
        configPanel.add(txtPoints);
        configPanel.add(lblTemps);
        configPanel.add(txtTemps);

        add(configPanel, BorderLayout.NORTH);

        // Section centrale
        JPanel selectionPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Sélection"));

        JLabel ressourceLabel = new JLabel("Ressource :");
        ressourceComboBox = new JComboBox<>(RESSOURCES);
        ressourceComboBox.addItemListener(this);

        JLabel notionLabel = new JLabel("Notion :");
        notionComboBox = new JComboBox<>();
        notionComboBox.setEnabled(false);
        notionComboBox.addItemListener(this);

        JLabel niveauLabel = new JLabel("Difficulté :");
        JPanel niveauPanel = new JPanel(new FlowLayout());

        tresFacileButton = new JButton();
        facileButton = new JButton();
        moyenButton = new JButton();
        difficileButton = new JButton();

        tresFacileButton.setPreferredSize(new Dimension(100, 100));
        facileButton.setPreferredSize(new Dimension(100, 100));
        moyenButton.setPreferredSize(new Dimension(100, 100));
        difficileButton.setPreferredSize(new Dimension(100, 100));

        tresFacileButton.setEnabled(false);
        facileButton.setEnabled(false);
        moyenButton.setEnabled(false);
        difficileButton.setEnabled(false);

        tresFacileButton.addActionListener(this);
        facileButton.addActionListener(this);
        moyenButton.addActionListener(this);
        difficileButton.addActionListener(this);

        niveauPanel.add(tresFacileButton);
        niveauPanel.add(facileButton);
        niveauPanel.add(moyenButton);
        niveauPanel.add(difficileButton);

        selectionPanel.add(ressourceLabel);
        selectionPanel.add(ressourceComboBox);
        selectionPanel.add(notionLabel);
        selectionPanel.add(notionComboBox);
        selectionPanel.add(niveauLabel);
        selectionPanel.add(niveauPanel);

        add(selectionPanel, BorderLayout.CENTER);

        // Section inférieure
        JPanel typePanel = new JPanel(new GridLayout(1, 2, 5, 5));
        typePanel.setBorder(BorderFactory.createTitledBorder("Type de Question"));

        JLabel typeLabel = new JLabel("Type :");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[] { "QCM", "Vrai/Faux" });

        typePanel.add(typeLabel);
        typePanel.add(typeComboBox);

        add(typePanel, BorderLayout.SOUTH);
    }

    @Override
    public void itemStateChanged(ItemEvent e)
    {
        if (e.getSource() == ressourceComboBox && e.getStateChange() == ItemEvent.SELECTED)
        {
            int index = ressourceComboBox.getSelectedIndex();
            notionComboBox.removeAllItems();
            for (String notion : NOTIONS[index])
            {
                notionComboBox.addItem(notion);
            }
            notionComboBox.setEnabled(true);
        }
        else if (e.getSource() == notionComboBox && e.getStateChange() == ItemEvent.SELECTED)
        {
            int ressourceIndex = ressourceComboBox.getSelectedIndex();
            int notionIndex = notionComboBox.getSelectedIndex();
            if (ressourceIndex >= 0 && notionIndex >= 0)
            {

                tresFacileButton.setIcon(DIFFICULTE_IMAGES[0]);
                facileButton.setIcon(DIFFICULTE_IMAGES[1]);
                moyenButton.setIcon(DIFFICULTE_IMAGES[2]);
                difficileButton.setIcon(DIFFICULTE_IMAGES[3]);

                tresFacileButton.setEnabled(true);
                facileButton.setEnabled(true);
                moyenButton.setEnabled(true);
                difficileButton.setEnabled(true);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // test
        if (e.getSource() == tresFacileButton)
        {
            JOptionPane.showMessageDialog(this, "Difficulté : Très Facile");
        }
        else if (e.getSource() == facileButton)
        {
            JOptionPane.showMessageDialog(this, "Difficulté : Facile");
        }
        else if (e.getSource() == moyenButton)
        {
            JOptionPane.showMessageDialog(this, "Difficulté : Moyen");
        }
        else if (e.getSource() == difficileButton)
        {
            JOptionPane.showMessageDialog(this, "Difficulté : Difficile");
        }
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Création de Question");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.add(new PanelCreationQst());
        frame.setVisible(true);
    }
}
