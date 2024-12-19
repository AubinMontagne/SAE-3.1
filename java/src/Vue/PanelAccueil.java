package src.Vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import src.Controleur;

public class PanelAccueil extends JPanel implements  ActionListener{
    private Controleur ctrl;
    private JPanel     panelAccueil;

    private JLabel     txtTitreApp;

    private JButton    btnBanque;
    private JButton    btnQuestionnaire;
    private JButton    btnRessources;
    private JButton    btnCreationQuestion;

    // Constructeur

    /**
     * Constructeur de la class PanelAccueil
     * @param ctrl  Le contrôleur
     */
    public PanelAccueil( Controleur ctrl )
    {
        this.ctrl         = ctrl;
        this.panelAccueil = new JPanel(new GridLayout(2,1));
        this.setLayout ( new BorderLayout() );

        this.setVisible(true);

        //Génération du texte
        this.txtTitreApp = new JLabel("QCM Builder");
        this.txtTitreApp.setFont(new Font("Arial", Font.BOLD, 34));
        this.txtTitreApp.setHorizontalAlignment(SwingConstants.CENTER);
        this.txtTitreApp.setVerticalAlignment(SwingConstants.CENTER);



        //Début génération des boutons
        this.btnBanque        = new JButton("Banque de Questions");
        this.btnQuestionnaire = new JButton("Génération Questionnaire");
        this.btnRessources    = new JButton("Ressources"              );
        this.btnCreationQuestion= new JButton("Création de question");

        this.btnBanque.setBackground(new Color(163,206,250));
        this.btnQuestionnaire.setBackground(new Color(163,206,250));
        this.btnRessources.setBackground(new Color(163,206,250));
        this.btnCreationQuestion.setBackground(new Color(163,206,250));

        this.btnBanque.setFont(new Font("Arial", Font.PLAIN, 22));
        this.btnQuestionnaire.setFont(new Font("Arial", Font.PLAIN, 22));
        this.btnRessources.setFont(new Font("Arial", Font.PLAIN, 22));
        this.btnCreationQuestion.setFont(new Font("Arial", Font.PLAIN, 22));


        //Début des ajouts dans la frame
        this.panelAccueil.add(this.txtTitreApp     );
        JPanel panelConfiguration = new JPanel(new GridLayout(4,1));

        panelConfiguration.add(this.btnBanque);
        panelConfiguration.add(this.btnQuestionnaire);
        panelConfiguration.add(this.btnRessources);
        panelConfiguration.add(this.btnCreationQuestion);

        this.panelAccueil.add(panelConfiguration);


        this.add(panelAccueil);


        //Ajout des listeners
        this.btnBanque       .addActionListener(this) ;
        this.btnQuestionnaire.addActionListener(this) ;
        this.btnRessources   .addActionListener(this) ;

    }

    // Methode
    /**
     * Methode actionPerformed
     * @param e L'évènement à traiter
     */
    public void actionPerformed(ActionEvent e){
        if ( btnBanque == e.getSource())
            FrameBanque.creerFrameBanque(this.ctrl);
        if( btnQuestionnaire == e.getSource())
            FrameQuestionnaire.creerFrameQuestionnaire(this.ctrl);
        if(btnRessources == e.getSource())
            FrameParametrage.creerFrameFrameParametrage(this.ctrl);
        if(btnCreationQuestion == e.getSource())
            new FrameCreationQuestion(this.ctrl);
    }
}