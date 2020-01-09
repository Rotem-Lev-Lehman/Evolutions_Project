package com.company.PlayingProcess;

import il.ac.bgu.cs.bp.bpjs.model.BProgram;
import il.ac.bgu.cs.bp.bpjs.model.ResourceBProgram;
import il.ac.bgu.cs.bp.bpjs.model.eventselection.PrioritizedBSyncEventSelectionStrategy;

import java.util.concurrent.ExecutorService;

public class GPSimulator extends Simulator{
    private String[] codeParts;
    private boolean playAgainstRandomGPPlayer;


    public GPSimulator(String[] codeParts, ExecutorService executorService, boolean playAgainstRandomPlayer) {
        super(executorService);
        this.codeParts = codeParts;
        playAgainstRandomGPPlayer = playAgainstRandomPlayer;
    }

    @Override
    protected String getFolderForJSSources() {
        return "GP/";
    }

    @Override
    protected BProgram createProgram() {
        BProgram program;
        if(playAgainstRandomGPPlayer)
            program = new ResourceBProgram(getFolderForJSSources() + aRandomResourceName, new PrioritizedBSyncEventSelectionStrategy());
        else
            program = new ResourceBProgram(getFolderForJSSources() + aOptimalResourceName, new PrioritizedBSyncEventSelectionStrategy());

        for(int i = 0; i < codeParts.length; i++) {
            String currentCodeInFunction = wrapInFunction(codeParts[i], i);
            program.prependSource(currentCodeInFunction);
        }

        return program;
    }

    private String wrapInFunction(String codePart, int i) {
        String params = "(){";
        if(i < 7)
            params = "(f,p){";
        return "function bThread" + i + params + codePart + "}";
    }
}
