/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvorerubronegra;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author yuri
 */
public class Arvore extends BST {

    public No raiz;
    public Map<Integer, List<No>> niveisArvore;
    public final int DUPLO_NEGRO = -1;
    public final int NEGRO = 0;
    public final int RUBRO = 1;

    public Arvore() {
        this.raiz = null;
    }

    public No simplesDireita(No no) {
        No noNovo = no.getEsquerdo();
        No pai = no.getPai();
        if (pai != null) {
            if (no.eEsquerdo()) {
                pai.setEsquerdo(noNovo);
            } else {
                pai.setDireito(noNovo);
            }
        } else {
            raiz = noNovo;
        }

        noNovo.setPai(pai);
        no.setEsquerdo(noNovo.getDireito());
        if (no.getEsquerdo() != null) {
            no.getEsquerdo().setPai(no);
        }

        noNovo.setDireito(no);
        noNovo.getDireito().setPai(noNovo);

        return noNovo;
    }

    public No simplesEsquerda(No no) {
        No noNovo = no.getDireito();
        No pai = no.getPai();
        if (pai != null) {
            if (no.eEsquerdo()) {
                pai.setEsquerdo(noNovo);
            } else {
                pai.setDireito(noNovo);
            }
        } else {
            raiz = noNovo;
        }

        noNovo.setPai(pai);
        no.setDireito(noNovo.getEsquerdo());
        if (no.getDireito() != null) {
            no.getDireito().setPai(no);
        }

        noNovo.setEsquerdo(no);
        noNovo.getEsquerdo().setPai(noNovo);

        return noNovo;
    }

    public No duplaDireita(No no) {
        no.setEsquerdo(simplesEsquerda(no.getEsquerdo()));
        return simplesDireita(no);
    }

    public No duplaEsquerda(No no) {
        no.setDireito(simplesDireita(no.getDireito()));
        return simplesEsquerda(no);
    }

    public void checarCor(No no) {
        while (no != null) {
            No pai = no.getPai();
            // se o pai não for raiz
            if (pai != null) {
                // se o pai é negro e o filho é rubro
                if (pai.getCor() == NEGRO && no.getCor() == RUBRO) {
                    break;
                    // pai é rubro e filho é rubro    
                } else if (pai.getCor() == RUBRO && no.getCor() == RUBRO) {
                    No avo = pai.getPai();
                    // se o pai é filho direito e o tio esquerdo é negro
                    if (pai.eDireito() && (avo.getEsquerdo() == null || (avo.getEsquerdo() != null && avo.getEsquerdo().getCor() == NEGRO))) {
                        // se o nó é direito
                        if (no.eDireito()) {
                            avo.setCor(RUBRO);
                            pai.setCor(NEGRO);
                            simplesEsquerda(avo);
                        } else {
                            avo.setCor(RUBRO);
                            no.setCor(NEGRO);
                            duplaEsquerda(avo);
                        }
                        // se o pai é filho esquerdo e o tio direito é negro
                    } else if (pai.eEsquerdo() && (avo.getDireito() == null || (avo.getDireito() != null && avo.getDireito().getCor() == NEGRO))) {
                        if (no.eEsquerdo()) {
                            avo.setCor(RUBRO);
                            pai.setCor(NEGRO);
                            simplesDireita(avo);
                        } else {
                            avo.setCor(RUBRO);
                            no.setCor(NEGRO);
                            duplaDireita(avo);
                        }
                        // se o pai e o tio forem rubros
                    } else if ((pai.eEsquerdo() && avo.getDireito().getCor() == RUBRO) || (pai.eDireito() && avo.getEsquerdo().getCor() == RUBRO)) {
                        avo.getEsquerdo().setCor(NEGRO);
                        avo.getDireito().setCor(NEGRO);
                        avo.setCor(RUBRO);
                    }

                    no = avo;
                }
            } else {
                // pinta raiz de negro
                no.setCor(NEGRO);
                break;
            }
        }
    }

