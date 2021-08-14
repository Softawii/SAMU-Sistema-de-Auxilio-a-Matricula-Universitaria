package br.ufrrj.samu;

import br.ufrrj.samu.controllers.SystemController;
import br.ufrrj.samu.utils.Util;
import br.ufrrj.samu.views.LoginFrame;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.IntelliJTheme.ThemeLaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class SAMU {

    private static final Logger LOGGER = LogManager.getLogger(SAMU.class);

    public static ThemeLaf customMoonlightContrastTheme;

    public static ThemeLaf customGrayTheme;

    private final LoginFrame loginFrame;

    private SystemController systemController;

    static
    {
        try {
            LOGGER.info("Loading FlatLaf custom themes from resources");
            customMoonlightContrastTheme = new ThemeLaf(new IntelliJTheme(requireNonNull(SAMU.class.getClassLoader().getResourceAsStream("intellijthemes/Moonlight Contrast.theme.json"))));
            customGrayTheme = new ThemeLaf(new IntelliJTheme(requireNonNull(SAMU.class.getClassLoader().getResourceAsStream("intellijthemes/Gray.theme.json"))));

            LOGGER.info("Installing FlatLaf info from previously loaded custom themes");
            FlatLaf.installLafInfo(customMoonlightContrastTheme.getName(), customMoonlightContrastTheme.getClass());
            FlatLaf.installLafInfo(customGrayTheme.getName(), customGrayTheme.getClass());

            LOGGER.info("Setting up Flat Gray heme");
            UIManager.setLookAndFeel(customGrayTheme);
        } catch (IOException | UnsupportedLookAndFeelException e) {
            LOGGER.warn(e);
//            e.printStackTrace();
        }
        LOGGER.info("Setting up Sansation Light Plain Font to all components");
        Util.setUIFont(new FontUIResource(Util.getSansationFont()));
//        Util.setUIFont(new FontUIResource("Sansation", Font.PLAIN, 20));
    }

    private SAMU() {

        systemController = new SystemController();
        loginFrame = new LoginFrame(systemController, this);
    }

    public static void startSamu() {
        LOGGER.info("Starting SAMU GUI");
        SwingUtilities.invokeLater(SAMU::new);
    }
}
