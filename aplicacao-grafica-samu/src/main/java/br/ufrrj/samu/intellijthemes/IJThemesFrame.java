package br.ufrrj.samu.intellijthemes;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

import static br.ufrrj.samu.utils.Util.centreWindow;

public class IJThemesFrame extends JFrame {

    private Container contentPane;
    private String frameTitle = "Themes";
    private int width = 300;
    private int height = 500;

    public IJThemesFrame() throws HeadlessException {
        super();
        frameInit();
        this.add(new IJThemesPanel());
        this.setVisible(false);
        this.setResizable(false);
        this.setFont(new FontUIResource("Serif", Font.PLAIN, 12)); //prevent null pointer exception
    }

    @Override
    protected void frameInit() {
        super.frameInit();
        contentPane = this.getContentPane();
        this.setSize(this.width, this.height);
        this.setTitle(this.frameTitle);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        this.setIconImage(new ImageIcon(requireNonNull(this.getClass().getClassLoader().getResource("friend.png"))).getImage());
        centreWindow(this);
        this.contentPane.setBackground(Color.magenta);
    }


}
