package core;

import hardware.CPU;
import hardware.Memory;
import hardware.Registers;
import instruction.Instruction;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ProcessorCore {
    private CPU cpu = new CPU();
    private Memory memory = new Memory(64 * 1024);
    private Registers registers = new Registers(8);
    private final boolean verbose;

    private InstructionDecoder decoder = new InstructionDecoder();
    private InstructionExecutor executor = new InstructionExecutor();

    public ProcessorCore(boolean verbose) {
        this.verbose = verbose;
    }

    public void run(String binaryPath) {
        loadBinary(binaryPath);

        cpu.setPC((short)1);
        cpu.setRunning(true);

        while(cpu.isRunning()) {
            short rawInstruction = memory.load(cpu.getPC());
            cpu.incrementPC();
            cpu.incrementCycle();
            cpu.incrementFetchCount();

            Instruction decodedInstruction = decoder.decode(rawInstruction);

            if (verbose) {
                System.out.printf("[PC]: %2d | ", cpu.getPC()-1);
                System.out.println(decodedInstruction);
            }

            executor.execute(decodedInstruction, cpu, registers, memory);
            cpu.incrementInstructionCount();
        }

        dump();
    }

    private void dump() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n--- Processor Execution Dump ---\n");
        sb.append(String.format("%-21s : %4d\n", "CPU Cycles", cpu.getCycles()));
        sb.append(String.format("%-21s : %4d\n", "Fetches", cpu.getFetchCount()));
        sb.append(String.format("%-21s : %4d\n", "Instructions Executed", cpu.getInstructionCount()));

        sb.append("\nFinal Register State:\n");
        sb.append(registers.toString());

        System.out.println(sb);
    }

    private void loadBinary(String binaryPath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(binaryPath);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);

            long fileSize = fileInputStream.getChannel().size();
            int numShorts = (int) (fileSize / 2);

            for (int i = 0; i < numShorts; i++) {
                int low = dataInputStream.readByte() & 0xFF;
                int high = dataInputStream.readByte() & 0xFF;
                int value = (low | (high << 8)) & 0xFFFF;

                memory.store((short) i, (short) value);
            }

            dataInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
