package hardware;

public class RegisterFile {
    private int[] registers;

    public RegisterFile(int amount) {
        this.registers = new int[amount];
    }

    public int getRegister(int index) {
        return registers[index];
    }

    public void setRegister(int index, int dataIn) {
        this.registers[index] = dataIn;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < registers.length; i++) {
            sb.append(String.format("[r%d]: %5d  ", i, registers[i]));
            if ((i + 1) % 4 == 0) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
