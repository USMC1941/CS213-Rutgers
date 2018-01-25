package controller;

import model.BoardIndex;
import model.Piece;

/**
 * This class helps with undoing a move.
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class LastMoveForRollback {

    /**
     * Source Index
     */
    public BoardIndex 	sourceIndex;

    /**
     * Target Index
     */
    public BoardIndex 	targetIndex;

    /**
     * Source Index for Rook for EnPassant.
     */
    public BoardIndex 	sourceIndexRook;

    /**
     * Target Index for Rook for EnPassant
     */
    public BoardIndex 	targetIndexRook;

    /**
     * Whether piece has moved
     */
	public boolean		isMove;

    /**
     * Whether piece is removed
     */
    public Piece		removedRegular;

    /**
     * Whether piece for EnPassant is removed
     */
    public Piece		removedEnPassant;

    /**
     * Whether piece for promotion is removed
     */
    public Piece 		removedIsPromotion;

    /**
     * Whether move is performed
     */
    public boolean		isRegularMove;

    /**
     * Whether castling is performed
     */
    public boolean		isCastling;

    /**
     * Whether EnPassant is performed
     */
    public boolean		isEnPassant;

    /**
     * Whether promotion is performed
     */
    public boolean		isPromotion;

    /**
     * Pawn who moved two steps
     */
    public Piece 		lastTwoStepPawnMove				= null;

    /**
     * Pawn who last moved two steps
     */
    public Piece 		lastTwoStepPawnMovePrevious		= null;


	/**
	 * @param isRollback Checks if piece needs rollback
	 */
	public void doInit(boolean isRollback) {
		sourceIndex						= null;
		targetIndex						= null;
		sourceIndexRook					= null;
		targetIndexRook					= null;
		//
		isMove							= false;
		removedRegular					= null;
		removedEnPassant				= null;
		removedIsPromotion = null;
		//
		isRegularMove					= false;
		isCastling						= false;
		isEnPassant						= false;
		isPromotion						= false;
		//
		if (isRollback) {
			lastTwoStepPawnMove			= lastTwoStepPawnMovePrevious;
		    lastTwoStepPawnMovePrevious	= null;
		}
		else {
		    lastTwoStepPawnMovePrevious	= lastTwoStepPawnMove;
			lastTwoStepPawnMove			= null;
		}
	}

	
    /**
     * @return Pawn who moved 2 steps in the last move
     */
    public Piece getLastTwoStepMovePawn() {
    	return lastTwoStepPawnMove;
    }
}