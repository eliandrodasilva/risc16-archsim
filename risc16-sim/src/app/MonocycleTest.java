package app;

import core.MonocycleProcessor;

public class MonocycleTest {
    public static void main(String[] args) {
        boolean verbose = false;

        MonocycleProcessor monocycleProcessor = new MonocycleProcessor(verbose);
        monocycleProcessor.run("bin/ps.bin");
    }
}
