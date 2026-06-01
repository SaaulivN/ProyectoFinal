import java.util.*;

public class GrafoDirigidoAciclico {
    private ArrayList<Nodo> grafo;

    //Constructor que recibe la cantidad de vertices que tendra el grafo
    public GrafoDirigidoAciclico(int n) {
        Random rnd = new Random();
        grafo = new ArrayList<>();
        ArrayList<Integer> datosGenerados = new ArrayList<>();

        while (grafo.size() < n) {
            int nuevoDato = rnd.nextInt(20);
            if (!datosGenerados.contains(nuevoDato)) {
                datosGenerados.add(nuevoDato);
                grafo.add(new Nodo(nuevoDato));
            }
        }
    }

    private int obtenerIndicePorDato(int dato) {
        for (int i = 0; i < grafo.size(); i++) {
            if (grafo.get(i).getDato() == dato) {
                return i;
            }
        }
        return -1;
    }
    //agrega una arista que apunta desde el nodo i al nodo j
    public boolean insertarArista(int i, int j) {
        if (i < 0 || i >= grafo.size() || j < 0 || j >= grafo.size()) {
            //error que saltara si alguno de los nodos esta fuera del rango del grafo
            throw new IllegalArgumentException("Fuera de rango");
        }
        grafo.get(i).agregarAdyacenia(grafo.get(j));
        //comprobacion si la arista genera un ciclo
        if (tieneCiclos()) {
            //si se llega a generar un ciclo se eliminara la arista dejando el grafo en su estado anterior
            grafo.get(i).eliminarAdyacenia(grafo.get(j));
            throw new IllegalArgumentException("Se crea un ciclo");
        }
        System.out.println("Arista Agregada.");
        return true;
    }

    public boolean insertarAristaPorDato(int datoOrigen, int datoDestino) {
        int i = obtenerIndicePorDato(datoOrigen);
        int j = obtenerIndicePorDato(datoDestino);

        if (i == -1 || j == -1) {
            System.out.println("Error: Uno o ambos datos no existen en el grafo.");
            return false;
        }
        return insertarArista(i, j);
    }
    //metodo para obetener el grado de entrada del nodo i
    public int gradoDeEntrada(int i) {
        int grado = 0;
        if (i < 0 || i >= grafo.size()) {
            //error que saltara si el nodos esta fuera del rango del grafo
            throw new IllegalArgumentException("Nodo inexistente");
        }
        grado = grafo.get(i).getGradoEntrada();
        return grado;
    }

    //metodo para obetener el grado de salida del nodo i
    public int gradoDeSalida(int i) {
        int grado = 0;
        if (i < 0 || i >= grafo.size()) {
            //error que saltara si el nodos esta fuera del rango del grafo
            throw new IllegalArgumentException("Nodo inexistente");
        }
        grado = grafo.get(i).getGradoSalida();
        return grado;
    }
    //metodo que sumara las salidas de cada nodo para saber cuantas
    //aristas hay dentro del grafo
    public int cuantasAristasHay() {
        int aristas = 0;
        for (Nodo nodo : grafo) {
            aristas += nodo.getGradoSalida();
        }
        return aristas;
    }
    //metodo para comprobar si el nodo i apunta hacia el nodo j
    public boolean adyacente(int i, int j) {
        if (i < 0 || i >= grafo.size() || j < 0 || j >= grafo.size()) {
            //error que saltara si alguno de los nodos esta fuera del rango del grafo
            throw new IllegalArgumentException("Nodo inexistente");
        }
        Nodo origen = grafo.get(i);
        Nodo destino = grafo.get(j);
        //regresara si el nodo j se encuentra en la lista de adyacencia del nodo i
        return origen.getListaDeAdyacenia().contains(destino);
    }
    //metodo para eliminar todas las aristas del grafo
    public void eliminarAristas() {
        //se recorrera todo el grafo limpiando las listas de adyacencia de cada nodo y regresando sus grados de entrada a 0
        for (Nodo nodo : grafo) {
            nodo.getListaDeAdyacenia().clear();
            nodo.setGradoEntrada(0);
        }
    }

