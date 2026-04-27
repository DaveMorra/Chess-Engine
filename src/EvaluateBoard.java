public class EvaluateBoard {

    public static float getScore(ChessBoard board) {
        
        //score is pos for white advantage, neg for black advantage
        float estimatedGameProgress, gameScore = 0;
        if(board.board == null) {return 0;}

        //if any of the kings are missing, return givin max score
        if(!board.doesPieceExists(board.WHITE_KING)) {return -Float.MAX_VALUE;}
        if(!board.doesPieceExists(board.BLACK_KING)) {return Float.MAX_VALUE;}

        //evaluate game stage as a float, 0 is early game, 1 is late game
        estimatedGameProgress = EstimatedGameProgress(board);

        //material advantage * central control
        gameScore = materialAdvantage(board);

        //peice activity

        //king safty

        //pawn structure

        //add some randomess so each move is unque when testing
        //gameScore += ((float) (Math.random() / 20 ) - .025);

        return gameScore;

    }

    private static float EstimatedGameProgress(ChessBoard board) {
        float stage = board.totalMoves/80; //avg game is 80 moves, so will do this to make it simple
        return stage > 1 ? (float) .95 : stage;
    }

    private static float materialAdvantage(ChessBoard board) {
        float score = 0;
        int color = 0;
        int pieceIndex;

        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                if(board.board[y][x] == 0) {continue;}
                color = board.board[y][x] > 0 ? 1 : -1;
                pieceIndex = ((board.board[y][x]/10) * color ) - 1;
                score += PIECE_SCORE[pieceIndex] * color * SCORE_MULTIPLYER[y][x];
                System.out.println(score);
            }
        }

        return score;
    }

    private static int[] PIECE_TYPE_ALL = {10, 20, 30, 40, 50, 60, -10, -20, -30, -40, -50, -60};
    private static float[] PIECE_SCORE_ALL = {1, 3, (float) 3.1, 5, 9, 0, -1, -3, (float) -3.1, -5, -9, 0}; //king has value of zero
    private static int[] PIECE_TYPE = {10, 20, 30, 40, 50, 60};
    //private static int[] PIECE_TYPE = {10, 20, 30, 40, 50, 60};
    private static float[] PIECE_SCORE = {1, 3, (float) 3.1, 5, 9, 0}; //king has value of zero

    public static final float[][] SCORE_MULTIPLYER = {
        {(float)0.9, (float)0.9, (float)0.9, (float)0.9, (float)0.9, (float)0.9, (float)0.9, (float)0.9},
        {(float)0.9, (float)0.95, (float)0.95, (float)0.95, (float)0.95, (float)0.95, (float)0.95, (float)0.9},
        {(float)0.9, (float)0.95, (float)1, (float)1, (float)1, (float)1, (float)0.95, (float)0.9},
        {(float)0.9, (float)0.95, (float)1, (float)1.05, (float)1.05, (float)1, (float)0.95, (float)0.9},
        {(float)0.9, (float)0.95, (float)1, (float)1.05, (float)1.05, (float)1, (float)0.95, (float)0.9},
        {(float)0.9, (float)0.95, (float)1, (float)1, (float)1, (float)1, (float)0.95, (float)0.9},
        {(float)0.9, (float)0.95, (float)0.95, (float)0.95, (float)0.95, (float)0.95, (float)0.95, (float)0.9},
        {(float)0.9, (float)0.9, (float)0.9, (float)0.9, (float)0.9, (float)0.9, (float)0.9, (float)0.9}
    };



    public static final float PAWN_SCORE = 1;
    public static final float KNIGHT_SOCRE = 3;
    public static final float BISHOP_SOCRE = (float) 3.1;
    public static final float ROOK_SOCRE = 5;
    public static final float QUEEN_SOCRE = 9;
    public static final float KING_SOCRE = Float.MAX_VALUE;

}