    @Override
    public void inserir(int chave) {
        // Seta a raiz vermelha
        No noNovo = new No(chave, RUBRO);
        if (raiz == null) {
            raiz = noNovo;
            checarCor(noNovo);
            return;
        }

        No noAtual = raiz;
        No pai;
        while (true) {
            pai = noAtual;
            if (chave < noAtual.getChave()) {
                noAtual = noAtual.getEsquerdo();
                if (noAtual == null) {
                    pai.setEsquerdo(noNovo);
                    noNovo.setPai(pai);
                    checarCor(noNovo);
                    return;
                }
            } else {
                noAtual = noAtual.getDireito();
                if (noAtual == null) {
                    pai.setDireito(noNovo);
                    noNovo.setPai(pai);
                    checarCor(noNovo);
                    return;
                }
            }
        }
    }

    @Override
    public void remover(int chave) {
        No pai = raiz;
        No noAtual = raiz;
        No sucessor = null;
        boolean eFilhoEsquerdo = false;

        /* Procura o nó com a chave correspondente */
        while (noAtual.getChave() != chave) {
            pai = noAtual;
            if (noAtual.getChave() > chave) {
                eFilhoEsquerdo = true;
                noAtual = noAtual.getEsquerdo();
            } else {
                eFilhoEsquerdo = false;
                noAtual = noAtual.getDireito();
            }
            if (noAtual == null) {
                return;
            }
        }

        /* Se o nó removido for negro, seta o duplo negro */
        if (noAtual.getCor() == NEGRO) {
            noAtual.setCor(DUPLO_NEGRO);
        }

        /* Se o nó for externo */
        if (noAtual.eExterno()) {
            if (noAtual == raiz) {
                raiz = null;
            }
            if (eFilhoEsquerdo == true) {
                if (noAtual.getCor() == DUPLO_NEGRO) {
                    checarDuploNegro(noAtual);
                } else {
                    pai.setEsquerdo(null);
                }
            } else {
                if (noAtual.getCor() == DUPLO_NEGRO) {
                    checarDuploNegro(noAtual);
                } else {
                    pai.setDireito(null);
                }
            }
            /* Se o nó atual só tiver o filho esquerdo */
        } else if (noAtual.getDireito() == null) {
            if (noAtual == raiz) {
                raiz = noAtual.getEsquerdo();
                raiz.setPai(null);
                raiz.setCor(NEGRO);
            } else if (eFilhoEsquerdo) {
                pai.setEsquerdo(noAtual.getEsquerdo());
                sucessor = pai.getEsquerdo();
            } else {
                pai.setDireito(noAtual.getEsquerdo());
                sucessor = pai.getDireito();
            }
            /* Se o nó atual só tiver o filho direito*/
        } else if (noAtual.getEsquerdo() == null) {
            if (noAtual == raiz) {
                raiz = noAtual.getDireito();
                raiz.setPai(null);
                raiz.setCor(NEGRO);
            } else if (eFilhoEsquerdo) {
                pai.setEsquerdo(noAtual.getDireito());
                sucessor = pai.getEsquerdo();
            } else {
                pai.setDireito(noAtual.getDireito());
                sucessor = pai.getDireito();
            }
            /* Se tiver ambos os filhos */
        } else if (noAtual.temDireito() && noAtual.temEsquerdo()) {
            sucessor = getSucessor(noAtual);
            if (noAtual == raiz) {
                raiz = sucessor;
            } else if (eFilhoEsquerdo) {
                pai.setEsquerdo(sucessor);
            } else {
                pai.setDireito(sucessor);
            }
            sucessor.setEsquerdo(noAtual.getEsquerdo());
            if (sucessor.temEsquerdo()) {
                sucessor.getEsquerdo().setPai(sucessor);
            }
            if (sucessor.temDireito()) {
                sucessor.getDireito().setPai(sucessor);
            }
        }

        // Caso 2: Negro - Rubro
        if (noAtual.getCor() == DUPLO_NEGRO && sucessor != null && sucessor.getCor() == RUBRO) {
            sucessor.setCor(NEGRO);
            sucessor.setPai(noAtual.getPai());
        } else if (sucessor != null && sucessor.getCor() == NEGRO) {
            // Caso 4: Rubro - Negro
            if (noAtual.getCor() == RUBRO) {
                sucessor.setCor(RUBRO);
            }
            // Caso 3: Negro - Negro
            No duplaNegra = new No(0);
            if (sucessor.getPai().getEsquerdo() == null) {
                sucessor.getPai().setEsquerdo(duplaNegra);
            } else if (sucessor.getPai().getDireito() == null) {
                sucessor.getPai().setDireito(duplaNegra);
            } else {
                sucessor.getPai().getDireito().setCor(NEGRO);
                sucessor.getPai().getEsquerdo().setCor(NEGRO);
            }

            duplaNegra.setCor(DUPLO_NEGRO);
            duplaNegra.setPai(sucessor.getPai());
            sucessor.setEsquerdo(noAtual.getEsquerdo());

            if (sucessor.temEsquerdo()) {
                sucessor.getEsquerdo().setPai(sucessor);
            }

            sucessor.setDireito(noAtual.getDireito());

            if (sucessor.temDireito()) {
                sucessor.getDireito().setPai(sucessor);
            }

            sucessor.setPai(noAtual.getPai());
            checarDuploNegro(duplaNegra);
        }

    }

