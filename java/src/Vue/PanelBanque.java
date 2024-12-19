package src.Vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.Metier.Question;
import src.Metier.Notion;
import src.Controleur;
import src.Metier.Ressource;

public class PanelBanque extends JPanel implements  ActionListener, ItemListener {
    private Controleur ctrl;
	private Notion     notion;
	private JButton    btnCreaQuest;
    private JPanel     panelBanque;
    private JTable     tbQuestion;
	private JComboBox<Notion>    mdNotions;
	private JComboBox<Ressource> mdRessources;
	// Constructeur

	/**
	 * Constructeur de la class PanelBanque
	 * @param ctrl	Le contrôleur
	 */
    public PanelBanque(Controleur ctrl){
        this.ctrl         = ctrl;
        this.panelBanque  = new JPanel();
		this.setLayout (new BorderLayout());
		this.setVisible(true);

        String[] tabEntetes = {"Question", "Ressource", "Notion", "Point"};

		ArrayList<Question> questList = this.ctrl.getQuestions();
        String[][] data = new String[questList.size()][4];

		for(int i = 0; i < questList.size();i++){
			data[i][0] = questList.get(i).getEnonceFich();
			data[i][1] = questList.get(i).getNotion().getRessourceAssociee().getNom();
			data[i][2] = questList.get(i).getNotion().getNom();
			data[i][3] = "" + questList.get(i).getPoint();
		}
        DefaultTableModel model = new DefaultTableModel(data, tabEntetes);
        this.tbQuestion = new JTable(model);

		this.mdRessources   = new JComboBox<>(ctrl.getRessources().toArray(new Ressource[0]));
		this.mdNotions		= new JComboBox<>();

		JPanel panelParametre = new JPanel();

        // Nombre maximal de lignes sans scroll
        int maxVisibleRows = 5;

        // Calcul dynamique de la hauteur
        int rowHeight     = this.tbQuestion.getRowHeight();
        int headerHeight  = this.tbQuestion.getTableHeader().getHeight();
        int visibleHeight = rowHeight * Math.min(this.tbQuestion.getRowCount(), maxVisibleRows) + headerHeight;

        // Ajuster la taille visible de la table
        this.tbQuestion.setPreferredScrollableViewportSize(new Dimension(800, visibleHeight));

		JScrollPane scrollPane = new JScrollPane(this.tbQuestion);
		this.btnCreaQuest      = new JButton("Nouvelle Question");

		panelParametre.add(this.mdRessources);
		panelParametre.add(this.mdNotions);

		this.panelBanque.add(panelParametre   , BorderLayout.NORTH );
		this.panelBanque.add(scrollPane       , BorderLayout.CENTER);
		this.panelBanque.add(this.btnCreaQuest, BorderLayout.SOUTH );

        this.add(panelBanque);

		this.btnCreaQuest.addActionListener(this);

		this.mdRessources.addItemListener(this);
		this.mdNotions.addItemListener(this);
    }

	/**
	 * Contructeur de la class PanelBanque
	 * @param notion	La notion
	 * @param ctrl		Le contrôleur
	 */
	public PanelBanque(Notion notion, Controleur ctrl){
        this.ctrl        = ctrl;
		this.notion      = notion;
        this.panelBanque = new JPanel();
		this.setLayout ( new BorderLayout() );
		this.setVisible(true);

        String[] tabEntetes = {"Question", "Ressource", "Notion", "Point"};

		ArrayList<Question> questList = this.ctrl.getQuestionsParNotion(notion);
        String[][] data               = new String[questList.size()][4];

		for(int i = 0; i < questList.size();i++){
			data[i][0] = questList.get(i).getEnonceFich();
			data[i][1] = questList.get(i).getNotion().getRessourceAssociee().getNom();
			data[i][2] = questList.get(i).getNotion().getNom();
			data[i][3] = "" + questList.get(i).getPoint();
		}

		DefaultTableModel model = new DefaultTableModel(data, tabEntetes);
		this.tbQuestion = new JTable(model);

		this.mdRessources   = new JComboBox<>(ctrl.getRessources().toArray(new Ressource[0]));
		this.mdNotions		= new JComboBox<>(ctrl.getNotionsParRessource(notion.getRessourceAssociee()).toArray(new Notion[0]));
		this.mdRessources.setSelectedItem(notion.getRessourceAssociee());

		// Nombre maximal de lignes sans scroll
        int maxVisibleRows = 5;
		
		// Calcul dynamique de la hauteur
        int rowHeight     = this.tbQuestion.getRowHeight();
        int rowCount      = this.tbQuestion.getRowCount();
        int visibleHeight = rowHeight * Math.min(this.tbQuestion.getRowCount(), maxVisibleRows) + rowHeight;

        // Ajuster la taille visible de la table
        this.tbQuestion.setPreferredScrollableViewportSize(new Dimension(800, visibleHeight));

		JScrollPane scrollPane = new JScrollPane(this.tbQuestion);
		this.btnCreaQuest       = new JButton("Nouvelle Question"    );

		this.panelBanque.add(scrollPane, BorderLayout.CENTER);
		this.panelBanque.add(this.btnCreaQuest, BorderLayout.SOUTH  );

        this.add(panelBanque);

		this.btnCreaQuest.addActionListener(this);

		this.mdRessources.addItemListener(this);
		this.mdNotions.addItemListener(this);
    }

	// Methode
	/**
	 * Methode actionPerformed
	 * @param e L'évènement à traiter
	 */
	public void actionPerformed(ActionEvent e){
        if ( this.btnCreaQuest == e.getSource()){
            FrameCreationQuestion.creerFrameCreationQuestion(this.ctrl,this);
        }
	}

	public void maj(){
		String[] tabEntetes = {"Question", "Ressource", "Notion", "Point"};
		ArrayList<Question> questList;

		if (this.notion != null) {
			questList = this.ctrl.getQuestionsParNotion(notion);
		}else {
			questList = new ArrayList<>();
		}

		String[][] data = new String[questList.size()][4];

		for(int i = 0; i < questList.size();i++){
			data[i][0] = questList.get(i).getEnonceFich();
			data[i][1] = questList.get(i).getNotion().getRessourceAssociee().getNom();
			data[i][2] = questList.get(i).getNotion().getNom();
			data[i][3] = "" + questList.get(i).getPoint();
		}


		DefaultTableModel model = new DefaultTableModel(data, tabEntetes);
		this.tbQuestion.setModel(model);

		this.ctrl.miseAJourFichiers();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == this.mdRessources){
			System.out.println("Ressource");
			Ressource ressource = (Ressource) this.mdRessources.getSelectedItem();
			this.notion = (Notion) this.mdNotions.getSelectedItem();

			this.mdRessources   = new JComboBox<>(ctrl.getRessources().toArray(new Ressource[0]));
			this.mdNotions		= new JComboBox<>(ctrl.getNotionsParRessource(ressource).toArray(new Notion[0]));
			System.out.println(ctrl.getNotionsParRessource(ressource));
			this.mdRessources.setSelectedItem(ressource);
			if(this.notion != null && ressource != null && this.notion.getRessourceAssociee().equals(ressource)){
				this.mdNotions.setSelectedItem(this.notion);
			} else {
				this.notion	= null;
			}
		} else
			if (e.getSource() == this.mdNotions){
				System.out.println("Notion");
				this.notion = (Notion) this.mdNotions.getSelectedItem();
		}

		this.maj();
	}
}
