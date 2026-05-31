public class ColaSimple<T> {
    private T[] cola;
    private int inicio;
    private int fin;

    public ColaSimple(int capacidad) {
        cola = (T[]) new Object[capacidad];
        inicio = -1;
        fin = -1;
    }

    public ColaSimple() {
        cola = (T[]) new Object[10];
        inicio = -1;
        fin = -1;
    }

    public void insertar(T elemento) {
        if (fin == cola.length - 1) {
            System.out.println("Desbordamiento");
        } else {
            fin++;
            cola[fin] = elemento;
            if (inicio == -1) {
                inicio = 0;
            }
        }
    }



    public T eliminarDato() {
        T dato=null;
        if(inicio!= -1){
            dato = cola[inicio];
        }
        if(inicio==fin){
            inicio=-1;
            fin=-1;
        }else{
            inicio++;
        }
        return dato;
    }

    public boolean estaVacio() {
        return inicio == -1;
    }

}

