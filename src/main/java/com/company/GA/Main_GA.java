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

/**
 * This class will control the GA solution of the Tic Tac Toe algorithm
 */
public class Main_GA {
    public static void main(String[] args) {
        GA_Solver solver = new GA_Solver();
        solver.Solve();
    }

    public static void previousRun(){
        String aResourceName = "BPJSTicTacToe.js";
        BProgram program = new ResourceBProgram(aResourceName, new PrioritizedBSyncEventSelectionStrategy());
        //Integer[] weightsArray = new Integer[]{50, 40, 30, 30, 30, 30, 30, 35, 20, 10};
        Integer[] weightsArray = new Integer[]{1,1,1,1,1,1,1,1,1,1};

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
            if(move instanceof Move){
                Spot spot;
                if(move instanceof X){
                    spot = Spot.X;
                }
                else{
                    spot = Spot.O;
                }
                int col = ((Move)move).col;
                int row = ((Move)move).row;
                board[row][col] = spot;
                //PrintBoard(board);
            }

        }
        PrintBoard(board);
        System.out.println("Done moves");
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
