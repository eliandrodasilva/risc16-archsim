package predictor;

public class TwoBitsPredictor {
    private final short[] branchHistoryTable;
    private final short[] branchTargetBuffer;
    private final short[] branches;
    private final int size;

    public TwoBitsPredictor() {
        this(512);
    }

    public TwoBitsPredictor(int size) {
        this.size = size;
        branchHistoryTable = new short[size];
        branchTargetBuffer = new short[size];
        branches = new short[size];

        for (int i = 0; i < size; i++) {
            branchHistoryTable[i] = 2;
            branchTargetBuffer[i] = -1;
            branches[i] = -1;
        }
    }

    public short predict(short pc) {
        int index = pc % size;
        short nextPC = (short)(pc + 1);

        if(branches[index] != pc) {
            return nextPC;
        }
        if(branchHistoryTable[index] >= 2) {
            return branchTargetBuffer[index];
        } else {
            return nextPC;
        }
    }

    public void update(short pc, short target, boolean taken) {
        int index = pc % size;
        branches[index] = pc;
        branchTargetBuffer[index] = target;

        if (taken) {
            if (branchHistoryTable[index] < 3) branchHistoryTable[index]++;
        } else {
            if (branchHistoryTable[index] > 0) branchHistoryTable[index]--;
        }
    }
}

