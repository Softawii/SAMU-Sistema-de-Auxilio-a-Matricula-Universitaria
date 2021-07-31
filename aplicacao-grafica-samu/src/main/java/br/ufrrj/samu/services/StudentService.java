package br.ufrrj.samu.services;

import br.ufrrj.samu.entities.Student;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Objects;
import java.util.Optional;

public class StudentService {

    private static final Logger LOGGER = LogManager.getLogger(StudentService.class);

    private Connection connection;

    private BCryptPasswordEncoder encoder;

    private SubjectService subjectService;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            LOGGER.debug("org.sqlite.JDBC class loaded");
        } catch (ClassNotFoundException e) {
            LOGGER.warn("org.sqlite.JDBC class could not be loaded", e);
        }
    }

    /**
     * <b>Precisa</b> que o SubjectService seja definido usando <b>setSubjectService</b>
     */
    public StudentService() {
        try {
            LOGGER.debug("Instantiating BCryptPasswordEncoder");
            encoder = new BCryptPasswordEncoder(4);
            LOGGER.debug("Starting connection to database");
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:" +
                            new File(StudentService.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toPath().getParent() +
                            "\\database.db");
            LOGGER.warn("Initializing database");
            initDatabase();

        } catch (SQLException | URISyntaxException throwable) {
            LOGGER.warn(throwable);
        }
    }

    public SubjectService getSubjectService() {
        return subjectService;
    }

    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public BCryptPasswordEncoder getEncoder() {
        return encoder;
    }

    private void initDatabase() {
        ScriptRunner runner = new ScriptRunner(connection);
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(StudentService.class.getClassLoader().getResourceAsStream("database/init.sql"))));
        runner.setEscapeProcessing(false);
        runner.runScript(reader);
    }

    public Optional<Student> insertStudent(Student student) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Student (username, password, name, address, courses) VALUES (?1, ?2, ?3, ?4, ?5)");
            insertStatement.setString(1, student.getUsername());
            insertStatement.setString(2, encoder.encode(student.getPassword()));
            insertStatement.setString(3, student.getName());
            insertStatement.setString(4, student.getAddress());
            insertStatement.setString(5, student.getSubjectsCodes());

            insertStatement.executeUpdate();
            LOGGER.debug(String.format("Student with id %d was inserted to the database", student.getId()));
            long id = insertStatement.getGeneratedKeys().getLong(1);
            student.setId(id);
            return Optional.of(student);
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("Student with id '%d' could not be inserted to the database", student.getId()), throwable);
            return Optional.empty();
        }
    }

    public boolean deleteStudentById(long studentId) {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Student WHERE id=?1");
            findStatement.setLong(1, studentId);
            ResultSet findResultSet = findStatement.executeQuery();
            findResultSet.getLong(1);

            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM Student WHERE id=?1");
            deleteStatement.setLong(1, studentId);

            deleteStatement.executeUpdate();
            LOGGER.debug(String.format("Student with id %d was deleted from the database", studentId));
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("Student with id '%d' could not be found and deleted from the database", studentId), throwable);
            return false;
        }
        return true;
    }

    public boolean deleteStudentByUsername(String username) {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Student WHERE name=?1");
            findStatement.setString(1, username);
            ResultSet findResultSet = findStatement.executeQuery();
            findResultSet.getLong(1);

            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM Student WHERE name=?1");
            deleteStatement.setString(1, username);

            deleteStatement.executeUpdate();
            LOGGER.debug(String.format("Student with username '%s' was deleted from the database", username));
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("Student with username '%s' could not be found and deleted from the database", username), throwable);
            return false;
        }
        return true;
    }

    public boolean updateStudent(Student student) {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Student WHERE id=?1");
            findStatement.setLong(1, student.getId());
            ResultSet findResultSet = findStatement.executeQuery();
            findResultSet.getLong(1);

            PreparedStatement updateStatement = connection.prepareStatement("UPDATE Student SET username = ?1, password = ?2, name = ?3, address = ?4, courses = ?5 WHERE id = ?6;");
            updateStatement.setString(1, student.getUsername());
            updateStatement.setString(2, student.getPassword());
            updateStatement.setString(3, student.getName());
            updateStatement.setString(4, student.getAddress());
            updateStatement.setString(5, student.getSubjectsCodes());
            updateStatement.setLong(6, student.getId());

            updateStatement.executeUpdate();
            LOGGER.debug(String.format("Student with id '%d' and username '%s' was updated", student.getId(), student.getUsername()));
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("Student with id '%d' and username '%s' could not be updated", student.getId(), student.getUsername()), throwable);
            return false;
        }
        return true;
    }

    public Optional<Student> findStudentById(long userId) {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Student WHERE id=?1");
            findStatement.setLong(1, userId);

            ResultSet findResultSet = findStatement.executeQuery();
            long id = findResultSet.getLong(1);
            String username = findResultSet.getString(2);
            String password = findResultSet.getString(3);
            String name = findResultSet.getString(4);
            String address = findResultSet.getString(5);
            String subjectsString = findResultSet.getString(6);
            String course = findResultSet.getString(7);
            String semester = findResultSet.getString(8);


            Student user = new Student(id, username, password, name, address, subjectService.getSubjectFromStringArray(subjectsString.split(",")), course, semester);
            LOGGER.debug(String.format("Student with id '%d' and username '%s' was found with success", user.getId(), user.getUsername()));
            return Optional.of(user);
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("Student with id '%d' could not be found", userId), throwable);
            return Optional.empty();
        }
    }

    public Optional<Student> findStudentByUsername(String findName) {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Student WHERE username=?1");
            findStatement.setString(1, findName);

            ResultSet findResultSet = findStatement.executeQuery();
            long id = findResultSet.getLong(1);
            String username = findResultSet.getString(2);
            String password = findResultSet.getString(3);
            String name = findResultSet.getString(4);
            String address = findResultSet.getString(5);
            String subjectsString = findResultSet.getString(6);
            String course = findResultSet.getString(7);
            String semester = findResultSet.getString(8);


            Student user = new Student(id, username, password, name, address, subjectService.getSubjectFromStringArray(subjectsString.split(",")), course, semester);
            LOGGER.debug(String.format("Student with id '%d' and username '%s' was found with success", user.getId(), user.getUsername()));
            return Optional.of(user);
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("Student with username '%s' could not be found", findName), throwable);
            return Optional.empty();
        }
    }

}
