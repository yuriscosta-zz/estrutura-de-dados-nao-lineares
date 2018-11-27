/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo;

import java.util.ArrayList;
import java.util.Vector;
import javafx.scene.Camera;
import jdk.nashorn.internal.objects.NativeArray;

/**
 *
 * @author yuri
 */
public class Grafo {

    ArrayList<Vertice> vertices;
    ArrayList<Aresta> arestas;
    int espaco = 1;

    public Grafo() {
        vertices = new ArrayList<>();
        arestas = new ArrayList<>();
    }

    public void adicionarVertice() {
        System.out.println("Adicionar vértice:");
        vertices.add(new Vertice("v" + vertices.size()));
        System.out.println(vertices.get(vertices.size() - 1).toString());
    }

    public void adicionarAresta(String i, String f, Object custo) {
        System.out.println("Adicionar aresta:");

        Vertice inicio = existeVertice(i);
        Vertice fim = existeVertice(f);

        if (inicio == null) {
            System.out.println("Vértice " + inicio + " é inexistente!");
        } else if (fim == null) {
            System.out.println("Vértice " + fim + " é inexistente!");
        } else if (existeAresta(inicio.valor, fim.valor, custo) != null) {
            System.out.println("Aresta já existente!");
        } else {
            Aresta aresta = new Aresta(inicio, fim, custo, "a" + arestas.size());
            arestas.add(aresta);
            System.out.println(arestas.get(arestas.size() - 1).toString());
            espacamento(custo, true);
        }

    }

    public void removerVertice(String vertice) {
        System.out.println("Remover vértice:");
        String valor = "Valor não encontrado";
        Vertice verticeAux = existeVertice(vertice);
        if (verticeAux != null) {
            valor = (String) verticeAux.valor;
            vertices.remove(verticeAux);
        }

        removerArestaAux(valor, vertice);

        System.out.println(valor);
    }

    public void removerArestaAux(String nomeVertice, String numerovertice) {
        System.out.println("Removendo arestas da vértice removida:");

        ArrayList<Aresta> arestasAux = new ArrayList<>();

        for (Aresta a : arestas) {
            if (!a.inicio.valor.equals(nomeVertice) && !a.fim.valor.equals(nomeVertice)) {
                arestasAux.add(a);
            }
        }

        arestas = arestasAux;
    }

    public void removerAresta(String inicio, String fim, Object custo) {
        System.out.println("Remover aresta:");
        String valor = "Valor não encontrado";
        Aresta arestaAux = existeAresta(inicio, fim, custo);
        if (arestaAux != null) {
            valor = arestaAux.toString();
            arestas.remove(arestaAux);
            espacamento(custo, false);
        }

        System.out.println(valor);
    }

    private Vertice existeVertice(String vertice) {
        vertice = "v" + vertice;
        for (Vertice v : this.vertices) {
            if (v.valor.equals(vertice)) {
                return v;
            }
        }

        return null;
    }

    private Aresta existeAresta(String inicio, String fim, Object custo) {
        inicio = "v" + inicio;
        fim = "v" + fim;
        for (Aresta a : this.arestas) {
            if (a.inicio.valor.equals(inicio) && a.fim.valor.equals(fim) && a.custo == custo) {
                return a;
            }
        }

        return null;
    }

    private Aresta existeArestaSemCusto(Vertice v1, Vertice v2) {
        for (Aresta aresta : this.arestas) {
            if (aresta.inicio == v1 && aresta.fim == v2) {
                return aresta;
            }
        }

        return null;
    }

//
//    private Aresta ExisteArestaComNome(String nome) {
//        for (Aresta aresta : this.arestas) {
//            if (aresta.nome.equals(nome)) {
//                return aresta;
//            }
//        }
//
//        return null;
//    }

//    private boolean eAdjacente(Vertice v1, Vertice v2) {
//        for (Aresta a : arestas) {
//            if (a.inicio == v1 && a.fim == v2) {
//                return true;
//            }
//        }
//
//        return false;
//    }

