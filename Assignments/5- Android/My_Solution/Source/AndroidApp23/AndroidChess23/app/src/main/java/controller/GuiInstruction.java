package controller;


import java.io.Serializable;


/**
 * GUI instruction for a move (including rollback)
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class GuiInstruction implements Serializable {

    public String move;
    public String rollback;

    public boolean isWhite;
    public String message;

    public GuiInstruction(String _move, String _rollback) {
        move            = _move;
        rollback        = _rollback;
        isWhite         = false;
        message         = "UKN";
    }


    public String getMove() {
        return move;
    }

    public String getRollback() {
        return rollback;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public String getMessage() {
        return message;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
