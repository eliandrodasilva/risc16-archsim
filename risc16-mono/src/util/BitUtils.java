package util;

public class BitUtils {
    public static short extractBits (short value, int start, int length)
    {
        short mask = (short)((1 << length) - 1);
        return (short)((value >> start) & mask);
    }
}
