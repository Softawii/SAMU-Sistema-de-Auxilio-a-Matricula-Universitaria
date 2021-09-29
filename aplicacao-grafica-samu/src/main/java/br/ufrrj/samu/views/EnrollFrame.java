package br.ufrrj.samu.views;

import br.ufrrj.samu.controllers.SystemController;
import br.ufrrj.samu.entities.Lecture;
import br.ufrrj.samu.entities.Student;
import br.ufrrj.samu.utils.Util;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMoonlightContrastIJTheme;
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

public class EnrollFrame extends JFrame {

    private static final Logger LOGGER = LogManager.getLogger(EnrollFrame.class);

    private String frameTitle = "Fazer Matr\u00EDcula";
    private int width = 900;
    private int height = 500;
    private JTable lecturesTable;
    private JButton confirmEnrollment;
    private JButton cancelEnrollment;

    private Student student;
    private SystemController systemController;
    private List<Lecture> availableLectures;

    public EnrollFrame() throws HeadlessException {
        super();
        frameInit();

        this.systemController = SystemController.getInstance();
        this.student = (Student) systemController.getCurrentUser();

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        JPanel topPanel = initTopPanel();
        JScrollPane middlePanel = initMiddlePanel();
        JPanel bottomPanel = initBottomPanel();

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private JPanel initTopPanel() {

        JPanel topPanel = new JPanel();
        JLabel nameLabel = new JLabel(student.getName() + " - " + student.getCpf());
        JLabel courseLabel = new JLabel(student.getCourse().getName() + " - " + student.getSemester());

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
        String[] columnNames = new String[]{"Nome da Turma", "Professor", "Hor\u00E1rio",  "Fazer Matr\u00EDcula"};

        availableLectures = systemController.requestAvailableLectures();


        Object[][] data = new Object[availableLectures.size()][columnNames.length];
        for (int i = 0; i < availableLectures.size(); i++) {
            Lecture lecture = availableLectures.get(i);
            data[i][0] = lecture.getSubject().getName();
            data[i][1] = lecture.getTeacher().getName();
            data[i][2] = lecture.getSchedule();
            data[i][3] = false;

            LOGGER.debug(String.format("[Table] Inserting requested lecture in line %d: %s %s %s", i, data[i][0], data[i][1], data[i][2]));
        }
        int boolColumn = 3;
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
        JButton confirmEnrollment = new JButton("Confirmar Matr\u00EDcula");
        JButton cancelEnrollment = new JButton("Cancelar Matr\u00EDcula");
        confirmEnrollment.setFont(confirmEnrollment.getFont().deriveFont(20f));
        cancelEnrollment.setFont(cancelEnrollment.getFont().deriveFont(20f));

        confirmEnrollment.addActionListener(e -> {
            lecturesTable.setEnabled(false);
//            confirmEnrollment.setEnabled(false);
            for (int i = 0; i < lecturesTable.getRowCount(); i++) {
                if(((Boolean) lecturesTable.getModel().getValueAt(i, 3)) == true) {
                    Lecture lectureRow = availableLectures.get(i);
                    systemController.registerEnrollRequest(lectureRow);
                    //TODO MUDAR MENSAGEM DE LOG
                    LOGGER.debug(String.format("%s pre-requested in %s", student.getName(), lectureRow.getCode()));
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

    // Fala negao fala dinho, como é que tu ta meu parceiro - victor
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
                if (lecturesTable != null) {
                    resizeColumnsWidth(lecturesTable, lecturesTable.getSize());
                }
            }
        });
    }

    public void resizeColumnsWidth(JTable table, Dimension dimension) {
//        final TableColumnModel columnModel = table.getColumnModel();
//        float ratingPercentage = 0.20f;
//        columnModel.getColumn(0).setPreferredWidth((int) (dimension.getWidth() * ((1 - ratingPercentage) / 2)));
//        columnModel.getColumn(1).setPreferredWidth((int) (dimension.getWidth() * ((1 - ratingPercentage) / 2)));
//
//        columnModel.getColumn(2).setMinWidth((int) (40));
    }

    public static void main(String[] args) {
        FlatMoonlightContrastIJTheme.setup();
        new EnrollFrame();
    }
}
