package com.company.GA;

import com.company.BPStuff.MovesBProgramListener;
import com.company.BPStuff.TicTacToeEvents.Move;
import com.company.BPStuff.TicTacToeEvents.X;
import il.ac.bgu.cs.bp.bpjs.execution.BProgramRunner;
import il.ac.bgu.cs.bp.bpjs.model.BEvent;
import il.ac.bgu.cs.bp.bpjs.model.BProgram;
import il.ac.bgu.cs.bp.bpjs.model.ResourceBProgram;
import il.ac.bgu.cs.bp.bpjs.model.eventselection.PrioritizedBSyncEventSelectionStrategy;

import java.util.List;
import java.util.Map;

/**
 * This class will control the GA solution of the Tic Tac Toe algorithm
 */
public class Main_GA {
    public static void main(String[] args) {
        GA_Solver solver = new GA_Solver();
        solver.Solve();
        //previousRun();
    }

    public static void previousRun(){
        String aResourceName = "BPJSTicTacToe.js";
        BProgram program = new ResourceBProgram(aResourceName, new PrioritizedBSyncEventSelectionStrategy());
        Integer[] weightsArray = new Integer[]{50, 40, 30, 30, 30, 30, 30, 35, 20, 10};
        //Integer[] weightsArray = new Integer[]{1,1,1,1,1,1,1,1,1,1};

        program.putInGlobalScope("weights", weightsArray);
        BProgramRunner runner = new BProgramRunner(program);
        MovesBProgramListener listener = new MovesBProgramListener();
        runner.addListener(listener);
        runner.run();
        List<BEvent> eventsSelected = listener.getMoves();
        System.out.println("Printing moves:");
        Spot[][] board = new Spot[3][3];
        initBoard(board);
        for (BEvent move : eventsSelected) {
            System.out.println(move.name);
            if(!isStaticEvent(move) && isMove(move)){
                Spot spot;
                if(isX(move)){
                    spot = Spot.X;
                }
                else{
                    spot = Spot.O;
                }
                Map<String, Double> data = (Map<String, Double>)move.maybeData;
                int col = (int)(double)data.get("col");
                int row = (int)(double)data.get("row");
                board[row][col] = spot;
                //PrintBoard(board);
            }

        }
        PrintBoard(board);
        System.out.println("Done moves");
    }

    private static boolean isStaticEvent(BEvent event){
        return isXWin(event) || isOWin(event) || isDraw(event) || isGameOver(event);
    }

    private static boolean isGameOver(BEvent event) {
        return event.name.equals("GameOver");
    }

    private static boolean isDraw(BEvent event) {
        return event.name.equals("Draw");
    }

    private static boolean isOWin(BEvent event) {
        return event.name.equals("OWin");
    }

    private static boolean isXWin(BEvent event) {
        return event.name.equals("XWin");
    }

    private static boolean isMove(BEvent event){
        return isX(event) || isO(event);
    }

    private static boolean isO(BEvent event) {
        return event.name.contains("O");
    }

    private static boolean isX(BEvent event) {
        return event.name.contains("X");
    }

    private static void initBoard(Spot[][] board) {
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                board[i][j] = Spot.Empty;
            }
        }
    }

    enum Spot {
        X,
        O,
        Empty
    }

    private static void PrintBoard(Spot[][] board){
        for(int row = 0; row < board.length; row++){
            System.out.print("|");
            for(int col = 0; col < board[row].length; col++){
                if(board[row][col] == Spot.Empty)
                    System.out.print(" ");
                else
                    System.out.print(board[row][col]);
                System.out.print("|");
            }
            System.out.println();
        }
    }
}
