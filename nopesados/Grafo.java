package bo.edu.uagrm.ficct.inf310sb.ed2202101.grafos.nopesados;

import bo.edu.uagrm.ficct.inf310sb.ed2202101.arboles.NodoBinario;
import bo.edu.uagrm.ficct.inf310sb.ed2202101.excepciones.graphsExceptions.*;
import bo.edu.uagrm.ficct.inf310sb.ed2202101.grafos.nopesados.UtilsRecorridos.*;

import java.util.*;

public class Grafo{
    protected List<List<Integer>> listaDeAdyacencias;
    //no guardaremos el valor real del vertice dentro del Grafo (no tenerlo como atributo)
    //al manejarlo por fuera, hace que no nos preocupemos del valor real
    //nos permite saber en que posicion se encuentra, respecto a donde lo tengamos
    //almacenado el valor real de cada vertice

    public Grafo(){
        this.listaDeAdyacencias = new ArrayList<>();
    }

    public Grafo(int nroInicialDeVertices) throws ExcepcionNroVerticesInvalido {
        if(nroInicialDeVertices <= 0){
            throw new ExcepcionNroVerticesInvalido();
        }

        this.listaDeAdyacencias = new ArrayList<>();

        for (int i = 0; i < nroInicialDeVertices; i++){
            this.insertarVertice();
        }
    }

    public void insertarVertice() {
        List<Integer> adyacentesDeNuevoVertice = new ArrayList<>();
        this.listaDeAdyacencias.add(adyacentesDeNuevoVertice);
    }

    public int cantidadDeVertices(){
        return listaDeAdyacencias.size();
    }

    public int gradoDeVertice(int posDeVertice){
        //no estamos guardando el vertice como tal,
        //entonces lo refernciamos con una posicion (almacenando)
        //recibiremos la posicion, respecto a su lista de Adyacencias

        validarVertice(posDeVertice);

        //grado de un vertice No dirigido en de entrada y salida
        List<Integer> adyacenteDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        return adyacenteDelVertice.size();
    }

    protected void validarVertice(int posicionDeVertice) {
        //si la posicion que nos pasan NO esta dentro de rango
        //lanzamos una excepcion
        if(posicionDeVertice < 0 ||
        posicionDeVertice >= this.cantidadDeVertices()){
            throw new IllegalArgumentException("No existe vertice en la posicion" +
                    posicionDeVertice + "en este Grafo");
        }
    }

    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)){
            throw new ExcepcionAristaYaExiste();
        }
        //como es un grafo no dirigido
        //y estamos trabajando con listas de Adyacencia
        //debemos insertar en ambos sentidos:
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        //adicionamos la posicion del destino
        adyacentesDelOrigen.add(posVerticeDestino);
        //si lo queremos ordenados
        Collections.sort(adyacentesDelOrigen);//Integer es comparable, solo comparables funcionan en Collections

        //En caso de que el vertice de destino sea diferente
        //si es el mismo vertice destino, no tiene sentido insertar 2 veces
        //seria un lazo

        //si no es un lazo
        //preguntamos si son diferentes:
        if( posVerticeOrigen != posVerticeDestino){
            List<Integer> adyacentesDelDestino = this.listaDeAdyacencias.get(posVerticeDestino);
            adyacentesDelDestino.add(posVerticeOrigen);
            Collections.sort(adyacentesDelDestino);
        }
    }
    /*
    * si el anterior fuese el insertar de un Grafo Dirigido
    * lo que cambia es:
    *
    * No se inserta
    * En el Destino la posicion de Origen (ultimo IF)
    *
    * */

