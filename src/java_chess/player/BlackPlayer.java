/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_chess.player;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java_chess.Color;
import java_chess.board.Board;
import java_chess.board.Move;
import java_chess.board.Move.*;
import java_chess.board.Tile;
import java_chess.pieces.Piece;
import java_chess.pieces.Rook;

/**
 *
 * @author stefk
 */
public class BlackPlayer extends Player {

    public BlackPlayer(final Board board,
            final Collection<Move> whiteStandardLegalMoves,
            final Collection<Move> blackStandardLegalMoves) {
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
    
    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentLegals) {

        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !isInCheck()) {
            //king side castle
            if (!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(7);

                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(5, opponentLegals).isEmpty()
                            && Player.calculateAttacksOnTile(6, opponentLegals).isEmpty()
                            && rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 6, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
                    }

                }
            }

            //queen side castle
            if (!this.board.getTile(1).isTileOccupied()
                    && !this.board.getTile(2).isTileOccupied()
                    && !this.board.getTile(3).isTileOccupied()) {

                final Tile rookTile = this.board.getTile(0);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
                        Player.calculateAttacksOnTile(2, opponentLegals).isEmpty()&&
                        Player.calculateAttacksOnTile(3, opponentLegals).isEmpty()&&
                        rookTile.getPiece().getPieceType().isRook()) {
                    kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 2, (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }
    
}
