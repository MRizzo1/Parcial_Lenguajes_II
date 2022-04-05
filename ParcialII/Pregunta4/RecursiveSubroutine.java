import java.io.*;
import java.util.*;

class RecursiveSubroutine {

    static int alpha = ((5 + 3) % 5) + 3;
    static int beta = ((3 + 8) % 5) + 3;

    static int f(int n) {
        if (n >= 0 && 24 > n) {
            return n;
        } else if (n >= 24) {
            return f(n - beta) + f(n - beta * 2) + f(n - beta * 3) + f(n - beta * 4) + f(n - beta * 5)
                    + f(n - beta * 6);
        }
        return 0;
    }

    static int fTail(int n) {
        HashMap<Integer, Integer> baseCases = new HashMap<Integer, Integer>();
        for (int i = 0; i < 24; i++) {
            baseCases.put(i, i);
        }

        return fTailAux(30, 24, baseCases);
    }

    static int fTailAux(int n, int a, HashMap<Integer, Integer> baseCases) {
        if (n >= 0 && 24 > n)
            return baseCases.get(a - 1);

        baseCases.put(a, baseCases.get(a - 4) + baseCases.get(a - 8) + baseCases.get(a - 12) + baseCases.get(a - 16)
                + baseCases.get(a - 20) + baseCases.get(a - 24));

        return fTailAux(n - 1, a + 1, baseCases);
    }

    static int fLoop(int n) {
        HashMap<Integer, Integer> baseCases = new HashMap<Integer, Integer>();
        for (int i = 0; i < 24; i++) {
            baseCases.put(i, i);
        }

        for (int i = 24; i <= n; i++) {
            baseCases.put(i, baseCases.get(i - 4) + baseCases.get(i - 8) + baseCases.get(i - 12) + baseCases.get(i - 16)
                    + baseCases.get(i - 20) + baseCases.get(i - 24));
        }

        return baseCases.get(n);
    }

    public static void main(String args[]) throws IOException {

        int[] value = new int[]{100, 99, 99, 99, 99};

        for (int i = 0; i < 5; i++) {

            System.out.println("\n\nPrueba numero " + (i + 1) + " para el valor random: " + value[i] + "\n\n");

            long startTime = System.nanoTime();
            f(value[i]);
            long endTime = System.nanoTime();
            System.out.println("Time taken for simple recursion: " + (endTime - startTime)
                    + " nanoseconds");

            long startTime1 = System.nanoTime();
            fLoop(value[i]);
            long endTime1 = System.nanoTime();
            System.out.println("Time taken for iterative solution: " + (endTime1 - startTime1)
                    + " nanoseconds");
            long startTime2 = System.nanoTime();
            fTail(value[i]);
            long endTime2 = System.nanoTime();
            System.out.println("Time taken for tail recursion: " + (endTime2 - startTime2)
                    + " nanoseconds");
        }
    }
}
