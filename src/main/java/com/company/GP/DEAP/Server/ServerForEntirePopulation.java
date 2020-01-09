package com.company.GP.DEAP.Server;

import com.company.GA.GA_Solver;
import com.company.PlayingProcess.GPSimulator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;

public class ServerForEntirePopulation implements HttpHandler {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/TicTacToe", new ServerForEntirePopulation());
        server.setExecutor(GA_Solver.executorService); // creates a default executor
        server.start();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        InputStream res = httpExchange.getRequestBody();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(res));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        JSONObject obj = new JSONObject(content.toString());

        int randomPlayer = obj.getInt("rand");
        boolean playAgainstRandomPlayer = randomPlayer == 1;

        JSONArray c = obj.getJSONArray("population");
        List<GPIndividualTicTacToe> population = new LinkedList<>();

        for (int i = 0 ; i < c.length(); i++) {
            JSONObject instance = c.getJSONObject(i);
            int id = instance.getInt("id");
            String[] currentSolution = new String[10];
            for(int j = 0; j < 10; j++){
                currentSolution[j] = instance.getString("t" + j);
            }
            GPIndividualTicTacToe currentIndividual = new GPIndividualTicTacToe(id, currentSolution);
            population.add(currentIndividual);
        }

        population.parallelStream().forEach(ind -> evaluateIndividual(ind, playAgainstRandomPlayer));
        JSONArray resultArray = new JSONArray();
        for (GPIndividualTicTacToe individual : population) {
            JSONObject currObj = new JSONObject();
            currObj.put("id", individual.getId());
            currObj.put("fitness", individual.getFitness());
            resultArray.put(currObj);
        }
        JSONObject responseJSON = new JSONObject();
        responseJSON.put("population", resultArray);
        String response = responseJSON.toString();

        System.out.println(response);

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void evaluateIndividual(GPIndividualTicTacToe individual, boolean playAgainstRandomPlayer) {
        GPSimulator simulator = new GPSimulator(individual.getCurrentSolution(), GA_Solver.executorService, playAgainstRandomPlayer);

        individual.setFitness(simulator.getFitness());
    }


}
