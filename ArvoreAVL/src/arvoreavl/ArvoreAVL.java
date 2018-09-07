package arvoreavl;

import java.util.List;
import java.util.ArrayList;

public class ArvoreAVL {

    public static int INSERCAO = +1;
    public static int REMOCAO = -1;

    public No raiz;
    public int tamanho;

    public ArvoreAVL() {
        this.tamanho = 0;
    }

    public No getRaiz() {
        return raiz;
    }

    public void setRaiz(No raiz) {
        this.raiz = raiz;
    }

    public boolean estaVazia() {
        return this.getRaiz() == null;
    }

    public int getAltura() {
        return this.getRaiz() != null ? this.getRaiz().getAltura() : 0;
    }

    public List<No> getFilhos() {
        List<No> filhos = new ArrayList<>();
        this.getFilhos(filhos, this.getRaiz());

        return filhos;
    }

    public void getFilhos(List<No> filhos, No no) {
        if (no != null) {
            if (no.eInterno()) {
                this.getFilhos(filhos, no.getFilhoEsquerdo());
            }

            filhos.add(no);

            if (no.eInterno()) {
                this.getFilhos(filhos, no.getFilhoDireito());
            }
        }
    }

    public No pesquisar(int chave) {
        if (this.getRaiz() == null) {
            return this.getRaiz();
        }

        return pesquisar(chave, this.getRaiz());
    }

    private No pesquisar(int chave, No no) {
        if (no.eExterno()) {
            return no;
        }

        if (chave < no.getChave()) {
            return no.getFilhoEsquerdo() != null ? this.pesquisar(chave, no.getFilhoEsquerdo()) : no;
        } else if (chave == no.getChave()) {
            return no;
        } else if (chave > no.getChave()) {
            return no.getFilhoDireito() != null ? this.pesquisar(chave, no.getFilhoDireito()) : no;
        }

        return null;
    }

    private void rotacaoEsquerdaSimples(int chave) {
        No no = this.pesquisar(chave);
        if (no != null) {
            rotacaoEsquerdaSimples(no);
        }
    }

    private No rotacaoEsquerdaSimples(No no) {
        No filhoDireito = no.getFilhoDireito();
        No pai = no.getPai();
        No novoFilhoDireito = filhoDireito.getFilhoEsquerdo();

        if (pai != null) {
            if (no.getChave() > pai.getChave()) {
                pai.setFilhoDireito(filhoDireito);
            } else {
                pai.setFilhoEsquerdo(filhoDireito);
            }
        } else {
            this.setRaiz(filhoDireito);
        }

        filhoDireito.setPai(pai);
        filhoDireito.setFilhoEsquerdo(no);

        no.setPai(filhoDireito);
        no.setFilhoDireito(novoFilhoDireito);

        if (novoFilhoDireito != null) {
            novoFilhoDireito.setPai(no);
        }

        no.setFatorBalanceamento(no.getFatorBalanceamento() + 1 - Math.min(filhoDireito.getFatorBalanceamento(), 0));
        filhoDireito.setFatorBalanceamento(filhoDireito.getFatorBalanceamento() + 1 + Math.max(no.getFatorBalanceamento(), 0));

        return no;
    }

    private void rotacaoEsquerdaDupla(int chave) {
        No no = this.pesquisar(chave);
        if (no != null) {
            rotacaoEsquerdaDupla(no);
        }
    }

    private No rotacaoEsquerdaDupla(No no) {
        No filhoDireito = no.getFilhoDireito();

        this.rotacaoDireitaSimples(filhoDireito);
        this.rotacaoEsquerdaSimples(no);

        return no;
    }

    private void rotacaoDireitaSimples(int chave) {
        No no = this.pesquisar(chave);
        if (no != null) {
            rotacaoDireitaSimples(no);
        }
    }

