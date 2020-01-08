package com.company.GP.func;

import com.company.GP.TicTacToe;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

public class while_truef2 extends GPNode {
    @Override
    public String toString() {
        return "while_truef2";
    }
    public int expectedChildren() { return 2; }


    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {
        TicTacToe p = (TicTacToe)problem;
        p.tree.append("while (true) {");
        children[0].eval(evolutionState,i,gpData,adfStack,gpIndividual,problem);
        children[1].eval(evolutionState,i,gpData,adfStack,gpIndividual,problem);
        p.tree.append("}");
    }
}
