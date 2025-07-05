package util;

import hardware.CPU;
import hardware.RegisterFile;

public class SimulationReporter {
    public void generateReport(CPU cpu, RegisterFile registerFile) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n--- Processor Execution Dump ---\n");
        sb.append(String.format("%-21s : %4d\n", "CPU Cycles", cpu.getCycles()));
        sb.append(String.format("%-21s : %4d\n", "Fetches", cpu.getFetchCount()));
        sb.append(String.format("%-21s : %4d\n", "Instructions Executed", cpu.getInstructionCount()));

        sb.append("\nFinal Register State:\n");
        sb.append(registerFile.toString());

        System.out.println(sb);
    }
}
