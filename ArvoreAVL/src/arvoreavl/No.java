package arvoreavl;

public class No {

    private No pai;
    private No filhoEsquerdo;
    private No filhoDireito;
    private int chave;
    private Object valor;
    private int fatorBalanceamento;

    public No() {
    }

    public No(int chave, Object valor) {
        this.chave = chave;
        this.valor = valor;
    }

    public No getPai() {
        return pai;
    }

    public void setPai(No pai) {
        this.pai = pai;
    }

    public No getFilhoEsquerdo() {
        return filhoEsquerdo;
    }

    public void setFilhoEsquerdo(No filhoEsquerdo) {
        this.filhoEsquerdo = filhoEsquerdo;
    }

    public No getFilhoDireito() {
        return filhoDireito;
    }

    public void setFilhoDireito(No filhoDireito) {
        this.filhoDireito = filhoDireito;
    }

    public int getChave() {
        return chave;
    }

    public void setChave(int chave) {
        this.chave = chave;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public int getFatorBalanceamento() {
        return fatorBalanceamento;
    }

    public void setFatorBalanceamento(int fatorBalanceamento) {
        this.fatorBalanceamento = fatorBalanceamento;
    }

    public boolean temFilhoEsquerdo() {
        return this.filhoEsquerdo != null;
    }

    public boolean temFilhoDireito() {
        return this.filhoDireito != null;
    }

    public boolean eInterno() {
        return (this.temFilhoDireito() || this.temFilhoEsquerdo());
    }

    public boolean eExterno() {
        return !eInterno();
    }

    public int getProfundidade() {
        return this.getPai() == null ? 0 : 1 + this.getPai().getProfundidade();
    }

    public int getAltura() {
        if (this.eExterno()) {
            return 0;
        } else {
            int altura = 0;
            int alturaDireito = 0;
            int alturaEsquerdo = 0;

            if (this.temFilhoDireito()) {
                alturaDireito = this.getFilhoDireito().getAltura();
            }

            if (this.temFilhoEsquerdo()) {
                alturaEsquerdo = this.getFilhoEsquerdo().getAltura();
            }

            altura = alturaDireito > alturaEsquerdo ? alturaDireito : alturaEsquerdo;
            return ++altura;
        }
    }
}
