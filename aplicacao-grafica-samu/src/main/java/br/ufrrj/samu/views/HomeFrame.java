package br.ufrrj.samu.views;

import br.ufrrj.samu.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;

import static br.ufrrj.samu.utils.Util.centreWindow;
import static java.util.Objects.requireNonNull;

public class HomeFrame extends JFrame {

    private static final Logger LOGGER = LogManager.getLogger(HomeFrame.class);

    private String frameTitle = "SAMU - Sistema de Aux\u00EDlio a Matr\u00EDcula Universit\u00E1ria";
    private int width = 1366;
    private int height = 720;
    JPanel mainJPanel;

    JTable coursesTable;

    public HomeFrame() throws HeadlessException {
        super();
        frameInit();
        mainJPanel = new JPanel();
        mainJPanel.setLayout(new BorderLayout());
        mainJPanel.setBackground(Color.GREEN);

        initLeftSite();
        initRightSite();

        this.add(mainJPanel);
        this.setVisible(true);
    }

    private void initLeftSite() {
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setPreferredSize(new Dimension(250, height));
        userInfoPanel.setLayout(new GridBagLayout());
        Util.switchMode();
        Util.switchMode();
//        userInfoPanel.setBorder(BorderFactory.createLineBorder(UIManager.getColor("SAMU.homeBorderColor"), 2));
        userInfoPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, UIManager.getColor("Button.default.startBorderColor")));

        JLabel userImage = new JLabel();
        userImage.setBackground(Color.WHITE);
        userImage.setOpaque(true);
        userImage.setSize(new Dimension(150, 150));
        userImage.setIcon(new ImageIcon(requireNonNull(this.getClass().getClassLoader().getResource("images/userImage.png"))));

        JLabel username = new JLabel("Nome do Usu\u00E1rio");
        JLabel enrollment = new JLabel("Matr\u00EDcula");
        JLabel course = new JLabel("Curso");
        JLabel semester = new JLabel("Semestre");

        GridBagConstraints gridConstraints = new GridBagConstraints();

        gridConstraints.anchor = GridBagConstraints.CENTER;
        gridConstraints.weighty = 0.05;

        gridConstraints.gridy = 1;
        gridConstraints.gridx = 0;
        gridConstraints.fill = GridBagConstraints.NONE; /* Troquei para NONE, o both tava fazendo ficar com a parte branca bem maior */
        gridConstraints.insets = new Insets(0, 0, 10, 4);
        userInfoPanel.add(userImage, gridConstraints);

        gridConstraints.insets = new Insets(0, 0, 0, 0);

        gridConstraints.fill = GridBagConstraints.NONE;
        gridConstraints.gridy = 2;
        userInfoPanel.add(username, gridConstraints);

        gridConstraints.gridy = 3;
        userInfoPanel.add(enrollment, gridConstraints);

        gridConstraints.gridy = 4;
        userInfoPanel.add(course, gridConstraints);

        gridConstraints.gridy = 5;
        userInfoPanel.add(semester, gridConstraints);

        gridConstraints.weighty = 0.08;
        gridConstraints.gridy = 6;
        gridConstraints.insets = new Insets(20,0,0,0);
        userInfoPanel.add(new JLabel("Menu de Navega\u00E7\u00E3o"), gridConstraints);

        /* Zerei o espaçamento entre os botões e coloquei um preenchimento maneiro */
        gridConstraints.weighty = 0;
        gridConstraints.insets = new Insets(0,0,0,0);
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.anchor = GridBagConstraints.FIRST_LINE_START;

        gridConstraints.gridy = 7;
        userInfoPanel.add(new JButton("Disciplinas Matriculadas"), gridConstraints);

        gridConstraints.gridy = 8;
        userInfoPanel.add(new JButton("Disciplinas Dispon\u00EDveis"), gridConstraints);

        gridConstraints.gridy = 9;
        gridConstraints.weighty = 2.0;
        userInfoPanel.add(new JButton("Matriz Curricular"), gridConstraints);

        mainJPanel.add(userInfoPanel, BorderLayout.WEST);
    }

    private void initRightSite() {
        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setLayout(new GridBagLayout());


        JPanel tableJPanel = new JPanel();
        tableJPanel.setLayout(new BorderLayout());

        GridBagConstraints gridConstraints = new GridBagConstraints();
        JScrollPane table = coursesTable();

        tableJPanel.add(table, BorderLayout.CENTER);

        gridConstraints.gridx = 2;
        gridConstraints.gridy = 1;
        gridConstraints.insets = new Insets(10, 10, 10, 10);
        gridConstraints.anchor = GridBagConstraints.EAST;
        rightSidePanel.add(Util.THEME_BUTTON, gridConstraints);


        gridConstraints.gridx = 0;
        gridConstraints.gridy = 2;
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 1;
        gridConstraints.anchor = GridBagConstraints.CENTER;
        gridConstraints.fill = GridBagConstraints.BOTH;
        gridConstraints.gridwidth = 3;
        gridConstraints.insets = new Insets(10, 10, 10, 10);
        rightSidePanel.add(tableJPanel, gridConstraints);


        mainJPanel.add(rightSidePanel, BorderLayout.CENTER);
    }


    public JScrollPane coursesTable() {
        String[] columnNames = {"Nome da Disciplina", "Professor", "Hor\u00E1rio"};
        Object[][] data = {
                {"ARQUITETURA DE COMPUTADORES II", "MARCELO PANARO DE MORAES ZAMITH", "Qua: 10:00 - 12:00 Sex: 14:00 - 16:00"},
                {"C\u00C1LCULO APLICADO", "MARCELO PANARO DE MORAES ZAMITH", "Qua: 10:00 - 12:00 Sex: 14:00 - 16:00"},
                {"F\u00CDSICA PARA CI\u00CANCIA DA COMPUTA\u00C7\u00C3O", "JOSE WEBERSZPIL", "Qua: 10:00 - 12:00 Sex: 14:00 - 16:00"},
                {"MODELAGEM E PROJETO DE SOFTWARE", "FILIPE BRAIDA DO CARMO", "Ter e Qui: 10:00 - 12:00"},
                {"PROBABILIDADE E ESTAT\u00CDSTICA PARA CI\u00CANCIA DA COMPUTA\u00C7\u00C3O", "ADEILTON PEDRO DE ALCANTARA", "Seg e Sex: 16:00 - 18:00"},
                {"C\u00C1LCULO I", "ALEKSANDRO DE MELLO", "Seg e Sex: 16:00 - 18:00"},
        };
        coursesTable = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                return String.class;
            }

            @Override
            protected void createDefaultRenderers() {
                super.createDefaultRenderers();
            }
        };
