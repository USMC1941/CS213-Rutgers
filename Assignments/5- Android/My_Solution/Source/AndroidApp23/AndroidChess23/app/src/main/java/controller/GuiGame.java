package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Collection of all GUI instruction for a game. It could be saved to file for later replay
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class GuiGame implements Serializable {
    public List<GuiInstruction> moves;

    //
    public GuiGame() {
        moves = new ArrayList<>();
    }


    public void addOne(GuiInstruction input) {
        moves.add(input);
    }


    public GuiInstruction rollbackOne() {
        if (moves.size()>=1) {
            return moves.remove(moves.size() - 1);
        }
        else {
            return null;
        }
    }

    public GuiInstruction getAt(int index) {
        if (index>=0 && index<=moves.size()-1) {
            return moves.get(index);
        }
        else {
            return null;
        }
    }

    public int getMoveCount() {
        return moves.size();
    }
}
