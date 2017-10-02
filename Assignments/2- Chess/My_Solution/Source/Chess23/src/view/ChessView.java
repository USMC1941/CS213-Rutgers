package view;

import model.BoardIndex;
import model.Piece;

import java.util.Map;

/**
 * View for the application.
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class ChessView {

    /**
     * Delimiter for Chessboard output.
     */
    private final static String DELIMITER = " ";

    /**
     * @param pieceMap Input map of pieces to output.
     */
    public void show(Map<String, Piece> pieceMap) {
        for (int rank = 7; rank >= 0; rank--) {
            for (int file = 0; file < 8; file++) {
                Piece onePiece = pieceMap.get(BoardIndex.getKey(file, rank));
                if (onePiece == null) {
                    int sum = file + rank;
                    System.out.print((sum % 2 == 0) ? ("##" + DELIMITER) : ("  " + DELIMITER));
                }
                else {
                    System.out.print(onePiece + DELIMITER);
                }
            }
            //
            System.out.println(rank + 1);
        }
        //
        System.out.println(" a  b  c  d  e  f  g  h");
    }
}

/*
bR bN bB bQ bK bB bN bR 8
bp bp bp bp bp bp bp bp 7
   ##    ##    ##    ## 6
##    ##    ##    ##    5
   ##    ##    ##    ## 4
##    ##    ##    ##    3
wp wp wp wp wp wp wp wp 2
wR wN wB wQ wK wB wN wR 1
 a  b  c  d  e  f  g  h

White's move: e2 e4

bR bN bB bQ bK bB bN bR 8
bp bp bp bp bp bp bp bp 7
   ##    ##    ##    ## 6
##    ##    ##    ##    5
   ##    ## wp ##    ## 4
##    ##    ##    ##    3
wp wp wp wp    wp wp wp 2
wR wN wB wQ wK wB wN wR 1
 a  b  c  d  e  f  g  h

Black's move: e7 e5

bR bN bB bQ bK bB bN bR 8
bp bp bp bp ## bp bp bp 7
   ##    ##    ##    ## 6
##    ##    bp    ##    5
   ##    ## wp ##    ## 4
##    ##    ##    ##    3
wp wp wp wp    wp wp wp 2
wR wN wB wQ wK wB wN wR 1
 a  b  c  d  e  f  g  h

White's move:
 */