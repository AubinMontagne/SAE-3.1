package src.Vue;

import javax.swing.*;
import src.Controleur;

public class FrameBanque extends JFrame{
    private Controleur ctrl;
    private int idNotion;

    public FrameBanque( int idNotion ){
        this.ctrl = ctrl;
        this.idNotion = idNotion;

        System.out.println("Création de la frame Banque");


        this.setTitle("Banque de question de la notion "+ ctrl.getIdNotion(idNotion).getNom());
        this.setSize(850,200);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelBanque(this.idNotion, this.ctrl) );

        setVisible(true);
    }
}