package br.ufrrj.samu.views;

import br.ufrrj.samu.utils.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;

public class EvaluationFrame extends JFrame {

    private String frameTitle = "Avaliar turma";
    private int width = 900;
    private int height = 500;
    private JTable ratingTable;

    public EvaluationFrame() throws HeadlessException {
        super();
        frameInit();
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());

        JComboBox<String> rateComboBox = new JComboBox<>(EvaluationRate.getStringValues());
        String[] columnNames = new String[]{"Nome da Turma", "Professor", "Avalia\u00E7\u00E3o"};
        Object[][] data = new Object[][]{
                {"ARQUITETURA DE COMPUTADORES II", "MARCELO PANARO DE MORAES ZAMITH", "5"},
                {"ARQUITETURA DE COMPUTADORES II", "MARCELO PANARO DE MORAES ZAMITH", "5"},
                {"ARQUITETURA DE COMPUTADORES II", "MARCELO PANARO DE MORAES ZAMITH", "5"},
                {"ARQUITETURA DE COMPUTADORES II", "MARCELO PANARO DE MORAES ZAMITH", "5"},
                {"ARQUITETURA DE COMPUTADORES II", "MARCELO PANARO DE MORAES ZAMITH", "5"}
        };
        ratingTable = new JTable(data, columnNames);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        ratingTable.setDefaultRenderer(Object.class, centerRenderer);
        ratingTable.setColumnSelectionAllowed(true);
        ratingTable.setShowGrid(false);
        ratingTable.setFont(ratingTable.getFont().deriveFont(18f));
        ratingTable.setRowHeight(ratingTable.getFont().getSize() * 4);
        ratingTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        ratingTable.getTableHeader().setReorderingAllowed(false);
        ratingTable.setCellSelectionEnabled(false);
        ratingTable.setDragEnabled(false);
        ratingTable.setFillsViewportHeight(true);
        ratingTable.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                int row = ratingTable.rowAtPoint(e.getPoint());
                int col = ratingTable.columnAtPoint(e.getPoint());
                if (row > -1 && col > -1) {
                    Object value = ratingTable.getValueAt(row, col);
                    if (null != value && !"".equals(value)) {
                        ratingTable.setToolTipText(value.toString());// floating display cell content
                    } else {
                        ratingTable.setToolTipText(null);
                    }
                }
            }
        });

        rateComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println(e.getItem() + " selected");
                }
            }
        });



        JButton submit = new JButton("Confirmar avalia\u00E7\u00F5es");
        submit.addActionListener(e -> {
            for (int i = 0; i < ratingTable.getRowCount(); i++) {
                System.out.printf("Column[%d] rating: %s%n", i, ratingTable.getModel().getValueAt(i, 2));
            }
        });
        main.add(submit, BorderLayout.SOUTH);

        TableColumn rateColumn = ratingTable.getColumnModel().getColumn(2);
        JScrollPane scrollPane = new JScrollPane(ratingTable);

        rateColumn.setCellEditor(new DefaultCellEditor(rateComboBox));
        main.add(ratingTable.getTableHeader(), BorderLayout.PAGE_START);
        main.add(scrollPane, BorderLayout.CENTER);

        this.add(main);
        this.setVisible(true);
//        this.setResizable(false);
    }

    @Override
    protected void frameInit() {
        super.frameInit();
        this.setSize(this.width, this.height);
        this.setTitle(this.frameTitle);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setIconImage(new ImageIcon(requireNonNull(this.getClass().getClassLoader().getResource("friend.png"))).getImage());
        Util.centreWindow(this);
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                if (ratingTable != null) {
                    resizeColumnsWidth(ratingTable, ratingTable.getSize());
                }
            }
        });
    }

    enum EvaluationRate {
        FIVE("5"),
        FOUR("4"),
        THREE("3"),
        TWO("2"),
        ONE("1");
        private String value;
        EvaluationRate(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public static String[] getStringValues() {
            return Arrays.stream(values()).map(EvaluationRate::getValue).toArray(String[]::new);
        }
    }

    public void resizeColumnsWidth(JTable table, Dimension dimension) {
        final TableColumnModel columnModel = table.getColumnModel();
        float ratingPercentage = 0.20f;
        columnModel.getColumn(0).setPreferredWidth((int) (dimension.getWidth() * ((1 - ratingPercentage) / 2)));
        columnModel.getColumn(1).setPreferredWidth((int) (dimension.getWidth() * ((1 - ratingPercentage) / 2)));
        
        columnModel.getColumn(2).setMinWidth((int) (40));
    }

    public static void main(String[] args) {
        new EvaluationFrame();
    }
}
