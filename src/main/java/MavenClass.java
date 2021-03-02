import com.google.common.math.IntMath;

public class MavenClass {

    public int add (int value, int otherValue) {
        return IntMath.checkedAdd(value, otherValue);
    }
}
