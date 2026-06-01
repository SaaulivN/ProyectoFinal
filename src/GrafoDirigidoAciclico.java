import java.util.*;

public class GrafoDirigidoAciclico {
    private ArrayList<Nodo> grafo;

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

    public boolean insertarArista(int i, int j) {
        if (i < 0 || i >= grafo.size() || j < 0 || j >= grafo.size()) {
            System.out.println("Fuera de rango.");
            return false;
        }
        grafo.get(i).agregarAdyacenia(grafo.get(j));
        if (tieneCiclos()) {
            grafo.get(i).eliminarAdyacenia(grafo.get(j));
            System.out.println("No se puede porque se crea un ciclo");
            return false;
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

    public int gradoDeEntrada(int i) {
        int grado = -1;
        if (i < 0 || i >= grafo.size()) {
            return grado;
        }
        grado = grafo.get(i).getGradoEntrada();
        return grado;
    }

    public int gradoDeSalida(int i) {
        int grado = -1;
        if (i < 0 || i >= grafo.size()) {
            return grado;
        }
        grado = grafo.get(i).getGradoSalida();
        return grado;
    }

    public int cuantasAristasHay() {
        int aristas = 0;
        for (Nodo nodo : grafo) {
            aristas += nodo.getGradoSalida();
        }
        return aristas;
    }

    public boolean adyacente(int i, int j) {
        if (i < 0 || i >= grafo.size() || j < 0 || j >= grafo.size()) {
            return false;
        }
        Nodo origen = grafo.get(i);
        Nodo destino = grafo.get(j);
        return origen.getListaDeAdyacenia().contains(destino);
    }

    public void eliminarAristas() {
        for (Nodo nodo : grafo) {
            nodo.getListaDeAdyacenia().clear();
            nodo.setGradoEntrada(0);
        }
    }

    public boolean conectados(int datoOrigen, int datoDestino) {
        int i = obtenerIndicePorDato(datoOrigen);
        int j = obtenerIndicePorDato(datoDestino);

        if (i == -1 || j == -1) {
            System.out.println("Error: Uno o ambos datos no existen en el grafo.");
            return false;
        }

        Nodo origen = grafo.get(i);
        Nodo destino = grafo.get(j);

        if (origen.getListaDeAdyacenia().contains(destino)) {
            System.out.println("Los nodos SI estan conectados.");
            return true;
        }

        ColaSimple<Nodo> cola = new ColaSimple<>(grafo.size());
        Set<Nodo> visitados = new HashSet<>();
        visitados.add(origen);
        cola.insertar(origen);

        while (!cola.estaVacio()) {
            Nodo actual = cola.eliminarDato();
            for (Nodo vecino : actual.getListaDeAdyacenia()) {
                if (vecino == destino) {
                    System.out.println("Los nodos SI estan conectados.");
                    return true;
                }
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.insertar(vecino);
                }
            }
        }

        System.out.println("Los nodos NO estan conectados.");
        return false;
    }

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

    private boolean ciclo(Nodo actual, Set<Nodo> visitados, Set<Nodo> rutaActual) {
        if (rutaActual.contains(actual)) {
            return true;
        }
        if (visitados.contains(actual)) {
            return false;
        }
        visitados.add(actual);
        rutaActual.add(actual);
        for (Nodo nodo : actual.getListaDeAdyacenia()) {
            if (ciclo(nodo, visitados, rutaActual)) {
                return true;
            }
        }
        rutaActual.remove(actual);
        return false;
    }

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

    private void topologicalSortU(Nodo actual, Set<Nodo> visitados, Pila<Nodo> pila) {
        visitados.add(actual);
        for (Nodo nodo : actual.getListaDeAdyacenia()) {
            if (!visitados.contains(nodo)) {
                topologicalSortU(nodo, visitados, pila);
            }
        }
        pila.push(actual);
    }
}