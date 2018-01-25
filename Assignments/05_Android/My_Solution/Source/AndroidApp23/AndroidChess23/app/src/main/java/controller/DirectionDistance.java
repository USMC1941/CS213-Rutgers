package controller;

/**
 * The direction and distance of each move.
 *
 * @author William Chen
 * @author Chijun Sha
*/
public class DirectionDistance {

    /**
     * Represent all the different directions a piece can move.
     */
    public enum Direction {
		NONE,
	    NORTH,
	    SOUTH,
	    WEST,
	    EAST,
	    NW,
	    NE,
	    SW, 
	    SE 
	}


    /**
     * Distance between source and target.
     */
	private int distance;

    /**
     * Direction of the move.
     */
	private Direction direction;

    /**
     * The change in file for each step.
     */
	private int deltaFile;

    /**
     * The change in rank for each step.
     */
	private int deltaRank;

    /**
     * Remember the source file.
     */
	private int m_sourceFile;

    /**
     * Remember the source rank.
     */
	private int m_sourceRank;

    /**
     * Remember the target file.
     */
	private int m_targetFile;

    /**
     * Remember the target rank.
     */
	private int m_targetRank;


    /**
     * @param sourceFile Current file.
     * @param sourceRank Current rank.
     * @param targetFile Target file
     * @param targetRank Target rank
     */
    public DirectionDistance(int sourceFile, int sourceRank, int targetFile, int targetRank) {
		direction = Direction.NONE;
		//
		m_sourceFile 	= sourceFile;
		m_sourceRank	= sourceRank;
		m_targetFile	= targetFile;
		m_targetRank	= targetRank;
		//
        int dFile = targetFile - sourceFile;
        int dRank = targetRank - sourceRank;
        //
        if (dFile == 0) {
            distance = Math.abs(dRank);
            if (dRank > 0) {
            	direction	= Direction.NORTH;
            	deltaFile	= 0;
            	deltaRank	= 1;
            }
            else {
            	direction	= Direction.SOUTH;
            	deltaFile	= 0;
            	deltaRank	= -1;
            }
        }
        else if (dRank == 0) {
        	distance = Math.abs(dFile);
            if (dFile > 0) {
            	direction	= Direction.EAST;
            	deltaFile	= 1;
            	deltaRank	= 0;
            }
            else {
            	direction	= Direction.WEST;
            	deltaFile	= -1;
            	deltaRank	= 0;
            }
        }
        else if (Math.abs(dRank) == Math.abs(dFile)) {
        	distance = Math.abs(dFile);
            if (dFile > 0 && dRank > 0) {
            	direction	= Direction.NE;
            	deltaFile	= 1;
            	deltaRank	= 1;
            }
            else if (dFile > 0 && dRank < 0) {
            	direction	= Direction.SE;
            	deltaFile	= 1;
            	deltaRank	= -1;
            }
            else if (dFile < 0 && dRank > 0) {
            	direction	= Direction.NW;
            	deltaFile	= -1;
            	deltaRank	= 1;
            }
            else {
            	direction	= Direction.SW;
            	deltaFile	= -1;
            	deltaRank	= -1;
            }
        }
	}


    /**
     * @return Distance between source and target.
     */
    public int getDistance() {
		return distance;
	}


    /**
     * @return The change in file for each step
     */
    public int getDeltaFile() {
		return deltaFile;
	}


    /**
     * @return The change in rank for each step
     */
    public int getDeltaRank() {
		return deltaRank;
	}


    /**
     * @return True if move is valid in every direction, false if move is illegal.
     */
    public boolean isValid() {
		return direction != Direction.NONE;
	}


    /**
     * @return True if piece moves along the grid, false otherwise.
     */
    public boolean isParallel() {
		return direction == Direction.NORTH || direction == Direction.SOUTH || direction == Direction.WEST || direction == Direction.EAST;
	}


