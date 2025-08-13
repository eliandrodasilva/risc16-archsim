package app;

import core.PipelineProcessor;
import core.MonocycleProcessor;

public class Archsim {
    public static void main(String[] args) {
        boolean verbose = false;
        boolean pipeline = false;
        String fileName = null;

        if (args.length == 0) {
            System.out.println("Use: java Archsim <file.bin> [--verbose] [--pipeline]");
            System.exit(1);
        }

        for (String arg : args) {
            if (arg.equalsIgnoreCase("--verbose")) {
                verbose = true;
            } else if (arg.equalsIgnoreCase("--pipeline")) {
                pipeline = true;
            } else if (arg.toLowerCase().endsWith(".bin")) {
                fileName = arg;
            }
        }

        if (fileName == null) {
            System.out.println("Error: No .bin file specified");
            System.exit(1);
        }

        String filePath = "bin/" + fileName;

        if (pipeline) {
            PipelineProcessor processor = new PipelineProcessor(verbose);
            processor.run(filePath);
        } else {
            MonocycleProcessor processor = new MonocycleProcessor(verbose);
            processor.run(filePath);
        }
    }
}


