package src.Vue;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.util.ArrayList;

import src.*;
import src.Metier.*;

public class PanelQuestionnaireTab extends JPanel {
    private JTable tbQuestion;
    private JTable tbResult;
    private JPanel panelQuestionnaireTab;
    private ArrayList<Notion> notions;
    private Controleur ctrl;

    public PanelQuestionnaireTab(Controleur ctrl, Ressource r) {
        this.panelQuestionnaireTab = new JPanel(new BorderLayout());
        this.panelQuestionnaireTab.setVisible(true);
        this.ctrl = ctrl;

        this.notions = ctrl.getNotions();

        // Prepare table data
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

        // Define table model
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

        // Listen for updates
        model.addTableModelListener(e -> {
            if (e.getColumn() == 1) {
                int row = e.getFirstRow();
                for (int col = 2; col <= 5; col++) {
                    model.fireTableCellUpdated(row, col);
                }
            }
        });

        // Configure table
        tbQuestion = new JTable(model);
        tbQuestion.setPreferredScrollableViewportSize(new Dimension(
                tbQuestion.getPreferredScrollableViewportSize().width,
                tbQuestion.getRowHeight() * 6
        ));

        tbResult = new JTable(model);

        for (int col = 2; col <= 5; col++) {
            tbQuestion.getColumnModel().getColumn(col).setCellRenderer(new CustomCellRenderer(model));
            tbQuestion.getColumnModel().getColumn(col).setCellEditor(new CustomCellEditor(model));
        }

        JScrollPane scrollPane = new JScrollPane(tbQuestion);
        this.panelQuestionnaireTab.add(scrollPane, BorderLayout.CENTER);
        this.panelQuestionnaireTab.add(tbResult, BorderLayout.CENTER);

        this.add(this.panelQuestionnaireTab);
    }

    // Custom cell renderer
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

    // Custom cell editor
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
}
