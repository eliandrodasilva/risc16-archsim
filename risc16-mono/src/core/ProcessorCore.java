package core;

import hardware.CPU;
import hardware.Memory;
import instruction.Instruction;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ProcessorCore {
    private CPU cpu = new CPU();
    private Memory memory = new Memory(64 * 1024);

    private DecoderUnit decoder = new DecoderUnit();

    public void run(String binaryPath) {
        loadBinary(binaryPath);

        cpu.setPC((short)1);
        cpu.setRunning(true);

        while(cpu.isRunning()) {
            short rawInstruction = memory.load(cpu.getPC());
            cpu.incrementPC();
            System.out.println(rawInstruction);

            Instruction decodedInstruction = decoder.decode(rawInstruction);
            System.out.println(decodedInstruction);
            if(decodedInstruction.getFormat() == 0 && decodedInstruction.getOpcode() == 63) {
                cpu.setRunning(false);
            }
        }
    }

    private void loadBinary(String binaryPath) {
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
