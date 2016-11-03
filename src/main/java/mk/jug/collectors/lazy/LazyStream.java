package mk.jug.collectors.lazy;

import java.util.Arrays;
import java.util.List;

/**
 * Lazy stream demo
 */
public class LazyStream {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 5, 4, 6, 7, 8, 9, 10);

        System.out.println("Notice the order of evaluation:");
        System.out.println(
                numbers.stream()
                        .filter(LazyStream::isGT3)
                        .filter(LazyStream::isEven)
                        .map(LazyStream::doubleIt)
                        .findFirst()
                        .orElse(0));

        System.out.println("Evaluation is Lazy:");
        numbers.stream()
                .filter(LazyStream::isGT3)
                .filter(LazyStream::isEven)
                .map(LazyStream::doubleIt);
        System.out.println("No computation was done since we did not ask for the result");
    }

    public static boolean isGT3(int number) {
        System.out.println("isGT3 " + number);
        return number > 3;
    }

    public static boolean isEven(int number) {
        System.out.println("isEven " + number);
        return number % 2 == 0;
    }

    public static int doubleIt(int number) {
        System.out.println("doubleIt " + number);
        return number * 2;
    }
}