    private int adjacencia(Vertice v1, Vertice v2) {
        int total = 0;
        for (Aresta a : arestas) {
            if (a.inicio == v1 && a.fim == v2) {
                total++;
            }
        }

        return total;
    }

    public void descobrirMelhorCaminho(String inicio, String fim) {
        System.out.println("A menor rota = v" + inicio + " -> v" + fim);

        Vertice vInicio = existeVertice(inicio);
        Vertice vFim = existeVertice(fim);

        if (vInicio == null || vFim == null) {
            System.out.println("Vértice(s) Inexistente(s)");
        } else {
            ArrayList<Rota> abertas = new ArrayList<>();
            ArrayList<Rota> fechadas = new ArrayList<>();

            Rota rotaPai = new Rota();
            rotaPai.vertice = vInicio;
            rotaPai.peso = retornarCusto(vInicio, vInicio);
            rotaPai.pai = null;

            while (!rotaPai.vertice.equals(vFim)) {
                ArrayList<Rota> adjacentes = rotasAdjacentes(rotaPai);
                for (int i = 1; i < adjacentes.size(); i++) {
                    if (rotaPai.pai != null) {
                        adjacentes.get(i).pai.peso = rotaPai.pai.peso + retornarCusto(
                                rotaPai.vertice, adjacentes.get(i).vertice
                        );
                    } else {
                        adjacentes.get(i).pai.peso = retornarCusto(
                                rotaPai.vertice, adjacentes.get(i).vertice
                        );
                    }

                    if (!fechadas.contains(adjacentes.get(i))) {
                        adjacentes.get(i).pai = rotaPai;
                    }

                    abertas.add(adjacentes.get(i));
                }

                fechadas.add(rotaPai);
                abertas.remove(rotaPai);
                rotaPai = menorCaminhoAdjacente(adjacentes);
            }

            System.out.println("Rota a percorrer:");
            Rota rotaAux = rotaPai;
            int custo = 0;
            String texto = "";

            while (rotaAux != null) {
                custo += rotaAux.peso;
                if (rotaAux.pai == null) {
                    texto = rotaAux.vertice.valor + "[" + rotaAux.peso + "]" + texto;
                } else {
                    texto = " -> " + rotaAux.vertice.valor + "[" + rotaAux.peso + "]" + texto;
                }

                rotaAux = rotaAux.pai;
            }

            System.out.println(texto + "\nCusto total: " + custo);
        }
    }

    private String totalCusto(Vertice v1, Vertice v2) {
        int total = 0;
        for (Aresta a : arestas) {
            if (a.inicio == v1 && a.fim == v2) {
                total += (Integer) a.custo;
            }
        }

        return "" + total;
    }

    public void imprimirVertices() {
        int qtd = 0;
        System.out.println("Listar vértices:");
        for (Vertice v : vertices) {
            System.out.println(v.valor);
            qtd++;
        }
    }

    public void imprimirArestas() {
        System.out.println("Listar arestas:");
        for (Aresta a : arestas) {
            System.out.println(a.toString());
        }
    }

    public void matrizAdjacencia() {
        System.out.println("Matriz de adjacência:");
        for (Vertice v1 : vertices) {
            if (vertices.indexOf(v1) == 0) {
                for (Vertice v2 : vertices) {
                    if (vertices.indexOf(v2) == 0) {
                        System.out.print("   " + v2.valor + "|");
                    } else if (vertices.indexOf(v1) < vertices.size()) {
                        System.out.print(v2.valor + "|");
                    } else {
                        System.out.print(v2.valor);
                    }
                }

                System.out.println("");
            }

            for (Vertice v2 : vertices) {
                if (vertices.indexOf(v2) == 0) {
                    System.out.print(v1.valor + "| ");
                }

                System.out.print(adjacencia(v1, v2) + "| ");
            }

            System.out.println("");
        }
    }

