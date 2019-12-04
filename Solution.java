package com.javarush.task.task20.task2025;

import java.util.*;

/*
Алгоритмы-числа
Число S состоит из M цифр, например, S=370 и M(количество цифр)=3
Реализовать логику метода getNumbers, который должен среди натуральных чисел меньше N (long)
находить все числа, удовлетворяющие следующему критерию:
число S равно сумме его цифр, возведенных в M степень
getNumbers должен возвращать все такие числа в порядке возрастания

Пример искомого числа:
370 = 3*3*3 + 7*7*7 + 0*0*0
8208 = 8*8*8*8 + 2*2*2*2 + 0*0*0*0 + 8*8*8*8

На выполнение дается 10 секунд и 50 МБ памяти.
*/


public class Solution {
    static Set<Long> replica = new TreeSet<>();
    static long pow[][] = new long[9][19];

    public static void power(long[][] pow){
        for (int i = 0; i < pow.length; i++){
            for (int j = 0; j < pow[1].length; j++){
                if (j == 0) pow[i][j] = i+1;
                else pow[i][j] = pow[i][j-1] * pow[i][0];
            }
        }
    }

    public static long[] getNumbers(long N) {
        long[] result = new long[0];
        if (N > 0) {
            for (long i = 1; i < N; ) {
                long step = 1;
                if (isNumberUnique(i)) {
                    long x = summa(i);
                    if (isArmstrong(x) & x < N) {
                        replica.add(x);
                    }
                }
                step = findNextInc(i);
                if (step < i)
                    i = i + step;
                else
                    i = step;
            }
            if (replica.size() > 0) {
                result = new long[replica.size()];
                int i = 0;
                for (Long z : replica) {
                    result[i++] = z;
                }
            }
        }
        return result;
    }

    public static boolean isArmstrong(long a) {
        if (summa(a) == a)
            return true;
        else
            return false;
    }

    public static long summa(long a) {
        long addition = 0;
        int d = 0;
        long x = a;
        while (x >= 1) {
            d++;
            x /= 10;
        }
        long  b = a%10;
        while (a >= 1) {
            if ( b!= 0) {
                addition += pow[(int) (b - 1)][d - 1];
            }
            a /= 10;
            b = a%10;
        }
        return addition;
    }

    private static boolean isNumberUnique(long number) {

        long lastDigit = 0;
        long currentDigit;

        while (number > 0) {
            currentDigit = number % 10;
            if ((lastDigit < currentDigit||currentDigit == 0)&&lastDigit!=0) {
                return false;
            }
            lastDigit = currentDigit;
            number /= 10;
        }
        return true;
    }

    private static long findNextInc(long num) {
        if (num == 0) return 1;
        int zeros = 0;
        while (num%10 == 0) {
            zeros++;
            num /= 10;
        }
        long rem = num%10;
        if (zeros == 0) return ++num;
        long x = rem * (long) Math.pow(10, zeros - 1);
        return x;
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        power(pow);
        long[] array = getNumbers(100_000_000_000L);
        if (array.length > 0) {
            long end = System.currentTimeMillis() - start;
            long memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            System.out.println("Time = " + end / 1000);
            System.out.println("Memory = " + memory / 1048576);
            for (long a : array) {
                System.out.print(a + " ");
            }
        }
    }
}


