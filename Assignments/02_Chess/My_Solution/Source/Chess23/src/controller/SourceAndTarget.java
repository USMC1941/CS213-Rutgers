package controller;

import model.BoardIndex;

/**
 * This class represents a source and a target a piece can move.
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class SourceAndTarget {

    /**
     * Source index of the piece
     */
    private BoardIndex sourceIndex;

    /**
     * Target index of the piece
     */
    private BoardIndex targetIndex;

	
	private SourceAndTarget() {
		sourceIndex = null;
		targetIndex	= null;
	}


    /**
     * @param source Piece source
     * @param target Piece target
     */
    public SourceAndTarget(BoardIndex source, BoardIndex target) {
		sourceIndex = new BoardIndex(source);
		targetIndex	= new BoardIndex(target);
	}


    /**
     * @return Source index
     */
    public BoardIndex getSource() {
		return sourceIndex;
	}


    /**
     * @return Target Index
     */
    public BoardIndex getTarget() {
		return targetIndex;
	}
}