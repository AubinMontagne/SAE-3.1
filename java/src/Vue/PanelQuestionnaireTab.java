package src.Vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import src.*;
import src.Metier.Difficulte;
import src.Metier.*;

public class PanelQuestionnaireTab extends JPanel implements ActionListener
{
    private JTable            tblQuestion;
    private JTable            tblResult;
    private JPanel            panelQuestionnaireTab;
    private ArrayList<Notion> lstNotions;
    private Controleur        ctrl;
    private JButton           btnGenerer;
    private JLabel            lblTotal;
    private String            titreQuestionnaire;
    private Ressource         r;
    private boolean           estChrono;

    private FrameQuestionnaire frame;

    /**
     * Constructeur de la class PanelQuestionnaireTab
     * @param ctrl      Le contrôleur
     * @param r         La ressource
     * @param titre     Le titre d'un questionnaire
     * @param estChrono Si un questionnaire est chronométré
     */
    public PanelQuestionnaireTab(FrameQuestionnaire frame,Controleur ctrl, Ressource r, String titre, Boolean estChrono)
    {
        this.frame                 = frame;
        this.panelQuestionnaireTab = new JPanel(new BorderLayout());
        this.ctrl                  = ctrl;
        this.titreQuestionnaire    = titre;
        this.r                     = r;
        this.estChrono             = estChrono;
        this.lstNotions            = ctrl.getNotionsParRessource(this.r);

        // Instanciation des data des table
        String[] columnNames = {"Notion", "", "TF", "F", "M", "D", ""};
        Object[][] data = new Object[this.lstNotions.size()][7];

        int i = 0;
        for (int cpt = 0; cpt < this.lstNotions.size(); cpt++)
        {
            boolean verif = false;
            while (!verif && i < this.lstNotions.size())
            {
                if (this.lstNotions.get(i).getRessourceAssociee() == r)
                {
                    data[cpt][0] = this.lstNotions.get(i).getNom();
                    data[cpt][1] = false;
                    verif      = true;
                }
                i++;
            }
        }

        // Définition du model des tables
        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
            @Override
            public Class<?> getColumnClass(int columnIndex)
            {
                if (columnIndex == 1)                     {return Boolean.class;}
                if (columnIndex >= 2 && columnIndex <= 5) {return Integer.class;}
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column)
            {
                if (column == 0 || column == 6) {return false;}
                if (column >= 2 && column <= 5)
                {
                    Boolean isSelected = (Boolean) getValueAt(row, 1);
                    return isSelected != null && isSelected;
                }
                return true;
            }
        };

        // Capteur de changement
        model.addTableModelListener(e -> {
            if (e.getColumn() == 1) {
                int row = e.getFirstRow();
                for (int col = 2; col <= 5; col++) {
                    model.fireTableCellUpdated(row, col);
                }
            }
        });

        model.addTableModelListener(e -> {
            if (e.getColumn() >= 2 && e.getColumn() <= 5) {
                // Update Le total des colonnes si il y en a une qui change
                updateTotals(model);
            }
        });

        model.addTableModelListener(e -> {
            if (e.getColumn() == 1) { // Si la colonne modifiée est la colonne 1 (la case à cocher)
                int row = e.getFirstRow();
                Boolean isSelected = (Boolean) model.getValueAt(row, 1);
                
                // Si la case est désélectionnée, remettre les valeurs de la colonne 2 à 5 à 0
                if (isSelected != null && !isSelected) {
                    model.setValueAt(0, row, 2);
                    model.setValueAt(0, row, 3);
                    model.setValueAt(0, row, 4);
                    model.setValueAt(0, row, 5);
                }
            }
        });

        // Configuration de la table
        this.tblQuestion = new JTable(model);
        this.tblQuestion.setPreferredScrollableViewportSize( new Dimension(
                this.tblQuestion.getPreferredScrollableViewportSize().width,
                this.tblQuestion.getRowHeight() * 6
            )
        );

        for (int col = 2; col <= 5; col++)
        {
            this.tblQuestion.getColumnModel().getColumn(col).setCellRenderer(new CustomCellRenderer(model));
            this.tblQuestion.getColumnModel().getColumn(col).setCellEditor  (new CustomCellEditor  (model));
        }

        this.lblTotal = new JLabel("Totaux : TF = 0, F = 0, M = 0, D = 0");

        this.btnGenerer = new JButton("Générer Questionnaire");
		this.btnGenerer.addActionListener(this);

        JScrollPane scrollPane = new JScrollPane(this.tblQuestion);
        this.panelQuestionnaireTab.add(scrollPane     , BorderLayout.NORTH );
        this.panelQuestionnaireTab.add(this.lblTotal  , BorderLayout.CENTER);
        this.panelQuestionnaireTab.add(this.btnGenerer, BorderLayout.SOUTH );
        this.panelQuestionnaireTab.setVisible(true);

