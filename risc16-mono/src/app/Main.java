package app;

import core.ProcessorCore;

public class Main {
    public static void main(String[] args) {
//        if(args.length != 1) {
//            System.out.println("Usage: java Main <binary_file.bin>");
//        }
        ProcessorCore processorCore = new ProcessorCore();
        processorCore.run("bin/sim.bin");
    }
}
