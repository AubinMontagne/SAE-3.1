package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelCreationQst extends JPanel
{
    private Controleur ctrl;


    public PanelCreationQst(Controleur ctrl)
    {

        this.ctrl = ctrl;
        this.setLayout(null);

        // Création des composants
        JLabel lblTemps = new JLabel("Temps");
        JTextField txtTemps = new JTextField(5);
        JLabel lblPoint = new JLabel("Point");
        JTextField txtPoint = new JTextField(5);
        JLabel lblRessource = new JLabel("Ressource");
        JLabel lblNotion = new JLabel("Temps");
        // JComboBox<String> cbRessource = new JComboBox<>(new String[]);
        // JComboBox<String> cbNotion = new JComboBox<>(new String[]);
        String[] imageSansLettres = {
                "../../Images/carrevertsanslettre.png",
                "../../Images/carrebleusanslettre.png",
                "../../Images/carrerougesanslettre.png",
                "../../Images/carregrissanslettre.png"
        };

        String[] imageAvecLettres = {
                "../../Images/carrevertaveclettre.png",
                "../../Images/carrebleuaveclettre.png",
                "../../Images/carrerougeaveclettre.png",
                "../../Images/carregrisaveclettre.png",
        };



        // Placement des composants

        // Faut un if , le 2e doit s'activer quand on prend une notion
        for (String imgPath  : imageSansLettres)
        {
            try {
                // Charger l'image
                ImageIcon icon = new ImageIcon(imgPath);
                JButton imageButton = new JButton(icon);
                imageButton.setPreferredSize(new Dimension(40, 40));
                imageButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                niveauPanel.add(imageButton);
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement de l'image : " + imgPath);
            }

        }

        for (String imgPath : imageAvecLettres)
        {
            try {
                // Charger l'image
                ImageIcon icon = new ImageIcon(imgPath);
                JButton imageButton = new JButton(icon);
                imageButton.setPreferredSize(new Dimension(40, 40));
                imageButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                niveauPanel.add(imageButton);
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement de l'image : " + imgPath);
            }
        }
        // Activation des composants

    }

}