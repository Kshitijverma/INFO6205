package edu.neu.coe.info6205.util;

import edu.neu.coe.info6205.union_find.WQU_Depth;
import edu.neu.coe.info6205.union_find.WQU_Size;
import edu.neu.coe.info6205.union_find.WQUPC_OneLoop;
import edu.neu.coe.info6205.union_find.WQUPC_TwoLoop;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Alternatives_Benchmark {

    private static int bySizeSum = 0, byDepthSum = 0, oneLoopSum = 0, twoLoopSum = 0;

    //Weighted Quick Union storing size
    public static void test_WQU_BySize(int n) {
        WQU_Size bySize = new WQU_Size(n);
        Random random = new Random();

        while (bySize.count() != 1) {
            int first = random.nextInt(n);
            int second = random.nextInt(n);

            if (!bySize.connected(first, second)) {
                bySize.union(first, second);
            }
        }
        bySizeSum += bySize.getSize();
    }

    //Weighted Quick Union storing depth
    public static void test_WQU_ByDepth(int n) {
        WQU_Depth byDepth = new WQU_Depth(n);
        Random random = new Random();

        while (byDepth.count() != 1) {
            int first = random.nextInt(n);
            int second = random.nextInt(n);

            if (!byDepth.connected(first, second)) {
                byDepth.union(first, second);
            }
        }
        byDepthSum += byDepth.getMaximumDepth();
    }

    //Weighted Quick Union with One Loop Path Compression
    public static void test_WQU_OneLoop(int n) {
        WQUPC_OneLoop oneLoop = new WQUPC_OneLoop(n);
        Random random = new Random();

        while (oneLoop.count() != 1) {
            int first = random.nextInt(n);
            int second = random.nextInt(n);

            if (!oneLoop.connected(first, second)) {
                oneLoop.union(first, second);
            }
        }
        oneLoopSum += oneLoop.getDepth();
    }

    //Weighted Quick Union with Two Loop Path Compression
    public static void test_WQU_TwoLoop(int n) {
        WQUPC_TwoLoop twoLoop = new WQUPC_TwoLoop(n);
        Random random = new Random();

        while (twoLoop.count() != 1) {
            int first = random.nextInt(n);
            int second = random.nextInt(n);

            if (!twoLoop.connected(first, second)) {
                twoLoop.union(first, second);
            }
        }
        twoLoopSum += twoLoop.getDepth();
    }

    public static void main(String[] args) {
        Benchmark_Timer<Integer> benchmark_timer_WQU_BySize;
        Benchmark_Timer<Integer> benchmark_timer_WQU_ByDepth;
        Benchmark_Timer<Integer> benchmark_timer_WQU_OneLoop;
        Benchmark_Timer<Integer> benchmark_timer_WQU_TwoLoop;

        Consumer<Integer> run_WQU_BySize = xs -> test_WQU_BySize(xs);
        Consumer<Integer> run_WQU_ByDepth = xs -> test_WQU_ByDepth(xs);
        Consumer<Integer> run_WQU_OneLoop = xs -> test_WQU_OneLoop(xs);
        Consumer<Integer> run_WQU_TwoLoop = xs -> test_WQU_TwoLoop(xs);

        int[] n = {250, 500, 1000, 2000, 4000, 8000, 16000};
        int m = 30;

        benchmark_timer_WQU_BySize = new Benchmark_Timer<>("Weighted Quick Union Storing Size"
                ,null, run_WQU_BySize, null);
        benchmark_timer_WQU_ByDepth = new Benchmark_Timer<>("Weighted Quick Union Storing Depth"
                ,null, run_WQU_ByDepth, null);
        benchmark_timer_WQU_OneLoop = new Benchmark_Timer<>("Weighted Quick Union with One Loop Path Compression"
                ,null, run_WQU_OneLoop, null);
        benchmark_timer_WQU_TwoLoop = new Benchmark_Timer<>("Weighted Quick Union with Two Loop Path Compression"
                ,null, run_WQU_TwoLoop, null);

        for (int i = 0; i < n.length; i++) {
            int temp = n[i];
            Supplier<Integer> supplier = () -> temp;

            double bySizeTime = benchmark_timer_WQU_BySize.runFromSupplier(supplier, m);
            double byDepthTime = benchmark_timer_WQU_ByDepth.runFromSupplier(supplier, m);
            double oneLoopTime = benchmark_timer_WQU_OneLoop.runFromSupplier(supplier, m);
            double twoLoopTime = benchmark_timer_WQU_TwoLoop.runFromSupplier(supplier, m);

            double averageBySizeTime = bySizeSum / (m * 1.0 + 2.0);
            double averageByDepthTime = byDepthSum / (m * 1.0 + 2.0);
            double averageOneLoopSum = oneLoopSum / (m * 1.0 + 2.0);
            double averageTwoLoopSum = twoLoopSum / (m * 1.0 + 2.0);

            System.out.println("---------------------------------------------------For N = " + n[i] + "---------------------------------------------------------------------------------");
            System.out.println("Average time for QU storing size: " + bySizeTime + " Average depth for QU storing size: " + averageBySizeTime);
            System.out.println("Average time for QU storing Depth: " + byDepthTime + " Average depth for QU storing Depth: " + averageByDepthTime);
            System.out.println("Average time for QUPC with one Loop: " + oneLoopTime + " Average depth for QUPC with one Loop: " + averageOneLoopSum);
            System.out.println("Average time for QUPC with two Loop: " + twoLoopTime + " Average depth for QUPC with two Loop:" + averageTwoLoopSum);
            System.out.println();
            bySizeSum = 0;
            byDepthSum = 0;
            oneLoopSum = 0;
            twoLoopSum = 0;
        }
    }

}