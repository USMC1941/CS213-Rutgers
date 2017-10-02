package controller;

import java.util.ArrayList;
import java.util.List;

import model.Chessboard;
import model.Player;

/**
 * The controller of the application.
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class AndroidChessController {

    public Chessboard chessboard;
    public List<Player> players;
    public GuiGame  guiGame;
    public boolean isWhitesTurn;
    public boolean isDrawProposed;
    public boolean isCheck;
    public String szResult;                             //indicate the game ended and its result


    public AndroidChessController() {
        chessboard = new Chessboard();
        players = new ArrayList<>();
        players.add(new Player(true));
        players.add(new Player(false));
        //
        guiGame = new GuiGame();
        //
        isWhitesTurn    = true;
        isDrawProposed  = false;
        isCheck         = false;
        //
        szResult        = null;
    }


    public Outcome doMove(Move move) {
        Outcome outcome;
        if (move.isValid()) {
            if (move.isDraw()) {
                if (isDrawProposed) {
                    szResult = "Draw";
                    outcome = new Outcome(true, szResult);
                    //
                    GuiInstruction guiInst = new GuiInstruction("NOMOVE", "NOMOVE");
                    guiInst.setMessage(szResult);
                    guiGame.addOne(guiInst);
                    //
                    outcome.setReason(szResult);
                }
                else {
                    outcome = new Outcome(false, "Illegal move, try again");
                }
            }
            else if (move.isResign()) {
                szResult = !getCurrentPlayer().isWhitePlayer() ? "White wins" : "Black wins";
                outcome = new Outcome(true, szResult);
                //
                GuiInstruction guiInst = new GuiInstruction("NOMOVE", "NOMOVE");
                guiInst.setMessage(szResult);
                guiGame.addOne(guiInst);
                //
                outcome.setReason(szResult);
            }
            else {
                outcome = chessboard.doMove(move, getCurrentPlayer().isWhitePlayer());
                //
                if (outcome.isOK()) {
                    if (move.isRollback()) {
                        GuiInstruction gi = guiGame.rollbackOne();
                        //
                        outcome.setGuiInstruction(gi.getRollback());
                        //
                        switchPlayer();
                    }
                    else {
                        LastMoveForRollback lastMove = chessboard.getLastMoveForRollback();
                        //
                        GuiInstruction gi = lastMove.getGUIInstruction();
                        gi.setWhite(getCurrentPlayer().isWhitePlayer());
                        //
                        guiGame.addOne(gi);
                        //
                        outcome.setGuiInstruction(gi.getMove());
                        //
                        if (outcome.isOpponentCheckMate()) {
                            szResult = getCurrentPlayer().isWhitePlayer() ? "White wins" : "Black wins";
                            outcome.setReason(szResult);
                        }
                        if (outcome.isOpponentStaleMate()) {
                            szResult = "Draw";
                            outcome.setReason(szResult);
                        }
                        else {
                            switchPlayer();
                            //
                            isDrawProposed = move.isAskingDraw();
                            outcome.setProposingDraw(isDrawProposed);
                            //
                            isCheck = outcome.isOpponentInCheck();
                            if (isCheck) {
                                outcome.setReason("Check");
                            }
                        }
                        //
                        gi.setMessage(outcome.getReason());
                    }
                }
                else {
                    outcome = new Outcome(false, "Illegal move, try again");
                }
            }
        }
        else {
            outcome = new Outcome(false, "Illegal move, try again");
        }
        //
        return outcome;
    }



    /**
     * Checks for whether it is white or black's turn.
     */
    public void switchPlayer() {
        isWhitesTurn = !isWhitesTurn;
    }

    /**
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return players.get(isWhitesTurn ? 0 : 1);
    }



    public boolean gameEnded() {
        return szResult!=null;
    }















    /**
     * @param parameter Input file.
     */
    public void run(String parameter) {
        boolean isCheck = false;
        boolean isDrawProposed = false;
        while (true) {
            String input;
            //
        	{
                if (isCheck) {
                    System.out.println("Check");
                    System.out.println();
                }
                System.out.print(getCurrentPlayer().getPrompt());
                System.out.println();
        	}
            //
            Move move = new Move("");
            if (move.isValid()) {
            	if (move.isDraw()) {
            		if (isDrawProposed) {
            			System.out.println("Draw");
            			break;
            		}
                    System.out.println("Illegal move, try again");
                    System.out.println();
                }
            	else if (move.isResign()) {
                    System.out.println(!getCurrentPlayer().isWhitePlayer() ? "White wins" : "Black wins");
                    break;
            	}
            	else {
                    Outcome outcome = chessboard.doMove(move, getCurrentPlayer().isWhitePlayer());
                    //
                    if (outcome.isOK()) {
                        System.out.println();
                		//
                    	if (outcome.isOpponentCheckMate()) {
                            System.out.println("Checkmate");
                            System.out.println();
                            System.out.println(getCurrentPlayer().isWhitePlayer() ? "White wins" : "Black wins");
                            break;
                    	}
                        if (outcome.isOpponentStaleMate()) {
                    	    System.out.println("Stalemate");
                    	    System.out.println();
                            System.out.println("Draw");
                            break;
                        }
                        else {
                            switchPlayer();
                            //
                            isDrawProposed = move.isAskingDraw();
                            //
                            isCheck = outcome.isOpponentInCheck();
                        }
                    }
                    else {
                        System.out.println("Illegal move, try again");
                        System.out.println();
                    }
            	}
            }
            else {
                System.out.println("Illegal move, try again");
                System.out.println();
            }
        }
    }
}