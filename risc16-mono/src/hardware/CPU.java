package hardware;

public class CPU {
    private short pc;
    private int cycle;
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

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
