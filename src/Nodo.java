import java.util.ArrayList;
import java.util.List;

public class Nodo {
    private int dato;
    private ArrayList<Nodo> listaDeAdyacenia;
    private int gradoEntrada;

    public Nodo (int dato){
        this.dato = dato;
        this.gradoEntrada = 0;
        this.listaDeAdyacenia = new ArrayList<Nodo>();
    }

    public void agregarAdyacenia (Nodo  destino){
        listaDeAdyacenia.add(destino);
        destino.incremetarGradoEntrada();
    }

    public void eliminarAdyacenia (Nodo  destino) {
      if(listaDeAdyacenia.remove(destino)){
          destino.decremetarGradoEntrada();
      }
    }

    public void incremetarGradoEntrada () {
        gradoEntrada++;
    }
    public void decremetarGradoEntrada () {
        gradoEntrada--;
    }

    public int getDato () {
        return dato;
    }
    public List<Nodo> getListaDeAdyacenia () {
        return listaDeAdyacenia;
    }
    public int getGradoEntrada () {
        return gradoEntrada;
    }

    public int getGradoSalida(){
        return listaDeAdyacenia.size();
    }
    public void setDato (int dato) {
        this.dato = dato;
    }

    public void setGradoEntrada(int gradoEntrada) {
        this.gradoEntrada = gradoEntrada;
    }

}
