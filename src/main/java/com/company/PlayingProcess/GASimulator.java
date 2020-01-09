package com.company.PlayingProcess;

import il.ac.bgu.cs.bp.bpjs.model.BProgram;

import java.util.concurrent.ExecutorService;

public class GASimulator extends Simulator {
    private Integer[] weights;

    public GASimulator(Integer[] weights, ExecutorService executorService) {
        super(executorService);
        this.weights = weights;
    }

    @Override
    protected String getFolderForJSSources() {
        return "GA/";
    }

    @Override
    protected BProgram createProgram() {
        BProgram program = super.createProgram();
        program.putInGlobalScope("weights", weights);
        return program;
    }
}
