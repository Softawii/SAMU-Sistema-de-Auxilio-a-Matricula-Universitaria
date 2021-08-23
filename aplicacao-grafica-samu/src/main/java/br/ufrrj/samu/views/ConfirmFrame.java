package br.ufrrj.samu.views;

import br.ufrrj.samu.controllers.SystemController;
import br.ufrrj.samu.entities.Coordinator;
import br.ufrrj.samu.entities.Lecture;
import br.ufrrj.samu.entities.Student;
import br.ufrrj.samu.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ConfirmFrame extends JFrame {

    private static final Logger LOGGER = LogManager.getLogger(EnrollFrame.class);

    private String frameTitle = "Fazer Matr\u00EDcula";
    private int width = 900;
    private int height = 500;

    private Coordinator coordinator;
    private SystemController systemController;
    private JTable lecturesTable;
    private List<Lecture> requestedLectures;
    private int numRows;

    public ConfirmFrame() throws HeadlessException {
        super();
        frameInit();

        this.systemController = SystemController.getInstance();
        this.coordinator = (Coordinator) systemController.getCurrentUser();

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        JPanel topPanel = initTopPanel();
        JScrollPane middlePanel = initMiddlePanel();
        JPanel bottomPanel = initBottomPanel();

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        this.setVisible(false);
    }

    private JPanel initTopPanel() {
        JPanel topPanel = new JPanel();
        JLabel nameLabel = new JLabel(coordinator.getName() + " - " + coordinator.getCpf());
        JLabel courseLabel = new JLabel(coordinator.getCourse().getName() + " - " + Util.getCurrentSemester());

        Dimension dim = getPreferredSize();
        dim.height = 67;
        topPanel.setPreferredSize(dim);
        Border outerBorder = BorderFactory.createLineBorder(Color.MAGENTA);

        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridConstraint = new GridBagConstraints();

        gridConstraint.gridy = 0;
        gridConstraint.gridx = 0;
        gridConstraint.anchor = GridBagConstraints.CENTER;
        topPanel.add(nameLabel, gridConstraint);

        gridConstraint.gridy++;
        gridConstraint.gridx = 0;
        gridConstraint.anchor = GridBagConstraints.CENTER;
//        gridConstraint.insets = new Insets(0, 0, 0, 700);
        topPanel.add(courseLabel, gridConstraint);
        Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 10, 0);
        Border matteBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, UIManager.getColor("Button.default.startBorderColor"));
        topPanel.setBorder(BorderFactory.createCompoundBorder(emptyBorder, matteBorder));
        return topPanel;
    }
    private JScrollPane initMiddlePanel() {
        String[] columnNames = new String[]{"Aluno", "Nome da Turma", "Professor", "Hor\u00E1rio",  "Confirmar"};

        // TODO deixar semelhante ao diagrama, desacoplar
        requestedLectures = systemController.getCurrentPeriod().getLectureList();

        // num_rows = num_students
        numRows = requestedLectures.stream().mapToInt(lecture -> lecture.getPreEnrolledStudent().size()).sum();

        Object[][] data = new Object[numRows][columnNames.length];
        for (int i = 0, k = 0; i < requestedLectures.size(); i++) {
            Lecture lecture = requestedLectures.get(i);
            for (int j = 0; j < lecture.getPreEnrolledStudent().size(); k++, j++) {
                Student student = lecture.getPreEnrolledStudent().get(j);
                data[k][0] = student.getName();
                data[k][1] = lecture.getSubject().getName();
                data[k][2] = lecture.getTeacher().getName();
                data[k][3] = lecture.getSchedule();
                data[k][4] = false;
                LOGGER.debug(String.format("[Table] Inserting requested lecture in line %d: %s %s %s %s %s", i, data[i][0], data[i][1], data[i][2], data[i][3], data[i][4]));
            }
        }
        int boolColumn = 4;
        lecturesTable = new JTable(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == boolColumn;
            }
            @Override
            public Class<?> getColumnClass(int column) {
                if (column != boolColumn) {
                    return String.class;
                }
                return Boolean.class;
            }
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (row % 2 == 0) {
                    c.setBackground(UIManager.getColor("Table.background"));
                } else {
                    c.setBackground(UIManager.getColor("Table.alternateRowColor"));
                }
                return c;
            }
        };
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        lecturesTable.setDefaultRenderer(Object.class, centerRenderer);
        lecturesTable.setColumnSelectionAllowed(true);
        lecturesTable.setShowGrid(false);
        lecturesTable.setFont(lecturesTable.getFont().deriveFont(18f));
        lecturesTable.setRowHeight(lecturesTable.getFont().getSize() * 4);
        lecturesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        lecturesTable.getTableHeader().setReorderingAllowed(false);
        lecturesTable.setCellSelectionEnabled(false);
        lecturesTable.setDragEnabled(false);
        lecturesTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(lecturesTable);
        return scrollPane;
    }

    private JPanel initBottomPanel() {
        JPanel bottomPanel = new JPanel();
        JButton confirmEnrollment = new JButton("Confirmar Matr\u00EDculas");
        JButton cancelEnrollment = new JButton("Cancelar Matr\u00EDculas");
        confirmEnrollment.setFont(confirmEnrollment.getFont().deriveFont(20f));
        cancelEnrollment.setFont(cancelEnrollment.getFont().deriveFont(20f));

        confirmEnrollment.addActionListener(e -> {
            lecturesTable.setEnabled(false);
            for (int i = 0, k = 0; i < requestedLectures.size(); i++) {
                Lecture lecture = requestedLectures.get(i);
                for (int j = 0; j < lecture.getPreEnrolledStudent().size(); k++, j++) {
                    // bugando quando remove das listas
                    if (((Boolean) lecturesTable.getModel().getValueAt(i, 4)) == true) {
                        Student student = lecture.getPreEnrolledStudent().get(j);
                        student.getRequestedLectures().remove(lecture);
                        lecture.getPreEnrolledStudent().remove(student);
                        student.addEnrollLectures(lecture);
                        lecture.getStudents().add(student);
//                        LOGGER.debug(String.format("[Table] Inserting requested lecture in line %d: %s %s %s %s %s", i, data[i][0], data[i][1], data[i][2], data[i][3], data[i][4]));
                    }
                }
            }
            this.dispose();
            JOptionPane.showMessageDialog(this,
                    "Matr\u00EDcula realizada com sucesso!",
                    frameTitle,
                    JOptionPane.PLAIN_MESSAGE);
        });
        cancelEnrollment.addActionListener(null);
        bottomPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridConstraint = new GridBagConstraints();

        gridConstraint.insets = new Insets(20, 0, 20, 0);
        gridConstraint.gridx = 0;
        bottomPanel.add(confirmEnrollment, gridConstraint);
        gridConstraint.gridx = 1;
        bottomPanel.add(cancelEnrollment, gridConstraint);

        return bottomPanel;
    }

    @Override
    protected void frameInit() {
        super.frameInit();
        this.setSize(this.width, this.height);
        this.setTitle(this.frameTitle);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setIconImage(new ImageIcon(requireNonNull(this.getClass().getClassLoader().getResource("bemtevi.png"))).getImage());
        Util.centreWindow(this);
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
//                if (lecturesTable != null) {
//                    resizeColumnsWidth(lecturesTable, lecturesTable.getSize());
//                }
            }
        });
    }

}
