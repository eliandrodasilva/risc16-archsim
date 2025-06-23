package core;

import hardware.CPU;
import hardware.Memory;
import hardware.Registers;
import instruction.Instruction;

public class ExecutionUnit {
    public void execute(Instruction instruction, CPU cpu, Registers registers, Memory memory) {
        if(instruction.getFormat() == 0) {
            executeFormatR(instruction, cpu, registers);
        } else {
            executeFormatI(instruction, cpu, registers);
        }
    }

    private void executeFormatR(Instruction instruction, CPU cpu, Registers registers) {
        switch (instruction.getOpcode()) {
            case 63 -> {
                if (instruction.getOp1() == 0) cpu.setRunning(false);
            }
        }
    }

    private void executeFormatI(Instruction instruction, CPU cpu, Registers registers) {
        switch (instruction.getOpcode()) {
            case 0 -> {
                cpu.setPC(instruction.getOp1());
            }
            case 3 -> registers.setRegisters(instruction.getDest(), instruction.getOp1());
        }
    }
}
