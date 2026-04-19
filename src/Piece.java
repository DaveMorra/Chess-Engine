public class Piece {

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