package model;

import java.util.Map;

import controller.DirectionDistance;
import controller.LastMoveForRollback;
import controller.Move.Promotion;
import controller.Outcome;
import controller.SourceAndTarget;

/**
 * Parent Class of all the pieces.
 *
 * @author William Chen
 * @author Chijun Sha
 */
public abstract class Piece {

    /**
     * Position of a piece.
     */
    private BoardIndex boardIndex = null;

    /**
     * Color of a piece.
     */
    private boolean isWhite = false;

    /**
     * Checks if piece is previously moved. Needed for castling.
     */
    private boolean isMoved = false;

    /**
     * Chessboard the piece belongs too.
     */
    protected Chessboard chessBoard = null;


    /**
     * @param _isWhite True if piece is white
     * @param file File
     * @param rank Rank
     * @param cb Chessboard
     */
    protected Piece(boolean _isWhite, int file, int rank, Chessboard cb) {
        boardIndex = new BoardIndex(file, rank);
	    isWhite = _isWhite;
	    chessBoard = cb;
    }

    /**
     * @param _isWhite True if piece is white
     * @param fileRank Input File and Rank
     * @param cb Chessboard
     */
    protected Piece(boolean _isWhite, String fileRank, Chessboard cb) {
        char charFile = fileRank.toUpperCase().trim().charAt(0);
        char charRank = fileRank.toUpperCase().trim().charAt(1);
        //
        boardIndex = new BoardIndex(charFile-'A', charRank - '1');
	    isWhite = _isWhite;
	    chessBoard = cb;
    }


    /**
     * @return True if piece is previously moved.
     */
    public boolean isMoved() {
        return isMoved;
    }

    
    /**
     * @return True if piece is white.
     */
    public boolean isWhite() {
        return isWhite;
    }


    /**
     * @return Position of a piece.
     */
    public BoardIndex getBoardIndex() {
        return boardIndex;
    }


    /**
     * @param input Position of a piece
     */
    protected void setBoardIndex(BoardIndex input) {
        boardIndex = input;
    }


    /**
     * @param targetIndex Target position of a move
     * @param promotion Promotion. Pawn only.
     * @return Outcome of the move
     */
    abstract public Outcome doMove(BoardIndex targetIndex, Promotion promotion);


    /**
     * @param targetIndex Target position of a move
     * @return If this piece can attack target.
     */
    abstract public Outcome doAttack(BoardIndex targetIndex);


    /**
     * @return True if piece has legal move
     */
    abstract public boolean hasLegalMove();

    /**
     * @return True if piece has legal move
     */
    abstract public SourceAndTarget getOneLegalMove();

    /**
     * @param targetIndex Target position of a move
     * @param rollback If true, rollback after checking move. Used for checking checkmate and stalemate.
     * @return True if piece can move. False if move is illegal as King is in check after move.
     */
    protected boolean doActualMove(BoardIndex targetIndex, boolean rollback) {
        Map<String, Piece> pieceMap = chessBoard.getPieceMap();
    	//
        BoardIndex sourceIndex = getBoardIndex();
    	Piece target = pieceMap.get(targetIndex.getKey());
    	//
        pieceMap.remove(getKey());
        setBoardIndex(targetIndex);
        pieceMap.put(getKey(), this);
        //
        Piece king =  isWhite() ? chessBoard.getWhiteKing() : chessBoard.getBlackKing();
        boolean isKingUnderAttack = chessBoard.isUnderAttack(isWhite(), king.getBoardIndex());
        //
        if (isKingUnderAttack || rollback) {
            pieceMap.remove(getKey());
            setBoardIndex(sourceIndex);
            pieceMap.put(getKey(), this);
            //
            if (target!=null) {
                pieceMap.put(target.getKey(), target);
            }
        }
        else {
            LastMoveForRollback lastMove = chessBoard.getLastMoveForRollback();
            lastMove.doInit(false);
            //
            if (this instanceof Pawn) {
            	int dFile = targetIndex.fileIndex - sourceIndex.fileIndex;
            	int dRank = targetIndex.rankIndex - sourceIndex.rankIndex;
            	if ((dFile == 0) && Math.abs(dRank) == 2) {
            		lastMove.lastTwoStepPawnMove = this;
            	}
                else {
                	lastMove.lastTwoStepPawnMove = null;
                }
            }
            else {
            	lastMove.lastTwoStepPawnMove = null;
            }
        	//
            lastMove.isRegularMove = true;
            //
            lastMove.sourceIndex = new BoardIndex(sourceIndex);
            lastMove.targetIndex = new BoardIndex(targetIndex);
            //
            lastMove.isMove			= isMoved;						//before value
            lastMove.removedRegular = target;
            //
            if (!isMoved) {
            	isMoved = true;
            }
        }
        //
        return !isKingUnderAttack;
    }


