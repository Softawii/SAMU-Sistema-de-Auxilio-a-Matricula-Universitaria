package br.ufrrj.samu.repositories;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.*;

import br.ufrrj.samu.entities.Student;
import br.ufrrj.samu.entities.User;
import br.ufrrj.samu.exceptions.UnknownUserException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UsersRepository {

    private static final Logger LOGGER = LogManager.getLogger(SubjectRepository.class);

    private static UsersRepository instance;

    private Connection connection;

    private BCryptPasswordEncoder encoder;

    private UsersRepository() {
        try {
            LOGGER.debug("Starting connection to database");

            connection = Repository.connection;

            encoder = new BCryptPasswordEncoder(4);

            connection.setAutoCommit(true);
            LOGGER.debug("AutoCommit enabled");

            initDatabase();
            LOGGER.warn("Database initialized");

            connection.setAutoCommit(true);

        } catch (SQLException throwable) {
            LOGGER.warn(throwable);
        }
    }

    public BCryptPasswordEncoder getEncoder() {
        return encoder;
    }

    public static UsersRepository getInstance() {
        UsersRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized (UsersRepository.class) {
            if (UsersRepository.instance == null) {
                UsersRepository.instance = new UsersRepository();
            }
            return instance;
        }
    }

    private void initDatabase() {
        ScriptRunner runner = new ScriptRunner(connection);
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(StudentRepository.class.getClassLoader().getResourceAsStream("database/initUsers.sql"))));
        runner.setEscapeProcessing(false);
        runner.runScript(reader);

//        Optional<User> user = this.insert(new Student("yan", "1234", "Yan Carlos",
//                "000.000.000-01", "Rua Franca", "27/05/2001",
//                "Ciencia da Computacao", "2019.1", new ArrayList<>()));

    }

    private String type(User user) {
        if(user instanceof Student)
            return "STUDENT";

        return "USER";
    }

    public User insert(User user) throws UnknownUserException.AlreadyExistsException {
        // We need to specify the error, username and cpf need to be unique
        try (PreparedStatement insertStatement = Repository.connection.prepareStatement("INSERT INTO Users (username, password, name, cpf, address, birthday, type) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7)")) {
            insertStatement.setString(1, user.getUsername());
            insertStatement.setString(2, encoder.encode(user.getPassword()));
            insertStatement.setString(3, user.getName());
            insertStatement.setString(4, user.getCpf());
            insertStatement.setString(5, user.getAddress());
            insertStatement.setString(6, user.getBirthday());
            insertStatement.setString(7, this.type(user));

            insertStatement.executeUpdate();

            LOGGER.debug(String.format("User with username %s was inserted to the database", user.getUsername()));
            long id = insertStatement.getGeneratedKeys().getLong(1);
            user.setId(id);

            insertStatement.close();
            return user;

        } catch (SQLException throwable) {
            LOGGER.warn(String.format("User with name '%s' could not be inserted to the database", user.getUsername()), throwable);

            String msg = throwable.getMessage();

            if(msg.contains("cpf")) {
                throw new UnknownUserException.AlreadyExistsException("O CPF ja esta cadastrado", throwable);
            } else {
                throw new UnknownUserException.AlreadyExistsException("O Username ja esta cadastrado", throwable);
            }
        }
    }


    // TODO: It will expand to other tables!!!!! PAY ATTENTION IN THAT
    public boolean deleteByCpf(String cpf) {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Users WHERE cpf=?1");
            findStatement.setString(1, cpf);
            ResultSet findResultSet = findStatement.executeQuery();
            findResultSet.getString(1);

            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM Users WHERE cpf=?1");
            deleteStatement.setString(1, cpf);

            deleteStatement.executeUpdate();
            LOGGER.debug(String.format("User with cpf %s was deleted from the database", cpf));
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("User with cpf '%s' could not be found and deleted from the database", cpf), throwable);
            return false;
        }
        return true;
    }


    // TODO: It will expand to other tables!!!!! PAY ATTENTION IN THAT
    public boolean deleteByUsername(String username) {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM Users WHERE username=?1");
            findStatement.setString(1, username);
            ResultSet findResultSet = findStatement.executeQuery();
            findResultSet.getLong(1);

            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM Users WHERE username=?1");
            deleteStatement.setString(1, username);

            deleteStatement.executeUpdate();
            LOGGER.debug(String.format("User with username '%s' was deleted from the database", username));
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("User with username '%s' could not be found and deleted from the database", username), throwable);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        StudentRepository ur = StudentRepository.getInstance();
        //UsersRepository ur = UsersRepository.getInstance();

        try {
            ur.insert(new Student("yananzian", "1234", "Yan Carlos",
                    "000.000.000-01", "Rua Franca", "27/05/2001",
                    "Ciencia da Computacao", "2019.1", new ArrayList<>()));


        } catch (UnknownUserException.AlreadyExistsException e) {
            e.printStackTrace();
        }
    }
}
