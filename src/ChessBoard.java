import java.util.ArrayList;
//import Piece;
import java.util.Comparator;

public class ChessBoard {

    /*
    white -> positive
    black -> negitive

    piece   unmoved/defult  moved
    nothing 0
    pawn    10               11
    knight  20               21
    bishop  30               31
    rook    40               41
    queen   50               51
    king    60               61
    */

    public static final int WHITE_PAWN = 10;
    public static final int WHITE_KNIGHT = 20;
    public static final int WHITE_BISHOP = 30;
    public static final int WHITE_ROOK = 40;
    public static final int WHITE_QUEEN = 50;
    public static final int WHITE_KING = 60;

    public static final int WHITE_PAWN_MOVED = 11;
    public static final int WHITE_KNIGHT_MOVED = 21;
    public static final int WHITE_BISHOP_MOVED = 31;
    public static final int WHITE_ROOK_MOVED = 41;
    public static final int WHITE_QUEEN_MOVED = 51;
    public static final int WHITE_KING_MOVED = 61;

    public static final int BLACK_PAWN = -10;
    public static final int BLACK_KNIGHT = -20;
    public static final int BLACK_BISHOP = -30;
    public static final int BLACK_ROOK = -40;
    public static final int BLACK_QUEEN = -50;
    public static final int BLACK_KING = -60;

    public static final int BLACK_PAWN_MOVED = -11;
    public static final int BLACK_KNIGHT_MOVED = -21;
    public static final int BLACK_BISHOP_MOVED = -31;
    public static final int BLACK_ROOK_MOVED = -41;
    public static final int BLACK_QUEEN_MOVED = -51;
    public static final int BLACK_KING_MOVED = -61;

    public static final int EMPTY = 0;

    public ArrayList<ChessBoard> nextMoveList;
    public byte[][] board;
    public byte lastMovedSquare; //0-63
    public float score;
    public short totalMoves;
    public int whitesTime;
    public int blacksTime;
    //TODO ADD CASES FOR STALEMATE

    public ChessBoard() {
        resart();
    }

    public ChessBoard(ChessBoard chessBoard) {
        copy(chessBoard);
    }

    public boolean resart() {
        this.totalMoves = 0;
        this.whitesTime = 1;
        this.blacksTime = 1;
        nextMoveList = null;
        this.board = new byte[][] {
            {-40, -20, -30, -50, -60, -30, -20, -40},
            {-10, -10, -10, -10, -10, -10, -10, -10},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {10, 10, 10, 10, 10, 10, 10, 10},
            {40, 20, 30, 50, 60, 30, 20, 40}
        };
        //evaluateScore(); //set score to 0 in the futrue
        score = 0;
        return true;
    }

