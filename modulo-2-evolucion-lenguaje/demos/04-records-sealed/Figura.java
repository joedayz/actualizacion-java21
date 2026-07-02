public class Figura {
}

class Circulo extends Figura{}
class Cuadrado extends Figura{}
class Triangulo extends Figura{}
class Rectangulo extends Figura{}


sealed class Shape permits Circle, Rectangle, Triangle {

}

//HIJO puede ser final (no quiero tener hijos), sealed (quiero tener hijos pero controlados)
// o non-sealed (puede tener hijos sin restricciones)

final class Circle extends Shape{}
sealed class Rectangle extends Shape permits HijoRectangle{}
non-sealed class Triangle extends Shape{}


final class HijoRectangle extends Rectangle{}


class Otro extends Triangle{}
class Otro2 extends Triangle{}
class Otro3 extends Triangle{}
class Otro4 extends Triangle{}
class Otro5 extends Triangle{}
class Otro6 extends Triangle{}