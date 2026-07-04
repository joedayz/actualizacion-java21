import java.util.concurrent.StructuredTaskScope;

public class BusquedaHoteles {


    public static void main() throws Exception{

        long inicio = System.currentTimeMillis();

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            var booking = scope.fork(() -> buscarEnBooking());
            var expedia = scope.fork(() -> buscarEnExpedia());
            var airbnb = scope.fork(() -> buscarEnAirbnb());

            scope.join();
            scope.throwIfFailed();

            System.out.println("=== Resultados ===");
            System.out.println(booking.get());
            System.out.println(expedia.get());
            System.out.println(airbnb.get());
        }


        System.out.println("\nTiempo total: "
                + (System.currentTimeMillis() - inicio) + " ms");
    }

    static String buscarEnBooking() throws InterruptedException {
        Thread.sleep(2500);
        return "Booking: Hotel Costa Verde - $120";
    }

    static String buscarEnExpedia() throws InterruptedException {
        Thread.sleep(1500);
        throw new RuntimeException("Expedia no disponible");
    }

    static String buscarEnAirbnb() throws InterruptedException {
        Thread.sleep(2000);
        return "Airbnb: Departamento Miraflores - $95";
    }
}
