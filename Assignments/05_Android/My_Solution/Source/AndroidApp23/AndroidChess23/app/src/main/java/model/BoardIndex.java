package model;

/**
 * Index of a piece.
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class BoardIndex {

    /**
     * @param fileIndex File
     * @param rankIndex Rank
     * @return Creates key for map of pieces.
     */
    public static String getKey(int fileIndex, int rankIndex) {
        return "" + fileIndex + "-" + rankIndex;
    }

    /**
     * File
     */
    public int fileIndex;           // 1st Index: the columns (called files a to h).

    /**
     * Rank
     */
    public int rankIndex;           // 2nd Index: the rows (called ranks 1 to 8).


    /**
     * @param _fileIndex File
     * @param _rankIndex Rank
     */
    public BoardIndex(int _fileIndex, int _rankIndex) {
        fileIndex = _fileIndex;
        rankIndex = _rankIndex;
    }
    public BoardIndex(BoardIndex input) {
        fileIndex = input.fileIndex;
        rankIndex = input.rankIndex;
    }

    /**
     * @param obj Same position.
     * @return True if in same position, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BoardIndex)) {
            return false;
        }
        //
        return this.fileIndex == ((BoardIndex)obj).fileIndex && this.rankIndex == ((BoardIndex)obj).rankIndex;
    }

    /**
     * @return Key for map of pieces.
     */
    public String getKey() {
        return getKey(fileIndex, rankIndex);
    }


    public String toString() {
        return "" + fileIndex + "" + rankIndex;
    }
}