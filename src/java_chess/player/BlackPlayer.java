/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_chess.player;

import java.util.Collection;
import java_chess.Color;
import java_chess.board.Board;
import java_chess.board.Move;
import java_chess.pieces.Piece;

/**
 *
 * @author stefk
 */
public class BlackPlayer extends Player {

    public BlackPlayer(Board board,
            Collection<Move> whiteStandardLegalMoves,
            Collection<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }
    
    @Override
    public Color getColor() {
        return Color.BLACK;
    }
    
    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }
}
