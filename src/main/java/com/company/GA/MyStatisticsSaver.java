package com.company.GA;

import com.company.PlayingProcess.Simulator;
import io.jenetics.Genotype;
import io.jenetics.IntegerGene;
import io.jenetics.Phenotype;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.stat.DoubleMomentStatistics;
import io.jenetics.util.ISeq;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MyStatisticsSaver {
    private static List<EvolutionStatistics<Double, ?>> statistics;
    private static EvolutionStatistics<Double, ?> totalStatistics;
    private static List<Double> median;
    private static List<Integer[]> bestIndWeights;
    private static int generationNum;
    public static int UPDATE_USER_EVERY_GENERATION_NUM = 5;
    public static int PLAY_AGAINST_OPTIMAL_AT_GENERATION = 100;
    private static int nextTimeToUpdateUser;
    private String filename;

    public MyStatisticsSaver(String folder, String name, int experimentNum) {
        statistics = new LinkedList<>();
        totalStatistics = EvolutionStatistics.ofNumber();
        median = new LinkedList<>();
        bestIndWeights = new LinkedList<>();
        generationNum = 0;
        nextTimeToUpdateUser = UPDATE_USER_EVERY_GENERATION_NUM;
        filename = folder + "/" + name + "_" + experimentNum + ".csv";
    }

    public void Save(double crossP, int crossN, double mutP){
        String data = summarizeData(crossP, crossN, mutP);
        WriteToFile(filename, data);
    }

    private static String summarizeData(double crossP, int crossN, double mutP) {
        StringBuilder builder = new StringBuilder();
        builder.append("cross over p:,");
        builder.append(crossP);
        builder.append("\n");
        builder.append("cross over n (num of points for crossover):,");
        builder.append(crossN);
        builder.append("\n");
        builder.append("mutation p:,");
        builder.append(mutP);
        builder.append("\n");
        builder.append("avg time for generation:,");
        builder.append(totalStatistics.getEvolveDuration().getMean());
        builder.append("\n");
        builder.append("time for terminating:,");
        builder.append(totalStatistics.getEvolveDuration().getSum());
        builder.append("\n");
        builder.append("genID,max,average,median,min,best individual weights:");
        for(int i = 0; i < GA_Solver.SIZE_OF_SOLUTION; i++){
            builder.append(",w["+i+"]");
        }
        builder.append("\n");
        for (int i = 0; i < statistics.size(); i++) {
            builder.append(i);
            builder.append(",");
            builder.append(((DoubleMomentStatistics) statistics.get(i).getFitness()).toDoubleMoments().getMax());
            builder.append(",");
            builder.append(((DoubleMomentStatistics) statistics.get(i).getFitness()).toDoubleMoments().getMean());
            builder.append(",");
            builder.append(median.get(i));
            builder.append(",");
            builder.append(((DoubleMomentStatistics) statistics.get(i).getFitness()).toDoubleMoments().getMin());
            builder.append(",");
            for(int j = 0; j < GA_Solver.SIZE_OF_SOLUTION; j++){
                builder.append("," + bestIndWeights.get(i)[j]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public static void update(EvolutionResult<IntegerGene, Double> result) {
        EvolutionStatistics<Double, ?> curr = EvolutionStatistics.ofNumber();
        curr.accept(result);
        ISeq<Phenotype<IntegerGene, Double>> pop = result.getPopulation();
        List<Double> medianCalc = new LinkedList<>();
        var ref = new Object() {
            double maxFitness = -Double.MAX_VALUE;
            Genotype<IntegerGene> fittest = null;
        };
        pop.forEach((pt) -> {
            double currFitness = pt.getFitness();
            medianCalc.add(currFitness);

            if(currFitness > ref.maxFitness){
                ref.maxFitness = currFitness;
                ref.fittest = pt.getGenotype();
            }
        });
        Integer[] weightsForFittestIndividual = GA_Solver.getWeights(ref.fittest);
        medianCalc.sort(Double::compareTo);
        int midIndex = medianCalc.size() / 2;
        median.add(medianCalc.get(midIndex));
        bestIndWeights.add(weightsForFittestIndividual);
        statistics.add(curr);
        totalStatistics.accept(result);

        //update the user:
        if(generationNum >= nextTimeToUpdateUser){
            nextTimeToUpdateUser += UPDATE_USER_EVERY_GENERATION_NUM;
            System.out.println("Current generation is: " + generationNum);
        }

        if(generationNum >= PLAY_AGAINST_OPTIMAL_AT_GENERATION){
            Simulator.playingAgainstRandom = false;
        }
        generationNum++;
    }

    private static void WriteToFile(String fileName, String data) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(data);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
