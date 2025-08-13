package hardware;

public class Memory {
    private short[] memory;

    public Memory(int size) {
        this.memory = new short[size];
    }

    public short load(short adress) {
        return (short) memory[adress];
    }

    public void store(short adress, short value) {
        memory[adress] = value;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n--- Memory Dump ---\n");

        for (int i = 0; i < 15 && i < memory.length; i++) {
            sb.append(String.format("[m%-3d]: %6d ", i, memory[i]));
            if ((i + 1) % 4 == 0) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
