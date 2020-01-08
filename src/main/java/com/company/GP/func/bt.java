package com.company.GP.func;

import com.company.GP.TicTacToe;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

public class bt extends GPNode {
    @Override
    public String toString() {
        return "bt";
    }

    public int expectedChildren() { return 1; }

    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {
        TicTacToe p = (TicTacToe)problem;
        p.tree.append("bp.registerBThread(\"").append(p.getNextBtName()).append("\",function(){");
        children[0].eval(evolutionState,i,gpData,adfStack,gpIndividual,problem);
        p.tree.append("});");
    }
}
