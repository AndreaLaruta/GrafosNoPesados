package bo.edu.uagrm.ficct.inf310sb.ed2202101.grafos.nopesados;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DFS {
    private UtilsRecorridos controlMarcados;
    private Grafo grafo;
    private List<Integer> recorrido;


    public DFS(Grafo unGrafo, int posVerticePartida){
        this.grafo = unGrafo;
        grafo.validarVertice(posVerticePartida);
        recorrido = new ArrayList<>();
        controlMarcados = new UtilsRecorridos(this.grafo.cantidadDeVertices());//vertices Desmarcados
        procesarDFS(posVerticePartida);//ayuda a obtener por ej. isla1 ...isla2
        //arriba procesarDFS a partir desde un vertice
    }

    public void procesarDFS(int posVertice){//Y luego decir que apesar que DFS no pudo visitar todos
                                            //se desea continuar pero desde otro vertice, entonces llamamos a
                                            //procesarDFS y este continua desde ese otroVertice: marcandolo
                                            //agregandolo al recorrido y le hace la llamada recurs. correspondiente
        controlMarcados.marcarVertice(posVertice);
        recorrido.add(posVertice);
        Iterable<Integer> adyacentesDeVerticeEnTurno = grafo.adyacentesDeVertice((posVertice));
        for(Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno){
            if(!controlMarcados.estaVerticeMarcado(posVerticeAdyacente)){
                procesarDFS(posVerticeAdyacente);//pila del sistema
            }
        }
    }

    public boolean hayCaminoAVertice(int posVertice){
        grafo.validarVertice(posVertice);
        return controlMarcados.estaVerticeMarcado(posVertice);
    }

    public Iterable<Integer> elRecorrido(){
        return this.recorrido;
    }

    //si hay caminos a todos partiendo de ese vertice
    public boolean hayCaminosATodos(){
        return controlMarcados.estanTodosMarcados();
    }






    /***************************************************************************************************
     * **************************************************************************************************
     * ********************************* Ejercicios Practico 2 ******************************************
     * **************************************************************************************************
     * **************************************************************************************************
     * */

    /**
     *
     *  2.- Grafo Dirigido -> encontrar ciclos sin matriz de adyacencia
     *
     **/

    public boolean existeCiclo(){
        controlMarcados.desmarcarTodos();
        while(!controlMarcados.estanTodosMarcados()){
            int vertice = buscarVerticesSinMarcar();
            Queue<Integer> cola = new LinkedList<>();
            cola.offer(vertice);
            List<Integer> caminos = new ArrayList<>();
            while(!cola.isEmpty()){
                vertice = cola.poll();
                if(caminos.contains(vertice)){
                    return true;
                }
                caminos.add(vertice);
                controlMarcados.marcarVertice(vertice);
                for(int i =0; i < grafo.listaDeAdyacencias.get(vertice).size(); i++){
                    cola.add(grafo.listaDeAdyacencias.get(vertice).get(i));
                }
            }
        }
        return false;
    }

    public int buscarVerticesSinMarcar(){
        for(int i = 0; i < grafo.listaDeAdyacencias.size(); i++){
            if(!controlMarcados.estaVerticeMarcado(i)){
                return i;
            }
        }
        return grafo.listaDeAdyacencias.size();
    }


    /***************************************************************************************************
     * **************************************************************************************************
     * ********************************* Ejercicios Practico 2 ******************************************
     * **************************************************************************************************
     * **************************************************************************************************
     * */


    /**
     *
     *  3.- Grafo Dirigido -> encontrar ciclos sin matriz de adyacencia
     *
     **/

    public String componentesCiclo(){
        String s="";
        controlMarcados.desmarcarTodos();
        while(!controlMarcados.estanTodosMarcados()){
            int vertice = buscarVerticeSinMarcar();
            Queue <Integer> cola = new LinkedList<>();
            cola.offer(vertice);
            List<Integer> caminos = new ArrayList<>();
            while(!cola.isEmpty()){
                vertice = cola.poll();
                if(caminos.contains(vertice)){
                    int i=caminos.indexOf(vertice);
                    for ( i= i; i < caminos.size(); i++) {
                        s=s+caminos.get(i)+"\t";
                    }
                    return s;
                }
                caminos.add(vertice);
                controlMarcados.marcarVertice(vertice);
                for (int i= 0; i < grafo.listaDeAdyacencias.get(vertice).size(); i++){
                    cola.add(grafo.listaDeAdyacencias.get(vertice).get(i));
                }
            }
        }
        return "No hay Ciclo";
    }

    private int buscarVerticeSinMarcar() {
        for(int c=0; c < grafo.listaDeAdyacencias.size();c++){
            if(!controlMarcados.estaVerticeMarcado(c)){
                return c;
            }
        }
        return grafo.listaDeAdyacencias.size();
    }

}