/**/
    public boolean existeAdyacencia(int posVerticeOrigen, int posVerticeDestino){
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);

        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        return adyacentesDelOrigen.contains(posVerticeDestino);
    }

    //Iterable permite iterar en una coleccion
    public Iterable<Integer> adyacentesDeVertice(int posDeVertice){
        validarVertice(posDeVertice);
        List<Integer> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        Iterable<Integer> iterableDeAdyacentes = adyacentesDelVertice;
        return iterableDeAdyacentes;
    }

    public int cantidadDeAristas(){
        int cantAristas = 0;
        int cantLazos = 0;

        for(int i = 0; i < this.listaDeAdyacencias.size(); i++){
            List<Integer> adyacentesDeUnVertice = this.listaDeAdyacencias.get(i);
            for (Integer posDeAyacente: adyacentesDeUnVertice) {
                if (i == posDeAyacente){
                    cantLazos++;
                }else{
                    cantAristas++;
                }
            }//fin forEach
        }//fin for
        return cantLazos + (cantAristas / 2);
    }

    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino)throws ExcepcionAristaNoExiste{
        if(!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)){
            throw new ExcepcionAristaNoExiste();
        }
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(posVerticeDestino);
        adyacentesDelOrigen.remove(posicionDelDestino);

        if(posVerticeOrigen != posVerticeDestino){
            List<Integer> adyacentesDelDestino = this.listaDeAdyacencias.get(posVerticeDestino);
            int posicionDelOrigen = adyacentesDelDestino.indexOf(posVerticeOrigen);
            adyacentesDelDestino.remove(posicionDelOrigen);
        }
    }


    public void eliminarVertice(int posVerticeAEliminar){
        validarVertice(posVerticeAEliminar);
        this.listaDeAdyacencias.remove(posVerticeAEliminar);

        for (List<Integer> adyacentesDeUnVertice :
                this.listaDeAdyacencias) {
            int posicionDeVerticeEnAdyacencia = adyacentesDeUnVertice.indexOf(posVerticeAEliminar);

            if(posicionDeVerticeEnAdyacencia >= 0){
                adyacentesDeUnVertice.remove(posicionDeVerticeEnAdyacencia);
            }

            for(int i = 0; i < adyacentesDeUnVertice.size(); i++){
                int posicionAdyacente = adyacentesDeUnVertice.get(i);
                if(posicionAdyacente > posVerticeAEliminar){
                    adyacentesDeUnVertice.set(i, posicionAdyacente - 1);
                }
            }
        }
    }

    @Override
    public String toString() {
        String cadena = new String();
        if(listaDeAdyacencias.isEmpty()){
            return "null";
        }
        for (int i = 0; i < listaDeAdyacencias.size(); i++){
            cadena = cadena + "|" + i + "|" + "\t" + "-> [";
            for(int j = 0; j < listaDeAdyacencias.get(i).size(); j++){
                cadena = cadena + listaDeAdyacencias.get(i).get(j) + "\t";
            }
            cadena = cadena + "]" + "\n";
        }
        return cadena;
    }
    
    /***************************************************************************************************
    * **************************************************************************************************
    * ********************************* Ejercicios Practico 2 ******************************************
    * **************************************************************************************************
    * ************************************************************************************************** 
    * */
    
    /**
     *  1.- En un grafo No Dirigido -> realizar metodo eliminarVertice
     *  
    **/
    
    public void eliminarVerticeEj1 (int posVerticeAEliminar){
        validarVertice(posVerticeAEliminar);
        this.listaDeAdyacencias.remove(posVerticeAEliminar);

        for (List<Integer> adyacentesDeUnVertice :
                this.listaDeAdyacencias) {
            int posicionDeVerticeEnAdyacencia = adyacentesDeUnVertice.indexOf(posVerticeAEliminar);
            if(posicionDeVerticeEnAdyacencia >= 0){
                adyacentesDeUnVertice.remove(posicionDeVerticeEnAdyacencia);
            }

            for(int i = 0; i < adyacentesDeUnVertice.size(); i++){
                int posicionAdyacente = adyacentesDeUnVertice.get(i);
                if(posicionAdyacente > posVerticeAEliminar){
                    adyacentesDeUnVertice.set(i, posicionAdyacente - 1);
                }
            }
        }
    }

    /**
     *
     *  10 .- Grafo Dirigido -> cantidad de Islas
     *
     **/

  /*  public int cantIslas(){ //grafo

        controlMarcados.desmarcarTodos();
        int islas=0;
        int pos=buscarVerticeSinMarcar();

        while (!controlMarcados.estanTodosMarcados()){
            procesarDFS(pos);
            if(hayCaminosATodos()){
                islas++;
                return islas;
            }
            pos= buscarVerticeSinMarcarConAdyacenteMarcado();
            if (pos<0) {
                pos=buscarVerticeSinMarcar();
                islas++;
            }
        }
        return islas;
    }


    private int buscarVerticeSinMarcar() {
        for(int c=0; c < listaDeAdyacencias.size();c++){
            if(!controlMarcados.estaVerticeMarcado(c)){
                return c;
            }
        }
        return listaDeAdyacencias.size();
    }
    private int buscarVerticeSinMarcarConAdyacenteMarcado() {
        for(int c=0; c < listaDeAdyacencias.size();c++){
            if(!controlMarcados.estaVerticeMarcado(c)){
                for (int i = 0; i < listaDeAdyacencias.get(c).size(); i++) {
                    if (controlMarcados.estaVerticeMarcado(listaDeAdyacencias.get(c).get(i))) {
                        return c;
                    }
                }
            }
        }
        return -1;
    }
*/

}
