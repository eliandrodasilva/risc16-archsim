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

                if(decodedInstruction.getFormat() == 1 && decodedInstruction.getOpcode() == 0 && !predictor.isLastPredictionTaken()) {
                    // JUMP incond
                    pipeline.clear();
                    cpu.setPC(predictor.getLastPC());
                } else if(decodedInstruction.getFormat() == 1 && decodedInstruction.getOpcode() == 1) {
                    // JUMP cond
                }
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
            short pcNow = cpu.getPC();
            short raw   = memory.load(cpu.getPC());

            cpu.setPC(predictor.predict(cpu.getPC()));
            nextPipeline.setFetch((memory.load(cpu.getPC())));


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