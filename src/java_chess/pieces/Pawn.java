/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_chess.pieces;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java_chess.Color;
import java_chess.board.Board;
import java_chess.board.BoardUtils;
import java_chess.board.Move;
import java_chess.board.Move.*;

/**
 *
 * @author stefk
 */
public class Pawn extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATE = {7, 8, 9, 16};

    public Pawn(final int piecePosition, final Color pieceColor) {
        super(PieceType.PAWN, piecePosition, pieceColor, true);
    }
    
    public Pawn(final int piecePosition, final Color pieceColor, final boolean isFirstMove) {
        //проверяваме дали е първо местене на пешката
        super(PieceType.BISHOP, piecePosition, pieceColor, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        int candidateDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
            candidateDestinationCoordinate = this.piecePosition + (this.pieceColor.getDirection() * currentCandidateOffset);

            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }

            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
            } else if (currentCandidateOffset == 16 && this.isFirstMove()
                    && ((BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceColor().isBlack())
                    || (BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceColor().isWhite()))) {
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceColor.getDirection() * 8);
                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied()
                        && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset == 7
                    && !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isWhite()
                    || (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isBlack())))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceColor != pieceOnCandidate.getPieceColor()) {
                        legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                }
            } else if (currentCandidateOffset == 9
                    && !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isBlack()
                    || (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isWhite())))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceColor != pieceOnCandidate.getPieceColor()) {
                        legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }
    
    @Override
    public Pawn movePiece(final Move move){
        return new Pawn(move.getDestinationCoordinate(),move.getMovedPiece().getPieceColor());
    }

    @Override
    public String toString() {
        return Piece.PieceType.PAWN.toString();
    }

}
