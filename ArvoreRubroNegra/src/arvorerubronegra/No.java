/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvorerubronegra;

/**
 *
 * @author yuri
 */
public class No {

    private No pai;
    private No esquerdo;
    private No direito;
    private int chave;
    private int valor;
    private int cor;

    /* Construtores */
    public No(int chave) {
        this.chave = chave;
    }

    public No(int chave, int cor) {
        this.chave = chave;
        this.cor = cor;
    }

    public No(int chave, int cor, No pai) {
        this.chave = chave;
        this.cor = cor;
        this.pai = pai;
    }

    /* Getters e setters*/
    public No getPai() {
        return pai;
    }

    public void setPai(No pai) {
        this.pai = pai;
    }

    public No getEsquerdo() {
        return esquerdo;
    }

    public void setEsquerdo(No esquerdo) {
        this.esquerdo = esquerdo;
    }

    public No getDireito() {
        return direito;
    }

    public void setDireito(No direito) {
        this.direito = direito;
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }

    /* Verificações*/
    public boolean eDireito() {
        return this.getPai().getDireito() == this;
    }

    public boolean temDireito() {
        return this.getDireito() != null;
    }

    public boolean eEsquerdo() {
        return this.getPai().getEsquerdo() == this;
    }

    public boolean temEsquerdo() {
        return this.getEsquerdo() != null;
    }

    public boolean eInterno() {
        return this.temDireito() || this.temEsquerdo();
    }

    public boolean eExterno() {
        return !eInterno();
    }

    /* Altura e profundidade*/
    public int getAltura() {
        if (this.eExterno()) {
            return 0;
        } else {
            int altura;
            int alturaDireito = 0;
            int alturaEsquerdo = 0;

            if (this.temDireito()) {
                alturaDireito = this.getDireito().getAltura();
            }

            if (this.temEsquerdo()) {
                alturaEsquerdo = this.getEsquerdo().getAltura();
            }

            altura = alturaDireito > alturaEsquerdo ? alturaDireito : alturaEsquerdo;

            return altura + 1;
        }
    }

    public int getProfundidade() {
        return this.getPai() == null ? 0 : 1 + this.getPai().getProfundidade();
    }
}
