package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Panel extends JPanel
{
    private Controleur ctrl;


    public PanelBanque(Controleur ctrl)
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

        // Placement des composants

        // Activation des composants

    }

}