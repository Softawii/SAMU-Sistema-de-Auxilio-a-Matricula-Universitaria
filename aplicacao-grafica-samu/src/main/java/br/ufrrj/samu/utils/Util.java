package br.ufrrj.samu.utils;

import br.ufrrj.samu.SAMU;
import com.formdev.flatlaf.*;
import com.formdev.flatlaf.intellijthemes.FlatAllIJThemes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class Util {

    public static final JButton THEME_BUTTON;

    private static final ImageIcon LIGHT_MODE_ICON = new ImageIcon(requireNonNull(Util.class.getClassLoader().getResource("images/lightModeIcon.png")));

    private static final ImageIcon DARK_MODE_ICON = new ImageIcon(requireNonNull(Util.class.getClassLoader().getResource("images/darkModeIcon.png")));

    public static boolean isDarkMode = false;

    private static final Logger LOGGER = LogManager.getLogger(Util.class);

    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            Path currentPath = new File(Util.class.getProtectionDomain().getCodeSource().getLocation().toURI()).toPath().getParent();
            Path propertiesPath = Paths.get(currentPath.toString(), File.separator, ".properties");

            //If .properties file exists
            if (propertiesPath.toFile().isFile()) {
                PROPERTIES.load(Files.newInputStream(propertiesPath));
                boolean isFirstRun = Boolean.parseBoolean(((String) PROPERTIES.get("first-run")));
                if (isFirstRun) {
//                    initDatabase();
                    PROPERTIES.put("first-run", Boolean.toString(false));
                    PROPERTIES.store(Files.newOutputStream(propertiesPath), null);
                }
            } else {
                boolean success = propertiesPath.toFile().createNewFile();
                if (success) {
                    PROPERTIES.load(Files.newInputStream(propertiesPath));
                    PROPERTIES.put("first-run", Boolean.toString(false));
//                    initDatabase();
                    PROPERTIES.store(Files.newOutputStream(propertiesPath), null);
                } else {
                    //failed to create file
                }
            }

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    static
    {
        THEME_BUTTON = new JButton();
        THEME_BUTTON.setIcon(LIGHT_MODE_ICON);
        THEME_BUTTON.setFocusable(false);
        THEME_BUTTON.setRolloverEnabled(false);
        THEME_BUTTON.setFont(THEME_BUTTON.getFont().deriveFont(20f));
        THEME_BUTTON.addActionListener(e -> {
            Util.switchMode();
            if (Util.isDarkMode) {
                THEME_BUTTON.setIcon(DARK_MODE_ICON);
            } else {
                THEME_BUTTON.setIcon(LIGHT_MODE_ICON);
            }
        });
    }

    public static void switchMode() {
        isDarkMode = !isDarkMode;
        try {
            if (isDarkMode) {
                LOGGER.info("Changing to dark theme(Flat Moonlight Contrast IJTheme)");
                UIManager.setLookAndFeel(SAMU.customMoonlightContrastTheme);
            } else {
                LOGGER.info("Changing to light theme(Flat Gray IJTheme)");
                UIManager.setLookAndFeel(SAMU.customGrayTheme);
            }
        } catch (UnsupportedLookAndFeelException e) {
            LOGGER.warn(e);
            e.printStackTrace();
        }
        FlatLaf.updateUILater();
    }


    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    public static void installCustomThemes() {
        FlatLightLaf.installLafInfo();
        FlatDarculaLaf.installLafInfo();
        FlatDarkLaf.installLafInfo();
        FlatIntelliJLaf.installLafInfo();

        for (FlatAllIJThemes.FlatIJLookAndFeelInfo info : FlatAllIJThemes.INFOS) {
            UIManager.installLookAndFeel(info);
        }
    }

    public static String[] getAllFontFamilies() {
        return Arrays.stream(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()).filter(Font::isPlain).map(Font::getFamily).distinct().toArray(String[]::new);
    }

    public static void changeFont(Component component, Font font) {
        component.setFont(font.deriveFont(component.getFont().getStyle(), component.getFont().getSize2D()));
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                changeFont(child, font);
            }
        }
    }

    public static Map<String, UIManager.LookAndFeelInfo> getInstalledLookAndFeels() {
        return Arrays.stream(UIManager.getInstalledLookAndFeels()).collect(Collectors.toMap(UIManager.LookAndFeelInfo::getName, lookAndFeelInfo -> lookAndFeelInfo));
    }

    public static void setUIFont(FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource fontUIResource = (FontUIResource) value;
                UIManager.put(key, f.deriveFont(((float) fontUIResource.getSize())).deriveFont(Font.PLAIN));
            }
        }
    }

    public static Font getSansationFont() {
        InputStream fontStream = null;
        Font sansationFont = null;
        try  {
            fontStream = requireNonNull(Util.class.getClassLoader().getResourceAsStream("fonts/Sansation_Regular.ttf"));
            sansationFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            LOGGER.warn(e);
        }
        return sansationFont;
    }

    public static ImageIcon getImageWidth(String path, int width, int height) {
        ImageIcon image = new ImageIcon(requireNonNull(Util.class.getClassLoader().getResource(path)));
        return new ImageIcon(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }

    public static ImageIcon getImageWidth(ImageIcon image, int width, int height) {
        return new ImageIcon(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }
}
