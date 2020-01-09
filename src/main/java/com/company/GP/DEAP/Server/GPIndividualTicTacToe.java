package com.company.GP.DEAP.Server;

public class GPIndividualTicTacToe {
    private int id;
    private String[] currentSolution;
    private double fitness;

    public GPIndividualTicTacToe(int id, String[] currentSolution) {
        this.id = id;
        this.currentSolution = currentSolution;
    }

    public String[] getCurrentSolution() {
        return currentSolution;
    }

    public int getId() {
        return id;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
