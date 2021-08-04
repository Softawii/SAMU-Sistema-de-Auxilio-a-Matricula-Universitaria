package br.ufrrj.samu.repositories;

import br.ufrrj.samu.entities.Student;
import br.ufrrj.samu.exceptions.AlreadyExistsException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public class StudentRepository {

    private static final Logger LOGGER = LogManager.getLogger(StudentRepository.class);

    private static volatile StudentRepository instance;

    private Connection connection;

    private SubjectRepository subjectRepository;

    /**
     * <b>Precisa</b> que o SubjectService seja definido usando <b>setSubjectService</b>
     */
    private StudentRepository() {
        try {
            connection = connection = Repository.connection;

            connection.setAutoCommit(true);
            LOGGER.debug("AutoCommit enabled");

            LOGGER.warn("Initializing database");
            initDatabase();

        } catch (SQLException throwable) {
            LOGGER.warn(throwable);
        }
    }

    public SubjectRepository getSubjectService() {
        return subjectRepository;
    }

    public void setSubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }


    public static StudentRepository getInstance() {
        StudentRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized (StudentRepository.class) {
            if (StudentRepository.instance == null) {
                StudentRepository.instance = new StudentRepository();
            }
            return instance;
        }
    }


    private void initDatabase() {
        ScriptRunner runner = new ScriptRunner(connection);
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(StudentRepository.class.getClassLoader().getResourceAsStream("database/initStudent.sql"))));
        runner.setEscapeProcessing(false);
        runner.runScript(reader);
    }

    public Optional<Student> insert(Student student) throws AlreadyExistsException {

        UsersRepository uR = UsersRepository.getInstance();

        // It's throwing exceptions
        student = (Student) uR.insert(student);

        try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Student(id, requestedLectures, enrollLectures, course, semester) VALUES(?1, ?2, ?3, ?4, ?5)")){

            insertStatement.setLong(1, student.getId());
            insertStatement.setString(2, "");
            insertStatement.setString(3, "");
            insertStatement.setString(4, student.getCourse());
            insertStatement.setString(5, student.getSemester());

            insertStatement.executeUpdate();

            long id = insertStatement.getGeneratedKeys().getLong(1);
            student.setId(id);
            LOGGER.debug(String.format("Student with id %d was inserted to the database", student.getId()));

            insertStatement.close();
            return Optional.of(student);
        } catch (SQLException throwable) {

            // TODO: I think we need change this try / catch to a throw AlreadyExists !
            LOGGER.warn(String.format("Student with id '%d' could not be inserted to the database", student.getId()), throwable);
            return Optional.empty();
        }
    }
}
