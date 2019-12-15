package com.company.PlayingProcess;

import com.company.BPStuff.MovesBProgramListener;
import com.company.BPStuff.TicTacToeEvents.Move;
import il.ac.bgu.cs.bp.bpjs.execution.BProgramRunner;
import il.ac.bgu.cs.bp.bpjs.model.BEvent;
import il.ac.bgu.cs.bp.bpjs.model.BProgram;
import il.ac.bgu.cs.bp.bpjs.model.ResourceBProgram;
import il.ac.bgu.cs.bp.bpjs.model.eventselection.PrioritizedBSyncEventSelectionStrategy;

import java.util.List;

/**
 * Simulates games played by an individual.
 */
public abstract class Simulator {
    private static final String aResourceName = "BPJSTicTacToe.js";
    private static final int NUM_OF_GAMES = 5;

    public Simulator() {
    }

    protected BProgram createProgram(){
        return new ResourceBProgram(aResourceName, new PrioritizedBSyncEventSelectionStrategy());
    }

    public double getFitness(){
        double fitness = 0;
        for(int i = 0; i < NUM_OF_GAMES; i++){
            BProgram program = createProgram();
            BProgramRunner runner = new BProgramRunner(program);
            MovesBProgramListener listener = new MovesBProgramListener();
            runner.addListener(listener);
            runner.run();
            List<BEvent> eventsSelected = listener.getMoves();
            Game game = new Game();

            for (BEvent event : eventsSelected) {
                if(event instanceof Move){
                    game.AddMove((Move)event);
                }
                else{
                    game.setResult(event);
                    break;
                }
            }
            fitness += game.fitness();
            // TODO: 12/15/2019 maybe change the fitness to something else
        }
        return fitness;
    }
}
