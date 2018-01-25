package model;

import controller.DirectionDistance;
import controller.Move.Promotion;
import controller.Outcome;
import controller.SourceAndTarget;

/**
 * This class represents the Bishop.
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class Bishop extends Piece{

    /**
     * @param isWhite Color of piece
     * @param file File of current location
     * @param rank Rank of current location
     * @param cb Chessboard
     */
    public Bishop(boolean isWhite, int file, int rank, Chessboard cb) {
        super(isWhite, file, rank, cb);
        cb.getPieceMap().put(getKey(), this);
    }


    /**
     * @param isWhite Color of piece
     * @param fileRank Input File and Rank
     * @param cb Chessboard
     */
    public Bishop(boolean isWhite, String fileRank, Chessboard cb) {
        super(isWhite, fileRank, cb);
        cb.getPieceMap().put(getKey(), this);
    }


    /**
     * @param targetIndex Target index a piece moves too.
     * @param promotion Promotion. Only valid for pawn.
     * @return Outcome of the move.
     */
    public Outcome doMove(BoardIndex targetIndex, Promotion promotion) {
        return doMoveInternal(targetIndex, true);
    }


    /**
     * @param targetIndex Target index a piece moves too
     * @return Outcome if the piece can attack target.
     */
    public Outcome doAttack(BoardIndex targetIndex) {
        return doMoveInternal(targetIndex, false);
    }


    /**
     * @param targetIndex Target index a piece moves too.
     * @param doMove True if piece perform move, false checks if piece can attack target.
     * @return Outcome of the move.
     */
    private Outcome doMoveInternal(BoardIndex targetIndex, boolean doMove) {
        Outcome outcome;
        //
        BoardIndex currentIndex = getBoardIndex();
        //
        DirectionDistance dd = new DirectionDistance(currentIndex.fileIndex, currentIndex.rankIndex, targetIndex.fileIndex, targetIndex.rankIndex);
        //
        if (dd.isDiagonal()) {
            if (isTargetEmptyOrLegal(targetIndex)) {
                if (isNoneInBetween(dd)) {
                    if (doMove) {
                    	boolean isLegal = doActualMove(targetIndex, false);
                        if (isLegal) {
                            outcome = new Outcome(true, "OK");
                        }
                        else {
                            // King in check
                            outcome = new Outcome(false, "Illegal move, try again");
                        }
                    }
                    else {
                    	outcome = new Outcome(true, "OK");
                    }
                }
                else {
                    // Piece(s) in between, try again
                    outcome = new Outcome(false, "Illegal move, try again");
                }
            }
            else {
                // Target piece is the same color
                outcome = new Outcome(false, "Illegal move, try again");
            }
        }
        else {
            outcome = new Outcome(false, "Illegal move");
        }
        //
        return outcome;
    }


    /**
     * @return True if Bishop has legal move. For checkmate and stalemate.
     */
    @Override
    public boolean hasLegalMove() {
		return 		hasLegalMoveOneDirection(1, 1)!=null
				|| 	hasLegalMoveOneDirection(1, -1)!=null 
				|| 	hasLegalMoveOneDirection(-1, 1)!=null
				|| 	hasLegalMoveOneDirection(-1, -1)!=null;
    }

    
    @Override
    public SourceAndTarget getOneLegalMove() {
    	SourceAndTarget sat = null;
    	//
    	sat = hasLegalMoveOneDirection(1, 1);	
    	if (sat!=null) {
    		return sat;
    	}
    	//
    	sat = hasLegalMoveOneDirection(1, -1);
    	if (sat!=null) {
    		return sat;
    	}
    	//
    	sat = hasLegalMoveOneDirection(-1, 1);
    	if (sat!=null) {
    		return sat;
    	}
    	//
    	sat = hasLegalMoveOneDirection(-1, -1);
    	if (sat!=null) {
    		return sat;
    	}
    	//
    	return sat;
    }
    
    
    /**
     * @return The board output for Bishop.
     */
    @Override
    public String toString() {
        return super.toString() + "B";
    }
}