package pe.joedayz.modulo2.ejercicio03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pe.joedayz.modulo2.ejercicio03.PatternMatchingExercises.Circulo;
import static pe.joedayz.modulo2.ejercicio03.PatternMatchingExercises.Cuadrado;
import static pe.joedayz.modulo2.ejercicio03.PatternMatchingExercises.Rectangulo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Ejercicio 03 — Pattern Matching")
class PatternMatchingExercisesTest {

    @Nested
    @DisplayName("medir (instanceof patterns)")
    class Medir {

        @Test
        void stringNoBlankDevuelveLongitud() {
            assertEquals(4, PatternMatchingExercises.medir("Hola"));
        }

        @Test
        void stringBlankDevuelveMenosUno() {
            assertEquals(-1, PatternMatchingExercises.medir("   "));
            assertEquals(-1, PatternMatchingExercises.medir(""));
        }

        @Test
        void integerDevuelveValor() {
            assertEquals(42, PatternMatchingExercises.medir(42));
        }

        @Test
        void otrosTiposDevuelvenMenosUno() {
            assertEquals(-1, PatternMatchingExercises.medir(3.14));
            assertEquals(-1, PatternMatchingExercises.medir(null));
            assertEquals(-1, PatternMatchingExercises.medir(true));
        }
    }

    @Nested
    @DisplayName("area (switch + record patterns)")
    class Area {

        @Test
        void circulo() {
            assertEquals(Math.PI * 4, PatternMatchingExercises.area(new Circulo(2.0)), 0.0001);
        }

        @Test
        void rectangulo() {
            assertEquals(12.0, PatternMatchingExercises.area(new Rectangulo(3.0, 4.0)), 0.0001);
        }

        @Test
        void cuadrado() {
            assertEquals(25.0, PatternMatchingExercises.area(new Cuadrado(5.0)), 0.0001);
        }
    }

    @Nested
    @DisplayName("describir")
    class Describir {

        @Test
        void formatosEsperados() {
            assertEquals("Círculo r=2.0", PatternMatchingExercises.describir(new Circulo(2.0)));
            assertEquals("Rectángulo 3.0x4.0", PatternMatchingExercises.describir(new Rectangulo(3.0, 4.0)));
            assertEquals("Cuadrado l=5.0", PatternMatchingExercises.describir(new Cuadrado(5.0)));
        }
    }
}
