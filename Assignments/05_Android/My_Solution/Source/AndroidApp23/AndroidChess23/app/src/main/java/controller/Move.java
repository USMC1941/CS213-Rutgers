package controller;

import java.util.Arrays;
import java.util.List;

import model.BoardIndex;

/**
 * Represents a move by a player.
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class Move {

    /*
        Possible Tokens:
        resign
        draw
        draw?
        N / Q / R / B
        FileRank
        None
    */

    /**
     * All the possible choices a player has when promoting a pawn.
     */
    public enum Promotion {
		NONE,
	    Knight,
	    Queen,
	    Rook,
	    Bishop
	}

    /**
     * Current position of the move.
     */
    private BoardIndex currentBoardIndex = null;

    /**
     * Target position of the move.
     */
    private BoardIndex targetBoardIndex  = null;

    /**
     * If player resigns.
     */
    private boolean isResign             = false;

    /**
     * If player accept draw proposal.
     */
    private boolean isDraw               = false;

    /**
     * If player proposes draw.
     */
    private boolean isAskingDraw         = false;

    /**
     * Artificial Intelligence.
     */
    private boolean isAI               	= false;
    
    /**
     * Rollback.
     */
    private boolean isRollback          = false;
    
    /**
     * If player chooses promotion.
     */
    private Promotion promotion;

    /**
     * If valid move.
     */
    private boolean isValid              = true;

    /**
     * Give reason for invalid move.
     */
    private String reason                = "OK";


    /**
     * @return Current position of the move.
     */
    public BoardIndex getCurrentBoardIndex() {
        return currentBoardIndex;
    }

    /**
     * @return Target position of the move.
     */
    public BoardIndex getTargetBoardIndex() {
        return targetBoardIndex;
    }

    /**
     * @return True if player resigns.
     */
    public boolean isResign() {
        return isResign;
    }

    /**
     * @return True if player accept draw proposal.
     */
    public boolean isDraw() {
        return isDraw;
    }

    /**
     * @return True if player proposes draw.
     */
    public boolean isAskingDraw() {
        return isAskingDraw;
    }

    /**
     * @return True if AI.
     */
    public boolean isAI() {
        return isAI;
    }
    
    /**
     * @return True if Rollback.
     */
    public boolean isRollback() {
        return isRollback;
    }
    
    /**
     * @return Promotion if player chooses promotion.
     */
    public Promotion getPromotion() {
    	return promotion;
    }

    /**
     * @return Promotion if player chooses promotion.
     */
    public void setPromotion(Promotion input) {
        promotion = input;
    }

    /**
     * @return True if valid move.
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * @return Reason of invalid move.
     */
    public String getReason() {
        return reason;
    }


    public Move(int toFile, int toRank, int fromFile, int fromRank, boolean _isAskingDraw) {
        currentBoardIndex   = new BoardIndex(fromFile, fromRank);
        targetBoardIndex    = new BoardIndex(toFile, toRank);
        //
        isResign            = false;
        isDraw              = false;
        isAskingDraw        = _isAskingDraw;
        isAI               	= false;
        isRollback          = false;
        promotion           = Promotion.NONE;
    }


    /**
     * @param input Player input.
     */
    public Move(String input) {
    	promotion = Promotion.NONE;
    	//
        List<String> tokens = Arrays.asList(input.split("\\s+"));       // one or more white space
        //
        String first = tokens.get(0).trim().toUpperCase();
        //
        if (first.equalsIgnoreCase("resign")) {
            isResign = true;
        }
        else if (first.equalsIgnoreCase("draw")) {
            isDraw = true;
        }
        else if (first.equalsIgnoreCase("AI")) {
        	isAI = true;
        }
        else if (first.equalsIgnoreCase("Rollback")) {
        	isRollback = true;
        }
        else if (first.length() == 2) {
            char charFile1 = first.charAt(0);
            char charRank1 = first.charAt(1);
            //
            if (charFile1 >= 'A' && charFile1 <= 'H' && charRank1 >= '1' && charRank1 <= '8') {
                currentBoardIndex = new BoardIndex(charFile1-'A', charRank1 - '1');
            }
            else {
                isValid = false;
                // Invalid 1st file and rank
                reason = "Illegal input, enter again";
            }
            //
            if (tokens.size() >= 2) {
                /*Checks whether 2nd token has FileRank*/
                String second = tokens.get(1).trim().toUpperCase();
                if (second.length() == 2) {
                    char charFile2 = second.charAt(0);
                    char charRank2 = second.charAt(1);
                    //
                    if (charFile2 >= 'A' && charFile2 <= 'H' && charRank2 >= '1' && charRank2 <= '8') {
                        targetBoardIndex = new BoardIndex(charFile2 - 'A', charRank2 - '1');
                    }
                }
                else {
                    isValid = false;
                    // Invalid 2nd token
                    reason = "Illegal input, enter again";
                }
                //
                if (tokens.size() >= 3) {
                    /*Checks if 3rd token is either "draw?", "Q", "N", "R", "B"*/
                    String third = tokens.get(2).trim();
                    if (third.equalsIgnoreCase("draw?")) {
                        isAskingDraw = true;
                    }
                    else if (third.equalsIgnoreCase("Q")) {
                    	promotion = Promotion.Queen;
                    }
                    else if (third.equalsIgnoreCase("N")) {
                    	promotion = Promotion.Knight;
                    }
                    else if (third.equalsIgnoreCase("R")) {
                    	promotion = Promotion.Rook;
                    }
                    else if (third.equalsIgnoreCase("B")) {
                    	promotion = Promotion.Bishop;
                    }
                }
            }
            else {
                isValid = false;
                // Missing 2nd file and rank
                reason = "Illegal input, enter again";
            }
        }
        else {
            isValid = false;
            // Invalid 1st token
            reason = "Illegal input, enter again";
        }
    }


    public void setSourceAndTargetForAIMove(BoardIndex source, BoardIndex target) {
        currentBoardIndex = new BoardIndex(source) ;
        targetBoardIndex  = new BoardIndex(target);
    }
}