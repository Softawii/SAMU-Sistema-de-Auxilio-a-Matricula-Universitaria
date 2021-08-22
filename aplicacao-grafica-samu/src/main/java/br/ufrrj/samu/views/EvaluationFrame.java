package br.ufrrj.samu.views;

import br.ufrrj.samu.controllers.SystemController;
import br.ufrrj.samu.entities.Lecture;
import br.ufrrj.samu.entities.Student;
import br.ufrrj.samu.utils.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class EvaluationFrame extends JFrame {

    private static final Logger LOGGER = LogManager.getLogger(EvaluationFrame.class);

    private String frameTitle = "Avaliar turma";
    private int width = 900;
    private int height = 500;
    private JTable ratingTable;
    private Student student;
    private SystemController systemController;

    public EvaluationFrame() throws HeadlessException {
        super();

        this.systemController = SystemController.getInstance();
        // Talvez isso de exception, mas vamo que vamo :) - yan
        this.student = (Student) systemController.getCurrentUser();

        // bla bla bla da tela
        frameInit();
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        JComboBox<String> rateComboBox = new JComboBox<>(EvaluationRate.getStringValues());

        // Pegando turmas -> 1 no Diagrama de Classes - yan
        List<Lecture> enrollLectures = systemController.requestEnrollLectures()
                .stream()
                .filter(lecture -> !lecture.hasAlreadyEvaluated(student)).collect(Collectors.toList());

        if (enrollLectures.size() == 0) {
            this.dispose();
            JOptionPane.showMessageDialog(this,
                    "N\u00E3o h\u00E1 mais turmas para serem avaliadas!",
                    frameTitle,
                    JOptionPane.PLAIN_MESSAGE);
            return;
        }

        // Colunas importantes
        String[] columnNames = new String[]{"Nome da Turma", "Professor", "Avalia\u00E7\u00E3o"};
        Object[][] data = new Object[enrollLectures.size()][columnNames.length];
        for (int i = 0; i < enrollLectures.size(); i++) {
            Lecture lecture = enrollLectures.get(i);
            data[i][0] = lecture.getSubject().getName();
            data[i][1] = lecture.getTeacher().getName();
            data[i][2] = EvaluationRate.NONE.value;
            LOGGER.debug(String.format("[Table] Inserting enroll lecture in line %d: %s %s %s", i, data[i][0], data[i][1], data[i][2]));
        }
        ratingTable = new JTable(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

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

//        rateComboBox.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    System.out.println(e.getItem() + " selected");
//                }
//            }
//        });


        JButton submitButton = new JButton("Confirmar avalia\u00E7\u00F5es");
        submitButton.addActionListener(e -> {
            ratingTable.setEnabled(false);
            submitButton.setEnabled(false);
            for (int i = 0; i < ratingTable.getRowCount(); i++) {
                String stringRate = (String) ratingTable.getModel().getValueAt(i, 2);
                Lecture lectureRow = enrollLectures.get(i);
                if (stringRate.equals(EvaluationRate.NONE.value)) {
                    LOGGER.debug(String.format("Lecture %s rate was ignored due to table value", lectureRow.getCode()));
                    continue;
                }
                int rate = Integer.parseInt(stringRate);

                // 3 no diagrama de sequencia - yan
                systemController.evaluateLecture(lectureRow, rate);
                LOGGER.debug(String.format("Lecture %s was evaluated with rate %d", lectureRow.getCode(), rate));
            }
            this.dispose();
            JOptionPane.showMessageDialog(this,
                    "Avalia\u00E7\u00E3o conclu\u00EDda com sucesso!",
                    frameTitle,
                    JOptionPane.PLAIN_MESSAGE);
        });
        submitButton.setFont(submitButton.getFont().deriveFont(20f));
        main.add(submitButton, BorderLayout.SOUTH);

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
        this.setIconImage(new ImageIcon(requireNonNull(this.getClass().getClassLoader().getResource("bemtevi.png"))).getImage());
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
        ONE("1"),
        NONE("-");
        private final String value;
        EvaluationRate(String value) {
            this.value = value;
        }
        private String getValue() {
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

//    public static void main(String[] args) {
//        new EvaluationFrame();
//    }
}
