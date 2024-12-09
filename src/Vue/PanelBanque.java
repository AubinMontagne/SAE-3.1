package Vue;

import src.Controleur;
import Metier.Question;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing;


public class PanelBanque extends JPanel
{
    private Controleur ctrl;
    private JPanel panelAccueil;


    public PanelAccueil( Controleur ctrl){
        this.ctrl         = ctrl;
        this.panelAccueil = new JPanel();

        this.setVisible(true);


    }

    public void actionPerformed(ActionEvent e){
        if ( btBanque == e.getSource()){
            new FrameBanque(ctrl);
            dispose();
        }
        if( btQuestionnaire == e.getSource()){
            new FrameQuestionnaire(ctrl);
            dispose();
        }
        if(btRessources == e.getSource()){
            new FrameParam(ctrl);
            dispose();
        }
    }



}