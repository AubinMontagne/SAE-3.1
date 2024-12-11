import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;

public class PanelNotion extends JPanel implements ActionListener , ListSelectionListener
{	
    private JPanel panelNotion;
    private JButton btNouvNotion;
    private JList<Notion> list;
    private Ressource ressource;
    private ArrayList<Notion> notions;
  
    
    public PanelNotion( Ressource r){
        //this.ctrl         = ctrl;
        this.ressource = r;
        this.panelNotion = new JPanel();
        this.panelNotion.setLayout(new BorderLayout());
    
        // Liste des éléments
        Ressource ress1 = this.ressource;
        Ressource ress2 = new Ressource(2, "DevEfficace", "R8.01");
        Ressource ress3 = new Ressource(3, "Cryptomonaie", "R1.06");

        this.notions = new ArrayList<>();
        Notion not1 = new Notion(1,"Truk"         , ress1 );
        Notion not2 = new Notion(2,"Machin"       , ress1 );
        Notion not3 = new Notion(3,"Miche"        , ress2 );
        Notion not4 = new Notion(4,"Bidule"       , ress2 );
        Notion not5 = new Notion(5,"JeSaIsPaS"    , ress3 );
        Notion not6 = new Notion(5,"PlUsAcUnEiDeE", ress3 );

        this.notions.add(not1);
        this.notions.add(not2);
        this.notions.add(not3);
        this.notions.add(not4);
        this.notions.add(not5);

        // Création d'un modèle de liste
        DefaultListModel<Notion> listModel = new DefaultListModel<>();
        for (Notion notion : this.notions) {
            if( notion.getRessourceAssociee() == this.ressource)
                listModel.addElement(notion);
        }

        // Création des composants
        JLabel labTitre = new JLabel (this.ressource.toString());
        this.btNouvNotion = new JButton("Nouvelle Notion");
        this.list = new JList<>(listModel);
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajout des écouteurs
        this.list.addListSelectionListener(this);
        this.btNouvNotion.addActionListener(this);

        // Ajout de la liste dans un JScrollPane
        JScrollPane scrollPane = new JScrollPane(this.list);
        this.panelNotion.add(scrollPane, BorderLayout.CENTER);
        this.panelNotion.add(btNouvNotion, BorderLayout.SOUTH);
        this.panelNotion.add(labTitre, BorderLayout.NORTH);

        // Ajout du panel à la fenêtre
        this.add(this.panelNotion);
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Notion selectedNotion = this.list.getSelectedValue();
            if (selectedNotion != null) {
                new FrameBanque(selectedNotion);
            }
        }
    }
    public void actionPerformed(ActionEvent e) {
        if (btNouvNotion == e.getSource()) {
            System.out.println("Hey la frame CreaNotion s'ouvre");
            // new FrameRessource();
        }
    }
        
}
    