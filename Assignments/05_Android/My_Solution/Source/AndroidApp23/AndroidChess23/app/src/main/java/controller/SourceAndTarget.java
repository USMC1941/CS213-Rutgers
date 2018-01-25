package controller;

import model.BoardIndex;



public class SourceAndTarget {
	BoardIndex sourceIndex;
	BoardIndex targetIndex;

	
	private SourceAndTarget() {
		sourceIndex = null;
		targetIndex	= null;
	}
	
	
	public SourceAndTarget(BoardIndex source, BoardIndex target) {
		sourceIndex = new BoardIndex(source);
		targetIndex	= new BoardIndex(target);
	}
	

	public BoardIndex getSource() {
		return sourceIndex;
	}

	
	public BoardIndex getTarget() {
		return targetIndex;
	}
}
