package src.Vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.Metier.Question;
import src.Metier.Notion;
import src.Controleur;


public class PanelBanque extends JPanel implements  ActionListener{
    private Controleur ctrl;
	private Notion notion;
	private JButton btCreaQuest;
    private JPanel panelBanque;
    private JTable tbQuestion;
	


    public PanelBanque(Controleur ctrl){
        this.ctrl         = ctrl;
        this.panelBanque  = new JPanel();
		this.setLayout ( new BorderLayout() );
		this.setVisible(true);

        String[] tabEntetes = {"Question", "Ressource", "Notion", "Point"};

		ArrayList<Question> questList = this.ctrl.getQuestions();
        String[][] data = new String[questList.size()][4];

		for(int i = 0; i < questList.size();i++){
			data[i][0] = questList.get(i).getIntitule();
			data[i][1] = questList.get(i).getNotion().getRessourceAssociee().getNom();
			data[i][2] = questList.get(i).getNotion().getNom();
			data[i][3] = "" + questList.get(i).getPoint();
		}
        DefaultTableModel model = new DefaultTableModel(data, tabEntetes);
        this.tbQuestion = new JTable(model);

        // Nombre maximal de lignes sans scroll
        int maxVisibleRows = 5;

        // Calcul dynamique de la hauteur
        int rowHeight = this.tbQuestion.getRowHeight();
        int headerHeight = this.tbQuestion.getTableHeader().getHeight();
        int visibleHeight = rowHeight * Math.min(this.tbQuestion.getRowCount(), maxVisibleRows) + headerHeight;

        // Ajuster la taille visible de la table
        this.tbQuestion.setPreferredScrollableViewportSize(new Dimension(800, visibleHeight));

		JScrollPane scrollPane = new JScrollPane(this.tbQuestion);
		this.btCreaQuest = new JButton("Nouvelle Question"    );

		this.panelBanque.add(scrollPane, BorderLayout.CENTER);
		this.panelBanque.add(this.btCreaQuest, BorderLayout.SOUTH  );

        this.add(panelBanque);

		this.btCreaQuest.addActionListener(this)        ;
    }

	public PanelBanque(Notion notion, Controleur ctrl){
        this.ctrl        = ctrl;
		this.notion    = notion;
        this.panelBanque = new JPanel();
		this.setLayout ( new BorderLayout() );
		this.setVisible(true);

        String[] tabEntetes = {"Question", "Ressource", "Notion", "Point"};

		ArrayList<Question> questList = this.ctrl.getQuestionsParNotion(notion);
        String[][] data = new String[questList.size()][4];

		for(int i = 0; i < questList.size();i++){
			data[i][0] = questList.get(i).getIntitule();
			data[i][1] = questList.get(i).getNotion().getRessourceAssociee().getNom();
			data[i][2] = questList.get(i).getNotion().getNom();
			data[i][3] = "" + questList.get(i).getPoint();
		}

		DefaultTableModel model = new DefaultTableModel(data, tabEntetes);
		this.tbQuestion = new JTable(model);

		// Nombre maximal de lignes sans scroll
        int maxVisibleRows = 5;
		
		// Calcul dynamique de la hauteur
        int rowHeight = this.tbQuestion.getRowHeight();
        int rowCount = this.tbQuestion.getRowCount();
        int visibleHeight = rowHeight * Math.min(this.tbQuestion.getRowCount(), maxVisibleRows) + rowHeight;

        // Ajuster la taille visible de la table
        this.tbQuestion.setPreferredScrollableViewportSize(new Dimension(800, visibleHeight));

		JScrollPane scrollPane = new JScrollPane(this.tbQuestion);
		this.btCreaQuest = new JButton("Nouvelle Question"    );

		this.panelBanque.add(scrollPane, BorderLayout.CENTER);
		this.panelBanque.add(this.btCreaQuest, BorderLayout.SOUTH  );

        this.add(panelBanque);

		this.btCreaQuest.addActionListener(this);
    }
	public void actionPerformed(ActionEvent e){
        if ( this.btCreaQuest == e.getSource()){
            System.out.println("Hey la frame Creer question s'ouvre");
            new FrameCreationQuestion(this.ctrl);
        }
	}
}
