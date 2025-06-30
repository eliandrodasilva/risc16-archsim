package app;

import core.MonocycleProcessor;

public class Main {
    public static void main(String[] args) {
        boolean verbose = false;
//        if(args.length != 1) {
//            System.out.println("Usage: java Main <binary_file.bin>");
//        }
//        if (args[1].equals("--verbose")) {
//            verbose = true;
//        }

        MonocycleProcessor monocycleProcessor = new MonocycleProcessor(verbose);
        monocycleProcessor.run("bin/ps.bin");
    }
}
