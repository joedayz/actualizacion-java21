import jdk.jfr.Recording;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

/**
 * Demo: grabación programática con Java Flight Recorder.
 * Ejecutar: java JfrRecording.java
 *
 * Abre el archivo generado con Java Mission Control (JMC).
 */
public class JfrRecording {

    public static void main(String[] args) throws Exception {
        Path destino = Path.of("demo-recording.jfr");

        System.out.println("Grabando JFR durante 3 segundos → " + destino.toAbsolutePath());

        try (Recording recording = new Recording()) {
            recording.enable("jdk.GCPhasePause");
            recording.enable("jdk.ExecutionSample");
            recording.enable("jdk.ThreadAllocationStatistics");
            recording.start();

            // Trabajo de muestra: allocations + CPU
            long checksum = 0;
            for (int i = 0; i < 500_000; i++) {
                checksum += i;
                if (i % 100_000 == 0) {
                    new byte[64 * 1024];
                }
            }
            System.out.println("checksum=" + checksum);

            Thread.sleep(Duration.ofSeconds(3));
            recording.stop();
            recording.dump(destino);
        }

        long bytes = Files.size(destino);
        System.out.println("Archivo JFR generado: " + bytes + " bytes");
        System.out.println("Ábrelo con JMC: File → Open File → " + destino);
    }
}
