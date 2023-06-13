/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_chess.gui;

import com.google.common.collect.Lists;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java_chess.pieces.Piece;
import javax.swing.*;
import java.util.List;
import java_chess.board.*;
import java_chess.player.*;
import javax.imageio.ImageIO;

/**
 *
 * @author dimitarkg
 */
public class Table {

    private static Object get() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private boolean highlightLegalMoves;
    
    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");

    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private Board chessBoard;
    private static String defaultPieceImagesPath = "pieces/";

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);
    

    public Table() {
        this.gameFrame = new JFrame("Jchess");
        this.gameFrame.setLayout(new BorderLayout());

        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);

        this.chessBoard = Board.createStandardBoard();
        this.highlightLegalMoves = false;

        this.boardPanel = new BoardPanel();
        this.boardDirection = BoardDirection.NORMAL;
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMame());
        tableMenuBar.add(createPreferencesMenu());
        return tableMenuBar;
    }

    private JMenu createFileMame() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPNG = new JMenuItem("Load PGN File");
        openPNG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("open that pgn file");
            }
        });
        fileMenu.add(openPNG);
        // бутона за излизане от играта\/
        final JMenuItem exitMenuItem = new JMenuItem("EXIT");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        return fileMenu;
    } 
    
    private JMenu createPreferencesMenu(){//създаваме меню зас предпочитения
        final JMenu preferencesMenu = new JMenu("Preferences");
        //добавяме опция за завъртане на дъската
        final JMenuItem flipBoardMenuItem = new JMenuItem("FlipBoard");
        flipBoardMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                boardDirection = boardDirection.opposite();
                boardPanel.drawBoard(chessBoard);
            }
        
        
        });
        preferencesMenu.add(flipBoardMenuItem);
        preferencesMenu.addSeparator();
        final JCheckBoxMenuItem legalMovesHighilighterCheckbox = new JCheckBoxMenuItem("Highlight Legal Moves", false);
        legalMovesHighilighterCheckbox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                highlightLegalMoves = legalMovesHighilighterCheckbox.isSelected();
            }
        });
        preferencesMenu.add(legalMovesHighilighterCheckbox);
        return preferencesMenu;
    }
    
    private enum BoardDirection {//опция за завъртане на дъската
        NORMAL {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
        abstract BoardDirection opposite();

    }
    
    private class BoardPanel extends JPanel {// Създаваме игралното поле 

        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);//Добавяме всяко квадратче към лист
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(final Board board) {
            removeAll();
            for (final TilePanel tilePanel : boardDirection.traverse(boardTiles)) {
                tilePanel.drawTile(board); 
                add(tilePanel);
            }
            validate();
            repaint();
        }
        
    }
    public static class MoveLog{//запис на предишните ходове

        private final List<Move> moves;

        MoveLog() {
            this.moves = new ArrayList<>();
        }
        public List<Move> getMoves(){
            return this.moves;
        }
        public void addMove(final Move move) {
            this.moves.add(move);
        }
        public int size() {
            return this.moves.size();
        }
        public void clear() {
            this.moves.clear();
        }
        public Move removeMove(int index){
            return this.moves.remove(index);
        }
        public boolean removeMove(final Move move){
            return this.moves.remove(move);
        }

    }

    private class TilePanel extends JPanel {//създаваме всяко отделно квадратче

        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);

            addMouseListener(new MouseListener() {// на всеки квадрат слушаме за кликове
                @Override
                public void mouseClicked(final MouseEvent e) {//взимаме и пускаме фигура
                    if (SwingUtilities.isRightMouseButton(e)) {//при второ натискане пускаме фигурата
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                    } else if (SwingUtilities.isLeftMouseButton(e)) {//при първо натискане взимаме фигурата
                        if (sourceTile == null) {
                            sourceTile = chessBoard.getTile(tileId);
                            humanMovedPiece = sourceTile.getPiece();
                            if (humanMovedPiece == null) {//ако на полето няма фигура не избираме полето
                                sourceTile = null;
                            }
                        } else {
                            destinationTile = chessBoard.getTile(tileId);
                            final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                            if (transition.getMoveStatus().isDone()) {
                                chessBoard = transition.getTransitionBoard();
                                //TODO --------------------------------dobawqne na 
                            }
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPanel.drawBoard(chessBoard);
                            }
                        });
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {
                }

                @Override
                public void mouseReleased(final MouseEvent e) {
                }

                @Override
                public void mouseEntered(final MouseEvent e) {
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                }

            });

            validate();
        }

        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            validate();
            repaint();
            highlightLegals(board);
        }

        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if (board.getTile(this.tileId).isTileOccupied()) {
                try {
                    final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath + board.getTile(this.tileId).getPiece().getPieceColor().toString().substring(0, 1) + ""
                            + board.getTile(this.tileId).getPiece().toString() + ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        private void highlightLegals(final Board board) {
            if (highlightLegalMoves) {
                for (final Move move : pieceLegalMoves(board)) {
                    if (move.getDestinationCoordinate() == this.tileId) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("pieces/green_dot.png")))));
                        }
                        catch (final IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        
        private Collection<Move> pieceLegalMoves(final Board board) {
            if(humanMovedPiece != null && humanMovedPiece.getPieceColor() == board.currentPlayer().getColor()) {
                return humanMovedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }

        private void assignTileColor() {// задаваме цветовете на всяко квадратче, ред по ред
            if (BoardUtils.EIGHT_RANK[this.tileId]
                    || BoardUtils.SIXTH_RANK[this.tileId]
                    || BoardUtils.FOURTH_RANK[this.tileId]
                    || BoardUtils.SECOND_RANK[this.tileId]) {
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);//всички четни квадратчета от тези редове се оцветяват в бяло, а нечетните в черно
            } else if (BoardUtils.SEVENTH_RANK[this.tileId]
                    || BoardUtils.FIFTH_RANK[this.tileId]
                    || BoardUtils.THIRD_RANK[this.tileId]
                    || BoardUtils.FIRST_RANK[this.tileId]) {
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);// обратното за останалите редове

            }
        }
    }
}
