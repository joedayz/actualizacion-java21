import java.util.Objects;

public class RecordsSealed {

    // ===== ANTES (Java 8): POJO con todo el boilerplate a mano =====
    static final class PuntoClasico {
        private final int x;
        private final int y;

        PuntoClasico(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() { return x; }
        int getY() { return y; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PuntoClasico)) return false;
            PuntoClasico that = (PuntoClasico) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() { return Objects.hash(x, y); }

        @Override
        public String toString() { return "PuntoClasico[x=" + x + ", y=" + y + "]"; }
    }

    // ===== AHORA (Java 16+): record =====
    // Constructor, getters (x(), y()), equals, hashCode y toString: generados automáticamente.
    record Punto(int x, int y) {

        // GENERADO IMPLICITAMENTE : CONSTRUCTOR CANONICO
//        Punto (int x, int y) {
//            if (x < 0 || y < 0) {
//                throw new IllegalArgumentException("Coordenadas no pueden ser negativas");
//            }
//
//            //obligado a asignar x e y, aunque no sea necesario en un record
//            this.x = x;
//            this.y = y;
//
//        }


        // Constructor compacto: para validar sin repetir la asignación de campos
//        Punto {
//            if (x < 0 || y < 0) {
//                throw new IllegalArgumentException("Coordenadas no pueden ser negativas");
//            }
//        }

        // Los records pueden tener métodos adicionales
        double distanciaAlOrigen() {
            return Math.sqrt(x * x + y * y);
        }

        //NOTA IMPORTANTE
        // Si sobreescribe el equals(), tambien debes sobreescribir el hashCode() para mantener el contrato entre ambos
        // Si a.equals(b) es true, entonces a.hashCode() == b.hashCode()
        // Si no se cumple: HashMap y HashSet pueden comportarse de manera inesperada,
        // ya que dependen del hashCode() para ubicar objetos en sus estructuras internas.
    }

    // ===== Sealed classes (Java 17+): jerarquía cerrada de tipos =====
    sealed interface FormaPago permits Efectivo, Tarjeta, Yape {}
    record Efectivo(double monto) implements FormaPago {}
    record Tarjeta(double monto, String ultimosDigitos) implements FormaPago {}
    record Yape(double monto, String celular) implements FormaPago {}

    // Nota: como FormaPago es "sealed" y solo permite estas 3 implementaciones,
    // el compilador puede validar exhaustividad en un switch (ver demo 05).


    //ejemplo con Transporte

    sealed interface Transporte
            permits Auto, Bicicleta, Avion {
    }

    final class Auto implements Transporte {
    }

    non-sealed class Bicicleta implements Transporte {
    }

    final class Avion implements Transporte {
    }


    //ejemplo con Vehiculo

    sealed interface Vehiculo
            permits Terrestre, Aereo {
    }

    sealed class Terrestre
            implements Vehiculo
            permits AutoPower, Moto {
    }

    final class AutoPower extends Terrestre {
    }

    final class Moto extends Terrestre {
    }

    final class Aereo implements Vehiculo {
    }




    public static void main(String[] args) {
        System.out.println("=== Comparando POJO clásico vs record ===");
        PuntoClasico p1 = new PuntoClasico(3, 4);
        Punto p2 = new Punto(3, 4);
        System.out.println(p1);
        System.out.println(p2); // toString generado automáticamente
        System.out.println("Distancia al origen (record): " + p2.distanciaAlOrigen());

        System.out.println();
        System.out.println("=== equals/hashCode gratis en records ===");
        System.out.println(new Punto(1, 1).equals(new Punto(1, 1))); // true

        System.out.println();
        System.out.println("=== Validación en constructor compacto ===");
        try {
            new Punto(-1, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Sealed interface: solo 3 formas de pago posibles ===");
        FormaPago pago = new Yape(50.0, "999888777");
        System.out.println(pago);
    }
}