    private No rotacaoDireitaSimples(No no) {
        No filhoEsquerdo = no.getFilhoEsquerdo();
        No pai = no.getPai();
        No novoFilhoEsquerdo = filhoEsquerdo.getFilhoDireito();

        if (pai != null) {
            if (no.getChave() > pai.getChave()) {
                pai.setFilhoDireito(filhoEsquerdo);
            } else {
                pai.setFilhoEsquerdo(filhoEsquerdo);
            }
        } else {
            this.setRaiz(filhoEsquerdo);
        }

        filhoEsquerdo.setPai(pai);
        filhoEsquerdo.setFilhoDireito(no);

        no.setPai(filhoEsquerdo);
        no.setFilhoEsquerdo(novoFilhoEsquerdo);

        if (novoFilhoEsquerdo != null) {
            novoFilhoEsquerdo.setPai(no);
        }

        no.setFatorBalanceamento(no.getFatorBalanceamento() - 1 - Math.max(filhoEsquerdo.getFatorBalanceamento(), 0));
        filhoEsquerdo.setFatorBalanceamento(filhoEsquerdo.getFatorBalanceamento() - 1 + Math.min(no.getFatorBalanceamento(), 0));

        return no;
    }

    private void rotacaoDireitaDupla(int chave) {
        No no = this.pesquisar(chave);
        if (no != null) {
            rotacaoDireitaDupla(no);
        }
    }

    private No rotacaoDireitaDupla(No no) {
        No filhoEsquerdo = no.getFilhoEsquerdo();

        this.rotacaoEsquerdaSimples(filhoEsquerdo);
        this.rotacaoDireitaSimples(no);

        return no;
    }

    public void balancear(No no) {
        int fatorBalanceamento = no.getFatorBalanceamento();

        if (fatorBalanceamento == 2) {
            if (no.getFilhoEsquerdo() != null && no.getFilhoEsquerdo().getFatorBalanceamento() < 0) {
                this.rotacaoDireitaDupla(no);
            } else {
                this.rotacaoDireitaSimples(no);
            }
        } else if (fatorBalanceamento == -2) {
            if (no.getFilhoDireito() != null && no.getFilhoDireito().getFatorBalanceamento() > 0) {
                this.rotacaoEsquerdaDupla(no);
            } else {
                this.rotacaoEsquerdaSimples(no);
            }
        }
    }

    private void atualizarFatorBalanceamento(No no, int tipoOperacao) {
        No pai = no.getPai();
        if (no == null || pai == null) {
            return;
        }

        if (tipoOperacao == INSERCAO) {
            if (no.getChave() > pai.getChave()) {
                pai.setFatorBalanceamento(pai.getFatorBalanceamento() - 1);
            } else {
                pai.setFatorBalanceamento(pai.getFatorBalanceamento() + 1);
            }

            if (pai.getFatorBalanceamento() != 0) {
                if (pai.getFatorBalanceamento() > 1 || pai.getFatorBalanceamento() < -1) {
                    balancear(pai);
                } else {
                    atualizarFatorBalanceamento(pai, tipoOperacao);
                }
            }
        } else if (tipoOperacao == REMOCAO) {
            if (no.getChave() > pai.getChave()) {
                pai.setFatorBalanceamento(pai.getFatorBalanceamento() + 1);
            } else {
                pai.setFatorBalanceamento(pai.getFatorBalanceamento() - 1);
            }

            if ((pai.getFatorBalanceamento() == -2) || (pai.getFatorBalanceamento() == 2)) {
                balancear(pai);
                atualizarFatorBalanceamento(pai.getPai(), tipoOperacao);
            } else {
                atualizarFatorBalanceamento(pai, tipoOperacao);
            }
        }
    }

    public void inserir(int chave) {
        this.inserir(chave, null);
    }

