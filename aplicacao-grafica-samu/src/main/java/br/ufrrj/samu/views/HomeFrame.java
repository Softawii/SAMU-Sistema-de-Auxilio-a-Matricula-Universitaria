package br.ufrrj.samu.views;

import br.ufrrj.samu.SAMU;
import br.ufrrj.samu.entities.Lecture;
import br.ufrrj.samu.entities.Student;
import br.ufrrj.samu.entities.Subject;
import br.ufrrj.samu.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static br.ufrrj.samu.utils.Util.centreWindow;
import static java.util.Objects.requireNonNull;

public class HomeFrame extends JFrame {

    private static final Logger LOGGER = LogManager.getLogger(HomeFrame.class);

    private String frameTitle = "SAMU - Sistema de Aux\u00EDlio a Matr\u00EDcula Universit\u00E1ria";
    private int width = 1366;
    private int height = 720;

    JPanel mainJPanel;

    JTable concludedTable;

    private Student student;
    private JButton logoutButton;
    private JButton avaliarDisciplinasButton;
    private JButton realizarMatriculaButton;
    private JTabbedPane tabbedPane;
    private JTable requestedTable;
    private JTable enrolledTable;

    public HomeFrame(Student student, SAMU samu) throws HeadlessException {
        super();
        frameInit();
        this.student = student;

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
//        userInfoPanel.setBorder(BorderFactory.createLineBorder(UIManager.getColor("SAMU.homeBorderColor"), 2));
        userInfoPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, UIManager.getColor("Button.default.startBorderColor")));

        JLabel userImage = new JLabel();
        userImage.setBackground(Color.WHITE);
        userImage.setOpaque(true);
        userImage.setSize(new Dimension(150, 150));
        userImage.setIcon(requireNonNull(Util.getImageWidth("images/userImage.png", 128,128)));

        JLabel username = new JLabel("Nome: " + student.getName());
        JLabel enrollment = new JLabel("Matr\u00EDcula: " + String.format("%s",student.getCpf()));
        JLabel course = new JLabel("Curso: " + student.getCourse().getName());
        JLabel semester = new JLabel("Entrou: " + student.getSemester());

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
        realizarMatriculaButton = new JButton("Realizar Matr\u00EDcula");
        realizarMatriculaButton.setFocusable(false);
        realizarMatriculaButton.setFont(realizarMatriculaButton.getFont().deriveFont(15f));
        realizarMatriculaButton.setFont(realizarMatriculaButton.getFont().deriveFont(15f));
        realizarMatriculaButton.addActionListener(e -> {
            EnrollFrame enrollFrame = new EnrollFrame();
            enrollFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    System.out.println("fechou a janela");
                    refreshData();
                }
            });
        });
        userInfoPanel.add(realizarMatriculaButton, gridConstraints);

        gridConstraints.gridy = 8;
        avaliarDisciplinasButton = new JButton("Avaliar Disciplinas");
        avaliarDisciplinasButton.setFocusable(false);
        avaliarDisciplinasButton.setEnabled(true);
        avaliarDisciplinasButton.setFont(avaliarDisciplinasButton.getFont().deriveFont(15f));
        avaliarDisciplinasButton.addActionListener(e -> {
            new EvaluationFrame();
        });
        userInfoPanel.add(avaliarDisciplinasButton, gridConstraints);

        gridConstraints.gridy = 9;
        gridConstraints.weighty = 2.0;
        logoutButton = new JButton("Logout");
        logoutButton.setFocusable(false);
        logoutButton.setEnabled(false);
        logoutButton.setFont(logoutButton.getFont().deriveFont(15f));
        userInfoPanel.add(logoutButton, gridConstraints);

        mainJPanel.add(userInfoPanel, BorderLayout.WEST);
    }

    private void initRightSite() {
        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setLayout(new GridBagLayout());

        JPanel tableJPanel = new JPanel();
        tableJPanel.setLayout(new BorderLayout());

        GridBagConstraints gridConstraints = new GridBagConstraints();
        JScrollPane scrollPaneEnrollLecture = initEnrollLecturesTable();
        JScrollPane scrollPaneRequestedLectures = initRequestedLectures();
        JScrollPane scrollPaneConcludedLectures = initConcludedSubjects();

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Turmas Matriculadas", scrollPaneEnrollLecture);
        tabbedPane.addTab("Turmas Pr\u00E9-Matriculadas", scrollPaneRequestedLectures);
        tabbedPane.addTab("Disciplinas Conclu\u00EDdas", scrollPaneConcludedLectures);

        tableJPanel.add(tabbedPane, BorderLayout.CENTER);

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

    private JScrollPane initConcludedSubjects() {
        concludedTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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
        initConcludedSubjectsData();
        concludedTable.setColumnSelectionAllowed(true);
        concludedTable.setShowGrid(false);
        concludedTable.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                int row = concludedTable.rowAtPoint(e.getPoint());
                int col = concludedTable.columnAtPoint(e.getPoint());
                if (row > -1 && col > -1) {
                    Object value = concludedTable.getValueAt(row, col);
                    if (null != value && !"".equals(value)) {
                        concludedTable.setToolTipText(value.toString());// floating display cell content
                    } else {
                        concludedTable.setToolTipText(null);
                    }
                }
            }
        });
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        concludedTable.setDefaultRenderer(String.class, centerRenderer);
        concludedTable.setFont(concludedTable.getFont().deriveFont(18f));
        concludedTable.setRowHeight(concludedTable.getFont().getSize() * 4);
        concludedTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        concludedTable.getTableHeader().setReorderingAllowed(false);
        concludedTable.setCellSelectionEnabled(false);
        concludedTable.setDragEnabled(false);
        concludedTable.setFillsViewportHeight(true);

        return new JScrollPane(concludedTable);
    }

    private JScrollPane initRequestedLectures() {

        requestedTable = new JTable() {
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
        initRequestedLecturesData();
        requestedTable.setColumnSelectionAllowed(true);
        requestedTable.setShowGrid(false);
        requestedTable.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                int row = requestedTable.rowAtPoint(e.getPoint());
                int col = requestedTable.columnAtPoint(e.getPoint());
                if (row > -1 && col > -1) {
                    Object value = requestedTable.getValueAt(row, col);
                    if (null != value && !"".equals(value)) {
                        requestedTable.setToolTipText(value.toString());// floating display cell content
                    } else {
                        requestedTable.setToolTipText(null);
                    }
                }
            }
        });
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        requestedTable.setDefaultRenderer(String.class, centerRenderer);
        requestedTable.setFont(requestedTable.getFont().deriveFont(18f));
        requestedTable.setRowHeight(requestedTable.getFont().getSize() * 4);
        requestedTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        requestedTable.getTableHeader().setReorderingAllowed(false);
        requestedTable.setCellSelectionEnabled(false);
        requestedTable.setDragEnabled(false);
        requestedTable.setFillsViewportHeight(true);

        return new JScrollPane(requestedTable);
    }

    private JScrollPane initEnrollLecturesTable() {
        enrolledTable = new JTable() {
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
        initEnrolledLecturesData();
        enrolledTable.setColumnSelectionAllowed(true);
        enrolledTable.setShowGrid(false);
        enrolledTable.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                int row = enrolledTable.rowAtPoint(e.getPoint());
                int col = enrolledTable.columnAtPoint(e.getPoint());
                if (row > -1 && col > -1) {
                    Object value = enrolledTable.getValueAt(row, col);
                    if (null != value && !"".equals(value)) {
                        enrolledTable.setToolTipText(value.toString());// floating display cell content
                    } else {
                        enrolledTable.setToolTipText(null);
                    }
                }
            }
        });
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        enrolledTable.setDefaultRenderer(String.class, centerRenderer);
        enrolledTable.setFont(enrolledTable.getFont().deriveFont(18f));
        enrolledTable.setRowHeight(enrolledTable.getFont().getSize() * 4);
        enrolledTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        enrolledTable.getTableHeader().setReorderingAllowed(false);
        enrolledTable.setCellSelectionEnabled(false);
        enrolledTable.setDragEnabled(false);
        enrolledTable.setFillsViewportHeight(true);

        return new JScrollPane(enrolledTable);
    }

    public static void resizeColumnsWidth(JTable table, Dimension dimension) {
        final TableColumnModel columnModel = table.getColumnModel();
        float namePercentage = 0.50f;
        columnModel.getColumn(0).setPreferredWidth((int) (dimension.getWidth() * namePercentage));
        for (int columnIndex = 1; columnIndex < table.getColumnCount(); columnIndex++) {
            columnModel.getColumn(columnIndex).setPreferredWidth((int) (dimension.getWidth() * ((1.0 - namePercentage) / 2)));
        }
    }

    public void refreshData() {
        initRequestedLecturesData();
        initEnrolledLecturesData();
        initConcludedSubjectsData();
    }

    private void initConcludedSubjectsData() {
        String[] columnNames = {"Codigo", "Nome", "Descrição"};

        List<Subject> concludedSubjects = student.getConcludedSubjects();

        Object[][] data = new Object[concludedSubjects.size()][columnNames.length];
        for (int i = 0; i < concludedSubjects.size(); i++) {
            Subject lecture = concludedSubjects.get(i);
            data[i][0] = lecture.getCode();
            data[i][1] = lecture.getName();
            data[i][2] = lecture.getDescription();

            LOGGER.debug(String.format("[Table] Inserting concluded subjects in line %d: %s %s %s", i, data[i][0], data[i][1], data[i][2]));
        }
        concludedTable.setModel(new DefaultTableModel(data, columnNames));
    }

    private void initEnrolledLecturesData() {
        String[] columnNames = {"Nome da Disciplina", "Professor", "Hor\u00E1rio"};

        List<Lecture> enrollLectures = student.getEnrollLectures();

        Object[][] data = new Object[enrollLectures.size()][columnNames.length];
        for (int i = 0; i < enrollLectures.size(); i++) {
            Lecture lecture = enrollLectures.get(i);
            data[i][0] = lecture.getSubject().getName();
            data[i][1] = lecture.getTeacher().getName();
            data[i][2] = lecture.getSchedule();

            LOGGER.debug(String.format("[Table] Inserting enroll lecture in line %d: %s %s %s", i, data[i][0], data[i][1], data[i][2]));
        }
        enrolledTable.setModel(new DefaultTableModel(data, columnNames));
    }

    private void initRequestedLecturesData() {
        String[] columnNames = {"Nome da Disciplina", "Professor", "Hor\u00E1rio"};

        List<Lecture> requestedLectures = student.getRequestedLectures();

        Object[][] data = new Object[requestedLectures.size()][columnNames.length];
        for (int i = 0; i < requestedLectures.size(); i++) {
            Lecture lecture = requestedLectures.get(i);
            data[i][0] = lecture.getSubject().getName();
            data[i][1] = lecture.getTeacher().getName();
            data[i][2] = lecture.getSchedule();

            LOGGER.debug(String.format("[Table] Inserting requested lecture in line %d: %s %s %s", i, data[i][0], data[i][1], data[i][2]));
        }

        requestedTable.setModel(new DefaultTableModel(data, columnNames));
    }

    @Override
    protected void frameInit() {
        super.frameInit();
        this.setSize(this.width, this.height);
        this.setMinimumSize(new Dimension(this.width, this.height));
        this.setTitle(this.frameTitle);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon(requireNonNull(this.getClass().getClassLoader().getResource("bemtevi.png"))).getImage());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LOGGER.info("Closing LoginFrame window");
            }
        });
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                if (concludedTable != null) {
                    resizeColumnsWidth(concludedTable, concludedTable.getSize());
                }
            }
        });
        centreWindow(this);
//        this.setResizable(false);
    }

}
