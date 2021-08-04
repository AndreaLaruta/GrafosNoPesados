package bo.edu.uagrm.ficct.inf310sb.ed2202101.grafos.nopesados;

import bo.edu.uagrm.ficct.inf310sb.ed2202101.excepciones.graphsExceptions.ExcepcionAristaNoExiste;
import bo.edu.uagrm.ficct.inf310sb.ed2202101.excepciones.graphsExceptions.ExcepcionAristaYaExiste;
import bo.edu.uagrm.ficct.inf310sb.ed2202101.excepciones.graphsExceptions.ExcepcionNroVerticesInvalido;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Digrafo extends Grafo{

    public Digrafo(){
        super(); //llama al constructor del padre
    }

    public Digrafo (int nroInicialDeVertices) throws ExcepcionNroVerticesInvalido {
        super(nroInicialDeVertices);
    }

    //no cambian: insertarVertice y eliminarVertice

    /*Cambian
      - insertarArista
      - cantidadAristas
      - eliminarArista
      - gradoDeUnVertice
    */

    @Override
    public int gradoDeVertice(int posDeVertice) {
        throw new UnsupportedOperationException("Metodo no Soportado en Grafos Dirigidos");
    }

    public int gradoDeEntradaDeVertice(int posDeVertice) {
        super.validarVertice(posDeVertice);
        int entradasDeVertice = 0;
        for(int i = 0; i < listaDeAdyacencias.size(); i++){
            List<Integer> adyacentesDelVertice = listaDeAdyacencias.get(i);
            for(int j = 0; j < adyacentesDelVertice.size(); j++){
                if(posDeVertice == adyacentesDelVertice.indexOf(j)){
                    entradasDeVertice = entradasDeVertice + 1;
                }
            }
        }
        /* -- Entradas de Vertice con Foreach

        for (List<Integer> adyacentesDeUnVertice : super.listaDeAdyacencias) {
            for (Integer posDeAdyacente : adyacentesDeUnVertice) {
                if (posDeAdyacente == posDeVertice){
                    entradasDeVertice++;
                }
            }
        }
        */


        /* Usando iterable dentro del For

        for(int i = 0; i < listaDeAdyacencias.size(); i++){
            Iterable<Integer> adyacentesDeUnVertice = super.adyacentesDeVertice(i);
            for(Integer posDeAdyacente : adyacentesDeUnVertice){
                if(posDeAdyacente == posDeVertice){
                    entradasDeVertice++;
                }
            }
        }
        */

        return entradasDeVertice;
    }

    public int gradoDeSalidaDeVertice(int posDeVertice) {
        return super.gradoDeVertice(posDeVertice);
    }

    @Override
    public int cantidadDeAristas() {
        //sumar los size todas las listas existentes en la lista de Adyacencias
        int cantidadDeAristas = 0;
        for(int i = 0; i < listaDeAdyacencias.size(); i++){
            cantidadDeAristas = cantidadDeAristas + listaDeAdyacencias.get(i).size();
        }
        return cantidadDeAristas;
    }

    @Override
    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaYaExiste {
        super.validarVertice(posVerticeOrigen);
        super.validarVertice(posVerticeDestino);
        if(super.existeAdyacencia(posVerticeOrigen, posVerticeDestino)){
            throw new ExcepcionAristaYaExiste();
        }
        List<Integer> listaDeAdyacenciasDelOrigen = listaDeAdyacencias.get(posVerticeOrigen);
        listaDeAdyacenciasDelOrigen.add(posVerticeDestino);
        Collections.sort(listaDeAdyacenciasDelOrigen);
    }

    @Override
    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        super.validarVertice(posVerticeOrigen);
        super.validarVertice(posVerticeDestino);

        if(!super.existeAdyacencia(posVerticeOrigen, posVerticeDestino)){
            throw new ExcepcionAristaNoExiste();
        }
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(posVerticeDestino);
        adyacentesDelOrigen.remove(posVerticeDestino);
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
     *
     *  3.- Grafo Dirigido -> retorna componentes de las islas en Digrafo
     *
     **/

   /* public int cantIslas(){ //grafo

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
        for(int c=0; c < grafo.listaDeAdyacencias.size();c++){
            if(!controlMarcados.estaVerticeMarcado(c)){
                return c;
            }
        }
        return grafo.listaDeAdyacencias.size();
    }
    private int buscarVerticeSinMarcarConAdyacenteMarcado() {
        for(int c=0; c < grafo.listaDeAdyacencias.size();c++){
            if(!controlMarcados.estaVerticeMarcado(c)){
                for (int i = 0; i < grafo.listaDeAdyacencias.get(c).size(); i++) {
                    if (controlMarcados.estaVerticeMarcado(grafo.listaDeAdyacencias.get(c).get(i))) {
                        return c;
                    }
                }
            }
        }
        return -1;
    }

    public String componentesIslas(){
        String s="";
        controlMarcados.desmarcarTodos();
        if (grafo.listaDeAdyacencias.isEmpty()) {
            return "null";
        }
        int cant=1;
        while (!controlMarcados.estanTodosMarcados()) {
            s=s+"|"+cant+"|"+"\t ->";
            Queue<Integer> cola = new LinkedList();
            int pos=buscarVerticeSinMarcar();
            cola.add(pos);
            while (!cola.isEmpty()) {
                pos= cola.poll();
                controlMarcados.marcarVertice(pos);
                s=s+pos+"\t";
                for (int i = 0; i < grafo.listaDeAdyacencias.get(pos).size(); i++) {
                    int indice=grafo.listaDeAdyacencias.get(pos).get(i);
                    if (!controlMarcados.estaVerticeMarcado(indice)) {
                        cola.offer(grafo.listaDeAdyacencias.get(pos).get(i));          //cola : 1
                    }
                }
                pos=buscarVerticeSinMarcarConAdyacenteMarcado();
                if (pos>=0) {
                    cola.add(pos);
                }
            }
            cant++;
            s=s+"\n";
        }
        return s;
    }

    */
}
