package core;

import instruction.Instruction;
import util.SimulationReporter;
import predictor.TwoBitsPredictor;

public class PipelineProcessor extends Processor {
    private final boolean verbose;
    private final SimulationReporter reporter;
    private final TwoBitsPredictor predictor = new TwoBitsPredictor();
    private final Pipeline pipeline = new Pipeline();

    public PipelineProcessor(boolean verbose) {
        this.verbose = verbose;
        if(verbose) {
            predictor.setVerbose(true);
        }
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

                if(decodedInstruction.getFormat() == 1 && decodedInstruction.getOpcode() == 1) {
                    predictor.update(predictor.getLastPC(), executor.getTaken());

                    if(!predictor.isLastPredictionCorrect()){
                        if(verbose) {
                            System.out.printf("[BRANCH MISPREDICT]: PC: %d | Target: %d | Predicted: %s | Actual: %s%n", cpu.getPC()-1, decodedInstruction.getOp1(), (!executor.getTaken()) ? "TAKEN" : "NOT TAKEN", executor.getTaken() ? "TAKEN" : "NOT TAKEN");
                            System.out.printf("[PIPELINE FLUSHED]: Misprediction at PC: %d | Flushing invalid instructions%n", cpu.getPC()-1);
                        }
                        cpu.setPC(predictor.getLastPC());
                        pipeline.clear();
                    }
                }
                cpu.incrementInstructionCount();
            }

            // --- DECODE --- (ID)
            if(pipeline.fetch != null) {
                pipeline.setDecodedInstruction(decoder.decode(pipeline.fetch));
                if (verbose) {
                    System.out.printf("[PC DECODED]: %2d | ", cpu.getPC()-1);
                    System.out.println(pipeline.decodedInstruction);
                }
            }

            // --- FETCH --- (IF)
            short[] preDecoded = decoder.preDecode(memory.load(cpu.getPC()));

            short raw = memory.load(cpu.getPC());
            pipeline.setFetch(raw);

            if (preDecoded[0] == 1 && preDecoded[1] == 0) {
                cpu.setPC(preDecoded[2]);
            } else if (preDecoded[0] == 1 && preDecoded[1] == 1) {
                // JUMP cond
                short predictorPC = predictor.predict(cpu.getPC(), preDecoded[2]);
                cpu.setPC(predictorPC);
            } else {
                cpu.incrementPC();
            }

            cpu.incrementFetchCount();
            cpu.incrementCycle();
        }
        reporter.generateReport(cpu, memory, registerFile, predictor);
    }
}