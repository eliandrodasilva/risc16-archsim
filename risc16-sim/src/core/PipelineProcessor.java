package core;

import instruction.Instruction;
import util.SimulationReporter;
import predictor.TwoBitsPredictor;

import java.util.Scanner;

public class PipelineProcessor extends Processor {
    private final boolean verbose;
    private final SimulationReporter reporter;
    private final TwoBitsPredictor predictor = new TwoBitsPredictor();
    private final Pipeline pipeline = new Pipeline();
    private final Pipeline nextPipeline = new Pipeline();

    public PipelineProcessor(boolean verbose) {
        this.verbose = verbose;
        this.reporter = new SimulationReporter();
    }

    public void run(String binaryPath) {
        loadBinary(binaryPath);

        cpu.setPC((short)1);
        cpu.setRunning(true);

        while(cpu.isRunning()) {
            // --- EXECUTE and WRITE BACK--- (EX-WB)
            if(pipeline.decodedInstruction != null) {
                Instruction decodedInstruction = pipeline.decodedInstruction;
                executor.execute(decodedInstruction, cpu, registerFile, memory, outputBuffer);
            }

            // --- DECODE --- (ID)
            if(pipeline.fetch != null) {
                nextPipeline.setDecodedInstruction(decoder.decode(pipeline.fetch));
                if (verbose) {
                    System.out.printf("[PC]: %2d | ", cpu.getPC()-1);
                    System.out.println(decoder.decode(pipeline.fetch));
                }
            }

            // --- FETCH --- (IF)
            short[] preDecoded = decoder.preDecode(memory.load(cpu.getPC()));

            if (preDecoded[0] == 1 && preDecoded[1] == 0) {
                cpu.setPC(preDecoded[2]);
            } else if (preDecoded[0] == 1 && preDecoded[1] == 1) {
                // JUMP cond
                cpu.setPC(preDecoded[2]);
            }
            short raw = memory.load(cpu.getPC());
            nextPipeline.setFetch(raw);
            cpu.incrementPC();


            cpu.incrementFetchCount();

            // --- UPDATE PIPELINE ---
            pipeline.copyFrom(nextPipeline);
            nextPipeline.clear();

            //new Scanner(System.in).nextLine();

            cpu.incrementCycle();
        }

        reporter.generateReport(cpu, registerFile);
    }
}