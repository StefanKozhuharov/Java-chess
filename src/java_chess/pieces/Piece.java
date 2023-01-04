/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_chess.pieces;

import java.util.Collection;
import java.util.List;
import java_chess.Color;
import java_chess.board.Board;
import java_chess.board.Move;

/**
 *
 * @author stefk
 */
public abstract class Piece {

    protected final int piecePosition;
    protected final Color pieceColor;

    Piece(final int piecePosition, final Color pieceColor) {
        this.pieceColor = pieceColor;
        this.piecePosition = piecePosition;
    }
    
    public Color getPieceColor(){
        return this.pieceColor;
    } 
    
    public abstract Collection<Move> calculateLegalMoves(final Board board);
}