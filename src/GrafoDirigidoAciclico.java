
import java.util.*;

public class GrafoDirigidoAciclico {
    private ArrayList<Nodo> grafo;

    public GrafoDirigidoAciclico(int n) {
        Random rnd=new Random();
        grafo = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            grafo.add(new Nodo(rnd.nextInt(11)));
        }
    }

    public boolean insertarArista(int i, int j){
        grafo.get(i).agregarAdyacenia(grafo.get(j));
        if(tieneCiclos()){
            grafo.get(i).eliminarAdyacenia(grafo.get(j));
            System.out.println("Se crea un cilo");
            return false;
        }
        return true;
    }
    

    public int gradoDeEntrada(int i){
        int grado =-1;
        if(i<0 || i>= grafo.size()){
            return grado;
        }
        grado = grafo.get(i).getGradoEntrada();
        return grado;
    }

    public int gradoDeSalida(int i){
        int grado =-1;
        if(i<0 || i>= grafo.size()){
            return grado;
        }
        grado = grafo.get(i).getGradoSalida();
        return grado;
    }

    public int cuantasAristasHay(){
        int aristas=0;
        for(Nodo nodo: grafo){
            aristas += nodo.getGradoSalida();
        }
        return aristas;
    }

    public boolean adyacente(int i, int j){
        if(i < 0 || i >= grafo.size() || j < 0 || j >= grafo.size()){
            return false;
        }
        Nodo origen = grafo.get(i);
        Nodo destino = grafo.get(j);
        return origen.getListaDeAdyacenia().contains(destino);
    }

    public void eliminarAristas(){
        for(Nodo nodo: grafo){
            nodo.getListaDeAdyacenia().clear();
            nodo.setGradoEntrada(0);
        }
    }

    public boolean conectado(int i, int j){
        if(i < 0 || i >= grafo.size() || j < 0 || j >= grafo.size()){
            return false;
        }
        Nodo origen = grafo.get(i);
        Nodo destino = grafo.get(j);
        if(origen.getListaDeAdyacenia().contains(destino)){
            return true;
        }
        ColaSimple<Nodo> cola = new ColaSimple<>(grafo.size());
        Set<Nodo> visitados = new HashSet<>();
        visitados.add(origen);
        cola.insertar(origen);
        while(!cola.estaVacio()){
            Nodo actual = cola.eliminarDato();
            for(Nodo vecino : actual.getListaDeAdyacenia()){
                if(vecino == destino){
                    return true;
                }
                if(!visitados.contains(vecino)){
                    visitados.add(vecino);
                    cola.insertar(vecino);
                }
            }
        }
        return false;

    }

    public boolean tieneCiclos(){
        Set<Nodo> visitados = new HashSet<>();
        Set<Nodo> rutaActual = new HashSet<>();
        for(Nodo nodo : grafo){
            if(ciclo(nodo,visitados,rutaActual)){
                return true;
            }
        }
        return false;
    }

    private boolean ciclo(Nodo actual, Set<Nodo> visitados, Set<Nodo> rutaActual) {
        if(rutaActual.contains(actual)){
            return true;
        }
        if(visitados.contains(actual)){
            return false;
        }
        visitados.add(actual);
        rutaActual.add(actual);
        for(Nodo nodo : actual.getListaDeAdyacenia()){
            if(ciclo(nodo, visitados, rutaActual)){
                return true;
            }
        }
        rutaActual.remove(actual);
        return false;
    }

    //Comentario Prueb
    //Segundo cometario prueba

}
