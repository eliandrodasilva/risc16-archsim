package hardware;

public class Registers {
    private int[] registers;

    public Registers(int amount) {
        this.registers = new int[amount];
    }

    public int getRegister(int index) {
        return registers[index];
    }

    public void setRegisters(int index, int value) {
        this.registers[index] = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < registers.length; i++) {
            sb.append(String.format("[r%-2d]: %5d  ", i, registers[i]));
            if ((i + 1) % 5 == 0) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
