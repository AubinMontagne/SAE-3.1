import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.net.http.HttpResponse.BodyHandler;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public class PanelBanque extends JPanel implements  ActionListener
{
    //private Controleur ctrl;
	private Notion notion;
    private ArrayList<Question> questions;
	private JButton btCreaQuest;
    private JPanel panelBanque;
    private JTable tbQuestion;
	


    public PanelBanque( ){
        //this.ctrl         = ctrl;
        this.panelBanque = new JPanel();
		this.setLayout ( new BorderLayout() );
		this.setVisible(true);

        // En attendant d'avoir la liste des questions
		Question quest1 = new Question( "Quels Question1 ? ", "IDE", "VS Code", 145, 4) ;
		Question quest2 = new Question( "Quels Question2 ? ", "IDE", "Intel", 125, 2) ;
		Question quest3 = new Question( "Quels Question3 ? ", "IDE", "gedit", 55,5) ;
		Question quest4 = new Question( "Quels Question4 ? ", "code", "java", 45, 9) ;
		Question quest5 = new Question( "Quels Question5 ? ", "IDE", "sql", 5, 1) ;

        String[] tabEntetes = {"Question", "Ressource", "Notion", "Point"};

		

		String[][] data ={
			{quest1.getIntitule(), quest1.getRessource(), quest1.getNotion(), ""+quest1.getPoint()},
			{quest2.getIntitule(), quest2.getRessource(), quest2.getNotion(), ""+quest2.getPoint()},
			{quest3.getIntitule(), quest3.getRessource(), quest3.getNotion(), ""+quest3.getPoint()},
			{quest4.getIntitule(), quest4.getRessource(), quest4.getNotion(), ""+quest4.getPoint()},
			{quest5.getIntitule(), quest5.getRessource(), quest5.getNotion(), ""+quest5.getPoint()}
		};

		DefaultTableModel model = new DefaultTableModel(data, tabEntetes);
		this.tbQuestion = new JTable(model);

		// Calcul dynamique de la hauteur
        int rowHeight = this.tbQuestion.getRowHeight();
        int rowCount = this.tbQuestion.getRowCount();
        int tableHeight = rowHeight * rowCount + this.tbQuestion.getTableHeader().getHeight();

        // Ajuster la taille visible de la table
        this.tbQuestion.setPreferredScrollableViewportSize(new Dimension(800, tableHeight));

		JScrollPane scrollPane = new JScrollPane(this.tbQuestion);
		this.btCreaQuest = new JButton("Nouvelle Question"    );

		this.panelBanque.add(scrollPane, BorderLayout.CENTER);
		this.panelBanque.add(this.btCreaQuest, BorderLayout.SOUTH  );

        this.add(panelBanque);

		this.btCreaQuest.addActionListener(this)        ;
    }

	public PanelBanque(Notion n ){
        //this.ctrl         = ctrl;
		this.notion = n;
        this.panelBanque = new JPanel();
		this.setLayout ( new BorderLayout() );
		this.setVisible(true);

		Ressource ress1 = new Ressource(1, "QualitéDev"  , "R4.11");
        Ressource ress2 = new Ressource(2, "DevEfficace" , "R8.01");
        Ressource ress3 = new Ressource(3, "Cryptomonaie", "R1.06");

        Notion not1 = new Notion(1,"Truk"         , ress1 );
        Notion not2 = new Notion(2,"Machin"       , ress1 );
        Notion not3 = new Notion(3,"Miche"        , ress2 );

        // En attendant d'avoir la liste des questions
        this.questions = new ArrayList<Question>();
		Question quest1 = new Question( "Quels Question1 ? ", this.notion.getRessourceAssociee(), this.notion, 145, 4) ;
		Question quest2 = new Question( "Quels Question2 ? ", ress1, not1, 125, 2) ;
		Question quest3 = new Question( "Quels Question3 ? ", ress2, not2, 55,5) ;
		Question quest4 = new Question( "Quels Question4 ? ", ress3, not3, 45, 9) ;
		Question quest5 = new Question( "Quels Question5 ? ", this.notion.getRessourceAssociee(), this.notion, 5, 1) ;

        String[] tabEntetes = {"Question", "Ressource", "Notion", "Point"};

		this.questions.add(quest1);
        this.questions.add(quest2);
        this.questions.add(quest3);
        this.questions.add(quest4);
        this.questions.add(quest5);

		String[][] data = new String[this.questions.size()][4];
		int i = -1;
		for (int cpt = 0; cpt < this.questions.size(); cpt++) 
		{
			Question quest = this.questions.get(cpt);
			if(quest.geTNotion() == this.notion )
			{
				i++;
				data[i][0] = quest.getIntitule();
    			data[i][1] = quest.getTRessource().getNom(); // Supposons que Ressource a une méthode getNom()
    			data[i][2] = quest.geTNotion().getNom();           // Supposons que Notion a une méthode getNom()
    			data[i][3] = String.valueOf(quest.getPoint());
			}
    		
   			
		}

		DefaultTableModel model = new DefaultTableModel(data, tabEntetes);
		this.tbQuestion = new JTable(model);

		// Calcul dynamique de la hauteur
        int rowHeight = this.tbQuestion.getRowHeight();
        int rowCount = this.tbQuestion.getRowCount();
        int tableHeight = rowHeight * rowCount + this.tbQuestion.getTableHeader().getHeight();

        // Ajuster la taille visible de la table
        this.tbQuestion.setPreferredScrollableViewportSize(new Dimension(800, tableHeight));

		JScrollPane scrollPane = new JScrollPane(this.tbQuestion);
		this.btCreaQuest = new JButton("Nouvelle Question"    );

		this.panelBanque.add(scrollPane, BorderLayout.CENTER);
		this.panelBanque.add(this.btCreaQuest, BorderLayout.SOUTH  );

        this.add(panelBanque);

		this.btCreaQuest.addActionListener(this)        ;
    }
	public void actionPerformed(ActionEvent e){
        if ( this.btCreaQuest == e.getSource()){
            System.out.println("Hey la frame Creer question s'ouvre");
            
        }
	}
}
