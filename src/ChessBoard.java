import java.util.ArrayList;
//import Piece;
import java.util.Comparator;

public class ChessBoard {

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
        evaluateScore(); //set score to 0 in the futrue
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
                if((!Piece.isPiece(board[y][x])) || (isWhitesMove() != Piece.isWhite(board[y][x]))) {continue;}
                
                //see what peice type is selected and execute corrisponding function
                //can use multicasting if java -V is 14.0+
                switch(Piece.type(board[y][x])) {
                    case Piece.WHITE_PAWN:
                        getPawnMoves(y,x);
                        continue;
                    case Piece.BLACK_PAWN:
                        getPawnMoves(y,x);
                        continue;
                    case Piece.WHITE_KNIGHT:
                        getKnightMoves(y,x);
                        continue;
                    case Piece.BLACK_KNIGHT:
                        getKnightMoves(y,x);
                        continue;
                    case Piece.WHITE_BISHOP:
                        getBishopMoves(y,x);
                        continue;
                    case Piece.BLACK_BISHOP:
                        getBishopMoves(y,x);
                        continue;
                    case Piece.WHITE_ROOK:
                        getRookMoves(y,x);
                        continue;
                    case Piece.BLACK_ROOK:
                        getRookMoves(y,x);
                        continue;
                    case Piece.WHITE_QUEEN:
                        getQueenMoves(y,x);    
                        continue;
                    case Piece.BLACK_QUEEN:
                        getQueenMoves(y,x); 
                        continue;
                    case Piece.WHITE_KING:
                        getKingMoves(y,x);
                        continue;
                    case Piece.BLACK_KING:
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
        {System.out.print("\nScore: " + score);}

        return true;
    }

    private void printSquare(int y, int x) {
         
        //invert color if darkmode is on
        boolean darkMode = true;
        int piece = darkMode ? board[y][x] * -1: board[y][x];

        switch(Piece.type(piece)) {
            case Piece.WHITE_PAWN:
                System.out.print("\u2659 ");
                break;
            case Piece.BLACK_PAWN: //black pawn
                System.out.print("\u265F ");
                break;
            case Piece.WHITE_ROOK: //white rook
                System.out.print("\u2656 ");
                break;
            case Piece.BLACK_ROOK: //black rook
                System.out.print("\u265C ");
                break;
            case Piece.WHITE_KING: //white king
                System.out.print("\u2654 ");
                break;
            case Piece.BLACK_KING: //black king
                System.out.print("\u265A ");
                break;
            case Piece.WHITE_QUEEN: //white queen
                System.out.print("\u2655 ");
                break;
            case Piece.BLACK_QUEEN: //black queen
                System.out.print("\u265B ");
                break;
            case Piece.WHITE_BISHOP: //white bishop
                System.out.print("\u2657 ");
                break;
            case Piece.BLACK_BISHOP: //black bishop
                System.out.print("\u265D ");
                break;
            case Piece.WHITE_KNIGHT: //white knight
                System.out.print("\u2658 ");
                break;
            case Piece.BLACK_KNIGHT: //black knight
                System.out.print("\u265E ");
                break;
            case Piece.EMPTY: //empty
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
        if(Piece.isWhite(board[y][x]) && true) {
            ChessBoard newBoard = generateNextBoard();
            newBoard.board[y][x] = Piece.EMPTY;
            newBoard.board[y-1][x] = 11;
        }
        if(Piece.isBlack(board[y][x]) && true) {
            ChessBoard newBoard = generateNextBoard();
            newBoard.board[y][x] = Piece.EMPTY;
            newBoard.board[y+1][x] = -11;
        }
    }

    private void getKnightMoves(int y, int x) {
        
        final byte moveArrayY[] = {2, 2, 1, 1, -1, -1, -2, -2};
        final byte moveArrayX[] = {1, -1, 2, -2, 2, -2, 1, -1};
        for(int i = 0; i < 8; i++) {
            if(isInBounds(y + moveArrayY[i], x + moveArrayX[i]) && (!Piece.isPiece(board[y + moveArrayY[i]][x + moveArrayX[i]]) || Piece.isWhite(board[y + moveArrayY[i]][x + moveArrayX[i]]) != isWhitesMove())) {
                ChessBoard newBoard = generateNextBoard();
                newBoard.board[y + moveArrayY[i]][x + moveArrayX[i]] = newBoard.board[y][x];
                newBoard.board[y][x] = Piece.EMPTY;
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
                if(isInBounds(yNew, xNew) && (!Piece.isPiece(board[yNew][xNew]) || isTherePiece(yNew, xNew))) {
                    
                    //System.out.println("test");
                    ChessBoard newBoard = generateNextBoard();
                    newBoard.board[yNew][xNew] = newBoard.board[y][x];
                    newBoard.board[y][x] = Piece.EMPTY;

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
                if(isInBounds(yNew, xNew) && (!Piece.isPiece(board[yNew][xNew]) || isTherePiece(yNew, xNew))) {
                    
                    //System.out.println("test");
                    ChessBoard newBoard = generateNextBoard();
                    newBoard.board[yNew][xNew] = newBoard.board[y][x];
                    newBoard.board[y][x] = Piece.EMPTY;

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
                if(isInBounds(yNew, xNew) && (!Piece.isPiece(board[yNew][xNew]) || isTherePiece(yNew, xNew))) {
                    
                    //System.out.println("test");
                    ChessBoard newBoard = generateNextBoard();
                    newBoard.board[yNew][xNew] = newBoard.board[y][x];
                    newBoard.board[y][x] = Piece.EMPTY;

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
            if(isInBounds(yNew, xNew) && (!Piece.isPiece(board[yNew][xNew]) || isTherePiece(yNew, xNew))) {
                    
                //System.out.println("test");
                ChessBoard newBoard = generateNextBoard();
                newBoard.board[yNew][xNew] = newBoard.board[y][x];
                newBoard.board[y][x] = Piece.EMPTY;

            //if current square is own piece, ignore and break future chain moves
            } else { break; }
        }

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
        return (board[y][x] != 0) && (Piece.isWhite(board[y][x]) == (totalMoves%2==0));
    }

    //true is piece, and is their own
    private boolean isTherePiece(int y, int x) {
        return (board[y][x] != 0) && (Piece.isWhite(board[y][x]) != (totalMoves%2==0));
    }

    //call evaluation function to return score
    private float evaluateScore() {
        return score = Math.round(EvaluateBoard.getScore(this)*10000)/10000;
    }

}

