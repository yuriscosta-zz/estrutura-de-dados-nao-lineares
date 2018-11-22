/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo;

/**
 *
 * @author yuri
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        grafo.adicionarVertice();
        grafo.adicionarVertice();
        grafo.adicionarVertice();
        grafo.adicionarVertice();

        grafo.adicionarAresta("0", "0", 50);
        grafo.adicionarAresta("0", "0", 20);
        grafo.adicionarAresta("2", "3", 90);
        grafo.adicionarAresta("1", "4", 50);

//        grafo.imprimirVertices();
//        grafo.imprimirArestas();
        grafo.substituirVertice("1", "4");
        grafo.substituirVertice("1", "4");

//        grafo.imprimirVertices();
//        grafo.substituirAresta("0", 100);
//        grafo.imprimirArestas();
//        grafo.matrizAdjacencia();;
//        grafo.matrizIncidencia();
//        grafo.matrizCusto();
    }
}
