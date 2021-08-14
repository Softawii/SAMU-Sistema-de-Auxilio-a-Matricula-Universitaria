package br.ufrrj.samu.utils;

import br.ufrrj.samu.SAMU;
import br.ufrrj.samu.entities.Lecture;
import br.ufrrj.samu.entities.Student;
import br.ufrrj.samu.entities.Subject;
import br.ufrrj.samu.entities.Teacher;
import br.ufrrj.samu.exceptions.AlreadyExistsException;
import br.ufrrj.samu.repositories.*;
import com.formdev.flatlaf.*;
import com.formdev.flatlaf.intellijthemes.FlatAllIJThemes;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class Util {

    public static final JButton THEME_BUTTON;

    private static final ImageIcon LIGHT_MODE_ICON = new ImageIcon(requireNonNull(Util.class.getClassLoader().getResource("images/lightModeIcon.png")));

    private static final ImageIcon DARK_MODE_ICON = new ImageIcon(requireNonNull(Util.class.getClassLoader().getResource("images/darkModeIcon.png")));

    public static boolean isDarkMode = false;

    private static final Logger LOGGER = LogManager.getLogger(Util.class);

    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            Path currentPath = new File(Util.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toPath().getParent();
            Path propertiesPath = Paths.get(currentPath.toString(), File.separator, ".properties");

            //If .properties file exists
            if (propertiesPath.toFile().isFile()) {
                PROPERTIES.load(Files.newInputStream(propertiesPath));
                boolean isFirstRun = Boolean.parseBoolean(((String) PROPERTIES.get("first-run")));
                if (isFirstRun) {
                    initDatabase();
                    PROPERTIES.put("first-run", Boolean.toString(false));
                    PROPERTIES.store(Files.newOutputStream(propertiesPath), null);
                }
            } else {
                boolean success = propertiesPath.toFile().createNewFile();
                if (success) {
                    PROPERTIES.load(Files.newInputStream(propertiesPath));
                    PROPERTIES.put("first-run", Boolean.toString(false));
                    initDatabase();
                    PROPERTIES.store(Files.newOutputStream(propertiesPath), null);
                } else {
                    //failed to create file
                }
            }

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    static
    {
        THEME_BUTTON = new JButton();
        THEME_BUTTON.setIcon(LIGHT_MODE_ICON);
        THEME_BUTTON.setFocusable(false);
        THEME_BUTTON.setRolloverEnabled(false);
        THEME_BUTTON.setFont(THEME_BUTTON.getFont().deriveFont(20f));
        THEME_BUTTON.addActionListener(e -> {
            Util.switchMode();
            if (Util.isDarkMode) {
                THEME_BUTTON.setIcon(DARK_MODE_ICON);
            } else {
                THEME_BUTTON.setIcon(LIGHT_MODE_ICON);
            }
        });
    }

    public static void switchMode() {
        isDarkMode = !isDarkMode;
        try {
            if (isDarkMode) {
                LOGGER.info("Changing to dark theme(Flat Moonlight Contrast IJTheme)");
                UIManager.setLookAndFeel(SAMU.customMoonlightContrastTheme);
            } else {
                LOGGER.info("Changing to light theme(Flat Gray IJTheme)");
                UIManager.setLookAndFeel(SAMU.customGrayTheme);
            }
        } catch (UnsupportedLookAndFeelException e) {
            LOGGER.warn(e);
            e.printStackTrace();
        }
        FlatLaf.updateUILater();
    }


    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public static void installCustomThemes() {
        FlatLightLaf.installLafInfo();
        FlatDarculaLaf.installLafInfo();
        FlatDarkLaf.installLafInfo();
        FlatIntelliJLaf.installLafInfo();

        for (FlatAllIJThemes.FlatIJLookAndFeelInfo info : FlatAllIJThemes.INFOS) {
            UIManager.installLookAndFeel(info);
        }
    }

    public static String[] getAllFontFamilies() {
        return Arrays.stream(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()).filter(Font::isPlain).map(Font::getFamily).distinct().toArray(String[]::new);
    }

    public static void changeFont(Component component, Font font) {
        component.setFont(font.deriveFont(component.getFont().getStyle(), component.getFont().getSize2D()));
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                changeFont(child, font);
            }
        }
    }

    public static Map<String, UIManager.LookAndFeelInfo> getInstalledLookAndFeels() {
        return Arrays.stream(UIManager.getInstalledLookAndFeels()).collect(Collectors.toMap(UIManager.LookAndFeelInfo::getName, lookAndFeelInfo -> lookAndFeelInfo));
    }

    public static void setUIFont(FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource fontUIResource = (FontUIResource) value;
                UIManager.put(key, f.deriveFont(((float) fontUIResource.getSize())).deriveFont(Font.PLAIN));
            }
        }
    }

    public static Font getSansationFont() {
        InputStream fontStream = null;
        Font sansationFont = null;
        try  {
            fontStream = requireNonNull(Util.class.getClassLoader().getResourceAsStream("fonts/Sansation_Regular.ttf"));
            sansationFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            LOGGER.warn(e);
        }
        return sansationFont;
    }

    private static void initDatabase() {

        BufferedReader databaseReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(StudentRepository.class.getClassLoader().getResourceAsStream("database/initDatabase.sql"))));
        try (databaseReader;
             Connection connection = DriverManager.getConnection(
                "jdbc:sqlite:" +
                        new File(Repository.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toPath().getParent() +
                        "\\database.db")) {
            connection.setAutoCommit(true);
            ScriptRunner runner = new ScriptRunner(connection);
            runner.setEscapeProcessing(false);

            runner.runScript(databaseReader);
        } catch (SQLException | URISyntaxException | IOException throwables) {
            throwables.printStackTrace();
        }

        // Init interno
        StudentRepository studentRepository = StudentRepository.getInstance();
        UsersRepository usersRepository = UsersRepository.getInstance();
        SubjectRepository subjectRepository = SubjectRepository.getInstance();
        LectureRepository lectureRepository = LectureRepository.getInstance();
        TeacherRepository teacherRepository = TeacherRepository.getInstance();

        try {
            // Subjects
            // DCC
            Subject subject5 = new Subject("Computadores e Sociedade", "Estudos dos Aspectos Sociais, Economicos, Legais e Profissionais de Computaçao.", "DCC00", List.of(""));
            Subject subject6 = new Subject("Matematica Discreta para Computacao", "Logica Matematica.", "DCC01", List.of(""));
            Subject subject7 = new Subject("Programacao Estruturada", "Estudo sobre as estruturas de um programa, as declaracoes e os principais comandos.", "DCC02", List.of(""));
            subjectRepository.insert(subject5);
            subjectRepository.insert(subject6);
            subjectRepository.insert(subject7);
            subjectRepository.insert(new Subject("Introducao a Ciencia da Computacao", "Introducao sobre a area de computacao. Estudo sobre conversao de bases, hardware e software basico.", "DCC03", List.of("")));
            subjectRepository.insert(new Subject("Circuitos Digitais", "Estudo sobre a algebra de Boole. Circuitos Aritmeticos e Circuitos Sequenciais.", "DCC04", List.of("")));
            subjectRepository.insert(new Subject("Logica para Computacao", "Relacoes semanticas entre conectivos da Logica Proposicional. Logica de Predicados.", "DCC05", List.of("")));
            subjectRepository.insert(new Subject("Engenharia de Software", "Estudos sobre os processos, gerenciamento, planejamento, metricas e testes de um software.", "DCC06", List.of("")));
            subjectRepository.insert(new Subject("Estrutura de Dados I", "Complexidade de Algoritmos. Estudo sobre as Listas lineares e encadeadas. Algoritmos de Ordenacao. Arvores Binarias e muito mais!", "DCC07", List.of("DCC02")));
            subjectRepository.insert(new Subject("Arquitetura de Computadores I", "Introducao a organizacao de computadores. Instrucoes e linguagens de maquina.", "DCC08", List.of("DCC04")));
            subjectRepository.insert(new Subject("Linguagens Formais e Automatos", "Muita materia complexa.", "DCC09", List.of("")));
            subjectRepository.insert(new Subject("Modelagem e Projeto de Software", "Modelagem de Casos de Uso. Modelagem de Classes. Modelagem de Interacoes, de Estados e de Atividades. Projeto de Software. Pratica de estudo de caso.", "DCC10", List.of("DCC06")));
            subjectRepository.insert(new Subject("Programacao Orientada a Objetos", "Estudo sobre Classe e Objeto, Encapsulamento, Heranca, Polimorfismo.", "DCC11", List.of("DCC02")));
            subjectRepository.insert(new Subject("Grafos e Algoritmos", "Estudo sobre os variados tipos de grafos e muitos algorimos para a analise desses grafos.", "DCC12", List.of("")));

            // DTL
            Subject subject = new Subject("Geometria Analitica", "Estudo sobre matrizes, determinantes e sistemas. Vetores. Retas e planos. Curvas. Superficies.", "DTL00", List.of(""));
            subjectRepository.insert(subject);
            Subject subject1 = new Subject("Calculo I", "\tFuncoes de uma variavel real. Graficos. Limites e continuidade. A derivada e sua aplicacao.", "DTL01", List.of(""));
            subjectRepository.insert(subject1);
            Subject subject2 = new Subject("Algebra Linear", "Sistemas de equacoes lineares. Espacos vetoriais. Transformacoes lineares.", "DTL02", List.of("DTL00"));
            subjectRepository.insert(subject2);
            Subject subject3 = new Subject("Calculo II", "Estudo sobre Integrais.", "DTL03", List.of("DTL01"));
            subjectRepository.insert(subject3);
            Subject subject4 = new Subject("Algebra Linear Computacional", "Algoritmos para operacoes basicas entre vetores e matrizes.", "DTL04", List.of("DTL02"));
            subjectRepository.insert(subject4);

            // Lectures and Teachers
            Teacher teacher = teacherRepository.insert(new Teacher("brunoD", "12345", "Bruno Dembogurski", "000.000.000-10", "Casa segura", "01/01/1995",  new ArrayList<>(), "curso"));
            Lecture lecture = new Lecture("plano de classe", "sala da turma", "hora da aula", "TM01", subject5, null,  new ArrayList<>());
            lecture.setTeacher(teacher.getId());
            teacher.addLecture(lecture);
            lectureRepository.insert(lecture);

            Teacher teacher1 = teacherRepository.insert(new Teacher("camila", "12345", "Camila Lacerda", "000.000.000-11", "Casa segura", "01/01/1995", new ArrayList<>(), "curso"));
            Lecture lecture1 = new Lecture("plano de classe", "sala da turma", "hora da aula", "TM02", subject, null,  new ArrayList<>());
            lecture1.setTeacher(teacher1.getId());
            teacher1.addLecture(lecture1);
            lectureRepository.insert(lecture1);

            Teacher teacher2 = teacherRepository.insert(new Teacher("ligia", "12345", "Ligia Passos", "000.000.000-12", "Casa segura", "01/01/1995",  new ArrayList<>(), "curso"));
            Lecture lecture2 = new Lecture("plano de classe", "sala da turma", "hora da aula", "TM03", subject6, null,  new ArrayList<>());
            lecture2.setTeacher(teacher2.getId());
            teacher2.addLecture(lecture2);
            lectureRepository.insert(lecture2);

            Teacher teacher3 = teacherRepository.insert(new Teacher("braida", "12345", "Filipe Braida", "000.000.000-13", "Casa segura", "01/01/1995", new ArrayList<>(), "curso"));
            Lecture lecture3 = new Lecture("plano de classe", "sala da turma", "hora da aula", "TM04", subject7, null,  new ArrayList<>());
            lecture3.setTeacher(teacher3.getId());
            teacher3.addLecture(lecture3);
            lectureRepository.insert(lecture3);

            // Students
            studentRepository.insert(new Student("yan", "1234", "Yan Charlos", "000.000.000-01", "Minha Casa", "27/05/2001", "That ass", "2019-1",  new ArrayList<>(),  new ArrayList<>()));
            studentRepository.insert(new Student("edu", "1234", "Eduardo Ferro", "000.000.000-02", "Minha Casa", "27/05/2001", "Ciencia da Computacao", "2019-1",  new ArrayList<>(),  new ArrayList<>()));
            studentRepository.insert(new Student("romulo", "1234", "Romulo Menezes", "000.000.000-03", "Minha Casa", "27/05/2001", "Administração", "2019-1",  new ArrayList<>(),  new ArrayList<>()));
            studentRepository.insert(new Student("vasilo", "1234", "Mateus Campello", "000.000.000-04", "Minha Casa", "27/05/2001", "Direito", "2019-1", List.of(lecture, lecture1, lecture2, lecture3),  new ArrayList<>()));
            studentRepository.insert(new Student("slindin", "1ns3rtS3qu3nc1@2021LG", "Vikthour López", "000.000.000-05", "Minha Casa", "27/05/2001", "Ciencia da Computacao", "2019-1",  new ArrayList<>(),  new ArrayList<>()));
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        }
    }

}
