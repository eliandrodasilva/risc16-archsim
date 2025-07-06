package instruction;

import hardware.CPU;
import hardware.Memory;
import hardware.RegisterFile;

public class InstructionExecutor {
    public void execute(Instruction instruction, CPU cpu, RegisterFile registerFile, Memory memory, StringBuilder outputBuffer) {
        if(instruction.getFormat() == 0) {
            executeFormatR(instruction, cpu, registerFile, memory, outputBuffer);
        } else {
            executeFormatI(instruction, cpu, registerFile);
        }
    }

    private void executeFormatR(Instruction instruction, CPU cpu, RegisterFile registerFile, Memory memory, StringBuilder outputBuffer) {
        short operand1 = (short) registerFile.getRegister(instruction.getOp1());
        short operand2 = (short) registerFile.getRegister(instruction.getOp2());

        switch (instruction.getOpcode()) {
            case 0 -> registerFile.setRegister(instruction.getDest(), (operand1 + operand2));
            case 1 -> registerFile.setRegister(instruction.getDest(), (operand1 - operand2));
            case 2 -> registerFile.setRegister(instruction.getDest(), (operand1 * operand2));
            case 3 -> registerFile.setRegister(instruction.getDest(), (operand1 / operand2));
            case 4 -> registerFile.setRegister(instruction.getDest(), (operand1 == operand2) ? 1 : 0);
            case 5 -> registerFile.setRegister(instruction.getDest(), (operand1 != operand2) ? 1 : 0);
            case 15 ->  registerFile.setRegister(instruction.getDest(), memory.load(operand1));
            case 16 -> memory.store(operand1, operand2);
            case 63 -> {
                switch (operand1) {
                    case 0 -> cpu.setRunning(false);
                    case 1 -> {
                        short address = (short) registerFile.getRegister(1);
                        short value;

                        StringBuilder sb = new StringBuilder();

                        while ((value = memory.load(address)) != 0) {
                            outputBuffer.append((char) value);
                            address++;
                        }

                        System.out.print(sb);
                    }
                    case 2 -> outputBuffer.append("\n");// System.out.println();
                    case 3 -> outputBuffer.append(registerFile.getRegister(1)); // System.out.print(registerFile.getRegister(1));
                }
            }
        }
    }

    private void executeFormatI(Instruction instruction, CPU cpu, RegisterFile registerFile) {
        switch (instruction.getOpcode()) {
            case 0 -> cpu.setPC(instruction.getOp1());
            case 1 -> {
                if (registerFile.getRegister(instruction.getDest()) == 1) cpu.setPC(instruction.getOp1());
            }
            case 3 -> registerFile.setRegister(instruction.getDest(), instruction.getOp1());
        }
    }
}
