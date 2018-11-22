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
public class Aresta {

    Vertice inicio, fim;
    Object custo;
    String nome;

    public Aresta(Vertice inicio, Vertice fim, Object custo, String nome) {
        this.inicio = inicio;
        this.fim = fim;
        this.custo = custo;
        this.nome = nome;
    }

    public Vertice getInicio() {
        return inicio;
    }

    public void setInicio(Vertice inicio) {
        this.inicio = inicio;
    }

    public Vertice getFim() {
        return fim;
    }

    public void setFim(Vertice fim) {
        this.fim = fim;
    }

    public Object getCusto() {
        return custo;
    }

    public void setCusto(Object custo) {
        this.custo = custo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome + "(" + inicio.valor + "," + fim.valor + ")[" + custo + "]";
    }
    
    public Object getVerticeFinal() {
        return fim.valor;
    }

}
