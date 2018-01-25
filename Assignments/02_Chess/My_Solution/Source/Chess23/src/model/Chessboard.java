package model;

import controller.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main class for the Chessboard
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class Chessboard {

    /**
     * Map of Pieces.
     */
    private Map<String, Piece> 	pieceMap;

    /**
     * White King
     */
    private Piece whiteKing;

    /**
     * Black King
     */
    private Piece blackKing;

    
    /**
     * For rollback.
     */
    private LastMoveForRollback lastMoveForRollback;
    
    
    /**
     * @return Map of pieces
     */
    public Map<String, Piece> getPieceMap() {
        return pieceMap;
    }

    /**
     * @return White King
     */
    public Piece getWhiteKing() {
    	return whiteKing;
    }

    /**
     * @return Black King
     */
    public Piece getBlackKing() {
    	return blackKing;
    }

    
    /**
     * @return For rollback
     */
    public LastMoveForRollback getLastMoveForRollback() {
    	return lastMoveForRollback;
    }


    /**
     * Initializes chessboard to starting position
     */
    public Chessboard() {
    	doInitStart();
    }


    /**
     * @param move Move from player input.
     * @param isWhite Checks if piece is white
     * @return Outcome of the move.
     */
    public Outcome doMove(Move move, boolean isWhite) {
    	if (move.isAI()) {
    		SourceAndTarget sat = getOneLegalMove(isWhite);
    		if (sat!=null) {
    		    move.setSourceAndTargetForAIMove(sat.getSource(), sat.getTarget());
        		return _doMove(move, isWhite);
    		}
            return new Outcome(false, "No Legal Move");
        }
        if (move.isRollback()) {
            boolean ret = Piece.doRollback(this);
            if (ret) {
                return new Outcome(true, "Roll backed");
            }
            return new Outcome(false, "Not roll backed");
    	}
        return _doMove(move, isWhite);
    }


    /**
     * @param move Move from player
     * @param isWhite Checks if piece is white
     * @return Outcome of move
     */
    private Outcome _doMove(Move move, boolean isWhite) {
        BoardIndex currentIndex = move.getCurrentBoardIndex();
        BoardIndex targetIndex = move.getTargetBoardIndex();
        //
        Piece sourcePiece = pieceMap.get(currentIndex.getKey());
        //
        if (sourcePiece == null) {
            // No piece in current file and rank
            return new Outcome(false, "Illegal move, try again");
        }
        //
        if (currentIndex.equals(targetIndex)) {
            // Current and Target are the same
            return new Outcome(false, "Illegal move, try again");
        }
        //
        if (sourcePiece.isWhite() == isWhite) {
            Outcome outcome = sourcePiece.doMove(targetIndex, move.getPromotion());
            //
            if (outcome.isOK()) {
                Piece opponentKing = (!isWhite) ? whiteKing : blackKing;
                // If opponent's king in check after the move
                boolean opponentInCheck = isUnderAttack(!isWhite, opponentKing.getBoardIndex());
                //
                boolean opponentHasLegalMove = hasLegalMove(!isWhite);
                //
                if (opponentInCheck) {
                    if (!opponentHasLegalMove) {
                        outcome.setOpponentCheckMate();
                    }
                    else {
                        outcome.setOpponentInCheck();
                    }
                }
                else {
                    // Not in check but no more legal moves
                    if (!opponentHasLegalMove) {
                        outcome.setOpponentStaleMate();
                    }
                }
            }
            //
            return outcome;
        }
        //
        return new Outcome(false, "Illegal move, try again");
    }


    /**
     * @param isWhite True if piece is white
     * @return True if all pieces of 1 color has at least 1 legal move
     */
    public boolean hasLegalMove(boolean isWhite) {
    	List<Piece> list = Helper.filter(pieceMap.values(), isWhite, (p,is_white)->(p.isWhite()==is_white));
    	//
        return Helper.findOne(list, Piece::hasLegalMove);
    }


    /**
     * @param isWhite True if piece is white
     * @return One legal move
     */
    public SourceAndTarget getOneLegalMove(boolean isWhite) {
    	List<Piece> list = Helper.filter(pieceMap.values(), isWhite, (p,is_white)->(p.isWhite()==is_white));
    	//
    	if (list.size()>0) {
        	long lng = System.currentTimeMillis();
        	lng = lng >>> 2;
        	lng = lng % list.size();
        	//
        	int start = (int) lng;
            for (int i=0; i<list.size(); i++) {
            	int j = (i + start) % list.size();
            	SourceAndTarget sat = list.get(j).getOneLegalMove();
                if (sat!=null) {
                    return sat;
                }
            }
            //
            return null;
    	}
        return null;
    }

    
    
    /**
     * @param isWhite True if piece is white
     * @param targetIndex Target index
     * @return True if a piece is under attack
     */
    public boolean isUnderAttack(boolean isWhite, BoardIndex targetIndex) {
    	return Helper.findOne(
    			pieceMap.values(), 
    			targetIndex,
    			isWhite,
                (p, tIndex, is_white) -> p.isWhite() == !is_white && p.doAttack(tIndex).isOK()
    		);
    }

    /**
     * Sets the pieces to the initial position.
     */
    private void doInitStart() {
        pieceMap = new HashMap<>();
        //
    	lastMoveForRollback = new LastMoveForRollback();
        //
        whiteKing = new King(true, 4, 0, this);
        blackKing = new King(false, 4, 7, this);
        //
        // Pawn
        for (int i = 0; i < 8; i++) {
            new Pawn(true, i, 1, this);
            new Pawn(false, i, 6, this);
        }
        //
        // Rook
        new Rook(true, 0, 0, this);
        new Rook(true, 7, 0, this);
        new Rook(false, 0, 7, this);
        new Rook(false, 7, 7, this);
        //
        // Knight
        new Knight(true, 1, 0, this);
        new Knight(true, 6, 0, this);
        new Knight(false, 1, 7, this);
        new Knight(false, 6, 7, this);
        //
        // Bishop
        new Bishop(true, 2, 0, this);
        new Bishop(true, 5, 0, this);
        new Bishop(false, 2, 7, this);
        new Bishop(false, 5, 7, this);
        //
        // Queen
        new Queen(true, 3, 0, this);
        new Queen(false, 3, 7, this);
    }


    /**
     * Stalemate test. Not used.
     */
    private void doInitStaleMate1() {
        whiteKing = new King(true, 5, 4, this);
        blackKing = new King(false, 5, 7, this);
        //
        new Pawn(true, 5, 6, this);
	}

    /**
     * Stalemate test. Not used.
     */
    private void doInitStaleMate2() {
        whiteKing = new King(true, "b5", this);
        blackKing = new King(false, "a8", this);
        //
        new Rook(true, "h8", this);
        new Bishop(false, "b8", this);
	}

    /**
     * Stalemate test. Not used.
     */
    private void doInitStaleMate3() {
        whiteKing = new King(true, "d4", this);
        blackKing = new King(false, "a1", this);
        //
        new Rook(true, "b2", this);
	}


    /**
     * Promotion test. Not used.
     */
    private void doInitPromotion() {
        whiteKing = new King(true, 5, 4, this);
        blackKing = new King(false, 2, 7, this);
        //
        new Pawn(true, 7, 6, this);
        new Queen(false, 6, 7, this);
        new Rook(true, "a7", this);
	}
}