package com.company.GP;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPProblem;
import ec.util.Parameter;

public class TicTacToe extends GPProblem {
    public StringBuilder tree = null;

    public void setup(final EvolutionState state,
                      final Parameter base)
    {
        super.setup(state,base);
        tree = new StringBuilder();
    }

    @Override
    public void evaluate(EvolutionState evolutionState, Individual individual, int i, int i1) {

    }
}
