package br.ufrrj.samu.repositories;

import br.ufrrj.samu.entities.Lecture;
import br.ufrrj.samu.entities.Student;
import br.ufrrj.samu.entities.User;
import br.ufrrj.samu.exceptions.AlreadyExistsException;
import br.ufrrj.samu.exceptions.CouldNotUpdateUserException;
import br.ufrrj.samu.exceptions.WrongRequestedUserTypeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class StudentRepository {

    private static final Logger LOGGER = LogManager.getLogger(StudentRepository.class);

    private static volatile StudentRepository instance;

    private Connection connection;

    private SubjectRepository subjectRepository;

    /**
     * <b>Precisa</b> que o SubjectService seja definido usando <b>setSubjectService</b>
     */
    private StudentRepository() {
        connection = connection = Repository.connection;
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

    // Should only be called to insert new students
    public Optional<Student> insert(Student student) throws AlreadyExistsException {
        UsersRepository USERS_REPOSITORY = UsersRepository.getInstance();
        // It's throwing exceptions
        student = (Student) USERS_REPOSITORY.insert(student);

        try (PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Student(id, requestedLectures, enrollLectures, course, semester) VALUES(?1, ?2, ?3, ?4, ?5)")){

            insertStatement.setLong(1, student.getId());
            // TODO: PLEASE CHECK IT
            insertStatement.setString(2, Lecture.parseListOfLecture(student.getEnrollLectures()));
            insertStatement.setString(3, Lecture.parseListOfLecture(student.getRequestedLectures()));
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

    public Student update(Student student) throws CouldNotUpdateUserException {
        UsersRepository USERS_REPOSITORY = UsersRepository.getInstance();
        // Throws 'CouldNotUpdateUserException' if unable to update user
        try {
            USERS_REPOSITORY.update(student);
        } catch (CouldNotUpdateUserException e) {
            throw new CouldNotUpdateUserException(String.format("Could not update Student with id '%d' ", e.getUserId()), e, e.getUserId());
        }

        try (PreparedStatement insertStatement = connection.prepareStatement("UPDATE Student SET requestedLectures=?2, enrollLectures=?3, course=?4, semester=?5 WHERE id=?1")){
            insertStatement.setLong(1, student.getId());
            insertStatement.setString(2, Lecture.parseListOfLecture(student.getEnrollLectures()));
            insertStatement.setString(3, Lecture.parseListOfLecture(student.getRequestedLectures()));
            insertStatement.setString(4, student.getCourse());
            insertStatement.setString(5, student.getSemester());

            insertStatement.executeUpdate();

            long id = insertStatement.getGeneratedKeys().getLong(1);
            student.setId(id);
            LOGGER.debug(String.format("Student with id %d was updated", student.getId()));

            insertStatement.close();
            return student;
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("Student with id '%d' could not be updated", student.getId()), throwable);
            throw new CouldNotUpdateUserException(String.format("Could not update Student with id '%d' ", student.getId()), throwable, student.getId());
        }
    }

    public Optional<Student> findById(long studentId) throws WrongRequestedUserTypeException, SQLException {
        UsersRepository USERS_REPOSITORY = UsersRepository.getInstance();
        LectureRepository LECTURE_REPOSITORY = LectureRepository.getInstance();
        Optional<User> userOptional = USERS_REPOSITORY.findById(studentId);

        if (userOptional.isEmpty()) {
            return Optional.empty();
        } else if (!(userOptional.get() instanceof Student)) {
            throw new WrongRequestedUserTypeException(String.format("Requested User with id '%d' is not a Student", studentId));
        }
        try (PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Student WHERE id=?1")){
            findStatement.setLong(1, studentId);

            ResultSet findResultsResultSet = findStatement.executeQuery();
            String requestedLectures = findResultsResultSet.getString(2);
            String enrollLectures = findResultsResultSet.getString(3);
            String course = findResultsResultSet.getString(4);
            String semester = findResultsResultSet.getString(5);
            List<Lecture> requestedLecturesList = LECTURE_REPOSITORY.getFromStringArray(requestedLectures.split(","));
            List<Lecture> enrollLecturesList = LECTURE_REPOSITORY.getFromStringArray(enrollLectures.split(","));

            User user = userOptional.get();
            Student student = new Student(studentId, course, semester, enrollLecturesList, requestedLecturesList);
            student.setUsername(user.getUsername());
            student.setPassword(user.getPassword());
            student.setName(user.getName());
            student.setCpf(user.getCpf());
            student.setAddress(user.getAddress());
            student.setBirthday(user.getBirthday());
            LOGGER.debug(String.format("Student with id %d was found to the database", student.getId()));

            return Optional.of(student);
        } catch (SQLException throwable) {
            // TODO: I think we need change this try / catch to a throw AlreadyExists !
            LOGGER.warn(String.format("Student with id '%d' could not be found", studentId), throwable);
            throw new SQLException(String.format("Student with id '%d' could not be found", studentId));
        }
    }

    public List<String> getFromStringArray(String[] studentsIds) {
        UsersRepository USERS_REPOSITORY = UsersRepository.getInstance();
        ArrayList<String> students = new ArrayList<>();

        for(String student : studentsIds) {
            //TODO GAMBIARRA, desfazer futuramente
            if (student.isEmpty()) {
                continue;
            }

            Optional<User> studentObj = null;
            try {
                studentObj = USERS_REPOSITORY.findById(Long.parseLong(student));
                studentObj.ifPresent(user -> {
                    students.add((Long.toString(user.getId())));
                });
            } catch (Exception wrongRequestedUserType) {
                //TODO parece gambiarra, devo corrigir?
                LOGGER.warn(wrongRequestedUserType);
//                wrongRequestedUserType.printStackTrace();
            }
        }
        return students;
    }
}
