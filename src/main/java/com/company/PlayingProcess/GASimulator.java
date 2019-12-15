package com.company.PlayingProcess;

import il.ac.bgu.cs.bp.bpjs.model.BProgram;

public class GASimulator extends Simulator {
    private int[] weights;

    public GASimulator(int[] weights) {
        this.weights = weights;
    }

    @Override
    protected BProgram createProgram() {
        BProgram program = super.createProgram();
        program.putInGlobalScope("weights", weights);
        return program;
    }
}
