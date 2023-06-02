/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_chess.gui;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author dimitarkg
 */
public class Table {

    private final JFrame gameFrame;
    public static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);

    public Table() {
        this.gameFrame = new JFrame("Jchess");

        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setVisible(true);
    }
}