    @Override
    public No getSucessor(No no) {
        No sucessor = null;
        No noAtual = no.getDireito();

        while (noAtual != null) {
            sucessor = noAtual;
            noAtual = noAtual.getEsquerdo();
        }

        // Verifica se o sucessor tem filho direito
        if (sucessor != no.getDireito()) {
            sucessor.getPai().setEsquerdo(sucessor.getDireito());
            if (sucessor.getPai().temEsquerdo()) {
                sucessor.getPai().getEsquerdo().setPai(sucessor.getPai());
            }
        } else {
            if (sucessor.getPai() != null) {
                if (sucessor.temDireito()) {
                    sucessor.getPai().setDireito(sucessor.getDireito());
                    sucessor.getPai().getDireito().setCor(sucessor.getCor());
                    sucessor.getPai().getDireito().setPai(sucessor.getPai());
                } else {
                    sucessor.getPai().setDireito(null);
                }
            }
        }

        return sucessor;
    }

    public void checarDuploNegro(No no) {
        No pai = no.getPai();
        boolean eFilhoEsquerdo = no.eEsquerdo();
        if (eFilhoEsquerdo) {
            // Caso 3.1
            if ((pai.temDireito() && pai.temEsquerdo()) && pai.getCor() == NEGRO && pai.getDireito().getCor() == RUBRO) {
                pai.getDireito().setCor(NEGRO);
                pai.setCor(RUBRO);
                simplesEsquerda(pai);
                checarDuploNegro(no);
                // Caso 3.2A
            } else if ((pai.temDireito() && pai.temEsquerdo())
                    && pai.getCor() == NEGRO
                    && (pai.getDireito().temDireito() && pai.getDireito().temEsquerdo())
                    && pai.getDireito().getEsquerdo().getCor() == NEGRO
                    && pai.getDireito().getDireito().getCor() == NEGRO) {
                no.setCor(NEGRO);
                pai.getDireito().setCor(RUBRO);
                pai.setCor(DUPLO_NEGRO);
                checarDuploNegro(pai);
                // Caso 3.2B
            } else if ((pai.temDireito() && pai.temEsquerdo())
                    && pai.getCor() == RUBRO
                    && pai.getDireito().getCor() == NEGRO
                    && ((pai.getDireito().temDireito() && pai.getDireito().temEsquerdo())
                    && pai.getDireito().getEsquerdo().getCor() == NEGRO
                    && pai.getDireito().getDireito().getCor() == NEGRO) || pai.getDireito().eExterno()) {
                pai.setCor(NEGRO);
                pai.getDireito().setCor(RUBRO);
                pai.setEsquerdo(null);
                // Caso 3.3
            } else if (pai.temDireito() && pai.temEsquerdo()
                    && pai.getDireito().getCor() == NEGRO
                    && ((pai.getDireito().temDireito() && pai.getDireito().temEsquerdo()
                    && pai.getDireito().getEsquerdo().getCor() == RUBRO
                    && pai.getDireito().getDireito().getCor() == NEGRO))
                    || pai.getDireito().temEsquerdo()
                    && pai.getDireito().getEsquerdo().getCor() == RUBRO) {
                pai.getDireito().getEsquerdo().setCor(NEGRO);
                pai.getDireito().setCor(RUBRO);
                pai.setEsquerdo(null);
                simplesDireita(pai.getEsquerdo());
                // Caso 3.4
            } else if (pai.temDireito() && pai.temDireito()
                    && pai.getDireito().getCor() == NEGRO
                    && pai.getDireito().getDireito() != null
                    && pai.getDireito().getDireito().getCor() == RUBRO) {
                pai.getDireito().setCor(pai.getCor());
                pai.getDireito().getDireito().setCor(NEGRO);
                pai.setCor(NEGRO);
                pai.setEsquerdo(null);
                simplesEsquerda(pai);
            }
        } else {
            // Caso 3.1
            if (pai.temDireito() && pai.temEsquerdo()
                    && pai.getEsquerdo().getCor() == RUBRO) {
                pai.getEsquerdo().setCor(NEGRO);
                pai.setCor(RUBRO);
                simplesDireita(pai);
                checarDuploNegro(no);
                // Caso 3.2A
            } else if (pai.temDireito() && pai.temEsquerdo()
                    && pai.getEsquerdo().getCor() == NEGRO
                    && ((pai.getEsquerdo().temDireito() && pai.getEsquerdo().temEsquerdo())
                    && pai.getEsquerdo().getDireito().getCor() == NEGRO
                    && pai.getEsquerdo().getEsquerdo().getCor() == NEGRO)) {
                no.setCor(NEGRO);
                pai.getEsquerdo().setCor(RUBRO);
                pai.setCor(DUPLO_NEGRO);
                checarDuploNegro(pai);
                // Caso 3.2B
            } else if (pai.temDireito() && pai.temEsquerdo()
                    && pai.getCor() == RUBRO
                    && pai.getEsquerdo().getCor() == NEGRO
                    && ((pai.getEsquerdo().temDireito() && pai.getEsquerdo().temEsquerdo())
                    && pai.getEsquerdo().getEsquerdo().getCor() == NEGRO
                    && pai.getEsquerdo().getDireito().getCor() == NEGRO)
                    || pai.getEsquerdo().eExterno()) {
                pai.setCor(NEGRO);
                pai.getEsquerdo().setCor(RUBRO);
                pai.setDireito(null);
                // Caso 3.3
            } else if (pai.temDireito() && pai.temEsquerdo()
                    && pai.getEsquerdo().getCor() == NEGRO
                    && ((pai.getEsquerdo().temDireito() && pai.getEsquerdo().temEsquerdo()
                    && pai.getEsquerdo().getDireito().getCor() == RUBRO
                    && pai.getEsquerdo().getEsquerdo().getCor() == NEGRO))
                    || pai.getEsquerdo().temDireito()
                    && pai.getEsquerdo().getDireito().getCor() == RUBRO) {
                pai.getEsquerdo().getDireito().setCor(NEGRO);
                pai.getEsquerdo().setCor(RUBRO);
                pai.setDireito(null);
                simplesEsquerda(pai.getDireito());
                // Caso 3.4
            } else if (pai.temDireito() && pai.temDireito()
                    && pai.getEsquerdo().getCor() == NEGRO
                    && pai.getEsquerdo().getEsquerdo() != null
                    && pai.getEsquerdo().getEsquerdo().getCor() == RUBRO) {
                pai.getEsquerdo().setCor(pai.getCor());
                pai.getEsquerdo().getEsquerdo().setCor(NEGRO);
                pai.setCor(NEGRO);
                pai.setDireito(pai.getDireito().getDireito());
                simplesDireita(pai);
            }
        }
    }