    public boolean copy(ChessBoard chessBoard) {

        this.board = new byte[8][8];
        this.score = chessBoard.score;
        this.totalMoves = chessBoard.totalMoves;
        this.whitesTime = chessBoard.whitesTime;
        this.blacksTime = chessBoard.blacksTime;
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                this.board[y][x] = chessBoard.board[y][x];
            }
        }

        return true;
    }

    public ArrayList<ChessBoard> getNextIteration() {
        nextMoveList = new ArrayList<>(200);
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                
                //if board spot is not a peice or curr piece is opposite color or team team that suppost to move, break.
                if((!isPiece(board[y][x])) || (isWhitesMove() != isWhite(board[y][x]))) {continue;}
                
                //see what peice type is selected and execute corrisponding function
                //can use multicasting if java -V is 14.0+
                switch(type(board[y][x])) {
                    case WHITE_PAWN:
                        getPawnMoves(y,x);
                        continue;
                    case BLACK_PAWN:
                        getPawnMoves(y,x);
                        continue;
                    case WHITE_KNIGHT:
                        getKnightMoves(y,x);
                        continue;
                    case BLACK_KNIGHT:
                        getKnightMoves(y,x);
                        continue;
                    case WHITE_BISHOP:
                        getBishopMoves(y,x);
                        continue;
                    case BLACK_BISHOP:
                        getBishopMoves(y,x);
                        continue;
                    case WHITE_ROOK:
                        getRookMoves(y,x);
                        continue;
                    case BLACK_ROOK:
                        getRookMoves(y,x);
                        continue;
                    case WHITE_QUEEN:
                        getQueenMoves(y,x);    
                        continue;
                    case BLACK_QUEEN:
                        getQueenMoves(y,x); 
                        continue;
                    case WHITE_KING:
                        getKingMoves(y,x);
                        continue;
                    case BLACK_KING:
                        getKingMoves(y,x);
                        continue;

                }
            }
        }
        for(ChessBoard nextMove:nextMoveList) {nextMove.evaluateScore();} //find score
        nextMoveList.sort(Comparator.comparing(ChessBoard::getScore)); //sort according to score
        nextMoveList.trimToSize(); //trip to size as this size will never change
        return nextMoveList;
    }

    public boolean print() {

        for(int y = 0; y < 8; y++) {
            System.out.print("\n");
            for(int x = 0; x < 8; x++) {
                printSquare(y, x);
            }
        }
        if(isWhitesMove()) {System.out.print("\nMove: White");}
        else {{System.out.print("\nMove: Black");}}
        {System.out.print("\nScore: " + score + "\n");}

        return true;
    }

    private void printSquare(int y, int x) {
         
        //invert color if darkmode is on
        boolean darkMode = true;
        int piece = darkMode ? board[y][x] * -1: board[y][x];

        switch(type(piece)) {
            case WHITE_PAWN:
                System.out.print("\u2659 ");
                break;
            case BLACK_PAWN: //black pawn
                System.out.print("\u265F ");
                break;
            case WHITE_ROOK: //white rook
                System.out.print("\u2656 ");
                break;
            case BLACK_ROOK: //black rook
                System.out.print("\u265C ");
                break;
            case WHITE_KING: //white king
                System.out.print("\u2654 ");
                break;
            case BLACK_KING: //black king
                System.out.print("\u265A ");
                break;
            case WHITE_QUEEN: //white queen
                System.out.print("\u2655 ");
                break;
            case BLACK_QUEEN: //black queen
                System.out.print("\u265B ");
                break;
            case WHITE_BISHOP: //white bishop
                System.out.print("\u2657 ");
                break;
            case BLACK_BISHOP: //black bishop
                System.out.print("\u265D ");
                break;
            case WHITE_KNIGHT: //white knight
                System.out.print("\u2658 ");
                break;
            case BLACK_KNIGHT: //black knight
                System.out.print("\u265E ");
                break;
            case EMPTY: //empty
                if((y%2==0 && x%2==0) || (y%2==1 && x%2==1)) {
                    System.out.print("\u2591"+"\u2591"); 
                }
                else {System.out.print("  ");}
                break;
        }
    }

    private ChessBoard generateNextBoard() {
        ChessBoard newBoard = new ChessBoard(this);
        newBoard.totalMoves++;
        nextMoveList.add(newBoard);
        return newBoard;
    }

    private void getPawnMoves(int y, int x) {
        
        //can move forward once
        if(isWhite(board[y][x]) && true) {
            ChessBoard newBoard = generateNextBoard();
            newBoard.board[y][x] = EMPTY;
            newBoard.board[y-1][x] = 11;
        }
        if(isBlack(board[y][x]) && true) {
            ChessBoard newBoard = generateNextBoard();
            newBoard.board[y][x] = EMPTY;
            newBoard.board[y+1][x] = -11;
        }
    }

    private void getKnightMoves(int y, int x) {
        
        final byte moveArrayY[] = {2, 2, 1, 1, -1, -1, -2, -2};
        final byte moveArrayX[] = {1, -1, 2, -2, 2, -2, 1, -1};
        for(int i = 0; i < 8; i++) {
            if(isInBounds(y + moveArrayY[i], x + moveArrayX[i]) && (!isPiece(board[y + moveArrayY[i]][x + moveArrayX[i]]) || isWhite(board[y + moveArrayY[i]][x + moveArrayX[i]]) != isWhitesMove())) {
                ChessBoard newBoard = generateNextBoard();
                newBoard.board[y + moveArrayY[i]][x + moveArrayX[i]] = newBoard.board[y][x];
                newBoard.board[y][x] = EMPTY;
            }
        }

    }

    //append all bishop moves
    private void getBishopMoves(int y, int x) {
        
        final byte moveArrayY[] = {1, 1, -1, -1};
        final byte moveArrayX[] = {1, -1, 1, -1};

        byte yNew, xNew;
        for(int i = 0; i < 4; i++) {
            for(int length = 1; length <= 8; length++) {
                
                yNew = (byte) (y + (moveArrayY[i] * length));
                xNew = (byte) (x + (moveArrayX[i] * length));

                //if piece is in bounds, and the quare is empty or, opposite teams piece is getting captured
                if(isInBounds(yNew, xNew) && (!isPiece(board[yNew][xNew]) || isTherePiece(yNew, xNew))) {
                    
                    //System.out.println("test");
                    ChessBoard newBoard = generateNextBoard();
                    newBoard.board[yNew][xNew] = newBoard.board[y][x];
                    newBoard.board[y][x] = EMPTY;

                //if current square is own piece, ignore and break future chain moves
                } else { break; }

                //if caputered opposite teams pience, break future chain moves
                if(isTherePiece(yNew, xNew)) { break; }

            }
        }

    }

    //append all rook moves
    private void getRookMoves(int y, int x) {
        
        final byte moveArrayY[] = {1, -1, 0, 0};
        final byte moveArrayX[] = {0, 0, 1, -1};

        byte yNew, xNew;
        for(int i = 0; i < 4; i++) {
            for(int length = 1; length <= 8; length++) {
                
                yNew = (byte) (y + (moveArrayY[i] * length));
                xNew = (byte) (x + (moveArrayX[i] * length));

                //if piece is in bounds, and the quare is empty or, opposite teams piece is getting captured
                if(isInBounds(yNew, xNew) && (!isPiece(board[yNew][xNew]) || isTherePiece(yNew, xNew))) {
                    
                    //System.out.println("test");
                    ChessBoard newBoard = generateNextBoard();
                    newBoard.board[yNew][xNew] = newBoard.board[y][x];
                    newBoard.board[y][x] = EMPTY;

                //if current square is own piece, ignore and break future chain moves
                } else { break; }

                //if caputered opposite teams pience, break future chain moves
                if(isTherePiece(yNew, xNew)) { break; }

            }
        }

    }

    //append all queen moves
    private void getQueenMoves(int y, int x) {
        
        final byte moveArrayY[] = {1, -1, 0, 0, 1, 1, -1, -1};
        final byte moveArrayX[] = {0, 0, 1, -1, 1, -1, 1, -1};

        byte yNew, xNew;
        for(int i = 0; i < 8; i++) {
            for(int length = 1; length <= 8; length++) {
                
                yNew = (byte) (y + (moveArrayY[i] * length));
                xNew = (byte) (x + (moveArrayX[i] * length));

                //if piece is in bounds, and the quare is empty or, opposite teams piece is getting captured
                if(isInBounds(yNew, xNew) && (!isPiece(board[yNew][xNew]) || isTherePiece(yNew, xNew))) {
                    
                    //System.out.println("test");
                    ChessBoard newBoard = generateNextBoard();
                    newBoard.board[yNew][xNew] = newBoard.board[y][x];
                    newBoard.board[y][x] = EMPTY;

                //if current square is own piece, ignore and break future chain moves
                } else { break; }

                //if caputered opposite teams pience, break future chain moves
                if(isTherePiece(yNew, xNew)) { break; }

            }
        }

    }

    //append all queen moves
    private void getKingMoves(int y, int x) {
        
        final byte moveArrayY[] = {1, -1, 0, 0, 1, 1, -1, -1};
        final byte moveArrayX[] = {0, 0, 1, -1, 1, -1, 1, -1};

        byte yNew, xNew;
        for(int i = 0; i < 8; i++) {
                
            yNew = (byte) (y + (moveArrayY[i]));
            xNew = (byte) (x + (moveArrayX[i]));

            //if piece is in bounds, and the quare is empty or, opposite teams piece is getting captured
            if(isInBounds(yNew, xNew) && (!isPiece(board[yNew][xNew]) || isTherePiece(yNew, xNew))) {
                    
                //System.out.println("test");
                ChessBoard newBoard = generateNextBoard();
                newBoard.board[yNew][xNew] = newBoard.board[y][x];
                newBoard.board[y][x] = EMPTY;

            //if current square is own piece, ignore and break future chain moves
            } else { break; }
        }

    }

    public boolean doesPieceExists(int piece) {
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                if((board[y][x]/10) == piece/10) {return true;}
            }
        }
        return false;
    }

    public int numberOfGivenPieces(int piece) {
        int num = 0;
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                if((board[y][x]/10) == piece/10) {num++;}
            }
        }
        return num;
    }

    private boolean isInBounds(int y,int x) {
        return (y>=0) && (y <= 7) && (x>=0) && (x <= 7);
    }

    public float getScore() {
        
        return score;
    }

    private boolean isWhitesMove() {
        return totalMoves%2==0;
    }

    //true is piece, and is its own
    private boolean isOwnPiece(int y, int x) {
        return (board[y][x] != 0) && (isWhite(board[y][x]) == (totalMoves%2==0));
    }

    //true is piece, and is their own
    private boolean isTherePiece(int y, int x) {
        return (board[y][x] != 0) && (isWhite(board[y][x]) != (totalMoves%2==0));
    }

    public boolean isGameOver() {
        return (!doesPieceExists(WHITE_KING) || !doesPieceExists(BLACK_KING));
    }

    //call evaluation function to return score
    private float evaluateScore() {
        if(nextMoveList.isEmpty()) {
            return score = Math.round(EvaluateBoard.getScore(this)*10000)/10000;
        }
        if(isWhitesMove()) {
            return getMaxScoreFromNextMove();
        }
        return getMaxScoreFromNextMove();
    }

    public float getMaxScoreFromNextMove() {
        float max= Float.MIN_NORMAL;
        for(ChessBoard nextBoard:nextMoveList) {
            max = Math.max(max, nextBoard.score);
        }
        return max;
    }

    public float getMinScoreFromNextMove() {
        float min= Float.MAX_VALUE;
        for(ChessBoard nextBoard:nextMoveList) {
            min = Math.min(min, nextBoard.score);
        }
        return min;
    }

    public final static boolean isPiece(int piece) {
        return piece!=0;
    }

    public final static boolean isWhite(int piece) {
        return piece>0;
    }

    public final static boolean isBlack(int piece) {
        return piece<0;
    }

    public final static int type(int piece) {
        return piece-(piece%10);
    }

    public final static boolean hasMoved(int piece) {
        return piece%10==1;
    }

    public final static byte setMoved(byte piece) {
        return (byte) (type(piece) + 1);
    }

}

