/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_chess.player;

import java.util.Collection;
import java_chess.board.Board;
import java_chess.board.Move;
import java_chess.pieces.King;
import java_chess.pieces.Piece;

/**
 *
 * @author stefk
 */
public abstract class Player {
    
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    
    Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves){
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = legalMoves;
    }
    
    private King establishKing(){
        for(final Piece piece : getActivePieces()){
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        }
        throw new RuntimeException("Shouldn't be possible. This isn't a valid board");
    }
    
    public abstract Collection<Piece> getActivePieces();
}
