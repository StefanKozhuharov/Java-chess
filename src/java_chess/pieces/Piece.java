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

    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Color pieceColor;
    protected final boolean isFirstMove;

    Piece(final PieceType pieceType, final int piecePosition, final Color pieceColor) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        this.piecePosition = piecePosition;
        this.isFirstMove = false;
    }

    public int getPiecePosition() {
        return this.piecePosition;
    }

    public Color getPieceColor() {
        return this.pieceColor;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public enum PieceType {

        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING("K") {
            @Override
            public boolean isKing() {
                return true;
            }
        };

        private String pieceName;

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }

        public abstract boolean isKing();
    }

}
