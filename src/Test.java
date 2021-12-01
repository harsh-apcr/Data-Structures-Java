import Stack.*;

import java.util.Random;

public class Test {
    public static void main(String[] args) throws StackEmptyException {
        final MyStack<Integer> stack = new ArrayStack<>();
        final Random rd = new Random();
        final int size = 3000;
        for (int i = 1;i <= 3000;i++) {
            stack.push(rd.nextInt(1000));
        }
        for (int i = 1;i <= 3000;i++) {
            System.out.println(stack.pop());
        }

    }
}