    /**
     * @param targetIndex Target position of a move
     * @param rookSourceIndex Current position of rook
     * @param rookTargetIndex Target position of rook
     * @return True if King and Rook can castle. Always return true as King will not be under attack when castling begins.
     */
    protected boolean doActualMoveCastling(BoardIndex targetIndex, BoardIndex rookSourceIndex, BoardIndex rookTargetIndex) {
        Map<String, Piece> pieceMap = chessBoard.getPieceMap();
        //
        BoardIndex sourceIndex = getBoardIndex();
        //
        pieceMap.remove(getKey());
        setBoardIndex(targetIndex);
        pieceMap.put(getKey(), this);
        //
        Piece rook = pieceMap.remove(rookSourceIndex.getKey());
        rook.setBoardIndex(rookTargetIndex);
        pieceMap.put(rook.getKey(), rook);
        //
        {	LastMoveForRollback lastMove = chessBoard.getLastMoveForRollback();
            lastMove.doInit(false);
            //
            lastMove.isCastling = true;
            //
            lastMove.sourceIndex 		= new BoardIndex(sourceIndex);
            lastMove.targetIndex 		= new BoardIndex(targetIndex);
            lastMove.sourceIndexRook 	= new BoardIndex(rookSourceIndex);
            lastMove.targetIndexRook	= new BoardIndex(rookTargetIndex);
        }
        //
        return true;
    }


    /**
     * @param targetIndex Target position of a move
     * @param enPassantIndex Index of pawn to be removed after en passant.
     * @param rollback True if rollback is needed
     * @return True if pawn can en passant
     */
    protected boolean doActualMoveIsEnPassant(BoardIndex targetIndex, BoardIndex enPassantIndex, boolean rollback) {
        Map<String, Piece> pieceMap = chessBoard.getPieceMap();
    	//
        BoardIndex sourceIndex = getBoardIndex();
        //
        pieceMap.remove(getKey());
        setBoardIndex(targetIndex);
        pieceMap.put(getKey(), this);
        //
        Piece killedPaw = pieceMap.get(enPassantIndex.getKey());
        pieceMap.remove(enPassantIndex.getKey());
        //
        Piece king = isWhite() ? chessBoard.getWhiteKing() : chessBoard.getBlackKing();
        boolean isKingUnderAttack = chessBoard.isUnderAttack(isWhite(), king.getBoardIndex());
        //
        if (isKingUnderAttack || rollback) {
            pieceMap.remove(getKey());
            setBoardIndex(sourceIndex);
            pieceMap.put(getKey(), this);
            //
            pieceMap.put(killedPaw.getKey(), killedPaw);
        }
        else {
            LastMoveForRollback lastMove = chessBoard.getLastMoveForRollback();
            lastMove.doInit(false);
            //
            lastMove.isEnPassant = true;
            //
            lastMove.sourceIndex = new BoardIndex(sourceIndex);
            lastMove.targetIndex = new BoardIndex(targetIndex);
            //
            lastMove.isMove				= isMoved;						//before value
            lastMove.removedEnPassant 	= killedPaw;
        	//
        	if (!isMoved) {
        		isMoved = true;
        	}
        }
        //
        return !isKingUnderAttack;
    }


