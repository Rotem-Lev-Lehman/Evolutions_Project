package com.company.GA;

import com.company.PlayingProcess.GASimulator;
import com.company.PlayingProcess.Game;
import il.ac.bgu.cs.bp.bpjs.internal.ExecutorServiceMaker;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Limits;
import io.jenetics.util.Factory;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class GA_Solver {
    public static final int SIZE_OF_SOLUTION = 10;
    private static final int POPULATION_SIZE = 50;
    private static final long AMOUNT_OF_GENERATIONS = 150;
    private static final AtomicInteger INSTANCE_COUNTER = new AtomicInteger();
    public static ExecutorService executorService = ExecutorServiceMaker.makeWithName("BProgramRunner-" + INSTANCE_COUNTER.incrementAndGet());

    private final double crossP;
    private final int crossN;
    private final double mutP;

    public GA_Solver(double crossP, int crossN, double mutP) {
        this.crossP = crossP;
        this.crossN = crossN;
        this.mutP = mutP;
    }

    public void Solve() {

        Factory<Genotype<IntegerGene>> gtf = Genotype.of(IntegerChromosome.of(1, 11), SIZE_OF_SOLUTION);

        Engine<IntegerGene, Double> engine = Engine.builder(GA_Solver::eval, gtf)
                .populationSize(POPULATION_SIZE)
                .alterers(new MultiPointCrossover<>(crossP, crossN), new Mutator<>(mutP))
                .selector(new TournamentSelector<>())
                .maximizing()
                .build();

        Genotype<IntegerGene> result = engine.stream()
                .limit(Limits.byFixedGeneration(AMOUNT_OF_GENERATIONS))
                .peek(MyStatisticsSaver::update)
                .collect(EvolutionResult.toBestGenotype());
        System.out.println("Fitness = " + eval(result));
        System.out.println(Arrays.toString(getWeights(result)));

    }

    private static double eval(Genotype<IntegerGene> gt) {
        Integer[] weights = getWeights(gt);
        //calculate the fitness by running it in the simulator:
        GASimulator simulator = new GASimulator(weights, executorService);
        return simulator.getFitness();
    }

    public static Integer[] getWeights(Genotype<IntegerGene> gt) {
        Integer[] weights = new Integer[SIZE_OF_SOLUTION];
        for (int i = 0; i < gt.length(); i++) {
            weights[i] = gt.getChromosome(i).stream().mapToInt(IntegerGene::intValue).toArray()[0];
        }
        return weights;
    }
}
