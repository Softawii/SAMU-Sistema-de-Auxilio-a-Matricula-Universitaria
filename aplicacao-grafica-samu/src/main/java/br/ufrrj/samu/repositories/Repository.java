package br.ufrrj.samu.repositories;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Repository {

    private static final Logger LOGGER = LogManager.getLogger(SubjectRepository.class);

    protected static Connection connection;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            LOGGER.debug("org.sqlite.JDBC class loaded");
        } catch (ClassNotFoundException e) {
            LOGGER.warn("org.sqlite.JDBC class could not be loaded", e);
        }
    }

    static {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:" +
                            new File(Repository.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toPath().getParent() +
                            "\\database.db");
        } catch (SQLException | URISyntaxException throwable) {
            LOGGER.warn(throwable);
        }
    }

}