    /**
     * @param targetIndex Target position of a move
     * @param promotion Promotion
     * @param rollback True if rollback is needed
     * @return True if promotion is successful
     */
    protected boolean doActualMoveIsPromotion(BoardIndex targetIndex, Promotion promotion/*Piece promPiece*/, boolean rollback) {
        Map<String, Piece> pieceMap = chessBoard.getPieceMap();
    	//
        BoardIndex sourceIndex = getBoardIndex();
    	//
        Piece target = pieceMap.get(targetIndex.getKey());
    	//
        pieceMap.remove(getKey());
        //
        Piece promPiece;
        if (promotion == Promotion.Bishop) {
            promPiece = new Bishop(isWhite(), targetIndex.fileIndex, targetIndex.rankIndex, chessBoard);
        }
        else if (promotion == Promotion.Rook) {
            promPiece = new Rook(isWhite(), targetIndex.fileIndex, targetIndex.rankIndex, chessBoard);
        }
        else if (promotion == Promotion.Knight) {
            promPiece = new Knight(isWhite(), targetIndex.fileIndex, targetIndex.rankIndex, chessBoard);
        }
        else {
            promPiece = new Queen(isWhite(), targetIndex.fileIndex, targetIndex.rankIndex, chessBoard);
        }
        //pieceMap.put(promPiece.getKey(), promPiece);
        //
        Piece king = isWhite() ? chessBoard.getWhiteKing() : chessBoard.getBlackKing();
        boolean isKingUnderAttack = chessBoard.isUnderAttack(isWhite(), king.getBoardIndex());
        //
        if (isKingUnderAttack || rollback) {
            pieceMap.put(getKey(), this);
            //
            if (target!=null) {
                pieceMap.put(target.getKey(), target);
            }
            else {
            	pieceMap.remove(targetIndex.getKey());
            }
        }
        else {
            LastMoveForRollback lastMove = chessBoard.getLastMoveForRollback();
            lastMove.doInit(false);
            //
            lastMove.isPromotion = true;
            //
            lastMove.sourceIndex = new BoardIndex(sourceIndex);
            lastMove.targetIndex = new BoardIndex(targetIndex);
            //
            lastMove.removedRegular 		= target;
            lastMove.removedisPromotion 	= this;
            lastMove.promotionTo            = promPiece.toString();
        	//
        	if (!isMoved) {
        		isMoved = true;
        	}
        }
        //
        return !isKingUnderAttack;
    }

    
    
    
    public static boolean doRollback(Chessboard chessBoard) {
    	LastMoveForRollback lastMove = chessBoard.getLastMoveForRollback();
        Map<String, Piece> pieceMap = chessBoard.getPieceMap();
    	//
        if (lastMove.isRegularMove) {
            Piece target = pieceMap.get(lastMove.targetIndex.getKey());
            //
            pieceMap.remove(target.getKey());
            target.setBoardIndex(lastMove.sourceIndex);
            pieceMap.put(target.getKey(), target);
            target.isMoved = lastMove.isMove;
            //
            if (lastMove.removedRegular!=null) {
               	pieceMap.put(lastMove.removedRegular.getKey(), lastMove.removedRegular);
            }
            //
            lastMove.doInit(true);
        	//
        	return true;
        }
        else if (lastMove.isCastling) {
            Piece target 		= pieceMap.get(lastMove.targetIndex.getKey());
            //
            pieceMap.remove(target.getKey());
            target.setBoardIndex(lastMove.sourceIndex);
            pieceMap.put(target.getKey(), target);
            //
            //
            Piece targetRook 	= pieceMap.get(lastMove.targetIndexRook.getKey());
        	//
            pieceMap.remove(targetRook.getKey());
            targetRook.setBoardIndex(lastMove.sourceIndexRook);
            pieceMap.put(targetRook.getKey(), targetRook);
            //
            lastMove.doInit(true);
            //
        	return true;
        }
        else if (lastMove.isEnPassant) {
            Piece target = pieceMap.get(lastMove.targetIndex.getKey());
            //
            pieceMap.remove(target.getKey());
            target.setBoardIndex(lastMove.sourceIndex);
            pieceMap.put(target.getKey(), target);
            target.isMoved = lastMove.isMove;
            //
           	pieceMap.put(lastMove.removedEnPassant.getKey(), lastMove.removedEnPassant);
            //
            lastMove.doInit(true);
        	//
        	return true;
        }
        else if (lastMove.isPromotion) {
            Piece target 		= pieceMap.get(lastMove.targetIndex.getKey());
            pieceMap.remove(target.getKey());
            //
           	pieceMap.put(lastMove.removedisPromotion.getKey(), lastMove.removedisPromotion);
           	//
            if (lastMove.removedRegular!=null) {
               	pieceMap.put(lastMove.removedRegular.getKey(), lastMove.removedRegular);
            }
            //
            lastMove.doInit(true);
        	//
        	return true;
        }
        else {
        	return false;
        }
    }
    
    
    
    /**
     * @return Board output for white and black pieces.
     */
    @Override
    public String toString() {
        return isWhite ? "W" : "B";
    }

    /**
     * @return Key for the map of pieces.
     */
    public String getKey() {
        return "" + boardIndex.fileIndex + "-" + boardIndex.rankIndex;
    }

    /**
     * @param targetIndex Target position of a move
     * @return True if target is empty or an opponent piece
     */
    public boolean isTargetEmptyOrLegal(BoardIndex targetIndex) {
        Piece targetPiece = chessBoard.getPieceMap().get(targetIndex.getKey());
        //
        // Target square has no piece or has piece of opposite color
        return (targetPiece == null) || (isWhite()==!targetPiece.isWhite());
    }


