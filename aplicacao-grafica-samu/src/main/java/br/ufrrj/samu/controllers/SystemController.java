package br.ufrrj.samu.controllers;

import br.ufrrj.samu.entities.*;
import br.ufrrj.samu.exceptions.PasswordNotMatchesException;
import br.ufrrj.samu.exceptions.UnknownUserException;
import br.ufrrj.samu.exceptions.WrongRequestedUserTypeException;

import java.util.ArrayList;
import java.util.List;

public class SystemController {

    //pre-maricular student methods
    public List<Lecture> requestAvailableLecture(User user) {
        try {
            return currentSemester.getAvailableLectures(user);
        } catch (WrongRequestedUserTypeException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private Semester currentSemester;

    private User currentUser;

    public List<User> userList;

    private SystemController() {
        this.userList = new ArrayList<>();
        this.currentSemester = new Semester();
        initDatabase();
    }

    private static volatile SystemController instance;
    public static SystemController getInstance() {
        SystemController result = instance;
        if (result != null) {
            return result;
        }
        synchronized(SystemController.class) {
            if (instance == null) {
                instance = new SystemController();
            }
            return instance;
        }
    }

    public User signIn(String username, String password) throws UnknownUserException, PasswordNotMatchesException {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    currentUser = user;
                    return user;
                } else {
                    throw new PasswordNotMatchesException();
                }
            }
        }
        throw new UnknownUserException();
    }

    public List<Lecture> requestEnrollLectures() {
        // TODO Gambiarra? Talvez, mas isso só ocorreria se um estudante estivesse logado - edu
        return ((Student) currentUser).getEnrollLectures();
    }

    public List<Lecture> requestAvailableLectures() {
        Student student = (Student) currentUser;
        try {
            return currentSemester.getAvailableLectures(student);
        } catch (WrongRequestedUserTypeException e) {
            return List.of();
        }
    }

    public void registerEnrollRequest(Lecture lecture) {
        registerEnrollRequest(lecture, (Student) currentUser);
        return;
    }

    public void registerEnrollRequest(Lecture lecture, Student student) {
        // TODO Falta a relação entre o período?
        // Relação turma -> estudante
        lecture.addPreEnrolledStudent(student);

        // Relação estudante -> turma
        student.addRequestedLectures(lecture);

    }

    public void evaluateLecture(Lecture lecture, int rate) {
        lecture.evaluate((Student) currentUser, rate);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Semester getCurrentPeriod() {
        return currentSemester;
    }

    public void confirmEnrollment(Student student, Lecture lecture) {
        student.getRequestedLectures().remove(lecture);
        lecture.getPreEnrolledStudent().remove(student);
        student.addEnrollLectures(lecture);
        lecture.getStudents().add(student);
    }


    public void deniedEnrollment(Student student, Lecture lecture) {
        student.getRequestedLectures().remove(lecture);
        lecture.getPreEnrolledStudent().remove(student);
    }

    private void initDatabase() {
        // DTL
        Subject subject = new Subject("Geometria Analitica", "Estudo sobre matrizes, determinantes e sistemas. Vetores. Retas e planos. Curvas. Superficies.", "DTL00", List.of());
        Subject subject1 = new Subject("Calculo I", "Funcoes de uma variavel real. Graficos. Limites e continuidade. A derivada e sua aplicacao.", "DTL01", List.of());
        Subject subject2 = new Subject("Algebra Linear", "Sistemas de equacoes lineares. Espacos vetoriais. Transformacoes lineares.", "DTL02", List.of("DTL00"));
        Subject subject3 = new Subject("Calculo II", "Estudo sobre Integrais.", "DTL03", List.of("DTL01"));
        Subject subject4 = new Subject("Algebra Linear Computacional", "Algoritmos para operacoes basicas entre vetores e matrizes.", "DTL04", List.of("DTL02"));

        // DCC
        Subject subject5 = new Subject("Computadores e Sociedade", "Estudos dos Aspectos Sociais, Economicos, Legais e Profissionais de Computaçao.", "DCC00", List.of());
        Subject subject6 = new Subject("Matematica Discreta para Computacao", "Logica Matematica.", "DCC01", List.of());
        Subject subject7 = new Subject("Programacao Estruturada", "Estudo sobre as estruturas de um programa, as declaracoes e os principais comandos.", "DCC02", List.of());
        Subject subject8 = new Subject("Introducao a Ciencia da Computacao", "Introducao sobre a area de computacao. Estudo sobre conversao de bases, hardware e software basico.", "DCC03", List.of());
        Subject subject9 = new Subject("Circuitos Digitais", "Estudo sobre a algebra de Boole. Circuitos Aritmeticos e Circuitos Sequenciais.", "DCC04", List.of());
        Subject subject10 = new Subject("Logica para Computacao", "Relacoes semanticas entre conectivos da Logica Proposicional. Logica de Predicados.", "DCC05", List.of());
        Subject subject11 = new Subject("Engenharia de Software", "Estudos sobre os processos, gerenciamento, planejamento, metricas e testes de um software.", "DCC06", List.of());
        Subject subject12 = new Subject("Estrutura de Dados I", "Complexidade de Algoritmos. Estudo sobre as Listas lineares e encadeadas. Algoritmos de Ordenacao. Arvores Binarias e muito mais!", "DCC07", List.of("DCC02"));
        Subject subject13 = new Subject("Arquitetura de Computadores I", "Introducao a organizacao de computadores. Instrucoes e linguagens de maquina.", "DCC08", List.of("DCC04"));
        Subject subject14 = new Subject("Linguagens Formais e Automatos", "Muita materia complexa.", "DCC09", List.of());
        Subject subject15 = new Subject("Modelagem e Projeto de Software", "Modelagem de Casos de Uso. Modelagem de Classes. Modelagem de Interacoes, de Estados e de Atividades. Projeto de Software. Pratica de estudo de caso.", "DCC10", List.of("DCC06"));
        Subject subject16 = new Subject("Programacao Orientada a Objetos", "Estudo sobre Classe e Objeto, Encapsulamento, Heranca, Polimorfismo.", "DCC11", List.of("DCC02"));
        Subject subject17 = new Subject("Grafos e Algoritmos", "Estudo sobre os variados tipos de grafos e muitos algorimos para a analise desses grafos.", "DCC12", List.of());

        // Empty :DD,
        List<Subject> optionalCSSubjects = List.of();
        List<Subject> coreCSSubjects = List.of(
                subject, subject1, subject2, subject3, subject4, subject5, subject6, subject7, subject8,
                subject9, subject10, subject11, subject12, subject13, subject14, subject15, subject16, subject17
        );
        Curriculum CSCurriculum = new Curriculum(optionalCSSubjects, coreCSSubjects);

        Course CSCourse = new Course("Ci\u00EAncia da Computa\u00E7\u00E3o", CSCurriculum);

        // Lectures and Teachers
        Teacher teacher = new Teacher("brunoD", "12345", "Bruno Dembogurski", "000.000.000-10", "Casa segura", "01/01/1995",  new ArrayList<>(), CSCourse);
        Lecture lecture = new Lecture("plano de classe", "sala da turma", "hora da aula", "TM01", subject5, null,  new ArrayList<>(), new ArrayList<>());
        lecture.setTeacher(teacher);
        teacher.addLecture(lecture);

        Teacher teacher1 = new Teacher("camila", "12345", "Camila Lacerda", "000.000.000-11", "Casa segura", "01/01/1995", new ArrayList<>(), CSCourse);
        Lecture lecture1 = new Lecture("plano de classe", "sala da turma", "hora da aula", "TM02", subject, null,  new ArrayList<>(), new ArrayList<>());
        lecture1.setTeacher(teacher1);
        teacher1.addLecture(lecture1);

        Coordinator teacher2 = new Coordinator("ligia", "1234", "Ligia Passos", "000.000.000-12", "Casa segura", "01/01/1995",  new ArrayList<>(), CSCourse);
        Lecture lecture2 = new Lecture("plano de classe", "sala da turma", "hora da aula", "TM03", subject6, null,  new ArrayList<>(), new ArrayList<>());
        CSCourse.setCoordinator(teacher2);
        lecture2.setTeacher(teacher2);
        teacher2.addLecture(lecture2);

        Teacher teacher3 = (new Teacher("braida", "12345", "Filipe Braida", "000.000.000-13", "Casa segura", "01/01/1995", new ArrayList<>(), CSCourse));
        Lecture lecture3 = new Lecture("plano de classe", "sala da turma", "hora da aula", "TM04", subject7, null,  new ArrayList<>(), new ArrayList<>());
        lecture3.setTeacher(teacher3);
        teacher3.addLecture(lecture3);

        List<Teacher> teacherList = List.of(teacher, teacher1, teacher2, teacher3);

        currentSemester.addLecture(lecture);
        currentSemester.addLecture(lecture1);
        currentSemester.addLecture(lecture2);
        currentSemester.addLecture(lecture3);

        // Students
        Student student = new Student("yan", "1234", "Yan Charlos", "000.000.000-01", "Minha Casa", "27/05/2001", CSCourse, "2019-1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student1 = new Student("edu", "1234", "Eduardo Ferro", "000.000.000-02", "Minha Casa", "27/05/2001", CSCourse, "2019-1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student2 = new Student("romulo", "1234", "Romulo Menezes", "000.000.000-03", "Minha Casa", "27/05/2001", CSCourse, "2019-1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student3 = new Student("vasilo", "1234", "Mateus Campello", "000.000.000-04", "Minha Casa", "27/05/2001", CSCourse, "2019-1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Student student4 = new Student("slindin", "1ns3rtS3qu3nc1@2021LG", "Vikthour López", "000.000.000-05", "Minha Casa", "27/05/2001", CSCourse, "2019-1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        userList.add(student);
        userList.add(student1);
        userList.add(student2);
        userList.add(student3);
        userList.add(student4);

        userList.add(teacher2);


        this.registerEnrollRequest(lecture, student3);
        this.registerEnrollRequest(lecture1, student3);

        List<Student> studentList = List.of(student, student1, student2, student3, student4);
    }

}