    //metodo que revisa si hay un camino del nodo Origen al nodo Destino
    public boolean conectados(int datoOrigen, int datoDestino) {
        int i = obtenerIndicePorDato(datoOrigen);
        int j = obtenerIndicePorDato(datoDestino);
        //comprobacion de si alguno de los nodos no esta en el grafo
        if (i == -1 || j == -1) {
            throw new IllegalArgumentException("Error: Uno o ambos datos no existen en el grafo.");
        }

        Nodo origen = grafo.get(i);
        Nodo destino = grafo.get(j);
        //comprobacion de si se conectan directamente por una arista
        if (origen.getListaDeAdyacenia().contains(destino)) {
            System.out.println("Los nodos SI estan conectados.");
            return true;
        }

        ColaSimple<Nodo> cola = new ColaSimple<>(grafo.size());
        Set<Nodo> visitados = new HashSet<>();
        visitados.add(origen);
        cola.insertar(origen);

        while (!cola.estaVacio()) {
            //se saca de la cola el nodo a comprobar
            Nodo actual = cola.eliminarDato();
            //se recorreran todos los vecinos a los que apunta el nodo
            for (Nodo vecino : actual.getListaDeAdyacenia()) {
                //comprobacion si se llego al objetivo
                if (vecino == destino) {
                    System.out.println("Los nodos SI estan conectados.");
                    return true;
                }

                if (!visitados.contains(vecino)) {
                    //se agregara este nodo a la lista de nodos revisados o visitados
                    visitados.add(vecino);
                    //si el vecino no es el destino se agregara a la cola para seguir recorriendo los vecinos de este nodo
                    cola.insertar(vecino);
                }
            }
        }

        System.out.println("Los nodos NO estan conectados.");
        return false;
    }
    //Metodo que recorre el grafo completo en busqueda de ciclos
    public boolean tieneCiclos() {
        Set<Nodo> visitados = new HashSet<>();
        Set<Nodo> rutaActual = new HashSet<>();
        for (Nodo nodo : grafo) {
            if (ciclo(nodo, visitados, rutaActual)) {
                return true;
            }
        }
        return false;
    }
    /*
    Este es un metodo auxiliar para revisar si en el grafo se genera un ciclo
    este metodo recibira el nodo actual, un hashset de los nodos ya visitados y un hashset de la ruta actual
     */
    private boolean ciclo(Nodo actual, Set<Nodo> visitados, Set<Nodo> rutaActual) {
        //si el nodo ya esta dentro de la ruta actual significa que hay un ciclo porque ya se paso por ahi
        if (rutaActual.contains(actual)) {
            return true;
        }
        //si los nodos visitados ya contienen al nodo actual siginifica que este ya fue revisado y no tendra ciclo
        if (visitados.contains(actual)) {
            return false;
        }

        //despuesde las comprobaciones se agregara el nodo a los 2 hashset
        visitados.add(actual);
        rutaActual.add(actual);
        //se revisaran todos los nodos a los que apunta este utilizando el metodo de manera recursiva
        for (Nodo nodo : actual.getListaDeAdyacenia()) {
            if (ciclo(nodo, visitados, rutaActual)) {
                return true;
            }
        }
        //si no se encontro un ciclo se removera el nodo de la ruta actual
        rutaActual.remove(actual);
        return false;
    }


    //metodo para mostrar el grafo en forma de Matriz utilizando los valores de los vertices
    public void mostrarEstructuraBien() {
        int n = grafo.size();
        System.out.println("\nMatriz de Adyacencia:");

        System.out.print("     ");
        for (int j = 0; j < n; j++) {
            System.out.printf("%2d ", grafo.get(j).getDato());
        }
        System.out.println();

        System.out.print("    -");
        for (int j = 0; j < n; j++) {
            System.out.print("---");
        }
        System.out.println();

        for (int i = 0; i < n; i++) {
            System.out.printf("%2d | ", grafo.get(i).getDato());

            for (int j = 0; j < n; j++) {
                if (adyacente(i, j)) {
                    System.out.print(" 1 ");
                } else {
                    System.out.print(" 0 ");
                }
            }
            System.out.println();
        }
    }
    //metodo para mostrar el grafo en forma de Matriz utilizando los indices de los vertices
    public void mostrarEstructura() {
        int n = grafo.size();
        System.out.println("\nMatriz de Adyacencia:");

        System.out.print("    ");
        for (int j = 0; j < n; j++) {
            System.out.printf("%2d ", j);
        }
        System.out.println("\n    ______________");
        for (int i = 0; i < n; i++) {
            System.out.printf("%2d ", i);
            System.out.printf("|");
            for (int j = 0; j < n; j++) {
                if (adyacente(i, j)) {
                    System.out.print(" 1 ");
                } else {
                    System.out.print(" 0 ");
                }
            }
            System.out.println();
        }
    }

    //metodo principal para hacer el topological sort
    public String topologicalSort() {
        Pila<Nodo> pila = new Pila();
        Set<Nodo> visitados = new HashSet<>();
        for (Nodo nodo : grafo) {
            if (!visitados.contains(nodo)) {
                topologicalSortU(nodo, visitados, pila);
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!pila.pilaVacia()) {
            sb.append(pila.pop().getDato()).append(" ");
        }
        return sb.toString().trim();
    }
    /*
    metodo auxiliar para el topologicalSort, recibira el nodo actual, un HashSet de los nodos visitados
    y una pila de nodos
     */
    private void topologicalSortU(Nodo actual, Set<Nodo> visitados, Pila<Nodo> pila) {
        //Se agrega el nodo al hashset de visitados
        visitados.add(actual);
        //Se recorren los nodos a los que apunta el nodo actual
        for (Nodo nodo : actual.getListaDeAdyacenia()) {
            //si el nodo aun no ha sido registrado como visitado  se utilizara de manera recursiva el metodo recorriendo todo el grafo
            if (!visitados.contains(nodo)) {
                topologicalSortU(nodo, visitados, pila);
            }
        }
        //finalmente se agregara el nodo a la pila
        pila.push(actual);
    }
}