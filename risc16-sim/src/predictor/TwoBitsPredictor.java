package predictor;

public class TwoBitsPredictor {
    private final short[] branchHistoryTable;
    private final short[] branchTargetBuffer;
    private final short[] branches;
    private final int size;

    // Variáveis para rastrear a última predição
    private short lastPC = -1;
    private short lastPredictedTarget = -1;
    private boolean lastPredictionTaken = false;

    // Variáveis pra depois usar como taxa de sucesso do preditor
    private int correctPredictions = 0;
    private int incorrectPredictions = 0;

    public TwoBitsPredictor() {
        this(512);
    }

    public TwoBitsPredictor(int size) {
        this.size = size;
        branchHistoryTable = new short[size];
        branchTargetBuffer = new short[size];
        branches = new short[size];

        for (int i = 0; i < size; i++) {
            branchHistoryTable[i] = 2; // Estado inicial: Weakly Taken
            branchTargetBuffer[i] = -1;
            branches[i] = -1;
        }
    }

    public short predict(short pc) {
        int index = pc % size;
        short nextPC = (short)(pc + 1);

        // Salva informações da predição atual
        lastPC = pc;

        // Se não há entrada para este PC, prediz não tomado
        if(branches[index] != pc) {
            lastPredictedTarget = nextPC;
            lastPredictionTaken = false;
            return nextPC;
        }

        // Se o estado é >= 2 (Weakly Taken ou Strongly Taken), então jumpa
        if(branchHistoryTable[index] >= 2) {
            lastPredictedTarget = branchTargetBuffer[index];
            lastPredictionTaken = true;
            return branchTargetBuffer[index];
        } else {
            // Estado < 2 (Strongly Not Taken ou Weakly Not Taken), não jumpa
            lastPredictedTarget = nextPC;
            lastPredictionTaken = false;
            return nextPC;
        }
    }

    public void update(short pc, short target, boolean taken) {
        int index = pc % size;
        branches[index] = pc;
        branchTargetBuffer[index] = target;

        // Atualiza o estado do preditor de 2 bits
        if (taken) {
            // Branch foi tomado, incrementa o estado (máximo 3)
            if (branchHistoryTable[index] < 3) {
                branchHistoryTable[index]++;
            }
        } else {
            // Branch não foi tomado, decrementa o estado (mínimo 0)
            if (branchHistoryTable[index] > 0) {
                branchHistoryTable[index]--;
            }
        }
    }

    // Metodo para verificar se a predição foi correta
    public boolean wasLastPredictionCorrect(short actualTarget, boolean actualTaken) {
        if (actualTaken) {
            return lastPredictionTaken && (lastPredictedTarget == actualTarget);
        } else {
            return !lastPredictionTaken;
        }
    }

    // Getters para acessar informações da última predição
    public short getLastPC() {
        return lastPC;
    }

    public short getLastPredictedTarget() {
        return lastPredictedTarget;
    }

    public boolean isLastPredictionTaken() {
        return lastPredictionTaken;
    }

    // Getters para taxa de sucesso
    public int getCorrectPredictions() {
        return correctPredictions;
    }

    public int getIncorrectPredictions() {
        return incorrectPredictions;
    }
}

