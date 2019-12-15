package com.company.GA;

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
    private String filename;

    public MyStatisticsSaver(String folder, String name, int experimentNum) {
        statistics = new LinkedList<>();
        totalStatistics = EvolutionStatistics.ofNumber();
        median = new LinkedList<>();
        filename = folder + "/" + name + experimentNum + ".csv";
    }

    public void Save(){
        String data = summarizeData();
        WriteToFile(filename, data);
    }

    private static String summarizeData() {
        StringBuilder builder = new StringBuilder();
        builder.append("avg time for generation:,");
        builder.append(totalStatistics.getEvolveDuration().getMean());
        builder.append("\n");
        builder.append("time for terminating:,");
        builder.append(totalStatistics.getEvolveDuration().getSum());
        builder.append("\n");
        builder.append("genID,max,average,median,min\n");
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
            builder.append("\n");
        }
        return builder.toString();
    }

    public static void update(EvolutionResult<?, Double> result) {
        EvolutionStatistics<Double, ?> curr = EvolutionStatistics.ofNumber();
        curr.accept(result);
        ISeq<? extends Phenotype<?, Double>> pop = result.getPopulation();
        List<Double> medianCalc = new LinkedList<>();
        pop.forEach((pt) -> {
            medianCalc.add(pt.getFitness());
        });
        medianCalc.sort(Double::compareTo);
        int midIndex = medianCalc.size() / 2;
        median.add(medianCalc.get(midIndex));
        statistics.add(curr);
        totalStatistics.accept(result);
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
