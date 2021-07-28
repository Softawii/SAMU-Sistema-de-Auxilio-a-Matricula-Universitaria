package br.ufrrj.samu.services;

import br.ufrrj.samu.entities.User;
import br.ufrrj.samu.utils.LoginStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Optional;

public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    private Connection connection;

    private BCryptPasswordEncoder encoder;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            LOGGER.debug("org.sqlite.JDBC class loaded");
        } catch (ClassNotFoundException e) {
            LOGGER.warn("org.sqlite.JDBC class could not be loaded", e);
        }
    }

    public UserService() {
        try {
            LOGGER.debug("Instantiating BCryptPasswordEncoder");
            encoder = new BCryptPasswordEncoder(4);
            LOGGER.debug("Starting connection to database");
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:" +
                            new File(UserService.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toPath().getParent() +
                            "\\database.db");
            Statement statement = connection.createStatement();
            LOGGER.warn("Creating User table if not exists");
            statement.execute("CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR UNIQUE, password VARCHAR);");
        } catch (SQLException | URISyntaxException throwable) {
            LOGGER.warn(throwable);
        }
    }

    public Optional<User> insertUser(User user) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO USERS(name, password) VALUES (?1, ?2)");
            insertStatement.setString(1, user.getUsername());
            insertStatement.setString(2, encoder.encode(user.getPassword()));

            insertStatement.executeUpdate();
            LOGGER.debug(String.format("User with id %d was inserted to the database", user.getId()));
            long id = insertStatement.getGeneratedKeys().getLong(1);
            user.setId(id);
            return Optional.of(user);
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("User with id '%d' could not be inserted to the database", user.getId()), throwable);
            return Optional.empty();
        }
    }

    public boolean deleteUserById(long userId) {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM USERS WHERE id=?1");
            findStatement.setLong(1, userId);
            ResultSet findResultSet = findStatement.executeQuery();
            findResultSet.getLong(1);

            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM USERS WHERE id=?1");
            deleteStatement.setLong(1, userId);

            deleteStatement.executeUpdate();
            LOGGER.debug(String.format("User with id %d was deleted from the database", userId));
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("User with id '%d' could not be found and deleted from the database", userId), throwable);
            return false;
        }
        return true;
    }

    public boolean deleteUserByUsername(String username) {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM USERS WHERE name=?1");
            findStatement.setString(1, username);
            ResultSet findResultSet = findStatement.executeQuery();
            findResultSet.getLong(1);

            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM USERS WHERE name=?1");
            deleteStatement.setString(1, username);

            deleteStatement.executeUpdate();
            LOGGER.debug(String.format("User with username '%s' was deleted from the database", username));
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("User with username '%s' could not be found and deleted from the database", username), throwable);
            return false;
        }
        return true;
    }

    public boolean atualizarUsuario(User user) {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM USERS WHERE id=?1");
            findStatement.setLong(1, user.getId());
            ResultSet findResultSet = findStatement.executeQuery();
            findResultSet.getLong(1);

            PreparedStatement updateStatement = connection.prepareStatement("UPDATE USERS SET name = ?1, password = ?2 WHERE id = ?3;");
            updateStatement.setString(1, user.getUsername());
            updateStatement.setString(2, user.getPassword());
            updateStatement.setLong(3, user.getId());

            updateStatement.executeUpdate();
            LOGGER.debug(String.format("User with id '%d' and username '%s' was updated", user.getId(), user.getUsername()));
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("User with id '%d' and username '%s' could not be updated", user.getId(), user.getUsername()), throwable);
            return false;
        }
        return true;
    }

    public Optional<User> obterUsuario(long userId) {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM USERS WHERE id=?1");
            findStatement.setLong(1, userId);

            ResultSet findResultSet = findStatement.executeQuery();
            long id = findResultSet.getLong(1);
            String username = findResultSet.getString(2);
            String password = findResultSet.getString(3);
            User user = new User(id, username, password);
            LOGGER.debug(String.format("User with id '%d' and username '%s' was found with success", user.getId(), user.getUsername()));
            return Optional.of(user);
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("User with id '%d' could not be found", userId), throwable);
            return Optional.empty();
        }
    }

    public Optional<User> obterUsuario(String name) {
        try {
            PreparedStatement findStatement = connection.prepareStatement("SELECT * FROM USERS WHERE name=?1");
            findStatement.setString(1, name);

            ResultSet findResultSet = findStatement.executeQuery();
            long id = findResultSet.getLong(1);
            String username = findResultSet.getString(2);
            String password = findResultSet.getString(3);
            User user = new User(id, username, password);
            LOGGER.debug(String.format("User with id '%d' and username '%s' was found with success", user.getId(), user.getUsername()));
            return Optional.of(user);
        } catch (SQLException throwable) {
            LOGGER.warn(String.format("User with username '%s' could not be found", name), throwable);
            return Optional.empty();
        }
    }

    public LoginStatus checkPassword(String username, String password) {
        /**
         *
         *
         */
        Optional<User> userDB = this.obterUsuario(username);

        if(userDB.isPresent()) {
            LOGGER.debug(userDB.get());
            LOGGER.debug("DB: " + userDB.get().getPassword() + " : " + this.encoder.encode(userDB.get().getPassword()));
            LOGGER.debug("IN: " + password + " : " + this.encoder.encode(password));
        }

        if(userDB.isEmpty())
            return LoginStatus.UNKNOWN_USER;
        else {
            User user = userDB.get();

            if(user.getPassword().equals(password))
                return LoginStatus.SUCCESS;
        }
        return LoginStatus.WRONG_PASSWORD;
    }

}
