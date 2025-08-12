package util;

import hardware.CPU;
import hardware.RegisterFile;
import predictor.TwoBitsPredictor;

public class SimulationReporter {
    public void generateReport(CPU cpu, RegisterFile registerFile, TwoBitsPredictor predictor) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n--- Processor Execution Dump ---\n");
        sb.append(String.format("%-21s : %4d\n", "CPU Cycles", cpu.getCycles()));
        sb.append(String.format("%-21s : %4d\n", "Fetches", cpu.getFetchCount()));
        sb.append(String.format("%-21s : %4d\n", "Instructions Executed", cpu.getInstructionCount()));

        if(predictor != null) {
            int correct = predictor.getCorrectPredictions();
            int incorrect = predictor.getIncorrectPredictions();
            int total = predictor.getTotalPredictions();
            double accuracy = total > 0 ? ((double) correct / total) * 100 : 0.0;
            sb.append("\n--- Branch Predictor Report ---\n");
            sb.append(String.format("%-23s : %4d\n", "Total Predictions", total));
            sb.append(String.format("%-23s : %4d\n", "Correct Predictions", correct));
            sb.append(String.format("%-23s : %4d\n", "Incorrect Predictions", incorrect));
            sb.append(String.format("%-23s : %5.1f%%\n", "Accuracy Rate", accuracy));
        }

        sb.append("\nFinal Register State:\n");
        sb.append(registerFile.toString());

        System.out.println(sb);
    }
}
