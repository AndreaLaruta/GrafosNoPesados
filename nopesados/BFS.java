package bo.edu.uagrm.ficct.inf310sb.ed2202101.grafos.nopesados;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {//recorrido en AMPLITUD

    private UtilsRecorridos controlMarcados;//variable miembro
    private Grafo grafo;//acceso a un grafo
    private List<Integer> recorrido;//suponemos que lo busca este recorrido
    //retornar una lista con las posiciones de
    // los vertices segun lo que se visita
    //con el recorrido que se implementa

    public BFS(Grafo unGrafo, int posVerticePartida){//posicion de vertice de partida
        this.grafo = unGrafo; //guardamos el grafo
        grafo.validarVertice(posVerticePartida);//validamos la posicion del vertice
        recorrido = new ArrayList<>();
        controlMarcados = new UtilsRecorridos(this.grafo.cantidadDeVertices());// ya estan todos desmarcados
        //instanciamos Utils de Control de Recorridos
        //Se pasa del grafo-> la cantidad de vertices

        //lo que se realiza aqui -> directamente en el constructor que se ejecute el recorrido
        //ya simplemente luego necesitamos metodos para consultar por ejemplo:
        // - recuperar cual es el recorrido
        // - si esta marcado un determinado vertice en ese recorrido
        // - si hay camino hacia algun vertice, empezando por ese vertice de partida
        //hacemos su llamada:
        ejecutarBFS(posVerticePartida);
    }

    private void ejecutarBFS(int posVertice){
        Queue<Integer> cola = new LinkedList<>();
        cola.offer(posVertice);
        controlMarcados.marcarVertice(posVertice);
        do{
            int posVerticeEnTurno = cola.poll();
            //como tenemos una lista de recorridos,
            //esa lista la preparamos para que en el constructor:
            recorrido.add(posVerticeEnTurno);//si llegaramos a necesitar un BFS que no nos retorne el orden en el que visite los vertices partiendo desde posVerticePartida
                            //no necesitamos private lista List<Integer> recorrido; ni instanciar el recorrido
                            //pero en este caso lo haremos asi
            Iterable<Integer> adyacentesDelVerticeEnTurno = grafo.adyacentesDeVertice(posVerticeEnTurno);
            //con el iterable seremos capaces de revisar cuales son los adyacentes a ese verticeEnTurno
            //o a que vertices puedo llegar partiendo de ese vertice en turno en un solo salto
            //realizamos un forEach sobre el iterable
            for (Integer posVerticeAdyacente :
                    adyacentesDelVerticeEnTurno) {
                if (controlMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
                    cola.offer(posVerticeAdyacente);
                    controlMarcados.marcarVertice(posVerticeAdyacente);
                }
            }

        }while(!cola.isEmpty());
    }

    //determinar si hay camino desde verticePartida a otroVertice
    //hay camino desde vertice que se pasa al constructor a otro vertice (posVertice)
    public boolean hayCaminoAVertice(int posVertice){
        grafo.validarVertice(posVertice);
        return controlMarcados.estaVerticeMarcado(posVertice);//del control de marcados saber si esta marcado el vertice de esa posicion
        //asi sabemos si hay camino hacia el vertice, si posVertice quedo marcado mientras se realizaba el recorrido partiendo del verticePartida,
        //quiere decir que desde el verticePartida a posVertice hay camino, si paso el mismo vertice, el recorrido lo toma como parte del camino
        //y quiere decir que desde el vertice de partida hay camino al verticeDePartida
    }

    //recorrido Desde verticePartida
    public Iterable<Integer> elRecorrido(){
        return this.recorrido;//lista se maneja como iterable
    }

    //si hay caminos a todos partiendo de ese vertice
    public boolean hayCaminosATodos(){
        return controlMarcados.estanTodosMarcados();
    }

}
