package com.company.GP;

import com.company.GA.GA_Solver;
import com.company.PlayingProcess.GPSimulator;

public class Main_GP {
    static String[] optimalSolution = {
            "bp.registerBThread(\"AddThirdO(<\" + f[p[0]].x + \",\" + f[p[0]].y + \">,\" + \"<\" + f[p[1]].x + \",\" + f[p[1]].y + \">,\" + \"<\" + f[p[2]].x + \",\" + f[p[2]].y + \">)\", function() {\n" +
                    "\t\twhile (true) {\n" +
                    "\t\t\tbp.sync({ waitFor:[ O(f[p[0]].x, f[p[0]].y) ] });\n" +
                    "\n" +
                    "\t\t\tbp.sync({ waitFor:[ O(f[p[1]].x, f[p[1]].y) ] });\n" +
                    "\n" +
                    "\t\t\tbp.sync({ request:[ O(f[p[2]].x, f[p[2]].y) ] }, 50);\n" +
                    "\t\t}\n" +
                    "\t});",
            "bp.registerBThread(\"PreventThirdX(<\" + f[p[0]].x + \",\" + f[p[0]].y + \">,\" + \"<\" + f[p[1]].x + \",\" + f[p[1]].y + \">,\" + \"<\" + f[p[2]].x + \",\" + f[p[2]].y + \">)\", function() {\n" +
                    "\t\twhile (true) {\n" +
                    "\t\t\tbp.sync({ waitFor:[ X(f[p[0]].x, f[p[0]].y) ] });\n" +
                    "\n" +
                    "\t\t\tbp.sync({ waitFor:[ X(f[p[1]].x, f[p[1]].y) ] });\n" +
                    "\n" +
                    "\t\t\tbp.sync({ request:[ O(f[p[2]].x, f[p[2]].y) ] }, 40);\n" +
                    "\t\t}\n" +
                    "\t});",
            "bp.registerBThread(\"PreventFork22X(<\" + f[p[0]].x + \",\" + f[p[0]].y + \">,\" + \"<\" + f[p[1]].x + \",\" + f[p[1]].y + \">)\", function() {\n" +
                    "\t\twhile (true) {\n" +
                    "\t\t\tbp.sync({ waitFor:[ X(f[p[0]].x, f[p[0]].y) ] });\n" +
                    "\n" +
                    "\t\t\tbp.sync({ waitFor:[ X(f[p[1]].x, f[p[1]].y) ] });\n" +
                    "\n" +
                    "\t\t\tbp.sync({ request:[ O(2, 2), O(0,2), O(2,0) ] }, 30);\n" +
                    "\t\t}\n" +
                    "\t});",
            "bp.registerBThread(\"PreventFork02X(<\" + f[p[0]].x + \",\" + f[p[0]].y + \">,\" + \"<\" + f[p[1]].x + \",\" + f[p[1]].y + \">)\", function() {\n" +
                    "\t\twhile (true) {\n" +
                    "\t\t\tbp.sync({ waitFor:[ X(f[p[0]].x, f[p[0]].y) ] });\n" +
                    "\n" +
                    "\t\t\tbp.sync({ waitFor:[ X(f[p[1]].x, f[p[1]].y) ] });\n" +
                    "\n" +
                    "\t\t\tbp.sync({ request:[ O(0, 2), O(0,0), O(2,2) ] }, 30);\n" +
                    "\t\t}\n" +
                    "\t});",
            "bp.registerBThread(\"PreventFork20X(<\" + f[p[0]].x + \",\" + f[p[0]].y + \">,\" + \"<\" + f[p[1]].x + \",\" + f[p[1]].y + \">)\", function() {\n" +
                    "\t\twhile (true) {\n" +
                    "\t\t\tbp.sync({ waitFor:[ X(f[p[0]].x, f[p[0]].y) ] });\n" +
                    "\n" +
                    "\t\t\tbp.sync({ waitFor:[ X(f[p[1]].x, f[p[1]].y) ] });\n" +
                    "\n" +
                    "\t\t\tbp.sync({ request:[ O(2, 0), O(0,0), O(2,2) ] }, 30);\n" +
                    "\t\t}\n" +
                    "\t});",
            "bp.registerBThread(\"PreventFork00X(<\" + f[p[0]].x + \",\" + f[p[0]].y + \">,\" + \"<\" + f[p[1]].x + \",\" + f[p[1]].y + \">)\", function() {\n" +
                    "\t\twhile (true) {\n" +
                    "\t\t\tbp.sync({ waitFor:[ X(f[p[0]].x, f[p[0]].y) ] });\n" +
                    "\n" +
                    "\t\t\tbp.sync({ waitFor:[ X(f[p[1]].x, f[p[1]].y) ] });\n" +
                    "\n" +
                    "\t\t\tbp.sync({ request:[ O(0, 0), O(0,2), O(2,0) ] }, 30);\n" +
                    "\t\t}\n" +
                    "\t});",
            "bp.registerBThread(\"PreventForkdiagX(<\" + f[p[0]].x + \",\" + f[p[0]].y + \">,\" + \"<\" + f[p[1]].x + \",\" + f[p[1]].y + \">)\", function() {\n" +
                    "\t\twhile (true) {\n" +
                    "\t\t\tbp.sync({ waitFor:[ X(f[p[0]].x, f[p[0]].y) ] });\n" +
                    "\n" +
                    "\t\t\tbp.sync({ waitFor:[ X(f[p[1]].x, f[p[1]].y) ] });\n" +
                    "\n" +
                    "\t\t\tbp.sync({ request:[ O(0, 1), O(1, 0), O(1, 2), O(2, 1) ] }, 30);\n" +
                    "\t\t}\n" +
                    "\t});",
            "bp.registerBThread(\"Center\", function() {\n" +
                    "\twhile (true) {\n" +
                    "\t\tbp.sync({ request:[ O(1, 1) ] }, 35);\n" +
                    "\t}\n" +
                    "});",
            "bp.registerBThread(\"Corners\", function() {\n" +
                    "\twhile (true) {\n" +
                    "\t\tbp.sync({ request:[ O(0, 0), O(0, 2), O(2, 0), O(2, 2) ] }, 20);\n" +
                    "\n" +
                    "\t}\n" +
                    "});",
            "bp.registerBThread(\"Sides\", function() {\n" +
                    "\twhile (true) {\n" +
                    "\t\tbp.sync({ request:[ O(0, 1), O(1, 0), O(1, 2), O(2, 1) ] }, 10);\n" +
                    "\t}\n" +
                    "});"
    };
    public static void main(String[] args) {
        GPSimulator simulator = new GPSimulator(optimalSolution, GA_Solver.executorService, true);
        System.out.println(simulator.getFitness());
        GA_Solver.executorService.shutdown();
    }
}
