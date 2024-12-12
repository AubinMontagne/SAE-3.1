import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.util.ArrayList;

public class PanelRessource extends JPanel implements ActionListener, ListSelectionListener {
    private ArrayList<Ressource> ressources;
    private JPanel panelRessource;
    private JButton btNouvRess;
    private JList<Ressource> list;

    public PanelRessource() {
        // Initialisation du panel principal
        this.panelRessource = new JPanel();
        this.panelRessource.setLayout(new BorderLayout());

        // Liste des objets Ressource
        this.ressources = new ArrayList<>();
        Ressource ress1 = new Ressource( "QualitéDev", "R3.19");
        Ressource ress2 = new Ressource( "DevEfficace", "R8.01");
        Ressource ress3 = new Ressource( "Cryptomonaie", "R1.06");
        Ressource ress4 = new Ressource( "MathDiscrète", "R2.02");
        Ressource ress5 = new Ressource( "BaseDeDonnée", "R4.07");

        this.ressources.add(ress1);
        this.ressources.add(ress2);
        this.ressources.add(ress3);
        this.ressources.add(ress4);
        this.ressources.add(ress5);

        // Création d'un modèle de liste
        DefaultListModel<Ressource> listModel = new DefaultListModel<>();
        for (Ressource ressource : this.ressources) {
            listModel.addElement(ressource);
        }

        // Création des composants
        this.btNouvRess = new JButton("Nouvelle Ressource");
        this.list = new JList<>(listModel);
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ajout des écouteurs
        this.list.addListSelectionListener(this);
        this.btNouvRess.addActionListener(this);

        // Ajout de la liste dans un JScrollPane
        JScrollPane scrollPane = new JScrollPane(this.list);
        this.panelRessource.add(scrollPane, BorderLayout.CENTER);
        this.panelRessource.add(btNouvRess, BorderLayout.SOUTH);

        // Ajout du panel à la fenêtre
        this.add(this.panelRessource);
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            Ressource selectedRessource = this.list.getSelectedValue();
            if (selectedRessource != null) {
                new FrameNotion(selectedRessource);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (btNouvRess == e.getSource()) {
            System.out.println("Hey la frame CreaRessource s'ouvre");
            new FrameCreaRess();
        }
    }
}