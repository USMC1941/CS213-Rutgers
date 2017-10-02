package controller;

/**
 * Outcome of a move.
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class Outcome {

    /**
     * If move is successful
     */
    private boolean isOK;

    /**
     * Reason for invalid move
     */
    private String 	reason;

    /**
     * If opponent is in check after move
     */
    private boolean isOpponentInCheck	= false;

    /**
     * If opponent is in checkmate after move
     */
    private boolean isOpponentCheckMate	= false;

    /**
     * If opponent is in stalemate after move
     */
    private boolean isOpponentStaleMate	= false;


    /**
     * @param inBool True if move is successful
     * @param inString Reason of invalid move
     */
    public Outcome(boolean inBool, String inString) {
        isOK 	= inBool;
        reason 	= inString;
    }

    /**
     * @return True if move is successful
     */
    public boolean isOK() {
        return isOK;
    }

    /**
     * @return Reason of invalid move
     */
    public String getReason() {
        return reason;
    }

    /**
     * @return True if opponent is in check after move
     */
	public boolean isOpponentInCheck() {
		return isOpponentInCheck;
	}

    /**
     * Set isOpponentInCheck to true.
     */
    public void setOpponentInCheck() {
		this.isOpponentInCheck = true;
	}

    /**
     * @return True if opponent is in checkmate after move
     */
    public boolean isOpponentCheckMate() {
		return isOpponentCheckMate;
	}

    /**
     * Set isOpponentCheckMate to true.
     */
    public void setOpponentCheckMate() {
		this.isOpponentCheckMate = true;
	}

    /**
     * @return True if opponent is in stalemate after move
     */
    public boolean isOpponentStaleMate() {
		return isOpponentStaleMate;
	}

    /**
     * Set isOpponentStaleMate to true.
     */
    public void setOpponentStaleMate() {
		this.isOpponentStaleMate = true;
	}
}