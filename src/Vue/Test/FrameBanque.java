import javax.swing.*;

public class FrameBanque extends JFrame{
    //private Controleur ctrl;
    private Notion notion;

    public FrameBanque( ){
        //this.ctrl = ctrl;

        System.out.println("Création de la frame Banque");


        this.setTitle("Banque de question");
        this.setSize(850,200);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelBanque() );

        setVisible(true);
    }

    public FrameBanque( Notion n ){
        //this.ctrl = ctrl;
        this.notion = n;

        System.out.println("Création de la frame Banque");


        this.setTitle("Banque de question");
        this.setSize(850,200);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelBanque(this.notion) );

        setVisible(true);
    }
}