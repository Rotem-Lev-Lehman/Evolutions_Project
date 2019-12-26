package com.company.PlayingProcess;

import com.company.BPStuff.BProgramExecutorRunner;
import com.company.BPStuff.MovesBProgramListener;
import com.company.BPStuff.TicTacToeEvents.Move;
import il.ac.bgu.cs.bp.bpjs.execution.BProgramRunner;
import il.ac.bgu.cs.bp.bpjs.model.BEvent;
import il.ac.bgu.cs.bp.bpjs.model.BProgram;
import il.ac.bgu.cs.bp.bpjs.model.ResourceBProgram;
import il.ac.bgu.cs.bp.bpjs.model.eventselection.PrioritizedBSyncEventSelectionStrategy;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

/**
 * Simulates games played by an individual.
 */
public abstract class Simulator {
    private static final String aResourceName = "BPJSTicTacToe.js";
    private static final int NUM_OF_GAMES = 5;
    private ExecutorService executorService;


    public Simulator(ExecutorService executorService) {
        this.executorService = executorService;
    }

    protected BProgram createProgram(){
        return new ResourceBProgram(aResourceName, new PrioritizedBSyncEventSelectionStrategy());
    }

    public double getFitness(){
        return IntStream.range(0, NUM_OF_GAMES).parallel().mapToDouble(i -> {
            BProgram program = createProgram();
            BProgramExecutorRunner runner = new BProgramExecutorRunner(program, executorService);
            MovesBProgramListener listener = new MovesBProgramListener();
            runner.addListener(listener);
            runner.run();
            List<BEvent> eventsSelected = listener.getMoves();
            Game game = new Game();

            for (BEvent event : eventsSelected) {
                if(Game.isMove(event) && !Game.isStaticEvent(event)){
                    game.AddMove(event);
                }
                else{
                    game.setResult(event);
                    break;
                }
            }
            return game.fitness();
            // TODO: 12/15/2019 maybe change the fitness to something else
        }).sum();
    }
}
