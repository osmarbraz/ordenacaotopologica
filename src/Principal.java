/*
 * Universidade Federal de Santa Catarina - UFSC
 * Departamento de Informática e Estatística - INE
 * Programa de Pós-Graduação em Ciências da Computação - PROPG
 * Disciplinas: Projeto e Análise de Algoritmos
 * Prof Alexandre Gonçalves da Silva 
 *
 * Baseado nos slides 8 da aula do dia 20/10/2017 
 *
 * Página 446 Thomas H. Cormen 3a Ed
 *
 * Ordenação Topológica/Topological Sort
 */

/**
 * @author Osmar de Oliveira Braz Junior
 */
import java.util.LinkedList;

public class Principal {

    //A medida que o grafo é percorrido, os vértices visitados vão
    //sendo coloridos. Cada vértice tem uma das seguintes cores:
    final static int BRANCO = 0;//Vértce ainda não visitado
    final static int CINZA = 1; //Vértice visitado mas não finalizado
    final static int PRETO = 2; //Vértice visitado e finalizado

    //Vetor da situação vértice, armazena uma das cores
    static int cor[];
    //d[x] armazena o instante de descoberta de x.
    static int d[];
    //f[x] armazena o instante de finalização de x.
    static int f[];
    //Vertor dos pais de um vértice
    static int pi[];
    static int tempo;
        
    /**
     * Troca um número que representa a posição pela vértice do grafo.
     *
     * @param i Posição da vestimenta
     * @return Uma String com a vestimenta da posição
     */
    public static String trocar(int i) {        
        String[] vestimentas = {"camisa","gravata","paletó","cinto","relógio","cuecas","calças","sapatos","meias"};
        return vestimentas[i];
    }

    /**
     * Troca a vestimenta pela posição na matriz de adjacência
     *
     * @param v Palavra da vestimenta a ser troca pela posição
     * @return Um inteiro com a posição da vestimenta no grafo
     */
    public static int destrocar(String v) {        
        String[] vestimentas = {"camisa","gravata","paletó","cinto","relógio","cuecas","calças","sapatos","meias"};
        int pos = -1;
        for (int i = 0; i < vestimentas.length; i++) {
            if (vestimentas[i].equalsIgnoreCase(v)) {
                pos = i;
            }
        }
        return pos;
    }
    
    /**
     * Constrói recursivamente uma Árvore de Busca em profundidade. com raiz u.
     *
     * Consumo de tempo Adj[u] vezes
     *
     * Método DFS-Visit(G,u)
     * 
     * @param G Matriz de incidência do grafo
     * @param u Vértice raiz da árvore de busca
     * @param lista Lista com a ordenação topológica de G
     */
    public static void buscaEmProfundidadeVisita(int[][] G, int u, LinkedList lista) {
        //Quantidade vértices do grafo
        int n = G.length;

        cor[u] = CINZA;
        tempo = tempo + 1; //Vértice branco u acabou de ser descoberto
        d[u] = tempo;
        // Exporar as arestas (u,v)
        for (int v = 0; v < n; v++) {
            //Somente com os adjancentes ao vértice u
            if (G[u][v] != 0) {
                //Somente vértices nao visitados
                if (cor[v] == BRANCO) {
                    pi[v] = u;
                    buscaEmProfundidadeVisita(G, v, lista);
                }
            }
        }
        //Vértice u foi visitado e finalizado
        cor[u] = PRETO;
        tempo = tempo + 1;
        f[u] = tempo;     
        //Adiciona no início da lista da ordenação topologica depois do vértices estar visitado
        lista.addFirst(u);
    }

    /**
     * Busca em Profundidade (Breadth-first Search) recursivo. 
     * 
     * Recebe um grafo G e devolve 
     * (i) os instantes d[v] e f[v] para cada v 
     * (ii) uma Floresta de Busca em Profundiade
     *
     * Consumo de tempo é O(V)+V chamadas 
     * Complexidade de tempo é O(V+E)
     *
     * Método DFS(G)
     * @param G Grafo na forma de uma matriz de adjacência
     * @param lista Lista com a ordenação topológica de G
     */
    public static void buscaEmProfundidadeRecursivo(int[][] G, LinkedList lista) {
        //Quantidade vértices do grafo
        int n = G.length;

        //Inicializa os vetores
        cor = new int[n];
        d = new int[n];
        f = new int[n];
        pi = new int[n];

        //Inicialização dos vetores
        //Consome Theta(V)
        for (int u = 0; u < n; u++) {
            //Vértice i não foi visitado
            cor[u] = BRANCO;
            d[u] = -1;
            pi[u] = -1;
        }
        tempo = 0;

        //Percorre todos os vértices do grafo
        for (int u = 0; u < n; u++) {
            //Somente vértices nao visitados
            if (cor[u] == BRANCO) {
                buscaEmProfundidadeVisita(G, u, lista);
            }
        }
    }
    
    /**
     * Realiza a ordenação topológica do grafo.
     * 
     * Tempo Theta(V+E)
     * 
     * @param G Grafo na forma de uma matriz de adjacência a ser ordenado
     * @return Lista com a ordenação topológica de G
     */    
    public static LinkedList ordenacaoTopologica(int[][] G) {
        LinkedList lista = new LinkedList();
        //Vertice completamente visitado é adicionado ao inicio da lista no momento que é atribuido PRETO
        buscaEmProfundidadeRecursivo(G, lista);
        return lista;
    }

    public static void main(String args[]) {

        //Matriz de incidência para um grafo direcionado     
        //Grafo do slide 9
        //Página 446 Thomas H. Cormen 3ed
       
                int G[][] = 
               //c  g  p  c  r  c  c  s  m
               {{0, 1, 0, 1, 0, 0, 0, 0, 0},//camisa
                {0, 0, 1, 0, 0, 0, 0, 0, 0},//gravata
                {0, 0, 0, 0, 0, 0, 0, 0, 0},//paletó
                {0, 0, 1, 0, 0, 0, 0, 0, 0},//cinto
                {0, 0, 0, 0, 0, 0, 0, 0, 0},//relógio
                {0, 0, 0, 0, 0, 0, 1, 1, 0},//cueca
                {0, 0, 0, 1, 0, 0, 0, 0, 0},//calças
                {0, 0, 0, 0, 0, 0, 0, 0, 0},//sapatos
                {0, 0, 0, 0, 0, 0, 0, 1, 0}};//meias
    
        System.out.println(">>> Ordenação Topológica/Topological Sort <<<");

        //Lista do retorno da ordenação topologica
        LinkedList lista = ordenacaoTopologica(G);

        System.out.println();
        System.out.println("Ordem de Vestir d[x]/f[x]");
        for(int i=0;i<lista.size();i++){
            int u = (int)lista.get(i);
            System.out.println((i+1)+"="+trocar(u)+ "=" + d[i] + "/" + f[i]);              
        }

        System.out.println();
        System.out.println("Ordem de execução inversa de d[x]/f[x]");
        for (int i = G.length-1; i >= 0; i--) {
            System.out.println((i+1)+"="+trocar(i) + "=" + d[i] + "/" + f[i]);
        }
    }
}