package src.Vue;

import javax.swing.*;
import src.Controleur;
import src.Metier.Notion;

public class FrameBanque extends JFrame{
    private Controleur ctrl;
    private int idNotion;

	public FrameBanque(Controleur ctrl ){
        this.ctrl = ctrl;

        System.out.println("Création de la frame Banque");


        this.setTitle("Banque de question ");
        this.setSize(850,200);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelBanque(this.ctrl) );

        setVisible(true);
    }

    public FrameBanque( Notion notion, Controleur ctrl ){
        this.ctrl = ctrl;
        this.notion = notion;

        System.out.println("Création de la frame Banque");


        this.setTitle("Banque de question de la notion "+ ctrl.getNotionById(idNotion).getNom());
        this.setSize(850,200);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelBanque(this.idNotion, this.ctrl) );

        setVisible(true);
    }
}