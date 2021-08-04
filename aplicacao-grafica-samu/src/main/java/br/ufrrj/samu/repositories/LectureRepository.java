package br.ufrrj.samu.repositories;

import br.ufrrj.samu.entities.Lecture;
import br.ufrrj.samu.entities.Subject;
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

        LOGGER.debug(lecture);

        try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Lectures (code, schedule, classRoom, classPlan, subject, teacher, students) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7)")){

            insertStatement.setString(1, lecture.getCode());
            insertStatement.setString(2, lecture.getSchedule());
            insertStatement.setString(3, lecture.getClassRoom());
            insertStatement.setString(4, lecture.getClassPlan());
            insertStatement.setString(5, lecture.getSubject().getCode());
            insertStatement.setLong(6, lecture.getTeacher().getId());
            insertStatement.setString(7, lecture.getStudentsIds());

            insertStatement.executeUpdate();
            LOGGER.debug(String.format("Lecture with code %s was inserted to the database", lecture.getCode()));

            return Optional.of(lecture);
        } catch (SQLException throwable) {

            // TODO: I think we need change this try / catch to a throw AlreadyExists !
            LOGGER.warn(String.format("Subject with code '%s' could not be inserted to the database", lecture.getCode()), throwable);
            return Optional.empty();
        }
    }
}