        this.add(this.panelQuestionnaireTab);
        updateTotals(model);
    }


    // Class interne pour customisé les casses du tableau
    private static class CustomCellRenderer extends DefaultTableCellRenderer
    {
        private final DefaultTableModel model;

        public CustomCellRenderer(DefaultTableModel model) {
            this.model = model;
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column )
        {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            Boolean isRowSelected = (Boolean) model.getValueAt(row, 1);
            if (isRowSelected != null && isRowSelected && column >= 2 && column <= 5)
            {
                switch (column)
                {
                    case 2 -> setBackground(new Color(133, 222, 146));
                    case 3 -> setBackground(new Color(181, 165, 196));
                    case 4 -> setBackground(new Color(189, 40 , 47) );
                    case 5 -> setBackground(new Color(126, 128, 126));
                }
                setForeground(Color.BLACK);
            }
            else
            {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }
            return this;
        }
    }

    // Class interne pour pouvoir éditer la customisation des cases du tableau
    private static class CustomCellEditor extends DefaultCellEditor
    {
        private final DefaultTableModel model;

        public CustomCellEditor(DefaultTableModel model)
        {
            super(new JTextField());
            this.model = model;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
        {
            JTextField textField = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);

            textField.setDocument(new PlainDocument() {
                @Override
                public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException
                {
                    if (str.matches("\\d*"))
                    {
                        super.insertString(offset, str, attr);
                    }
                }
            });

            Boolean isRowSelected = (Boolean) model.getValueAt(row, 1);
            if (isRowSelected != null && isRowSelected && column >= 2 && column <= 5)
            {
                switch (column)
                {
                    case 2 -> textField.setBackground(new Color(133, 222, 146));
                    case 3 -> textField.setBackground(new Color(181, 165, 196));
                    case 4 -> textField.setBackground(new Color(189,  40, 47) );
                    case 5 -> textField.setBackground(new Color(126, 128, 126));
                }
                textField.setForeground(Color.BLACK);
            }
            else
            {
                textField.setBackground(Color.WHITE);
                textField.setForeground(Color.BLACK);
            }

            return textField;
        }
    }

    // Methode

    /**
     * Methode updateTotals
     * @param model Model du tableau
     */
    private void updateTotals(DefaultTableModel model)
    {
        int totalTF = 0;
        int totalF  = 0;
        int totalM  = 0;
        int totalD  = 0;
        int total   = 0;

        for (int i = 0; i < model.getRowCount(); i++)
        {
            // S'assurer que la valeur récupérée est bien un entier
            totalTF += getIntValue(model.getValueAt(i, 2));
            totalF  += getIntValue(model.getValueAt(i, 3));
            totalM  += getIntValue(model.getValueAt(i, 4));
            totalD  += getIntValue(model.getValueAt(i, 5));
        }

        total += totalD + totalF + totalM + totalTF;
        // Mettre à jour l'affichage des totaux
        this.lblTotal.setText(String.format(
                "Totaux : TF = %d, F = %d, M = %d, D = %d     Σ = %d", totalTF, totalF, totalM, totalD, total)
        );
    }

    // Fonction utilitaire pour obtenir un entier, ou 0 si la valeur est invalide
    private int getIntValue(Object value)
    {
        if (value == null)
        {
            return 0; // Retourner 0 si la valeur est nulle
        }

        try
        {
            if (value instanceof String)
            {
                // Si la valeur est une chaîne, essayer de la convertir en entier
                return Integer.parseInt((String) value);
            }
            else if (value instanceof Integer)
            {
                // Si la valeur est déjà un entier, la retourner directement
                return (Integer) value;
            }
        }
        catch (NumberFormatException e)
        {
            // Si la conversion échoue, retourner 0
            return 0;
        }
        return 0; // Retourner 0 par défaut si aucune condition n'est remplie
    }



    /**
     * Méthode pour récupérer les données des lstNotions sélectionnées.
     *
     * @return Liste des données pour les lstNotions sélectionnées.
     */
    private ArrayList<QuestionnaireData> getSelectedData()
    {
        ArrayList<QuestionnaireData> selectedData = new ArrayList<>();

        DefaultTableModel model = (DefaultTableModel) this.tblQuestion.getModel();

        for (int i = 0; i < model.getRowCount(); i++)
        {
            Boolean isSelected = (Boolean) model.getValueAt(i, 1);

            if (isSelected != null && isSelected)
            {
                String notion = (String) model.getValueAt(i, 0);
                int tf = getIntValue(model.getValueAt(i, 2));
                int f  = getIntValue(model.getValueAt(i, 3));
                int m  = getIntValue(model.getValueAt(i, 4));
                int d  = getIntValue(model.getValueAt(i, 5));

                selectedData.add(new QuestionnaireData(notion, tf, f, m, d));
            }
        }

        return selectedData;
    }

    /**
     * Classe interne pour stocker les données d'une ligne sélectionnée.
     */
    private static class QuestionnaireData
    {
        private String notion;
        private int    tf;
        private int    f;
        private int    m;
        private int    d;

        public QuestionnaireData(String notion, int tf, int f, int m, int d)
        {
            this.notion = notion;
            this.tf     = tf;
            this.f      = f;
            this.m      = m;
            this.d      = d;
        }

        public String getNotion() {return notion;}
        public int getTf()        {return tf;    }
        public int getF()         {return f;     }
        public int getM()         {return m;     }
        public int getD()         {return d;     }

        public void setTf(int tf) {this.tf = tf;}
        public void setF(int f)   {this.f  = f; }
        public void setM(int m)   {this.m  = m; }
        public void setD(int d)   {this.d  = d; }
    }

    /**
     * Methode actionPerformed
     * @param e L'évènement à traiter
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnGenerer)
        {
            // Récupération des données pour les lstNotions sélectionnées
            ArrayList<QuestionnaireData> selectedData = getSelectedData();

            if (selectedData.isEmpty())
            {
                JOptionPane.showMessageDialog(
                        this,
                        "Aucune notion sélectionnée.",
                        "Avertissement",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            Questionnaire questionnaire = new Questionnaire(
                    this.titreQuestionnaire,
                    this.r,
                    this.estChrono
            );

            // Préparation de l'affichage des données
            Difficulte tf = Difficulte.TRES_FACILE;
            Difficulte f  = Difficulte.FACILE;
            Difficulte m  = Difficulte.MOYEN;
            Difficulte d  = Difficulte.DIFFICILE;

            StringBuilder sb = new StringBuilder("Données du questionnaire:\n");
            int totalTF = 0, totalF = 0, totalM = 0, totalD = 0;

            for (QuestionnaireData data : selectedData)
            {
                int nbElt = ctrl.getQuestionsParNotionEtDifficulte(this.ctrl.getMetier().getNotionByNom(data.getNotion()),tf).size();
                if( nbElt < data.getTf())
                {
                    data.setTf(nbElt);
                }
                questionnaire.defNbQuestion(this.ctrl.getMetier().getNotionByNom(data.getNotion()), tf, data.getTf());

                System.out.println(data.getF());

                nbElt = ctrl.getQuestionsParNotionEtDifficulte(this.ctrl.getMetier().getNotionByNom(data.getNotion()),f).size();
                if( nbElt < data.getTf())
                {
                    data.setF(nbElt);
                }
                questionnaire.defNbQuestion(this.ctrl.getMetier().getNotionByNom(data.getNotion()), f, data.getF());


                nbElt = ctrl.getQuestionsParNotionEtDifficulte(this.ctrl.getMetier().getNotionByNom(data.getNotion()),m).size();
                if( nbElt < data.getM())
                {
                    data.setM(nbElt);
                }
                questionnaire.defNbQuestion(this.ctrl.getMetier().getNotionByNom(data.getNotion()), m, data.getM());


                nbElt = ctrl.getQuestionsParNotionEtDifficulte(this.ctrl.getMetier().getNotionByNom(data.getNotion()),d).size();
                if( nbElt < data.getTf())
                {
                    data.setD(nbElt);
                }
                questionnaire.defNbQuestion(this.ctrl.getMetier().getNotionByNom(data.getNotion()), d, data.getD());


                totalTF += data.getTf();
                totalF  += data.getF ();
                totalM  += data.getM ();
                totalD  += data.getD ();

                sb.append(String.format("Notion: %s\n"              , data.getNotion()));
                sb.append(String.format("  - Très facile (TF): %d\n", data.getTf())    );
                sb.append(String.format("  - Facile (F): %d\n"      , data.getF() )    );
                sb.append(String.format("  - Moyen (M): %d\n"       , data.getM() )    );
                sb.append(String.format("  - Difficile (D): %d\n"   , data.getD() )    );
            }

            // Ajout du total
            sb.append("\nRésumé des difficultés:\n");
            sb.append(String.format("  - Total Très Facile (TF): %d\n", totalTF));
            sb.append(String.format("  - Total Facile (F): %d\n"      , totalF));
            sb.append(String.format("  - Total Moyen (M): %d\n"       , totalM));
            sb.append(String.format("  - Total Difficile (D): %d\n"   , totalD));
            sb.append(String.format("  - Total Questions: %d\n"       , (totalTF + totalF + totalM + totalD)));

            // Affichage des données dans le JOptionPane
            JOptionPane.showMessageDialog(this, sb,"Resumé", JOptionPane.INFORMATION_MESSAGE);

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);

            if(returnValue == JFileChooser.APPROVE_OPTION)
            {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                this.ctrl.getMetier().initQuestionnaire(questionnaire,path);

                this.frame.dispose();
            }
        }
    }
}