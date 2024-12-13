import javax.swing.*;

public class FrameQuestionnaireTab extends JFrame{ 
    //private Controleur ctrl;

    public FrameQuestionnaireTab( /*Controleur ctrl,*/ Ressource r){
        //this.ctrl = ctrl;

        System.out.println("Création de la frame QuestionnaireTab");

        this.setTitle("Tableau des Notions");
        this.setSize(600,175);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelQuestionnaireTab(r) );

        setVisible(true);
    }
}