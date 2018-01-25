package controller;

import model.BoardIndex;
import model.Piece;



public class LastMoveForRollback {

	public BoardIndex 	sourceIndex;
	public BoardIndex 	targetIndex;
	public BoardIndex 	sourceIndexRook;
	public BoardIndex 	targetIndexRook;
	//
	public boolean		isMove;
	//
	public Piece		removedRegular;
	public Piece		removedEnPassant;
	public Piece		removedisPromotion;
	public String		promotionTo;
	//
	public boolean		isRegularMove;
	public boolean		isCastling;
	public boolean		isEnPassant;
	public boolean		isPromotion;
	//
	public Piece 		lastTwoStepPawnMove				= null;
	public Piece 		lastTwoStepPawnMovePrevious		= null;

	
	public void doInit(boolean isRollback) {
		sourceIndex						= null;
		targetIndex						= null;
		sourceIndexRook					= null;
		targetIndexRook					= null;
		//
		isMove							= false;
		removedRegular					= null;
		removedEnPassant				= null;
		removedisPromotion				= null;
		promotionTo						= null;
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





	public GuiInstruction getGUIInstruction() {
		String move;
		String back;
		//
		if (isRegularMove) {
			move 	= "M" + targetIndex.toString() + sourceIndex.toString();
			//
			back	= "M" + sourceIndex.toString() + targetIndex.toString();
			if (removedRegular!=null) {
				back = back + ";A" + removedRegular.toString() + removedRegular.getBoardIndex().toString();
			}
		}
		else if (isCastling) {
			move 	= "M" + targetIndex.toString() + sourceIndex.toString() + ";" + "M" + targetIndexRook.toString() + sourceIndexRook.toString();
			back	= "M" + sourceIndex.toString() + targetIndex.toString() + ";" + "M" + sourceIndexRook.toString() + targetIndexRook.toString();
		}
		else if (isEnPassant) {
			move 	= "M" + targetIndex.toString() + sourceIndex.toString();
			move 	= move + ";R" + removedEnPassant.getBoardIndex().toString();
			//
			back	= "M" + sourceIndex.toString() + targetIndex.toString();
			back 	= back + ";A" + removedEnPassant.toString() + removedEnPassant.getBoardIndex().toString();
		}
		else if (isPromotion) {
			move 	= "R" + sourceIndex.toString();
			move 	= move + ";A" + promotionTo + targetIndex.toString();
			//
			back	= "A" + removedisPromotion.toString() + sourceIndex.toString();
            if (removedRegular!=null) {
                back = back + ";A" + removedRegular.toString() + removedRegular.getBoardIndex().toString();
            }
            else {
				back = back + ";R" + targetIndex.toString();
			}
		}
		else {
			move = "";
			back = "";
		}
		//
		return new GuiInstruction(move, back);
	}
}



