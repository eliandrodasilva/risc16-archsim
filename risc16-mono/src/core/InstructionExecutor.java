package core;

import hardware.CPU;
import hardware.Memory;
import hardware.Registers;
import instruction.Instruction;

public class InstructionExecutor {
    public void execute(Instruction instruction, CPU cpu, Registers registers, Memory memory) {
        if(instruction.getFormat() == 0) {
            executeFormatR(instruction, cpu, registers, memory);
        } else {
            executeFormatI(instruction, cpu, registers);
        }
    }

    private void executeFormatR(Instruction instruction, CPU cpu, Registers registers, Memory memory) {
        short operand1 = (short) registers.getRegister(instruction.getOp1());
        short operand2 = (short) registers.getRegister(instruction.getOp2());

        switch (instruction.getOpcode()) {
            case 0 -> registers.setRegisters(instruction.getDest(), (operand1 + operand2));
            case 1 -> registers.setRegisters(instruction.getDest(), (operand1 - operand2));
            case 2 -> registers.setRegisters(instruction.getDest(), (operand1 * operand2));
            case 3 -> registers.setRegisters(instruction.getDest(), (operand1 / operand2));
            case 4 -> registers.setRegisters(instruction.getDest(), (operand1 == operand2) ? 1 : 0);
            case 5 -> registers.setRegisters(instruction.getDest(), (operand1 != operand2) ? 1 : 0);
            case 15 ->  registers.setRegisters(instruction.getDest(), memory.load(operand1));
            case 16 -> memory.store(operand1, operand2);
            case 63 -> {
                if (operand1 == 0) cpu.setRunning(false);
            }
        }
    }

    private void executeFormatI(Instruction instruction, CPU cpu, Registers registers) {
        switch (instruction.getOpcode()) {
            case 0 -> cpu.setPC(instruction.getOp1());
            case 1 -> {
                if (registers.getRegister(instruction.getDest()) == 1) cpu.setPC(instruction.getOp1());
            }
            case 3 -> registers.setRegisters(instruction.getDest(), instruction.getOp1());
        }
    }
}