    @Override
    public void imprimir(No raiz) {
        if (raiz != null) {
            imprimir(raiz.getEsquerdo());
            System.out.print(" " + raiz.getChave() + "(" + (raiz.getCor() == 0 ? "p" : "v") + ")");
            imprimir(raiz.getDireito());
        }
    }

    public void gerarHtml(No raiz, String fileName) {
        niveisArvore = new HashMap<>();
        construirNiveisArvore(raiz, 0);
        int i = 0;
        while (niveisArvore.get(i) != null) {
            if (i == 0 || i + 1 == niveisArvore.size()) {
                i++;
                continue;
            }
            int maxNoSize = new Double(Math.pow(2, i)).intValue();
            int j;
            for (j = 0; j < niveisArvore.get(i).size(); j++) {
                No node = (No) niveisArvore.get(i).get(j);
                if (node.getEsquerdo() == null) {
                    niveisArvore.get(i + 1).add(2 * j, new No(0));
                }
                if (node.getDireito() == null) {
                    niveisArvore.get(i + 1).add((2 * j) + 1, new No(0));
                }
            }
            while (j < maxNoSize) {
                niveisArvore.get(i + 1).add(new No(0));
                j++;
            }
            i++;
        }
        String htmlOutput = "<!doctype html> <html> <head> <script type='text/javascript' src='jquery-1.7.2.min.js'></script><script type='text/javascript' src='jqSimpleConnect.js'></script> <meta charset='utf-8'> <title>Visualização da árvore Rubro-Negra</title> <meta name='description' content='Visualização da árvore Rubro-Negra'> <meta name='author' content='20151014040004'> <style> .row { width: 100%; text-align: center; } .node { width: 30px; border-radius: 6px; border: 1px solid black; display: inline-table; margin-top: 10px; margin-bottom: 10px; } .redNo { background-color: red;  color: black; } .blackNo { background-color: black; color: white; } </style> </head> <body>";
        htmlOutput += "<div class='row'><div class='node blackNo' id='_" + raiz.getChave() + "'>" + raiz.getChave() + "</div></div>";
        i = 1;
        int startMagin = 80;
        while (niveisArvore.get(i) != null) {
            htmlOutput += "<div class='row'>";
            int margin = new Double((startMagin / i) * 1.2).intValue();
            for (No node : niveisArvore.get(i)) {
                if (node.getPai() != null) {
                    htmlOutput += String.format("<div id='%s' style='margin-left:%dpx; margin-right: %dpx;' class='node %s'>%d</div>", String.format("%d_%d", node.getPai().getChave(), node.getChave()), margin, margin, node.getCor() == 1 ? "redNo" : "blackNo", node.getChave());
                } else {
                    htmlOutput += String.format("<div style='margin-left:%dpx; margin-right: %dpx;' class='node'>-</div>", (startMagin / i), (startMagin / i));
                }
            }
            htmlOutput += "</div>";
            i++;
        }
        niveisArvore = null;
        htmlOutput += "<script>$(\".node[id]\").each(function(q,b){ var children = $(\".node[id^=\" + b.id.split(\"_\")[1] + \"_]\"); var i = 0; for (;children[i];) { jqSimpleConnect.connect(\"#\" + b.id, \"#\" + children[i].id, {radius: 2, color: 'black', anchorA: \"vertical\", anchorB: \"vertical\"}); i++; } b.style.zIndex = \"999\"; b.style.position = \"relative\"; }); window.onresize = function () { jqSimpleConnect.repaintAll(); };</script>";
        htmlOutput += "</body></html>";
        salvarArquivo(htmlOutput, fileName);
    }

    protected void construirNiveisArvore(No raiz, int level) {
        if (raiz != null) {
            construirNiveisArvore(raiz.getEsquerdo(), level + 1);
            if (niveisArvore.get(level) == null) {
                niveisArvore.put(level, new ArrayList<>());
            }
            niveisArvore.get(level).add(raiz);
            construirNiveisArvore(raiz.getDireito(), level + 1);
        }
    }

    protected void salvarArquivo(String fileContents, String fileName) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"))) {
            writer.write(fileContents);
            writer.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
