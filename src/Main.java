import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.printf("---=== Starting Simulator ===---\n");

        ChessBoard chessBoard = new ChessBoard();
        MainSimulator simulator = new MainSimulator();
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


    }
}