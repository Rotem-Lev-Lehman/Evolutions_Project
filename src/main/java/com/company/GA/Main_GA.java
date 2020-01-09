package com.company.GA;

import com.company.BPStuff.MovesBProgramListener;
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
        long startTime = System.currentTimeMillis();

        runGAWithStatistics();

        long finishTime = System.currentTimeMillis();
        long totalTime = finishTime - startTime;
        System.out.println();
        System.out.println("Total time: " + totalTime);
        //previousRun();
    }

    private static void runGAWithStatistics() {
        runSpecificExperiment("exp4",0.7, 2, 0.1);
        runSpecificExperiment("exp5",0.7, 2, 0.01);
        runSpecificExperiment("exp6",0.7, 2, 0.001);

        runSpecificExperiment("exp7",0.4, 2, 0.1);
        runSpecificExperiment("exp8",0.4, 2, 0.01);
        runSpecificExperiment("exp9",0.4, 2, 0.001);

        runSpecificExperiment("exp10",0.7, 3, 0.1);
        runSpecificExperiment("exp11",0.7, 3, 0.01);
        runSpecificExperiment("exp12",0.7, 3, 0.001);

        runSpecificExperiment("exp13",0.4, 3, 0.1);
        runSpecificExperiment("exp14",0.4, 3, 0.01);
        runSpecificExperiment("exp15",0.4, 3, 0.001);
        GA_Solver.executorService.shutdown();
    }

    public static void runSpecificExperiment(String name, double crossP, int crossN, double mutP){
        String folder = "src/main/results/GA/optX";
        System.out.println("now running experiment: " + name);
        for(int i = 0; i < 3; i++){
            MyStatisticsSaver statisticsSaver = new MyStatisticsSaver(folder, name, i);
            GA_Solver solver = new GA_Solver(crossP, crossN, mutP);
            solver.Solve();
            statisticsSaver.Save(crossP, crossN, mutP);
        }
    }

    public static void previousRun(){
        String aResourceName = "GA/BPJSTicTacToeRand.js";
        BProgram program = new ResourceBProgram(aResourceName, new PrioritizedBSyncEventSelectionStrategy());
        Integer[] weightsArray = new Integer[]{50, 40, 30, 30, 30, 30, 30, 35, 20, 10};
        //Integer[] weightsArray = new Integer[]{1,1,1,1,1,1,1,1,1,1};
                                            //[11, 10, 4, 8, 8, 2, 10, 11, 5, 2]
                                            //[11, 5, 6, 2, 1, 2, 5, 6, 4, 3]
                                            //[11, 9, 3, 2, 5, 1, 10, 10, 3, 1]
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
