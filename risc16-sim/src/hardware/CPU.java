package hardware;

public class CPU {
    private short pc;
    private int cycle;
    private int fetchCount;
    private int instructionCount;
    private boolean running;

    public void dump(RegisterFile registerFile) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n--- Processor Execution Dump ---\n");
        sb.append(String.format("%-21s : %4d\n", "CPU Cycles", getCycles()));
        sb.append(String.format("%-21s : %4d\n", "Fetches", getFetchCount()));
        sb.append(String.format("%-21s : %4d\n", "Instructions Executed", getInstructionCount()));

        sb.append("\nFinal Register State:\n");
        sb.append(registerFile.toString());

        System.out.println(sb);
    }

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
