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
}
