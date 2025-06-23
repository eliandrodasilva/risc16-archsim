package core;

import hardware.CPU;
import hardware.Memory;
import hardware.Registers;
import instruction.Instruction;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ProcessorCore {
    private CPU cpu = new CPU();
    private Memory memory = new Memory(64 * 1024);
    private Registers registers = new Registers(8);

    private DecoderUnit decoder = new DecoderUnit();
    private ExecutionUnit executor = new ExecutionUnit();

    public void run(String binaryPath) {
        loadBinary(binaryPath);

        cpu.setPC((short)1);
        cpu.setRunning(true);

        while(cpu.isRunning()) {
            short rawInstruction = memory.load(cpu.getPC());
            cpu.incrementPC();
            cpu.incrementCycle();

            Instruction decodedInstruction = decoder.decode(rawInstruction);
            System.out.printf("[PC]: " + (cpu.getPC()-1) + " | ");
            System.out.println(decodedInstruction);

            executor.execute(decodedInstruction, cpu, registers, memory);
        }
        System.out.println(registers);
        System.out.printf("CPU has executed %d cycle(s).\n", cpu.getCycles());
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
