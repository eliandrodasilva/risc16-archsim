package instruction;

import util.BitUtils;

public class InstructionDecoder {
    public Instruction decode(short raw) {
        short format = BitUtils.extractBits(raw, 15, 1);
        if(format == 0) {
            return new Instruction(
                    format,
                    BitUtils.extractBits(raw, 9, 6),
                    BitUtils.extractBits(raw, 6, 3),
                    BitUtils.extractBits(raw, 3, 3),
                    BitUtils.extractBits(raw, 0, 3)
            );
        } else {
            return new Instruction(
                    format,
                    BitUtils.extractBits(raw, 13, 2),
                    BitUtils.extractBits(raw, 10, 3),
                    BitUtils.extractBits(raw, 0, 10),
                    (short) 0
            );
        }
    }

    public short[] preDecode(short raw) {
        short format = BitUtils.extractBits(raw, 15, 1);
        short opcode = BitUtils.extractBits(raw, 13, 2);
        short dest = BitUtils.extractBits(raw, 10, 3);
        short op1 = BitUtils.extractBits(raw, 0, 10);
        return new short[]{format, opcode, op1, dest};
    }
}
