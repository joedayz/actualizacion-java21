public record Alumno(String nombre, int edad) {

    @Override
    public String toString() {
        return nombre + " (" + edad + ")";
    }
}

// NOTA : NO HAY HERENCIA DE RECORDS
//record HijoAlumno extends Alumno {}


class TestRecords {


    public static void main(String[] args) {
        Alumno alumno1 = new Alumno("Juan", 20);
        Alumno alumno2 = new Alumno("Juan", 20);
        Alumno alumno3 = new Alumno("Maria", 22);

        System.out.println(alumno1); // Alumno[nombre=Juan, edad=20]
        System.out.println(alumno2); // Alumno[nombre=Juan, edad=20]
        System.out.println(alumno3); // Alumno[nombre=Maria, edad=22]

        System.out.println(alumno1.equals(alumno2)); // true
        System.out.println(alumno1.equals(alumno3)); // false

        System.out.println(alumno1.hashCode()); // hash code
        System.out.println(alumno2.hashCode()); // same hash code as alumno1
        System.out.println(alumno3.hashCode()); // different hash code
    }

}
