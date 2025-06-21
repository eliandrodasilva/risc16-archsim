package instruction;

public class Instruction {
    private int format, opcode, dest, op1, op2;

    public Instruction(int format, int opcode, int dest, int op1, int op2) {
        this.format = format;
        this.opcode = opcode;
        this.dest = dest;
        this.op1 = op1;
        this.op2 = op2;
    }

    public int getFormat() {
        return format;
    }

    public int getOpcode() {
        return opcode;
    }

    public int getDest() {
        return dest;
    }

    public int getOp1() {
        return op1;
    }

    public int getOp2() {
        return op2;
    }
}