//        table.getModel().addTableModelListener(e -> {
//            int row = e.getFirstRow();
//            int column = e.getColumn();
//            if (column == 4) {
//                TableModel model = (TableModel) e.getSource();
//                String columnName = model.getColumnName(column);
//                Boolean checked = (Boolean) model.getValueAt(row, column);
//                if (checked) {
//                    System.out.println(columnName + ": " + true);
//                } else {
//                    System.out.println(columnName + ": " + false);
//                }
//            }
//        });
        coursesTable.setColumnSelectionAllowed(true);
        coursesTable.setShowGrid(false);
        coursesTable.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                int row = coursesTable.rowAtPoint(e.getPoint());
                int col = coursesTable.columnAtPoint(e.getPoint());
                if (row > -1 && col > -1) {
                    Object value = coursesTable.getValueAt(row, col);
                    if (null != value && !"".equals(value)) {
                        coursesTable.setToolTipText(value.toString());// floating display cell content
                    } else {
                        coursesTable.setToolTipText(null);
                    }
                }
            }
        });
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        coursesTable.setDefaultRenderer(String.class, centerRenderer);
//        table.getColumnModel().getColumn(0).setCellRenderer(new WordWrapCellRenderer()); //bugado
        coursesTable.setFont(coursesTable.getFont().deriveFont(18f));
        coursesTable.setRowHeight(coursesTable.getFont().getSize() * 4);
        coursesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        coursesTable.getTableHeader().setReorderingAllowed(false);
        coursesTable.setCellSelectionEnabled(false);
        coursesTable.setDragEnabled(false);
        coursesTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(coursesTable);
        return scrollPane;
    }

    public static void resizeColumnsWidth(JTable table, Dimension dimension) {
        final TableColumnModel columnModel = table.getColumnModel();
        float namePercentage = 0.50f;
        columnModel.getColumn(0).setPreferredWidth((int) (dimension.getWidth() * namePercentage));
        for (int columnIndex = 1; columnIndex < table.getColumnCount(); columnIndex++) {
            columnModel.getColumn(columnIndex).setPreferredWidth((int) (dimension.getWidth() * ((1.0 - namePercentage) / 2)));
        }
    }

    @Override
    protected void frameInit() {
        super.frameInit();
        this.setSize(this.width, this.height);
        this.setMinimumSize(new Dimension(this.width, this.height));
        this.setTitle(this.frameTitle);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon(requireNonNull(this.getClass().getClassLoader().getResource("friend.png"))).getImage());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LOGGER.info("Closing LoginFrame window");
            }
        });
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                if (coursesTable != null) {
                    resizeColumnsWidth(coursesTable, coursesTable.getSize());
                }
            }
        });
        centreWindow(this);
//        this.setResizable(false);
    }

    public static void main(String[] args) {
        new HomeFrame();
    }

}
