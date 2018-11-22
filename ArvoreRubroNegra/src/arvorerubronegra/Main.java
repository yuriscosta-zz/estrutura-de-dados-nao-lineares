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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Arvore arvoreRN = new Arvore();
        
        for (int i = 0; i < 20; i++) {
            arvoreRN.inserir(i);
        }
        
//        arvoreRN.inserir(3);
//        arvoreRN.inserir(5);
//        arvoreRN.inserir(6);
//        arvoreRN.inserir(2);
//        
//        arvoreRN.inserir(6);
//        
//        arvoreRN.remover(6);
        arvoreRN.imprimir(arvoreRN.raiz);
        arvoreRN.gerarHtml(arvoreRN.raiz, "rn.html");
    }
    
}
