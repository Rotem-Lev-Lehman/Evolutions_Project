package com.company.GP;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPProblem;
import ec.util.Parameter;

public class TicTacToe extends GPProblem {
    public StringBuilder tree = null;
    private static String[] btNames = {"Center", "Corners", "Sides"};
    private int btNameIndex;
    private static String[] btfNames = {"AddThirdO", "PreventThirdX", "PreventFork22X", "PreventFork02X", "PreventFork20X", "PreventFork00X", "PreventForkdiagX"};
    private int btfNameIndex;
    private int btfFPMaxNum;

    public void setup(final EvolutionState state,
                      final Parameter base)
    {
        super.setup(state,base);
        tree = new StringBuilder();
        btNameIndex = 0;
        btfNameIndex = 0;
        btfFPMaxNum = 2;
    }

    public String getNextBtName(){
        btNameIndex++;
        return btNames[btNameIndex - 1];
    }

    public String getNextBtfName(){
        btfNameIndex++;
        if(btfNameIndex > 2)
            btfFPMaxNum = 1;
        return btfNames[btfNameIndex - 1];
    }

    @Override
    public void evaluate(EvolutionState evolutionState, Individual individual, int i, int i1) {

    }
}
