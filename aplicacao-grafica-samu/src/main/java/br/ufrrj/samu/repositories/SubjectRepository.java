package br.ufrrj.samu.repositories;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.*;

import br.ufrrj.samu.entities.Subject;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SubjectRepository {

    private static final Logger LOGGER = LogManager.getLogger(SubjectRepository.class);

    private static SubjectRepository instance;

    private Connection connection;

    private SubjectRepository() {
        // INIT SQL
        connection = Repository.connection;
    }

    public static SubjectRepository getInstance() {
        SubjectRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized (SubjectRepository.class) {
            if (SubjectRepository.instance == null) {
                SubjectRepository.instance = new SubjectRepository();
            }
            return instance;
        }
    }

    public Optional<Subject> insert(Subject subject) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Subjects (code, name, description, prerequisites) VALUES (?1, ?2, ?3, ?4)");
            insertStatement.setString(1, subject.getCode());
            insertStatement.setString(2, subject.getName());
            insertStatement.setString(3, subject.getDescription());
            insertStatement.setString(4, subject.getPrerequisitesList());

//            // POR QUE DEUS?????????? :(
//            connection.setAutoCommit(true);
            insertStatement.executeUpdate();
            LOGGER.debug(String.format("Subject with code %s was inserted to the database", subject.getCode()));

            return Optional.of(subject);
        } catch (SQLException throwable) {

            // TODO: I think we need change this try / catch to a throw AlreadyExists !
            LOGGER.warn(String.format("Subject with code '%s' could not be inserted to the database", subject.getCode()), throwable);
            return Optional.empty();
        }
    }

    public boolean deleteByCode(String code) {
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
        try (PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Subjects WHERE code=?1")) {

            findStatement.setString(1, code);

            ResultSet findResultSet = findStatement.executeQuery();


            String name = findResultSet.getString(2);
            String description = findResultSet.getString(3);
            String prerequisites = findResultSet.getString(4);

            Subject subject = new Subject(name, description, code, Arrays.stream(prerequisites.split(",")).toList());
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
                LOGGER.debug(String.format("We couldn't find the subject %s, so let's throw an exception", subject));
                // TODO: ok ok?
            }
        }
        return subjects;
    }
}
