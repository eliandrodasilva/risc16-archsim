package predictor;

public class TwoBitsPredictor {
    private final short[] branchHistoryTable;
    private final int size;
    private boolean verbose = false;

    private short lastPC = -1;
    private short lastPredictedTarget = -1;
    private boolean lastPredictionTaken = false;
    private boolean wasLastPredictionCorrect;

    private int totalPredictions = 0;
    private int correctPredictions = 0;
    private int incorrectPredictions = 0;

    public TwoBitsPredictor() {
        this(512);
    }

    public TwoBitsPredictor(int size) {
        this.size = size;
        branchHistoryTable = new short[size];

        for (int i = 0; i < size; i++) {
            branchHistoryTable[i] = 2; // Estado inicial: Weakly Taken
        }
    }

    public short predict(short pc, short target) {
        int index = pc % size;
        short nextPC = (short)(pc + 1);
        totalPredictions++;

        lastPC = pc;

        // Se o estado é >= 2 (Weakly Taken ou Strongly Taken), então jumpa
        if(branchHistoryTable[index] >= 2) {
            lastPredictedTarget = target;
            lastPredictionTaken = true;
            if(verbose) {
                System.out.println(String.format("[BRANCH PREDICTED]: TAKEN     | PC: %d | Target: %d | Predicted Jump: YES", pc, target));
            }
            return lastPredictedTarget;
        } else {
            // Estado < 2 (Strongly Not Taken ou Weakly Not Taken), não jumpa
            lastPredictedTarget = nextPC;
            lastPredictionTaken = false;
            if(verbose) {
                System.out.println(String.format("[BRANCH PREDICTED]: NOT TAKEN | PC: %d | Target: %d | Predicted Jump: NO", pc, target));
            }            return nextPC;
        }

    }

    public void update(short pc, boolean taken) {
        int index = pc % size;

        wasLastPredictionCorrect = (lastPredictionTaken == taken);

        if (wasLastPredictionCorrect) {
            correctPredictions++;
        } else {
            incorrectPredictions++;
        }

        if (taken) {
            if (branchHistoryTable[index] < 3) {
                branchHistoryTable[index]++;
            }
        } else {
            if (branchHistoryTable[index] > 0) {
                branchHistoryTable[index]--;
            }
        }
    }

    public short getLastPC() {
        return lastPC;
    }

    public int getTotalPredictions() {
        return totalPredictions;
    }

    public int getCorrectPredictions() {
        return correctPredictions;
    }

    public int getIncorrectPredictions() {
        return incorrectPredictions;
    }

    public boolean getLastPredictionTaken() {
        return lastPredictionTaken;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isLastPredictionCorrect() {
        return wasLastPredictionCorrect;
    }
}
