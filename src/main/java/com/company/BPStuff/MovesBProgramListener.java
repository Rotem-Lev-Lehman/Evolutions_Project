package com.company.BPStuff;

import il.ac.bgu.cs.bp.bpjs.execution.listeners.BProgramRunnerListener;
import il.ac.bgu.cs.bp.bpjs.execution.listeners.BProgramRunnerListenerAdapter;
import il.ac.bgu.cs.bp.bpjs.model.BEvent;
import il.ac.bgu.cs.bp.bpjs.model.BProgram;
import il.ac.bgu.cs.bp.bpjs.model.BThreadSyncSnapshot;
import il.ac.bgu.cs.bp.bpjs.model.FailedAssertion;

import java.util.EventListenerProxy;
import java.util.LinkedList;
import java.util.List;

public class MovesBProgramListener extends BProgramRunnerListenerAdapter {
    List<BEvent> moves;

    public MovesBProgramListener() {
        this.moves = new LinkedList<>();
    }

    public List<BEvent> getMoves() {
        return moves;
    }

    @Override
    public void eventSelected(BProgram bProgram, BEvent bEvent) {
        //The only method we want to implement...
        moves.add(bEvent);
    }
}
