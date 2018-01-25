package controller;

import model.Chessboard;
import model.Player;
import view.ChessView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The controller of the application.
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class ChessController {

    /**
     * Displays the view.
     */
    private ChessView chessView;

    /**
     * Main model to represent the chessboard.
     */
    private Chessboard chessboard;

    /**
     * List of two players.
     */
    private List<Player> players;

    /**
     * True if white's turn, false if black's turn.
     */
    private boolean isWhitesTurn;

    /**
     * Scanner for standard input.
     */
    private Scanner stdin = null;

    /**
     * Input stream for the file.
     */
    private InputStream inputStream = null;

    /**
     * Scanner for input file.
     */
    private Scanner file = null;

    /**
     * @param playerWhite White Player.
     * @param playerBlack Black Player.
     */
    public ChessController(Player playerWhite, Player playerBlack) {
        chessView = new ChessView();
        chessboard = new Chessboard();
        players = new ArrayList<>();
        players.add(playerWhite);
        players.add(playerBlack);
        isWhitesTurn = true;
    }


    /**
     * @param filename The input file to be processed.
     */
    public void OpenReader(String filename) {
    	if (filename != null) {
	        try {
	            inputStream = new FileInputStream(filename);
                file = new Scanner(inputStream);
            }
            catch (IOException e) {
	            //System.out.println("Could not open batch file. Use stdin.");
	            file = null;
	            //e.printStackTrace();
	        }
    	}
    	//
    	if (file == null) {
        	stdin = new Scanner(System.in);
    	}
    }


    /**
     * @return One line from file or standard input.
     */
    public String readOneLine() {
        return (file != null) ? file.nextLine() : stdin.nextLine();
    }


    /**
     * Closes the file.
     */
    public void CloseReader() {
    	if (stdin != null) {
    		stdin.close();
    	}
    	//
    	if (file !=null) {
    		file.close();
    	}
    	//
    	if (inputStream !=null) {
    		try {
				inputStream.close();
			}
    		catch (IOException e) {
				//e.printStackTrace();
			}
    	}
    }


    /**
     * @param parameter Input file.
     */
    public void run(String parameter) {
        chessView.show(chessboard.getPieceMap());
        System.out.println();
        //
        OpenReader(parameter);
        //
        boolean isCheck = false;
        boolean isDrawProposed = false;
        while (true) {
            String input;
        	if (isBatchMode()) {
                input = readOneLine();
                if (isCheck) {
                    System.out.println("Check");
                    System.out.println();
                }
                System.out.println(getCurrentPlayer().getPrompt() + input);
                System.out.println();
        	}
        	else {
                if (isCheck) {
                    System.out.println("Check");
                    System.out.println();
                }
                System.out.print(getCurrentPlayer().getPrompt());
                input = readOneLine();
                System.out.println();
        	}
            //
            Move move = new Move(input);
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
                		chessView.show(chessboard.getPieceMap());
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
                        switchPlayer();
                        //
                        isDrawProposed = move.isAskingDraw();
                        //
                        isCheck = outcome.isOpponentInCheck();
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
        //
        CloseReader();
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

    /**
     * @return True if read from file, false if not.
     */
    public boolean isBatchMode() {
        return file != null;
    }
}