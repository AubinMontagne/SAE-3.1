package Vue;

import src.Controleur;
import javax.swing;

// AUBIN , VOUS ME LAISSEZ FAIRE çA
public class FrameQuestionnaire extends JFrame
{
    private Controleur ctrl;

    public FrameQuestionnaire( Controleur ctrl)
    {
        this.ctrl = ctrl;

        System.out.printl("Création de la frame Questionnaire");

        int width = (int) (screenSize.getWidth() * 0.8);
        int height = (int) (screenSize.getHeight() * 0.8);

        this.setTitle("Création du questionnaire");
        this.setSize(width,height);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(new PanelQuestionnaire(this.ctrl) );

        setVisible(true);
    }
}