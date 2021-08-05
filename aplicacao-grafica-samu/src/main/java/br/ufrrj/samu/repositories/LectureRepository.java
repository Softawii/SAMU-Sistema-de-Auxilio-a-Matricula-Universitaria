package br.ufrrj.samu.repositories;

import br.ufrrj.samu.entities.Lecture;
import br.ufrrj.samu.entities.Student;
import br.ufrrj.samu.entities.Subject;
import br.ufrrj.samu.entities.Teacher;
import br.ufrrj.samu.exceptions.SubjectNotFoundException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class LectureRepository {

    private static final Logger LOGGER = LogManager.getLogger(LectureRepository.class);

    private static LectureRepository instance;

    private Connection connection;

    private LectureRepository() {
        // INIT SQL
        try {
            connection = Repository.connection;

            connection.setAutoCommit(true);
            LOGGER.debug("AutoCommit enabled");

            LOGGER.warn("Initializing database");
            initDatabase();

        } catch (SQLException throwable) {
            LOGGER.warn(throwable);
        }
    }

    public static LectureRepository getInstance() {
        LectureRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized (LectureRepository.class) {
            if (LectureRepository.instance == null) {
                LectureRepository.instance = new LectureRepository();
            }
            return instance;
        }
    }

    private void initDatabase() {
        ScriptRunner runner = new ScriptRunner(connection);
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(StudentRepository.class.getClassLoader().getResourceAsStream("database/initLectures.sql"))));
        runner.setEscapeProcessing(false);
        runner.runScript(reader);
    }

    public Optional<Lecture> insert(Lecture lecture) {

        try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Lectures (code, schedule, classRoom, classPlan, subject, teacher, students) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7)")){

            insertStatement.setString(1, lecture.getCode());
            insertStatement.setString(2, lecture.getSchedule());
            insertStatement.setString(3, lecture.getClassRoom());
            insertStatement.setString(4, lecture.getClassPlan());
            insertStatement.setString(5, lecture.getSubject().getCode());
            insertStatement.setLong(6, lecture.getTeacher().getId());
            insertStatement.setString(7, lecture.getStudentsIds());

            // Essa linha de baixo é um bug lixo do krl
            connection.setAutoCommit(true);
            insertStatement.executeUpdate();

            LOGGER.debug(String.format("Lecture with code %s was inserted to the database", lecture.getCode()));

            return Optional.of(lecture);
        } catch (SQLException throwable) {

            // TODO: I think we need change this try / catch to a throw AlreadyExists !
            LOGGER.warn(String.format("Subject with code '%s' could not be inserted to the database", lecture.getCode()), throwable);
            return Optional.empty();
        }
    }

    public boolean deleteByCode(String code) {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Lectures WHERE code=?1");
            findStatement.setString(1, code);
            ResultSet findResultSet = findStatement.executeQuery();
            findResultSet.getString(1);

            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM Lectures WHERE code=?1");
            deleteStatement.setString(1, code);

            deleteStatement.executeUpdate();
            LOGGER.debug(String.format("Lecture with code %s was deleted from the database", code));
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("Lecture with code '%s' could not be found and deleted from the database", code), throwable);
            return false;
        }
        return true;
    }

    public Optional<Lecture> findByCode(String code) throws SubjectNotFoundException {
        try (PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Lectures WHERE code=?1")) {

            findStatement.setString(1, code);
            ResultSet findResultSet = findStatement.executeQuery();

            // Strings importantes
            String schedule = findResultSet.getString(2);
            String classRoom = findResultSet.getString(3);
            String classPlan = findResultSet.getString(4);

            // Subject relacionada
            SubjectRepository sr = SubjectRepository.getInstance();

            String subjectCode = findResultSet.getString(5);

            Optional<Subject> optSub = sr.findSubjectByCode(subjectCode);

            if(optSub.isEmpty())
                throw new SubjectNotFoundException("Subject '" + subjectCode + "' not found.");

            Subject subject = optSub.get();

            // TODO: Teacher
            Teacher teacher = new Teacher(2, "Braida", "1234");

            // Students
            // TODO: find student
            ArrayList<Student> students = new ArrayList<>();

            Lecture lecture = new Lecture(classPlan, classRoom, schedule, code, subject, teacher, students);

            LOGGER.debug(String.format("Lecture with code '%s' and name '%s' was found with success", lecture.getCode(), lecture.getSubject().getName()));


            return Optional.of(lecture);

        } catch (SQLException throwable) {
            LOGGER.warn(String.format("Lecture with code '%s' could not be found", code), throwable);
            return Optional.empty();
        }
    }
}
