package bo.edu.uagrm.ficct.inf310sb.ed2202101.grafos.nopesados;

import java.util.ArrayList;
import java.util.List;

public class UtilsRecorridos {
    //creando mi estructura de marcados
    protected List<Boolean> marcados;


    public UtilsRecorridos(int numVertices){
        marcados = new ArrayList<>();
        for(int i = 0; i < numVertices; i++){
            marcados.add(Boolean.FALSE);
        }
    }

    public void marcarVertice(int posVertice){
        //pre: la posicion es valida
        marcados.set(posVertice, Boolean.TRUE);
    }

    public boolean estaVerticeMarcado(int posVertice){
        return marcados.get(posVertice);
    }

    public void desmarcarTodos(){
        for (int i = 0; i < marcados.size(); i++){
            marcados.add(Boolean.FALSE);
        }
    }

    public boolean estanTodosMarcados(){
        for(Boolean marcado : marcados){
            if(!marcado){
                return false;// si alguno no esta marcado
            }
        }
        return true; //si termina el bucle, quiere decir que estan todos marcados
    }

}
