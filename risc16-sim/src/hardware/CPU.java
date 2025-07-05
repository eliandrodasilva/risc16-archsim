package hardware;

public class CPU {
    private short pc;
    private int cycle;
    private int fetchCount;
    private int instructionCount;
    private boolean running;


    public short getPC() {
        return pc;
    }

    public void setPC(short pc) {
        this.pc = pc;
    }

    public void incrementPC() {
        pc++;
    }

    public int getCycles() {
        return cycle;
    }

    public void incrementCycle() {
        cycle++;
    }

    public int getFetchCount() {
        return fetchCount;
    }

    public void incrementFetchCount() {
        fetchCount++;
    }

    public int getInstructionCount() {
        return instructionCount;
    }

    public void incrementInstructionCount() {
        instructionCount++;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
