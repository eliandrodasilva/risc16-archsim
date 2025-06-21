package instruction;

public class Instruction {
    private short format, opcode, dest, op1, op2;

    public Instruction(short format, short opcode, short dest, short op1, short op2) {
        this.format = format;
        this.opcode = opcode;
        this.dest = dest;
        this.op1 = op1;
        this.op2 = op2;
    }

    public short getFormat() {
        return format;
    }

    public short getOpcode() {
        return opcode;
    }

    public short getDest() {
        return dest;
    }

    public short getOp1() {
        return op1;
    }

    public short getOp2() {
        return op2;
    }
}
