public class Pila<T> {
    private T[] pila;
    int tope = -1;

    public Pila() {
        pila = (T[]) new Object[10];
        tope = -1;
    }

    public boolean pilaLlena() {
        return tope == pila.length - 1;
    }

    public boolean pilaVacia() {
        return tope == -1;
    }

    public void push(T dato) {
        if (pilaLlena()) {
            System.out.println("Desbordamiento");
        } else {
            pila[++tope] = dato;
        }
    }

    public T pop() {
        T dato = null;
        if (pilaVacia()) {
            System.out.println("Subdesbordamiento");
        } else {
            dato = pila[tope];
            tope--;
        }
        return dato;
    }

    public T peek() {
        return pila[tope];
    }

    public String invertirPila() {
        String resultado = "";
        for (int i = pila.length - 1; i >= 0; i--) {
            if (pila[i] != null) {
                resultado += pila[i];
            }
        }
        return resultado;
    }

    private boolean esPareja(char c1, char c2) {
        return (c1 == '(' && c2 == ')') || (c1 == '[' && c2 == ']') || (c1 == '{' && c2 == '}');
    }

    public boolean revisarSintaxis(String cadena) {
        Pila<Character> comprobador = new Pila<>();

        for (int i = 0; i < cadena.length(); i++) {
            char dato = cadena.charAt(i);
            if (dato == '(' || dato == '[' || dato == '{') {
                comprobador.push(dato);
            } else if (dato == ')' || dato == ']' || dato == '}') {
                if (comprobador.pilaVacia()) {
                    return false;
                }
                char datoFnl = comprobador.pop();
                if (!esPareja(datoFnl, dato)) {
                    return false;
                }
            }

        }
        return comprobador.pilaVacia();
    }

    public Pila<Integer> ordenarVector(int[] numeros) {
        Pila<Integer> numOrdenados = new Pila<>();
        Pila<Integer> auxiliar = new Pila<>();
        for (int i = 0; i < numeros.length; i++) {
            int dato = numeros[i];
            while (!numOrdenados.pilaVacia() && numOrdenados.peek() > dato) {
                auxiliar.push(numOrdenados.pop());
            }
            numOrdenados.push(dato);
            while (!auxiliar.pilaVacia()) {
                numOrdenados.push(auxiliar.pop());
            }
        }
        return numOrdenados;
    }
    public String toString() {
        if (pilaVacia()) return "Pila vacía";

        String res = "[";
        for (int i = 0; i <= tope; i++) {
            res += pila[i] + (i == tope ? "" : ", ");
        }
        return res + "]";
    }
}




