package pe.joedayz.modulo4.demo.jmh;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 3, time = 1)
@Fork(1)
public class StringConcatBenchmark {

    @Param({"10", "100", "1000"})
    public int iterations;

    @Benchmark
    public String concatWithPlus() {
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += i;
        }
        return result;
    }

    @Benchmark
    public String concatWithBuilder() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append(i);
        }
        return sb.toString();
    }
}
