package chess;

import model.Game;

/**
 * Main class of the Application.
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class Chess {

	/**
	 * @param args Filename of batch moves.
	 */
    public static void main(String[] args) {
        Game game = new Game();
        //
        String parameter = null;
        if (args != null && args.length > 0 && args[0] != null && args[0].length() > 0) {
        	parameter = args[0];
        }
        //
        game.playGame(parameter);
	}
}