    /**
     * @return True if piece moves diagonally, false otherwise.
     */
    public boolean isDiagonal() {
		return direction == Direction.NW || direction == Direction.NE || direction == Direction.SW || direction == Direction.SE;
	}


    /**
     * @return True if valid moves are knight, false otherwise.
     */
    public boolean isKnightly() {
		return	(m_targetFile == m_sourceFile + 1 && m_targetRank == m_sourceRank + 2 ) ||
    			(m_targetFile == m_sourceFile + 1 && m_targetRank == m_sourceRank - 2 ) ||
                (m_targetFile == m_sourceFile + 2 && m_targetRank == m_sourceRank + 1 ) ||
                (m_targetFile == m_sourceFile + 2 && m_targetRank == m_sourceRank - 1 ) ||
                (m_targetFile == m_sourceFile - 1 && m_targetRank == m_sourceRank + 2 ) ||
                (m_targetFile == m_sourceFile - 1 && m_targetRank == m_sourceRank - 2 ) ||
                (m_targetFile == m_sourceFile - 2 && m_targetRank == m_sourceRank + 1 ) ||
                (m_targetFile == m_sourceFile - 2 && m_targetRank == m_sourceRank - 1 );
	}


    /**
     * @return True if piece moves in all directions 1 step, false otherwise. (King)
     */
    public boolean isRegal() {
		return isValid() && distance==1;
	}

    /**
     * @param isWhite Color of the piece.
     * @return True if the King's side castling move is valid, false otherwise.
     */
    public boolean isCastlingKS(boolean isWhite) {          //no need to concern about file as the king is not moved yet.
		if (isWhite) {
			return m_sourceRank == 0 && distance == 2 && direction==Direction.EAST;
		}
		return m_sourceRank == 7 && distance == 2 && direction==Direction.EAST;
	}

    /**
     * @param isWhite Color of the piece
     * @return True if the Queen's side castling move is valid, false otherwise.
     */
    public boolean isCastlingQS(boolean isWhite) {
		if (isWhite) {
			return m_sourceRank == 0 && distance == 2 && direction==Direction.WEST;
		}
		return m_sourceRank == 7 && distance == 2 && direction==Direction.WEST;
	}


    /**
     * @param isWhite Color of the piece.
     * @return True if pawn moves one step, false otherwise.
     */
    public boolean isPawOneStep(boolean isWhite) {
		if (isWhite) {
			return direction == Direction.NORTH && distance == 1;
		}
		return direction == Direction.SOUTH && distance == 1;
	}

    /**
     * @param isWhite Color of the piece.
     * @return True if pawn moves 2 steps, false otherwise.
     */
    public boolean isPawnTwoStep(boolean isWhite) {
		if (isWhite) {
			return direction == Direction.NORTH && distance == 2 && m_sourceRank == 1;
		}
		return direction == Direction.SOUTH && distance == 2 && m_sourceRank == 6;
	}

    /**
     * @param isWhite Color of the piece.
     * @return True if En passant is valid, false otherwise.
     */
    public boolean isEnPassant(boolean isWhite) {
		if (isWhite) {
			return m_sourceRank == 4 &&  distance == 1 && (direction == Direction.NW || direction == Direction.NE);
		}
		return m_sourceRank == 3 && distance == 1 && (direction == Direction.SW || direction == Direction.SE);
	}

    /**
     * @param isWhite Color of the piece.
     * @return True if pawn is killed, false otherwise.
     */
    public boolean isPawnKill(boolean isWhite) {
		if (isWhite) {
			return distance == 1 && (direction == Direction.NW || direction == Direction.NE);
		}
		return distance == 1 && (direction == Direction.SW || direction == Direction.SE);
	}

    /**
     * @param isWhite Color of the piece.
     * @return True if pawn is promoted, false otherwise
     */
    public boolean isPawnPromotion(boolean isWhite) {
		return isWhite ? m_targetRank == 7 : m_targetRank == 0;
	}
}