    private void inserir(int chave, Object valor) {
        this.tamanho++;
        No novoNo = new No(chave, valor);

        if (this.getRaiz() == null) {
            this.setRaiz(novoNo);
            return;
        }

        No no = this.pesquisar(chave);

        if (no.getChave() != chave) {
            novoNo.setPai(no);

            if (chave > no.getChave()) {
                no.setFilhoDireito(novoNo);
            } else {
                no.setFilhoEsquerdo(novoNo);
            }

            atualizarFatorBalanceamento(novoNo, INSERCAO);
        } else {
            no.setValor(valor);
        }
    }

    public No remover(int chave) {
        if (this.getRaiz() == null) {
            return null;
        }

        return this.remover(chave, this.getRaiz());
    }

    private No remover(int chave, No no) {
        if (no == null) {
            return null;
        }

        if (chave < no.getChave()) {
            return this.remover(chave, no.getFilhoEsquerdo());
        } else if (chave > no.getChave()) {
            return remover(chave, no.getFilhoDireito());
        } else {
            // 2 filhos
            if (no.getFilhoEsquerdo() != null && no.getFilhoDireito() != null) {
                No noRemovido = no;
                No novoNo = no.getFilhoDireito();

                while (novoNo.getFilhoEsquerdo() != null && no.getFilhoDireito() != null) {
                    novoNo = novoNo.getFilhoEsquerdo();
                }

                novoNo = remover(novoNo.getChave(), no);
                no.setChave(novoNo.getChave());
                no.setValor(novoNo.getValor());

                return noRemovido;

                // 1 filho esquerdo
            } else if (no.getFilhoEsquerdo() != null) {
                No noRemovido = no;
                atualizarFatorBalanceamento(no, REMOCAO);

                if (no.getPai() == null) {
                    this.setRaiz(no.getFilhoEsquerdo());
                } else {
                    if (no.getPai().getChave() < no.getChave()) {
                        no.getPai().setFilhoDireito(no.getFilhoEsquerdo());
                    } else {
                        no.getPai().setFilhoEsquerdo(no.getFilhoEsquerdo());
                    }
                }

                no.getFilhoEsquerdo().setPai(no.getPai());
                return noRemovido;

                // 1 filho direito
            } else if (no.getFilhoDireito() != null) {
                No noRemovido = no;
                atualizarFatorBalanceamento(no, REMOCAO);

                if (no.getPai() == null) {
                    this.setRaiz(no.getFilhoDireito());
                } else {
                    if (no.getPai().getChave() < no.getChave()) {
                        no.getPai().setFilhoDireito(no.getFilhoDireito());
                    } else {
                        no.getPai().setFilhoEsquerdo(no.getFilhoDireito());
                    }
                }

                no.getFilhoDireito().setPai(no.getPai());
                return noRemovido;

                // 0 filhos
            } else {
                No pai = no.getPai();

                if (pai == null) {
                    this.setRaiz(null);
                    return no;
                }

                atualizarFatorBalanceamento(no, REMOCAO);
                no.setPai(null);

                if (no.getChave() > pai.getChave()) {
                    pai.setFilhoDireito(null);
                } else {
                    pai.setFilhoEsquerdo(null);
                }

                return no;
            }
        }
    }

    @Override
    public String toString() {
        String retorno = "\nAVL: ";
        retorno += "\n\n";

        if (this.getRaiz() == null) {
            retorno += "A árvore está vazia!\n";
        } else {
            for (int i = 0; i <= this.getAltura(); i++) {
                for (int j = 0; j <= this.getFilhos().size() + 1; j++) {
                    boolean ok = false;
                    int index = 0;
                    for (No filho : this.getFilhos()) {
                        if (filho.getProfundidade() == i && index + 1 == j) {
                            retorno += String.format("%03d[%d]", filho.getChave(), filho.getFatorBalanceamento());
                            ok = true;
                            break;
                        }

                        index++;
                    }
                    if (ok) {
                        continue;
                    }

                    retorno += "------";
                }
                retorno += "\n";
            }
        }
        retorno += "\n";

        return retorno;
    }
}
