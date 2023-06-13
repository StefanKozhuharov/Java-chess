/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template*/
package java_chess.gui;

import com.google.common.primitives.Ints;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java_chess.board.Move;
import java_chess.gui.Table.*;
import java_chess.pieces.Piece;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java_chess.pieces.Piece;

/**
 *
 * @author Mitko
 */

public class TakenPiecesPanel extends JPanel {//панела от ляво на дъската за взетите фигури

    private final JPanel northPanel;
    private final JPanel southPanel;

    private static final Color PANEL_COLOR = Color.decode("0xFDFE6");
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);
    private static final Dimension TAKEN_PIECES_DIMENSITO = new Dimension(40,80);

    public TakenPiecesPanel() {
        super(new BorderLayout());
        this.setBackground(PANEL_COLOR);
        this.setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8, 2));
        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(this.southPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_DIMENSITO);
    }
    
    public void redo(final MoveLog moveLog){
        this.northPanel.removeAll();
        this.southPanel.removeAll();
        
        final List<Piece> whiteTakenPieces = new ArrayList<>();
        final List<Piece> blackTakenPieces = new ArrayList<>();
        
        for(final Move move : moveLog.getMoves()){//преглеждаме всички ходове
            if(move.isAttack()){//ако е атакуващ ход слагаме взетата фигура в списъка
                final Piece takenPiece = move.getAttackedPiece();
                if(takenPiece.getPieceColor().isWhite()){
                    whiteTakenPieces.add(takenPiece);
                }else{
                    blackTakenPieces.add(takenPiece);
                }
                
            }
        }
        
        Collections.sort(whiteTakenPieces, new Comparator<Piece>(){//сортираме фигурите по сила на фигурета
            @Override
            public int compare(Piece o1, Piece o2){
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });
        
        Collections.sort(blackTakenPieces, new Comparator<Piece>(){
            @Override
            public int compare(Piece o1, Piece o2){
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });
        
        for(final Piece takenPiece : whiteTakenPieces){//слагаме изображението на всяка фигура
            try{
                final BufferedImage image = ImageIO.read(new File("pices/"
                        +takenPiece.getPieceColor().toString().substring(0,1)+""+ takenPiece.toString()));
                final ImageIcon icon = new ImageIcon();
                final JLabel imageLable = new JLabel();
                this.southPanel.add(imageLable);
            }catch(final IOException e){
                e.printStackTrace();
            }
        }
        
        for(final Piece takenPiece : blackTakenPieces){
            try{
                final BufferedImage image = ImageIO.read(new File("pices/"
                        +takenPiece.getPieceColor().toString().substring(0,1)+""+ takenPiece.toString()));
                final ImageIcon icon = new ImageIcon();
                final JLabel imageLable = new JLabel();
                this.southPanel.add(imageLable);
            }catch(final IOException e){
                e.printStackTrace();
            }
        }
        
        validate();
    }
}
