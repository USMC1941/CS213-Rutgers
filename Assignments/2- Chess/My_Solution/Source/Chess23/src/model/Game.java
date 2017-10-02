package model;

import controller.ChessController;

/**
 * Model for the game.
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class Game {

    /**
     * White player.
     */
    Player playerWhite;

    /**
     * Black player.
     */
    Player playerBlack;

    /**
     * Controller for chessboard.
     */
    ChessController controller;

    public Game() {
        playerWhite = new Player(true);
        playerBlack = new Player(false);
        controller = new ChessController(playerWhite, playerBlack);
    }

    /**
     * @param parameter Input file.
     */
    public void playGame(String parameter) {
        controller.run(parameter);
    }
}