package core;

import instruction.Instruction;

public class MonocycleProcessor extends Processor {
    private final boolean verbose;

    public MonocycleProcessor(boolean verbose) {
        this.verbose = verbose;
    }

    @Override
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

            executor.execute(decodedInstruction, cpu, registerFile, memory);
            cpu.incrementInstructionCount();
        }

        cpu.dump(registerFile);
    }
}
