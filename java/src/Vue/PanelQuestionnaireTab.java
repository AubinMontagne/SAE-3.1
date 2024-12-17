package src.Vue;

import java.awt.*;
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

public class PanelQuestionnaireTab extends JPanel implements ActionListener {
    private JTable            tbQuestion;
    private JTable            tbResult;
    private JPanel            panelQuestionnaireTab;
    private ArrayList<Notion> notions;
    private Controleur        ctrl;
    private JButton           btGenerer;
    private JLabel            lbTotal;
    private String            titreQuestionnaire;
    private Ressource         r;
    private boolean           estChrono;

    // Constructeur

    /**
     * Constructeur de la class PanelQuestionnaireTab
     * @param ctrl      Le contrôleur
     * @param r         La ressource
     * @param titre     Le titre d'un questionnaire
     * @param estChrono Si un questionnaire est chronométré
     */
    public PanelQuestionnaireTab(Controleur ctrl, Ressource r, String titre, Boolean estChrono) {
        this.panelQuestionnaireTab = new JPanel(new BorderLayout());
        this.panelQuestionnaireTab.setVisible(true);
        this.ctrl                  = ctrl;
        this.titreQuestionnaire    = titre;
        this.r                     = r;
        this.estChrono             = estChrono;

        this.notions               = ctrl.getNotions();

        // Instanciation des data des table
        String[] columnNames = {"Notion", "", "TF", "F", "M", "D", ""};
        Object[][] data = new Object[notions.size()][7];

        String[] columnResult = {"", "", "", "", "", ""};
        Object[] dataResult = { "Nb Questions / Catégorie", 0, 0, 0, 0, "Σ =" };

        int alInd = 0;
        for (int i = 0; i < this.notions.size(); i++) {
            boolean verif = false;
            while (!verif && alInd < this.notions.size()) {
                if (this.notions.get(alInd).getRessourceAssociee() == r) {
                    data[i][0] = this.notions.get(alInd).getNom();
                    data[i][1] = false;
                    verif = true;
                }
                alInd++;
            }
        }

        // Définition du model des tables
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) return Boolean.class;
                if (columnIndex >= 2 && columnIndex <= 5) return Integer.class;
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 6) return false;
                if (column >= 2 && column <= 5) {
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
                
                // Si la case est désélectionnée (false), remettre les valeurs de la colonne 2 à 5 à 0
                if (isSelected != null && !isSelected) {
                    model.setValueAt(0, row, 2); // Remettre à 0 la colonne 2 (TF)
                    model.setValueAt(0, row, 3); // Remettre à 0 la colonne 3 (F)
                    model.setValueAt(0, row, 4); // Remettre à 0 la colonne 4 (M)
                    model.setValueAt(0, row, 5); // Remettre à 0 la colonne 5 (D)
                }
            }
        });

        // Configuration de la table
        tbQuestion = new JTable(model);
        tbQuestion.setPreferredScrollableViewportSize(new Dimension(
                tbQuestion.getPreferredScrollableViewportSize().width,
                tbQuestion.getRowHeight() * 6
            ));

        for (int col = 2; col <= 5; col++) {
            tbQuestion.getColumnModel().getColumn(col).setCellRenderer(new CustomCellRenderer(model));
            tbQuestion.getColumnModel().getColumn(col).setCellEditor(new CustomCellEditor(model));
        }

        this.lbTotal = new JLabel("Totaux : TF = 0, F = 0, M = 0, D = 0");

        this.btGenerer = new JButton("Générer Questionnaire");
		this.btGenerer.addActionListener(this);

        JScrollPane scrollPane = new JScrollPane(tbQuestion);
        this.panelQuestionnaireTab.add(scrollPane, BorderLayout.NORTH);
        this.panelQuestionnaireTab.add(lbTotal, BorderLayout.CENTER);
        this.panelQuestionnaireTab.add(btGenerer, BorderLayout.SOUTH);

        this.add(this.panelQuestionnaireTab);
        updateTotals(model);
    }


    // Class interne pour customisé les casses du tableau
    private static class CustomCellRenderer extends DefaultTableCellRenderer {
        private final DefaultTableModel model;

        public CustomCellRenderer(DefaultTableModel model) {
            this.model = model;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            Boolean isRowSelected = (Boolean) model.getValueAt(row, 1);
            if (isRowSelected != null && isRowSelected && column >= 2 && column <= 5) {
                switch (column) {
                    case 2 -> setBackground(new Color(133, 222, 146));
                    case 3 -> setBackground(new Color(181, 165, 196));
                    case 4 -> setBackground(new Color(189, 40, 47));
                    case 5 -> setBackground(new Color(126, 128, 126));
                }
                setForeground(Color.BLACK);
            } else {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }
            return this;
        }
    }

    // Class interne pour pouvoir éditer la customisation des cases du tableau
    private static class CustomCellEditor extends DefaultCellEditor {
        private final DefaultTableModel model;

        public CustomCellEditor(DefaultTableModel model) {
            super(new JTextField());
            this.model = model;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            JTextField textField = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);

            textField.setDocument(new PlainDocument() {
                @Override
                public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                    if (str.matches("\\d*")) {
                        super.insertString(offset, str, attr);
                    }
                }
            });

            Boolean isRowSelected = (Boolean) model.getValueAt(row, 1);
            if (isRowSelected != null && isRowSelected && column >= 2 && column <= 5) {
                switch (column) {
                    case 2 -> textField.setBackground(new Color(133, 222, 146));
                    case 3 -> textField.setBackground(new Color(181, 165, 196));
                    case 4 -> textField.setBackground(new Color(189, 40, 47));
                    case 5 -> textField.setBackground(new Color(126, 128, 126));
                }
                textField.setForeground(Color.BLACK);
            } else {
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
    private void updateTotals(DefaultTableModel model) {
        int totalTF = 0;
        int totalF  = 0;
        int totalM  = 0;
        int totalD  = 0;
        int total   = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            // S'assurer que la valeur récupérée est bien un entier
            totalTF += getIntValue(model.getValueAt(i, 2));
            totalF += getIntValue(model.getValueAt(i, 3));
            totalM += getIntValue(model.getValueAt(i, 4));
            totalD += getIntValue(model.getValueAt(i, 5));
        }

        total += totalD + totalF + totalM + totalTF;
        // Mettre à jour l'affichage des totaux
        this.lbTotal.setText(String.format("Totaux : TF = %d, F = %d, M = %d, D = %d     Σ = %d", totalTF, totalF, totalM, totalD, total));
    }

    // Fonction utilitaire pour obtenir un entier, ou 0 si la valeur est invalide
    private int getIntValue(Object value) {
        if (value == null) {
            return 0; // Retourner 0 si la valeur est nulle
        }

        try {
            if (value instanceof String) {
                // Si la valeur est une chaîne, essayer de la convertir en entier
                return Integer.parseInt((String) value);
            } else if (value instanceof Integer) {
                // Si la valeur est déjà un entier, la retourner directement
                return (Integer) value;
            }
        } catch (NumberFormatException e) {
            // Si la conversion échoue, retourner 0
            return 0;
        }
        return 0; // Retourner 0 par défaut si aucune condition n'est remplie
    }

    /**
     * Methode actionPerformed
     * @param e L'évènement à traiter
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btGenerer) {
            // Récupération des données pour les notions sélectionnées
            ArrayList<QuestionnaireData> selectedData = getSelectedData();

            if (selectedData.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucune notion sélectionnée.", "Avertissement", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Questionnaire questionnaire = new Questionnaire(this.titreQuestionnaire, this.r, this.estChrono);

            // Préparation de l'affichage des données
            Difficulte tf = Difficulte.TRES_FACILE;
            
            StringBuilder sb = new StringBuilder("Données du questionnaire:\n");
            int totalTF = 0, totalF = 0, totalM = 0, totalD = 0;

            for (QuestionnaireData data : selectedData) {
                sb.append(String.format("Notion: %s\n", data.getNotion()));
                sb.append(String.format("  - Très facile (TF): %d\n", data.getTf()));
                sb.append(String.format("  - Facile (F): %d\n", data.getF()));
                sb.append(String.format("  - Moyen (M): %d\n", data.getM()));
                sb.append(String.format("  - Difficile (D): %d\n", data.getD()));

                totalTF += data.getTf();
                totalF += data.getF();
                totalM += data.getM();
                totalD += data.getD();
/*
                questionnaire.defNbQuestion(
                        ctrl.getMetier().getNotionByNom(data.getNotion(), tf, data.getTf()  )
                );

                questionnaire.defNbQuestion(
                        ctrl.getMetier().getNotionByNom(data.getNotion(), new Difficulte(f, 0),  data.getTf()  )
                );

                questionnaire.defNbQuestion(
                        ctrl.getMetier().getNotionByNom(data.getNotion(), new Difficulte(tf, 0), data.getTf()  )
                );

                questionnaire.defNbQuestion(
                        ctrl.getMetier().getNotionByNom(data.getNotion(), new Difficulte(tf, 0), data.getTf()  )
                );*/
            }

            // Ajout du total
            sb.append("\nRésumé des difficultés:\n");
            sb.append(String.format("  - Total Très Facile (TF): %d\n", totalTF));
            sb.append(String.format("  - Total Facile (F): %d\n", totalF));
            sb.append(String.format("  - Total Moyen (M): %d\n", totalM));
            sb.append(String.format("  - Total Difficile (D): %d\n", totalD));
            sb.append(String.format("  - Total Questions: %d\n", (totalTF + totalF + totalM + totalD)));

            // Affichage des données dans le JOptionPane
            JOptionPane.showMessageDialog(this, sb.toString(), "Résumé du questionnaire", JOptionPane.INFORMATION_MESSAGE);

            // Exemple de génération du questionnaire
            System.out.println(questionnaire.toString());
        }
    }

    /**
     * Méthode pour récupérer les données des notions sélectionnées.
     *
     * @return Liste des données pour les notions sélectionnées.
     */
    private ArrayList<QuestionnaireData> getSelectedData() {
        ArrayList<QuestionnaireData> selectedData = new ArrayList<>();

        DefaultTableModel model = (DefaultTableModel) tbQuestion.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean isSelected = (Boolean) model.getValueAt(i, 1);

            if (isSelected != null && isSelected) {
                String notion = (String) model.getValueAt(i, 0);
                int tf = getIntValue(model.getValueAt(i, 2));
                int f = getIntValue(model.getValueAt(i, 3));
                int m = getIntValue(model.getValueAt(i, 4));
                int d = getIntValue(model.getValueAt(i, 5));

                selectedData.add(new QuestionnaireData(notion, tf, f, m, d));
            }
        }

        return selectedData;
    }

    /**
     * Classe interne pour stocker les données d'une ligne sélectionnée.
     */
    private static class QuestionnaireData {
        private final String notion;
        private final int tf;
        private final int f;
        private final int m;
        private final int d;

        public QuestionnaireData(String notion, int tf, int f, int m, int d) {
            this.notion = notion;
            this.tf = tf;
            this.f = f;
            this.m = m;
            this.d = d;
        }

        public String getNotion() {
            return notion;
        }

        public int getTf() {
            return tf;
        }

        public int getF() {
            return f;
        }

        public int getM() {
            return m;
        }

        public int getD() {
            return d;
        }
    }
}
