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
public class BST {
    
    public static No raiz;
    
    public BST() {
        BST.raiz = null;
    }
    
    public boolean buscar(int chave) {
        No noAtual = raiz;
        while (noAtual != null) {
            if (noAtual.getChave() == chave) {
                return true;
            } else if (noAtual.getChave() > chave) {
                noAtual = noAtual.getEsquerdo();
            } else {
                noAtual = noAtual.getDireito();
            }
        }
        
        return false;
    }
    
    public void inserir(int chave) {
        No novoNo = new No(chave);
        if (raiz == null) {
            raiz = novoNo;
            return;
        }
        
        No noAtual = raiz;
        No pai;
        while (true) {
            pai = noAtual;
            if (chave < noAtual.getChave()) {
                noAtual = noAtual.getEsquerdo();
                if (noAtual == null) {
                    pai.setEsquerdo(novoNo);
                    return;
                }
            } else {
                noAtual = noAtual.getDireito();
                if (noAtual == null) {
                    pai.setDireito(novoNo);
                    return;
                }
            }
        }
    }
    
    public void remover(int chave) {
        No pai = raiz;
        No noAtual = raiz;
        boolean eEsquerdo = false;
        
        while (noAtual.getChave() != chave) {
            pai = noAtual;
            if (noAtual.getChave() > chave) {
                eEsquerdo = true;
                noAtual = noAtual.getEsquerdo();
            } else {
                eEsquerdo = false;
                noAtual = noAtual.getDireito();
            }
            if (noAtual == null) {
                return;
            }
        }
        
        if (noAtual.eExterno()) {
            if (noAtual == raiz) {
                raiz = null;
            }
            if (eEsquerdo == true) {
                pai.setEsquerdo(null);
            } else {
                pai.setDireito(null);
            }
        } else if (noAtual.getDireito() == null) {
            if (noAtual == raiz) {
                raiz = noAtual.getEsquerdo();
            } else if (eEsquerdo) {
                pai.setEsquerdo(noAtual.getEsquerdo());
            } else {
                pai.setDireito(noAtual.getEsquerdo());
            }
        } else if (noAtual.getEsquerdo() == null) {
            if (noAtual == raiz) {
                raiz = noAtual.getDireito();
            } else if (eEsquerdo) {
                pai.setEsquerdo(noAtual.getDireito());
            } else {
                pai.setDireito(noAtual.getDireito());
            }
        } else if (noAtual.temDireito() && noAtual.temEsquerdo()) {
            No sucessor = getSucessor(noAtual);
            if (noAtual == raiz) {
                raiz = sucessor;
            } else if (eEsquerdo) {
                pai.setEsquerdo(sucessor);
                sucessor.setPai(pai);
            } else {
                pai.setDireito(sucessor);
                sucessor.setPai(pai);
            }
            
            sucessor.setEsquerdo(noAtual.getEsquerdo());
        }
    }
    
    public No getSucessor(No no) {
        No sucessor = null;
        No sucessorPai = null;
        No noAtual = no.getDireito();
        
        while (noAtual != null) {
            sucessorPai = sucessor;
            sucessor = noAtual;
            noAtual = noAtual.getEsquerdo();
        }
        
        if (sucessor != no.getDireito()) {
            sucessorPai.setEsquerdo(sucessor.getDireito());
            if (sucessorPai.getEsquerdo() != null) {
                sucessorPai.getEsquerdo().setPai(sucessorPai);
            }
            
            sucessor.setDireito(no.getDireito());
            if (sucessor.getDireito() != null) {
                sucessor.getDireito().setPai(sucessor);
            }            
        } else {
            sucessor.getPai().setDireito(null);
        }
        
        return sucessor;
    }
    
    public void imprimir(No raiz) {
        if (raiz != null) {
            imprimir(raiz.getEsquerdo());
            System.out.println(raiz.getChave());
            imprimir(raiz.getDireito());
        }
    }
}