    public void matrizIncidencia() {
        System.out.println("Matriz incidência:");
        for (Vertice v : vertices) {
            if (vertices.indexOf(v) == 0) {
                for (Aresta a : arestas) {
                    if (arestas.indexOf(a) == 0) {
                        System.out.print("   " + a.nome + "|");
                    } else if (arestas.indexOf(v) < arestas.size()) {
                        System.out.print(a.nome + "|");
                    } else {
                        System.out.print(a.nome);
                    }
                }

                System.out.println("");
            }

            for (Aresta a : arestas) {
                if (arestas.indexOf(a) == 0) {
                    System.out.print(v.valor + "|");
                }

                if (v.valor.equals(a.inicio.valor) && v.valor.equals(a.fim.valor)) {
                    System.out.print("+2|");
                } else if (v.valor.equals(a.inicio.valor)) {
                    System.out.print("+1|");
                } else if (v.valor.equals(a.fim.valor)) {
                    System.out.print("-1|");
                } else {
                    System.out.print(" 0|");
                }
            }

            System.out.println("");
        }
    }

    public void matrizCusto() {
        System.out.println("Matriz de custo:");
        for (Vertice v1 : vertices) {
            if (vertices.indexOf(v1) == 0) {
                for (Vertice v2 : vertices) {
                    if (vertices.indexOf(v2) == 0) {
                        System.out.print("   " + this.espacar(v2.valor));
                    } else if (vertices.indexOf(v1) < vertices.size()) {
                        System.out.print(this.espacar(v2.valor));
                    } else {
                        System.out.print(this.espacar(v2.valor));
                    }
                }

                System.out.println("");
            }

            for (Vertice v2 : vertices) {
                if (vertices.indexOf(v2) == 0) {
                    System.out.print(v1.valor + "|");
                }

                System.out.print(espacar(totalCusto(v1, v2)));
            }

            System.out.println("");
        }
    }

    public void substituirVertice(String antigo, String novo) {
        System.out.println("Substituir vértice: ");
        boolean sub = false;
        for (Vertice v : vertices) {
            if (v.valor.equals("v" + antigo)) {
                v.valor = "v" + novo;
                System.out.println("v" + antigo + " -> v" + novo);
                sub = true;
            }
        }

        if (!sub) {
            System.out.println("v" + antigo + " não encontrado");
        }
    }

    public void substituirAresta(String aresta, Object custo) {
        System.out.println("Substituir aresta: ");
        boolean sub = false;
        for (Aresta a : arestas) {
            System.out.println(a.toString() + " -> [" + custo + "]");
            a.custo = custo;
            sub = true;
            espacamento(custo, true);
        }

        if (!sub) {
            System.out.println("a" + aresta + " não encontrado");
        }
    }

    private void espacamento(Object custo, boolean adicionar) {
        String custoString = custo.toString();
        if (adicionar) {
            if (custoString.length() > espaco) {
                espaco = custoString.length();
            }
        } else {
            espaco = 1;
            for (Aresta a : arestas) {
                if (a.custo.toString().length() > espaco) {
                    espaco = a.custo.toString().length();
                }
            }
        }
    }

    private String espacar(String custo) {
        String resultado = "";

        int i = espaco;
        while (i > custo.length()) {
            resultado += " ";
            i--;
        }

        return resultado + custo + "|";
    }

    private int retornarCusto(Vertice inicio, Vertice fim) {
        Aresta aresta = existeArestaSemCusto(inicio, fim);
        if (inicio == fim) {
            return 0;
        } else if (aresta != null) {
            return (Integer) aresta.custo;
        } else {
            return 1000000000;
        }
    }

    private ArrayList<Rota> rotasAdjacentes(Rota rotaPai) {
        ArrayList<Rota> rotas = new ArrayList<>();
        for (Aresta aresta : arestas) {
            if (rotaPai.vertice == aresta.inicio) {
                Rota rota = new Rota();
                rota.vertice = aresta.fim;
                rota.pai = rotaPai;
                rota.peso = (Integer) aresta.custo;
                rotas.add(rota);
            }
        }

        return rotas;
    }

    private Rota menorCaminhoAdjacente(ArrayList<Rota> adjacentes) {
        int menor = 1000000000;
        Rota rota = adjacentes.get(0);
        for (Rota r : adjacentes) {
            if (menor > r.peso) {
                rota = r;
            }
        }
        
        return rota;
    }
}
