package com.company.GA;

import il.ac.bgu.cs.bp.bpjs.execution.BProgramRunner;
import il.ac.bgu.cs.bp.bpjs.execution.listeners.PrintBProgramRunnerListener;
import il.ac.bgu.cs.bp.bpjs.model.BProgram;
import il.ac.bgu.cs.bp.bpjs.model.ResourceBProgram;
import il.ac.bgu.cs.bp.bpjs.model.eventselection.PrioritizedBSyncEventSelectionStrategy;

/**
 * This class will control the GA solution of the Tic Tac Toe algorithm
 */
public class Main_GA {
    public static void main(String[] args) {
        String aResourceName = "BPJSTicTacToe.js";
        BProgram program = new ResourceBProgram(aResourceName, new PrioritizedBSyncEventSelectionStrategy());
        BProgramRunner runner = new BProgramRunner(program);
        runner.addListener(new PrintBProgramRunnerListener());
        runner.run();
    }
}
