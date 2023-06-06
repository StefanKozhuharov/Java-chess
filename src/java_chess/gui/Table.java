/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_chess.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java_chess.pieces.Piece;
import javax.swing.*;

/**
 *
 * @author dimitarkg
 */
public class Table {

    private final JFrame gameFrame;
    private static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
 
    public Table() {
        this.gameFrame = new JFrame("Jchess");
        final JMenuBar tableMenuBar = new JMenuBar();
        populateMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setVisible(true);
    }
    private void populateMenuBar(final JMenuBar tableMenuBar){
        tableMenuBar.add(createFileName());
    }
    private JMenu createFileName(){
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPNG = new JMenuItem("Load PNG File");
        openPNG.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("open that png file");
            }
        });
        fileMenu.add(openPNG);
        return fileMenu;
    }
}
