package app;

import core.PipelineProcessor;

public class PipelineTest {
    public static void main(String[] args) {
        boolean verbose = false;

        PipelineProcessor pipelineProcessor = new PipelineProcessor(verbose);
        pipelineProcessor.run("bin/perfect-squares-large.bin");
    }
}
