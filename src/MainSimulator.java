public class MainSimulator {
    
    public MainSimulator() {
        System.out.printf("---=== Starting Simulator ===---\n");

        ChessBoard chessBoard = new ChessBoard();
        recursiveSim(chessBoard, 4);
        chessBoard.nextMoveList.get(0).print();
        /*
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.print();

        chessBoard.getNextIteration();
        ChessBoard iter1 = chessBoard.nextMoveList.get(0);
        
        iter1.getNextIteration();
        //ChessBoard iter2 = iter1.nextMoveList.get(0);
        for(ChessBoard iter2 : iter1.nextMoveList) {
            System.err.println("");
            iter2.print();
        }
        //simulator.inizalise() //already done when obj created
        //simulator.run()
        //simulator.nextMove()
        //simulator.evaluate()
        */
    }

    public boolean recursiveSim(ChessBoard board, int depth) {
        
        //base case
        if(depth == 0 || board.isGameOver()) { //also add case where game is over
            return true;
        }

        //TODO add alpha beta prunning

        //iteritive case
        board.getNextIteration();
        for(ChessBoard nextBoard : board.nextMoveList) {
            recursiveSim(nextBoard, depth--);
        }
        
        return true;
    }

}