    /**
     * @param targetIndex Target position of a move
     * @return True if target is empty
     */
    public boolean isTargetEmpty(BoardIndex targetIndex) {
        Piece targetPiece = chessBoard.getPieceMap().get(targetIndex.getKey());
        //
        // Target square has no piece
        return targetPiece == null;
    }

    /**
     * @param targetIndex Target position of a move
     * @return True if target is an opponent piece
     */
    public boolean isTargetLegal(BoardIndex targetIndex) {
        Piece targetPiece = chessBoard.getPieceMap().get(targetIndex.getKey());
        //
        // Target square has no piece or has piece of opposite color
        return (targetPiece != null) && (isWhite()==!targetPiece.isWhite());
    }


    /**
     * @param targetIndex Target position of a move
     * @return True if en passant target is present
     */
    public boolean isTargetLegalEnPassant(BoardIndex targetIndex) {
        LastMoveForRollback lastMove = chessBoard.getLastMoveForRollback();
        //
        Piece targetPiece = chessBoard.getPieceMap().get(targetIndex.getKey());
        //
        Piece LastTwoStepMovePaw = lastMove.getLastTwoStepMovePawn();
        //
        return (targetPiece != null) && targetPiece==LastTwoStepMovePaw;
    }


    /**
     * @param dd Direction and Distance
     * @return True if no pieces are between this piece and target
     */
    protected boolean isNoneInBetween(DirectionDistance dd) {
        Map<String, Piece> pieceMap = chessBoard.getPieceMap();
    	boolean out = true;
    	//
        for (int i = 1; i < dd.getDistance(); i++) {
        	int deltaFile = dd.getDeltaFile();
        	int deltaRank = dd.getDeltaRank();
            BoardIndex oneIndex = new BoardIndex(boardIndex.fileIndex + i * deltaFile, boardIndex.rankIndex + i * deltaRank);
            //
            Piece onePiece = pieceMap.get(oneIndex.getKey());
            if (onePiece!=null) {
            	out = false;
                break;
            }
        }
    	//
    	return out;
    }

    /**
     * @param deltaFile Change in file for each step
     * @param deltaRank Change in rank for each step
     * @return True if there is at least 1 legal move along 1 direction
     */
    protected SourceAndTarget hasLegalMoveOneDirection(int deltaFile, int deltaRank) {
    	BoardIndex sourceIndex = getBoardIndex();
    	//
        Map<String, Piece> pieceMap = chessBoard.getPieceMap();
        SourceAndTarget legalMove = null;
		//
    	for (int file = sourceIndex.fileIndex + deltaFile, rank = sourceIndex.rankIndex + deltaRank; (file >= 0 && file < 8 && rank >= 0 && rank < 8); file = file + deltaFile, rank = rank + deltaRank) {
    		BoardIndex targetIndex = new BoardIndex(file, rank);
    		//
            boolean isLegalMove;
            Piece targetPiece = pieceMap.get(targetIndex.getKey());
            if (targetPiece != null && targetPiece.isWhite() == isWhite()) {			//piece same color
                break;
            }
            else if ((targetPiece != null && targetPiece.isWhite() != isWhite())) {		//piece different color
                isLegalMove = doActualMove(targetIndex, true);
                if (!isLegalMove) {
                    break;
                }
            }
            else {
                isLegalMove = doActualMove(targetIndex, true);
            }
            //
            if (isLegalMove) {
            	legalMove = new SourceAndTarget(sourceIndex, targetIndex);
                break;
            }
        }
    	//
    	return legalMove;
    }


    /**
     * @param deltaFile Change in file for each step
     * @param deltaRank Change in rank for each step
     * @return True if is legal for 1 step in 1 direction
     */
    protected SourceAndTarget hasLegalMoveOneDirectionOneStep(int deltaFile, int deltaRank) {
    	BoardIndex sourceIndex = getBoardIndex();
    	//
        Map<String, Piece> pieceMap = chessBoard.getPieceMap();
        SourceAndTarget legalMove = null;
		//
    	for (int file=sourceIndex.fileIndex+deltaFile, rank=sourceIndex.rankIndex+deltaRank; (file>=0 && file<8 && rank>=0 && rank<8); file=100, rank=-100) {
    		BoardIndex targetIndex = new BoardIndex(file, rank);
    		//
    		Piece targetPiece = pieceMap.get(targetIndex.getKey());
    		if (targetPiece != null && targetPiece.isWhite() == isWhite()) {
    			break;
    		}
            boolean isLegalMove = doActualMove(targetIndex, true);
            //
            if (isLegalMove) {
            	legalMove = new SourceAndTarget(sourceIndex, targetIndex);;
                break;
            }
        }
    	//
    	return legalMove;
    }
}