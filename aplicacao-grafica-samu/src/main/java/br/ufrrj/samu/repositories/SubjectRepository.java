package br.ufrrj.samu.repositories;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.*;

import br.ufrrj.samu.entities.Student;
import br.ufrrj.samu.entities.Subject;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SubjectRepository {

    private static final Logger LOGGER = LogManager.getLogger(SubjectRepository.class);

    private static SubjectRepository instance;

    private Connection connection;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            LOGGER.debug("org.sqlite.JDBC class loaded");
        } catch (ClassNotFoundException e) {
            LOGGER.warn("org.sqlite.JDBC class could not be loaded", e);
        }
    }

    private SubjectRepository() {
        // INIT SQL
        try {
            LOGGER.debug("Starting connection to database");
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:" +
                            new File(SubjectRepository.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toPath().getParent() +
                            "\\database.db");

            //LOGGER.warn("Initializing database");
            initDatabase();

        } catch (SQLException | URISyntaxException throwable) {
            LOGGER.warn(throwable);
        }
    }

    public static SubjectRepository getInstance() {
        if (SubjectRepository.instance == null) {
            SubjectRepository.instance = new SubjectRepository();
        }
        return instance;
    }

    private void initDatabase() {
        ScriptRunner runner = new ScriptRunner(connection);
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(StudentRepository.class.getClassLoader().getResourceAsStream("database/initSubjects.sql"))));
        runner.setEscapeProcessing(false);
        runner.runScript(reader);
    }

    public Optional<Subject>  insertSubject(Subject subject) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Subjects (code, name, description, prerequisites) VALUES (?1, ?2, ?3, ?4)");
            insertStatement.setString(1, subject.getCode());
            insertStatement.setString(2, subject.getName());
            insertStatement.setString(3, subject.getDescription());
            insertStatement.setString(4, subject.getPrerequisitesList());

            insertStatement.executeUpdate();
            LOGGER.debug(String.format("Subject with code %s was inserted to the database", subject.getCode()));

            return Optional.of(subject);
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("Subject with code '%s' could not be inserted to the database", subject.getCode()), throwable);
            return Optional.empty();
        }
    }

    public boolean deleteSubjectByCode(String code) {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Subjects WHERE code=?1");
            findStatement.setString(1, code);
            ResultSet findResultSet = findStatement.executeQuery();
            findResultSet.getString(1);

            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM Subjects WHERE code=?1");
            deleteStatement.setString(1, code);

            deleteStatement.executeUpdate();
            LOGGER.debug(String.format("Subject with code %s was deleted from the database", code));
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("Subject with code '%s' could not be found and deleted from the database", code), throwable);
            return false;
        }
        return true;
    }

    public Optional<Subject> findSubjectByCode(String code) {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Subjects WHERE code=?1");
            findStatement.setString(1, code);

            ResultSet findResultSet = findStatement.executeQuery();


            String name = findResultSet.getString(2);
            String description = findResultSet.getString(3);
            String prerequisites = findResultSet.getString(4);
            String schedule = findResultSet.getString(5);

            // TODO: PreRequisites String to array
            // PREREQUISITES AS A STRING IS A TEMPORARY SOLUTION!!!
            Subject subject = new Subject(name, description, code, Arrays.stream(prerequisites.split(",")).toList(), schedule);
            LOGGER.debug(String.format("Subject with code '%s' and name '%s' was found with success", subject.getCode(), subject.getName()));
            return Optional.of(subject);

        } catch (SQLException throwable) {
            LOGGER.warn(String.format("Subject with code '%s' could not be found", code), throwable);
            return Optional.empty();
        }
    }

    public List<Subject> getSubjectFromStringArray(String[] subjectsArray) {

        ArrayList<Subject> subjects = new ArrayList<>();

        for(String subject : subjectsArray) {

            Optional<Subject> optSub = this.findSubjectByCode(subject);

            if(optSub.isPresent()) {
                subjects.add(optSub.get());
            } else {
                LOGGER.debug("We couldn't find the subject %s, so let's throw an exception");
                // TODO: ok ok?
            }
        }

        return subjects;
    }
}
