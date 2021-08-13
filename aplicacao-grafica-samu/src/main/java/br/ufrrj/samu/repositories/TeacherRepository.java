package br.ufrrj.samu.repositories;

import br.ufrrj.samu.entities.Lecture;
import br.ufrrj.samu.entities.Teacher;
import br.ufrrj.samu.entities.User;
import br.ufrrj.samu.exceptions.AlreadyExistsException;
import br.ufrrj.samu.exceptions.CouldNotUpdateUserException;
import br.ufrrj.samu.exceptions.TeacherNotFoundException;
import br.ufrrj.samu.exceptions.WrongRequestedUserTypeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TeacherRepository {

    private static final Logger LOGGER = LogManager.getLogger(StudentRepository.class);

    private static volatile TeacherRepository instance;

    private Connection connection;

    public TeacherRepository() {
        connection = connection = Repository.connection;
    }

    public static TeacherRepository getInstance() {
        TeacherRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized (TeacherRepository.class) {
            if (TeacherRepository.instance == null) {
                TeacherRepository.instance = new TeacherRepository();
            }
            return instance;
        }
    }

    // Should only be called to insert new teacher
    public Teacher insert(Teacher teacher) throws AlreadyExistsException {
        UsersRepository USERS_REPOSITORY = UsersRepository.getInstance();

        // It's throwing exceptions
        teacher = (Teacher) USERS_REPOSITORY.insert(teacher);

        try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Teachers (lectures, course) VALUES (?1, ?2)")){

            insertStatement.setString(1, teacher.getLecturesIds().orElse(""));
            insertStatement.setString(2, teacher.getCourse());

            insertStatement.executeUpdate();

            long id = insertStatement.getGeneratedKeys().getLong(1);
            teacher.setId(id);
            LOGGER.debug(String.format("Teacher with id %d was inserted to the database", teacher.getId()));
            return teacher;
        } catch (SQLException throwable) {
            // TODO: I think we need change this try / catch to a throw AlreadyExists !
            LOGGER.warn(String.format("Teacher with id '%d' could not be inserted to the database", teacher.getId()), throwable);
            throw new AlreadyExistsException(String.format("Teacher with id '%d' could not be inserted to the database", teacher.getId()), throwable);
        }
    }

    public Teacher update(Teacher teacher) throws CouldNotUpdateUserException {
        UsersRepository USERS_REPOSITORY = UsersRepository.getInstance();

        // Throws 'CouldNotUpdateUserException' if unable to update user
        try {
            USERS_REPOSITORY.update(teacher);
        } catch (CouldNotUpdateUserException e) {
            throw new CouldNotUpdateUserException(String.format("Could not update Student with id '%d' ", e.getUserId()), e, e.getUserId());
        }

        try (PreparedStatement insertStatement = connection.prepareStatement("UPDATE Teachers SET lectures=?2, course=?3 WHERE id=?1")){
            insertStatement.setLong(1, teacher.getId());
            insertStatement.setString(2, teacher.getLecturesIds().orElse(""));
            insertStatement.executeUpdate();

            long id = insertStatement.getGeneratedKeys().getLong(1);
            teacher.setId(id);
            LOGGER.debug(String.format("Teacher with id %d was updated", teacher.getId()));

            insertStatement.close();
            return teacher;
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("Teacher with id '%d' could not be updated", teacher.getId()), throwable);
            throw new CouldNotUpdateUserException(String.format("Could not update Teacher with id '%d' ", teacher.getId()), throwable, teacher.getId());
        }
    }

    public Teacher findById(long teacherId) throws WrongRequestedUserTypeException, SQLException, TeacherNotFoundException {
        UsersRepository USERS_REPOSITORY = UsersRepository.getInstance();
        LectureRepository LECTURE_REPOSITORY = LectureRepository.getInstance();
        Optional<User> userOptional = USERS_REPOSITORY.findById(teacherId);

        if (userOptional.isEmpty()) {
            throw new TeacherNotFoundException(String.format("Teacher with id '%d' could not be found", teacherId));
        } else if (!(userOptional.get() instanceof Teacher)) {
            throw new WrongRequestedUserTypeException(String.format("Requested Teacher with id '%d' is not a Teacher", teacherId));
        }
        try (PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Teachers WHERE id=?1")){
            findStatement.setLong(1, teacherId);

            ResultSet findResultsResultSet = findStatement.executeQuery();
            String lectures = findResultsResultSet.getString(1);
            String course = findResultsResultSet.getString(2);
            List<Lecture> lecturesList = LECTURE_REPOSITORY.getFromStringArray(lectures.split(","));

            User user = userOptional.get();
            Teacher teacher = new Teacher(lecturesList, course);
            teacher.setUsername(user.getUsername());
            teacher.setPassword(user.getPassword());
            teacher.setName(user.getName());
            teacher.setCpf(user.getCpf());
            teacher.setAddress(user.getAddress());
            teacher.setBirthday(user.getBirthday());
            LOGGER.debug(String.format("Student with id %d was found to the database", teacher.getId()));

            return teacher;
        } catch (SQLException throwable) {
            // TODO: I think we need change this try / catch to a throw AlreadyExists !
            LOGGER.warn(String.format("Student with id '%d' could not be found", teacherId), throwable);
            throw new SQLException(String.format("Student with id '%d' could not be found", teacherId));
        }
    }
}
