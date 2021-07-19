package br.ufrrj.samu.views;

import br.ufrrj.samu.utils.Util;

import javax.swing.*;
import java.awt.*;

import static java.util.Objects.requireNonNull;

public class FontsFrame extends JFrame {

    private String frameTitle = "Select a font";
    private int width = 350;
    private int height = 150;

    public FontsFrame(Component ijThemesFrame, Component SAM) throws HeadlessException {
        super();
        frameInit();
        JComboBox<String> fontFamiliesJComboBox = new JComboBox<>(Util.getAllFontFamilies());
        fontFamiliesJComboBox.setMaximumSize(new Dimension(width-50, height-10));
        fontFamiliesJComboBox.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                String fontFamily = ((String) ((JComboBox) e.getSource()).getSelectedItem());
                Util.changeFont(this, new Font(fontFamily, Font.PLAIN, 0));
                Util.changeFont(SAM, new Font(fontFamily, Font.PLAIN, 0));
                Util.changeFont(ijThemesFrame, new Font(fontFamily, Font.PLAIN, 0));
            });
        });
        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0; gridConstraints.gridy = 0;
        gridConstraints.insets = new Insets(10, 10, 10, 10);

        JPanel main = new JPanel();
        main.setLayout(new GridBagLayout());
        main.add(fontFamiliesJComboBox, gridConstraints);
        this.add(main);
//        this.setVisible(true);
        this.setResizable(false);
    }

    @Override
    protected void frameInit() {
        super.frameInit();
        this.setSize(this.width, this.height);
        this.setTitle(this.frameTitle);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setIconImage(new ImageIcon(requireNonNull(this.getClass().getClassLoader().getResource("friend.png"))).getImage());
        Util.centreWindow(this);
    }
}
