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

        grafo.adicionarAresta("1", "0", 20);
        grafo.adicionarAresta("0", "0", 100);
        grafo.adicionarAresta("3", "2", 90);

        grafo.imprimirVertices();
        grafo.imprimirArestas();

        grafo.removerVertice("0");
        grafo.imprimirArestas();
    }
}
