package com.company.PlayingProcess;

import com.company.BPStuff.BProgramExecutorRunner;
import com.company.BPStuff.MovesBProgramListener;
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
    private static final String aRandomResourceName = "BPJSTicTacToeRand.js";
    private static final String aOptimalResourceName = "BPJSTicTacToeOpt.js";
    private static final int NUM_OF_GAMES = 50;
    private ExecutorService executorService;
    public static boolean playingAgainstRandom = true;

    public Simulator(ExecutorService executorService) {
        this.executorService = executorService;
    }

    protected BProgram createProgram(){
        if(playingAgainstRandom)
            return new ResourceBProgram(aRandomResourceName, new PrioritizedBSyncEventSelectionStrategy());
        else
            return new ResourceBProgram(aOptimalResourceName, new PrioritizedBSyncEventSelectionStrategy());
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
