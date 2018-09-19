package C2Services;

public class C2AddTwoNumbers {

    private int result = 0;

    public C2AddTwoNumbers(int offset) {
        result += offset;

        System.out.println("AddTwoNumbers::__construct: " + result);
    }

    public int compute(int a, int b) {
        result += a + b;

        System.out.println("AddTwoNumbers::compute: " + result);

        return result;
    }
}
