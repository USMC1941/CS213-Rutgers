package model;

import controller.DirectionDistance;
import controller.Move.Promotion;
import controller.Outcome;
import controller.SourceAndTarget;

import java.util.Map;

/**
 * This class represents the King.
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class King extends Piece {

	/**
	 * @param isWhite Color of piece.
	 * @param file File of current location
	 * @param rank Rank of current location
	 * @param cb Chessboard
	 */
	public King(boolean isWhite, int file, int rank, Chessboard cb) {
        super(isWhite, file, rank, cb);
        cb.getPieceMap().put(getKey(), this);
    }

	/**
	 * @param isWhite Color of piece
	 * @param fileRank Input File and Rank
	 * @param cb Chessboard
	 */
	public King(boolean isWhite, String fileRank, Chessboard cb) {
        super(isWhite, fileRank, cb);
        cb.getPieceMap().put(getKey(), this);
    }

	/**
	 * @param targetIndex Target index a piece moves too
	 * @param promotion Promotion. Only valid for pawns
	 * @return Outcome of move
	 */
	@Override
    public Outcome doMove(BoardIndex targetIndex, Promotion promotion) {
        return doMoveInternal(targetIndex, true);
    }

	/**
	 * @param targetIndex Target index a piece moves too
	 * @return Outcome if piece can attack target
	 */
	public Outcome doAttack(BoardIndex targetIndex) {
        return doMoveInternal(targetIndex, false);
    }

	/**
	 * @param targetIndex Target index a piece moves too
	 * @param doMove True if piece perform move, false checks if piece can attack target
	 * @return Outcome of the move
	 */
	private Outcome doMoveInternal(BoardIndex targetIndex, boolean doMove) {
        Outcome outcome;
        //
        Map<String, Piece> pieceMap = chessBoard.getPieceMap();
        //
        BoardIndex currentIndex = getBoardIndex();
        //
        DirectionDistance dd = new DirectionDistance(currentIndex.fileIndex, currentIndex.rankIndex, targetIndex.fileIndex, targetIndex.rankIndex);
        //
        if (dd.isRegal()) {
       		if (isTargetEmptyOrLegal(targetIndex)) {
       			if (doMove) {
	       			boolean isLegal = doActualMove(targetIndex, false);
	       			if (isLegal) {
	           			outcome = new Outcome(true, "OK");
	       			}
	       			else {
	       			    // King not safe
	           			outcome = new Outcome(false, "Illegal move, try again");
	       			}
       			}
       			else {
           			outcome = new Outcome(true, "OK");
       			}
       		}
       		else {
       		    // Target illegal
       			outcome = new Outcome(false, "Illegal move, try again");
       		}
        }
        /*
            Castling is not permitted if:
            - The king has been moved earlier in the game.
            - The rook that castles has been moved earlier in the game.
            - There are pieces standing between your king and rook.
            - The king is in check.
            - The king moves through a square that is attacked by a piece of the opponent.
            - The king would be in check after castling.
        */
        else if (dd.isCastlingQS(isWhite()) || dd.isCastlingKS(isWhite())) {
            BoardIndex rookSourceIndex;
       		BoardIndex rookTargetIndex;
       		//
       		if (isWhite()) {
       			if (dd.isCastlingQS(isWhite())) {
       				rookSourceIndex = new BoardIndex(0, 0);
       	       		rookTargetIndex = new BoardIndex(3, 0);
       			}
       			else {
       				rookSourceIndex = new BoardIndex(7, 0);
       	       		rookTargetIndex = new BoardIndex(5, 0);
       			}
       		}
       		else if (dd.isCastlingQS(isWhite())) {
				rookSourceIndex = new BoardIndex(0, 7);
				rookTargetIndex = new BoardIndex(3, 7);
			}
			else {
				rookSourceIndex = new BoardIndex(7, 7);
				rookTargetIndex = new BoardIndex(5, 7);
			}
       		//
       		Piece rook = pieceMap.get(rookSourceIndex.getKey());
       		//
       		if (!isMoved() && (rook!=null) && !rook.isMoved()) {
	        	DirectionDistance ddCastling = new DirectionDistance(currentIndex.fileIndex, currentIndex.rankIndex, rookSourceIndex.fileIndex, rookSourceIndex.rankIndex);
	        	if (isNoneInBetween(ddCastling)) {
	                boolean isUnderAttack1 = chessBoard.isUnderAttack(isWhite(), currentIndex);
	                boolean isUnderAttack2 = chessBoard.isUnderAttack(isWhite(), targetIndex);
	                boolean isUnderAttack3 = chessBoard.isUnderAttack(isWhite(), new BoardIndex((currentIndex.fileIndex+targetIndex.fileIndex)/2, currentIndex.rankIndex));
	        		if (!isUnderAttack1 && !isUnderAttack2 && !isUnderAttack3) {
	        			if (doMove) {
		           			boolean isLegal = doActualMoveCastling(targetIndex, rookSourceIndex, rookTargetIndex);
		           			//
		           			if (isLegal) {
		               			outcome = new Outcome(true, "OK");
		           			}
		           			else {
		           			    // King not safe
		               			outcome = new Outcome(false, "Illegal move, try again");
		           			}
	        			}
	        			else {
	               			outcome = new Outcome(true, "OK");
	        			}
	        		}
	        		else {
	        		    // Under attack
	           			outcome = new Outcome(false, "Illegal move, try again");
	        		}
	        	}
	        	else {
	        	    // Pieces between king and rook
	       			outcome = new Outcome(false, "Illegal move, try again");
	        	}
       		}
       		else {
       		    // King or rook moved before
       			outcome = new Outcome(false, "Illegal move, try again");
       		}
        }
        else {
        	return new Outcome(false, "Illegal move, try again");
        }
    	//
        return outcome;
    }

	/**
	 * @return True if piece has legal move for checkmate and stalemate, false otherwise.
	 */
	@Override
	public boolean hasLegalMove() {
		// No need to check castling, since if castling is legal, move one step toward rook should be legal too.
		return hasLegalMoveOneDirectionOneStep(0, 1)!=null
                || hasLegalMoveOneDirectionOneStep(0, -1)!=null
                || hasLegalMoveOneDirectionOneStep(1, 0)!=null
                || hasLegalMoveOneDirectionOneStep(-1, 0)!=null
                || hasLegalMoveOneDirectionOneStep(1, 1)!=null
                || hasLegalMoveOneDirectionOneStep(1, -1)!=null
                || hasLegalMoveOneDirectionOneStep(-1, 1)!=null
                || hasLegalMoveOneDirectionOneStep(-1, -1)!=null;
	}
	
	
    @Override
    public SourceAndTarget getOneLegalMove() {
    	SourceAndTarget sat = null;
    	//
    	sat = hasLegalMoveOneDirectionOneStep(0, 1);
    	if (sat!=null) {
    		return sat;
    	}
    	//
    	sat = hasLegalMoveOneDirectionOneStep(0, -1);
    	if (sat!=null) {
    		return sat;
    	}
    	//
    	sat = hasLegalMoveOneDirectionOneStep(1, 0);
    	if (sat!=null) {
    		return sat;
    	}
    	//
    	sat = hasLegalMoveOneDirectionOneStep(-1, 0);
    	if (sat!=null) {
    		return sat;
    	}
    	//
    	sat = hasLegalMoveOneDirectionOneStep(1, 1);
    	if (sat!=null) {
    		return sat;
    	}
    	//
    	sat = hasLegalMoveOneDirectionOneStep(1, -1);
    	if (sat!=null) {
    		return sat;
    	}
    	//
    	sat = hasLegalMoveOneDirectionOneStep(-1, 1);
    	if (sat!=null) {
    		return sat;
    	}
    	//
    	sat = hasLegalMoveOneDirectionOneStep(-1, -1);
    	if (sat!=null) {
    		return sat;
    	}
    	//
    	return sat;
    }
	
    
    /**
     * @return The board output for the King.
     */
    @Override
    public String toString() {
        return super.toString() + "K";
    }
}