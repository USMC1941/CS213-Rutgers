package model;

/**
 * Class for players.
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class Player {

    /**
     * Color of the player.
     */
    private boolean isWhitePlayer;

    /**
     * @param _isWhitePlayer Player
     */
    public Player(boolean _isWhitePlayer) {
        isWhitePlayer = _isWhitePlayer;
    }

    /**
     * @return Prompt for player to enter moves.
     */
    public String getPrompt() {
        return (isWhitePlayer ? "White" : "Black") + "'s move: ";
    }

    /**
     * @return True if it is a White player, false if is a Black player.
     */
    public boolean isWhitePlayer() {
        return isWhitePlayer;
    }
}