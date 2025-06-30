package core;

import hardware.CPU;
import hardware.Memory;
import hardware.RegisterFile;
import instruction.InstructionDecoder;
import instruction.InstructionExecutor;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

abstract class Processor {
    protected final CPU cpu = new CPU();
    protected final Memory memory = new Memory(64 * 1024);
    protected final RegisterFile registerFile = new RegisterFile(8);
    protected final boolean verbose;

    protected final InstructionDecoder decoder = new InstructionDecoder();
    protected final InstructionExecutor executor = new InstructionExecutor();

    public Processor(boolean verbose) {
        this.verbose = verbose;
    }

    public abstract void run(String binaryPath);

    protected void loadBinary(String binaryPath) {
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
