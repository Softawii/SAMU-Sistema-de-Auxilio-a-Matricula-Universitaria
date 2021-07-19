package br.ufrrj.samu;

import br.ufrrj.samu.utils.Util;
import br.ufrrj.samu.views.LoginFrame;
import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class SAMU {

    private static final Logger LOGGER = LogManager.getLogger();

    private final LoginFrame loginFrame;

    static
    {
        LOGGER.info("Installing FlatLaf custom themes");
        Util.installCustomThemes();
        LOGGER.info("Setting up Flat Dark Purple IJTheme");
        FlatDarkPurpleIJTheme.setup();
        LOGGER.info("Setting up Sansation Light Plain Font");
        Util.setUIFont(new FontUIResource(Util.getSansationFont()));
//        Util.setUIFont(new FontUIResource("Sansation", Font.PLAIN, 20));
    }

    private SAMU() {
        loginFrame = new LoginFrame();
    }

    public static void startSamu() {
        LOGGER.info("Starting SAMU GUI");
        SwingUtilities.invokeLater(SAMU::new);
    }
}
