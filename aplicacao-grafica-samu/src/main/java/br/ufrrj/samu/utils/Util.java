package br.ufrrj.samu.utils;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatAllIJThemes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class Util {

    private static final Logger LOGGER = LogManager.getLogger();


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
        try {
            fontStream = Files.newInputStream(Paths.get(requireNonNull(Util.class.getClassLoader().getResource("fonts/Sansation_Regular.ttf")).toURI()));
            sansationFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
        } catch (IOException | URISyntaxException | FontFormatException e) {
            e.printStackTrace();
            LOGGER.warn(e);
        }
        return sansationFont;
    }

}
