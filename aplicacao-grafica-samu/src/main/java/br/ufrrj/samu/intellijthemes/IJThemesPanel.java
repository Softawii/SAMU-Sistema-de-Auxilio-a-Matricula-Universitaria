/*
 * Copyright 2019 FormDev Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.ufrrj.samu.intellijthemes;

import br.ufrrj.samu.utils.Util;
import com.formdev.flatlaf.*;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.io.FileInputStream;
import java.util.List;
import java.util.*;

/**
 * @author Karl Tauber
 */
public class IJThemesPanel
        extends JPanel {
    public static final String THEMES_PACKAGE = "/com/formdev/flatlaf/intellijthemes/themes/";

    private final IJThemesManager themesManager = new IJThemesManager();
    private final List<IJThemeInfo> themes = new ArrayList<>();
    private final HashMap<Integer, String> categories = new HashMap<>();
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton saveButton;
    private JButton sourceCodeButton;
    private JComboBox<String> filterComboBox;
    private JScrollPane themesScrollPane;
    private JList<IJThemeInfo> themesList;

    public IJThemesPanel() {
        initComponents();
        saveButton.setEnabled(false);
        sourceCodeButton.setEnabled(false);

        // create renderer
        themesList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                String title = categories.get(index);
                String name = ((IJThemeInfo) value).name;
                int sep = name.indexOf('/');
                if (sep >= 0)
                    name = name.substring(sep + 1).trim();

                JComponent c = (JComponent) super.getListCellRendererComponent(list, name, index, isSelected, cellHasFocus);
                c.setToolTipText(buildToolTip((IJThemeInfo) value));
                if (title != null)
                    c.setBorder(new CompoundBorder(new ListCellTitledBorder(themesList, title), c.getBorder()));
                return c;
            }

            private String buildToolTip(IJThemeInfo ti) {
                if (ti.themeFile != null)
                    return ti.themeFile.getPath();
                if (ti.resourceName == null)
                    return ti.name;

                return "Name: " + ti.name
                        + "\nLicense: " + ti.license
                        + "\nSource Code: " + ti.sourceCodeUrl;
            }
        });

        updateThemesList();
    }

    private void updateThemesList() {
        int filterLightDark = filterComboBox.getSelectedIndex();
        boolean showLight = (filterLightDark != 2);
        boolean showDark = (filterLightDark != 1);

        // load theme infos
        themesManager.loadBundledThemes();
        themesManager.loadThemesFromDirectory();

        // sort themes by name
        Comparator<? super IJThemeInfo> comparator = (t1, t2) -> t1.name.compareToIgnoreCase(t2.name);
        themesManager.bundledThemes.sort(comparator);
        themesManager.moreThemes.sort(comparator);

        // remember selection (must be invoked before clearing themes field)
        IJThemeInfo oldSel = themesList.getSelectedValue();

        themes.clear();
        categories.clear();

        // add core themes at beginning
        categories.put(themes.size(), "Core Themes");
        if (showLight) {
            themes.add(new IJThemeInfo("Flat Light", null, false, null, null, null, null, null, FlatLightLaf.class.getName()));
        }
        if (showDark) {
            themes.add(new IJThemeInfo("Flat Dark", null, true, null, null, null, null, null, FlatDarkLaf.class.getName()));
        }
        if (showLight) {
            themes.add(new IJThemeInfo("Flat IntelliJ", null, false, null, null, null, null, null, FlatIntelliJLaf.class.getName()));
        }
        if (showDark) {
            themes.add(new IJThemeInfo("Flat Darcula", null, true, null, null, null, null, null, FlatDarculaLaf.class.getName()));
        }

        // add themes from directory
        categories.put(themes.size(), "Current Directory");
        themes.addAll(themesManager.moreThemes);

        // add uncategorized bundled themes
        categories.put(themes.size(), "IntelliJ Themes");
        for (IJThemeInfo ti : themesManager.bundledThemes) {
            boolean show = (showLight && !ti.dark) || (showDark && ti.dark);
            if (show && !ti.name.contains("/")) {
                themes.add(ti);
            }
        }

        // add categorized bundled themes
        String lastCategory = null;
        for (IJThemeInfo ti : themesManager.bundledThemes) {
            boolean show = (showLight && !ti.dark) || (showDark && ti.dark);
            int sep = ti.name.indexOf('/');
            if (!show || sep < 0)
                continue;

            String category = ti.name.substring(0, sep).trim();
            if (!Objects.equals(lastCategory, category)) {
                lastCategory = category;
                categories.put(themes.size(), category);
            }

            themes.add(ti);
        }

        // fill themes list
        themesList.setModel(new AbstractListModel<IJThemeInfo>() {
            @Override
            public int getSize() {
                return themes.size();
            }

            @Override
            public IJThemeInfo getElementAt(int index) {
                return themes.get(index);
            }
        });

        // restore selection
        if (oldSel != null) {
            for (int i = 0; i < themes.size(); i++) {
                IJThemeInfo theme = themes.get(i);
                if (oldSel.name.equals(theme.name) &&
                        Objects.equals(oldSel.resourceName, theme.resourceName) &&
                        Objects.equals(oldSel.themeFile, theme.themeFile) &&
                        Objects.equals(oldSel.lafClassName, theme.lafClassName)) {
                    themesList.setSelectedIndex(i);
                    break;
                }
            }

            // select first theme if none selected
            if (themesList.getSelectedIndex() < 0)
                themesList.setSelectedIndex(0);
        }

        // scroll selection into visible area
        int sel = themesList.getSelectedIndex();
        if (sel >= 0) {
            Rectangle bounds = themesList.getCellBounds(sel, sel);
            if (bounds != null)
                themesList.scrollRectToVisible(bounds);
        }
    }

    public void selectPreviousTheme() {
        int sel = themesList.getSelectedIndex();
        if (sel > 0)
            themesList.setSelectedIndex(sel - 1);
    }

    public void selectNextTheme() {
        int sel = themesList.getSelectedIndex();
        themesList.setSelectedIndex(sel + 1);
    }

    private void themesListValueChanged(ListSelectionEvent e) {
        IJThemeInfo themeInfo = themesList.getSelectedValue();
        boolean bundledTheme = (themeInfo != null && themeInfo.resourceName != null);
        saveButton.setEnabled(bundledTheme);
        sourceCodeButton.setEnabled(bundledTheme);

        if (e.getValueIsAdjusting()) {
            return;
        }

        EventQueue.invokeLater(() -> {
            setTheme(themeInfo);
        });
    }

    private void setTheme(IJThemeInfo themeInfo) {
        if (themeInfo == null)
            return;

        // change look and feel
        if (themeInfo.lafClassName != null) {
            if (themeInfo.lafClassName.equals(UIManager.getLookAndFeel().getClass().getName()))
                return;

            FlatAnimatedLafChange.showSnapshot();

            try {
                UIManager.setLookAndFeel(themeInfo.lafClassName);
            } catch (Exception ex) {
                ex.printStackTrace();
                showInformationDialog("Failed to create '" + themeInfo.lafClassName + "'.", ex);
            }
        } else if (themeInfo.themeFile != null) {
            FlatAnimatedLafChange.showSnapshot();

            try {
                if (themeInfo.themeFile.getName().endsWith(".properties")) {
                    FlatLaf.setup(new FlatPropertiesLaf(themeInfo.name, themeInfo.themeFile));
                } else
                    FlatLaf.setup(IntelliJTheme.createLaf(new FileInputStream(themeInfo.themeFile)));
            } catch (Exception ex) {
                ex.printStackTrace();
                showInformationDialog("Failed to load '" + themeInfo.themeFile + "'.", ex);
            }
        } else {
            FlatAnimatedLafChange.showSnapshot();

            IntelliJTheme.setup(getClass().getResourceAsStream(THEMES_PACKAGE + themeInfo.resourceName));

            try {
                String themeClassName = themeInfo.name.contains("Material Theme UI Lite / ") ?
                        Util.getInstalledLookAndFeels().get(themeInfo.name.split(" / ")[1] + " (Material)").getClassName() :
                        Util.getInstalledLookAndFeels().get(themeInfo.name).getClassName();
                UIManager.setLookAndFeel(themeClassName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // update all components
        FlatLaf.updateUI();
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    private void showInformationDialog(String message, Exception ex) {
        JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(this),
                message + "\n\n" + ex.getMessage(),
                "FlatLaf", JOptionPane.INFORMATION_MESSAGE);
    }

    private void windowActivated() {
        // refresh themes list on window activation
        if (themesManager.hasThemesFromDirectoryChanged())
            updateThemesList();
    }

    private void filterChanged() {
        updateThemesList();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JLabel themesLabel = new JLabel();
        saveButton = new JButton();
        sourceCodeButton = new JButton();
        filterComboBox = new JComboBox<>();
        themesScrollPane = new JScrollPane();
        themesList = new JList<>();

        //======== this ========
        setLayout(new MigLayout(
                "insets dialog,hidemode 3",
                // columns
                "[grow,fill]",
                // rows
                "[]3" +
                        "[grow,fill]"));

        //---- themesLabel ----
        themesLabel.setText("Themes:");
        add(themesLabel, "cell 0 0");

        //---- filterComboBox ----
        filterComboBox.setModel(new DefaultComboBoxModel<>(new String[]{
                "all",
                "light",
                "dark"
        }));
        filterComboBox.putClientProperty("JComponent.minimumWidth", 0);
        filterComboBox.setFocusable(false);
        filterComboBox.addActionListener(e -> filterChanged());
        add(filterComboBox, "cell 0 0,alignx right,growx 0");

        //======== themesScrollPane ========
        {

            //---- themesList ----
            themesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            themesList.addListSelectionListener(e -> themesListValueChanged(e));
            themesScrollPane.setViewportView(themesList);
        }
        add(themesScrollPane, "cell 0 1");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
