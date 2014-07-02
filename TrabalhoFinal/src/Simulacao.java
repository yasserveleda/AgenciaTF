
import java.util.Random;



public class Simulacao
{
    private static final int duracao = 200; //arquivo
    private static final double probabilidadeChegada = 0.5; //arquivo
    private static final int totalCaixas = 5; //arquivo
    private QueueTAD<Cliente> fila;
    private Caixa[] caixas, caixasPilha;
    private GeradorClientes geradorClientes;
    private Acumulador statTemposEsperaFila;
    private Acumulador statComprimentosFila;
    private Random random = new Random();
    private boolean trace; // valor indica se a simulacao ira imprimir
    private StackArray<Cliente> pilha;
    private String auxString; 
    
    public Simulacao(boolean t){
        
        pilha = new StackArray<Cliente>();
        fila = new QueueLinked<Cliente>();
        caixas = new Caixa[totalCaixas];
        caixasPilha = new Caixa[totalCaixas];
        geradorClientes = new GeradorClientes(probabilidadeChegada);
        statTemposEsperaFila = new Acumulador();
        statComprimentosFila = new Acumulador();
        trace = t;

        for (int c = 0; c < totalCaixas; c++) {
            caixas[c] = new Caixa();
        }
        for (int c2 = 0; c2 < totalCaixas; c2++) {
            caixasPilha[c2] = new Caixa();
        }
        
    }
    
    public void simular(){
        
       
        for (int tempo = 0; tempo < duracao; tempo++) {
            
            if (geradorClientes.gerar()) {
               
                Cliente c = new Cliente(geradorClientes.getQuantidadeGerada(),
                        tempo);
                fila.add(c);
                if (trace) {
                    System.out.println(tempo + ": cliente " + c.getNumero()
                            + " (" + c.getTempoAtendimento()
                            + " min) entra na fila - " + fila.size()
                            + " pessoa(s)");
                }
            }
            
            for (int c = random.nextInt(totalCaixas), i = 0; i < totalCaixas; i++) {
                if (caixas[c].estaVazio()) {
                    
                    if (!fila.isEmpty()) {
                        
                        caixas[c].atenderNovoCliente(fila.remove());
                        statTemposEsperaFila.adicionar(tempo
                                - caixas[c].getClienteAtual().getInstanteChegada());
                        if (trace) {
                            System.out.println(tempo + ": cliente "
                                    + caixas[c].getClienteAtual().getNumero()
                                    + " chega ao caixa " + (c + 1));

                            System.out.println(tempo + ": cliente "
                                    + caixas[c].getClienteAtual().getNumero()
                                    + " (" + caixas[c].getClienteAtual().getTempoAtendimento()
                                    + " min) entra na pilha - " + pilha.size()
                                    + " pessoa(s)");
                        }
                    }
                } else {
                    
                    if (caixas[c].getClienteAtual().getTempoAtendimento() == 0) {
                        if (trace) {
                            System.out.println(tempo + ": cliente "
                                    + caixas[c].getClienteAtual().getNumero()
                                    + " deixa o caixa " + (c + 1));
                        }
                        pilha.push(caixas[c].dispensarClienteAtual());
                    } else {
                        caixas[c].getClienteAtual().decrementarTempoAtendimento();
                    }
                }

                
                for (int c2 = random.nextInt(totalCaixas), j = 0; j < totalCaixas; j++) {
                    if (caixasPilha[c2].estaVazio()) {

                       
                        if (!pilha.isEmpty()) {
                          
                            caixasPilha[c2].atenderNovoCliente(pilha.pop());
                            statTemposEsperaFila.adicionar(tempo - caixasPilha[c2].getClienteAtual().getInstanteChegada());
                            if (trace) {
                                System.out.println(tempo + ": cliente " + caixasPilha[c2].getClienteAtual().getNumero() + " chega ao caixa.");
                            }
                        }
                    } else {
                        
                        if (caixasPilha[c2].getClienteAtual().getTempoAtendimento() == 0) {
                            if (trace) {
                                System.out.println(tempo + ": cliente " + caixasPilha[c2].getClienteAtual().getNumero() + " deixa o caixa.");
                            }
                            caixasPilha[c2].dispensarClienteAtual();
                        } else {
                            caixasPilha[c2].getClienteAtual().decrementarTempoAtendimento();
                        }
                    }
                    statComprimentosFila.adicionar(pilha.size());
                }

            }
            statComprimentosFila.adicionar(fila.size());
        }
        
    }
    
    public void limpar(){
        fila = new QueueLinked<Cliente>();
        pilha = new StackArray<Cliente>();
        caixas = new Caixa[totalCaixas];
        caixasPilha = new Caixa[totalCaixas];
        geradorClientes = new GeradorClientes(probabilidadeChegada);
        statTemposEsperaFila = new Acumulador();
        statComprimentosFila = new Acumulador();

        for (int c = 0; c < totalCaixas; c++) {
            caixas[c] = new Caixa();
        }
        for (int c2 = 0; c2 < totalCaixas; c2++) {
            caixasPilha[c2] = new Caixa();
        }
    }
    
    public void imprimirResultados(){
        System.out.println();
        System.out.println("Resultados da Simulacao FILA");
        System.out.println("Duracao:" + duracao);
        System.out.println("Probabilidade de chegada de clientes: " + probabilidadeChegada);
        System.out.println("Tempo de atendimento minimo: " + Cliente.tempoMinAtendimento);
        System.out.println("Tempo de atendimento maximo: " + Cliente.tempoMaxAtendimento);

        for (int c = 0; c < totalCaixas; c++) {
            System.out.println("Cliente atendidos pelo caixa da fila " + (c + 1) + ": " + caixas[c].getNumeroAtendidos());
        }
        System.out.println("Clientes ainda na fila:" + fila.size());

        for (int c = 0; c < totalCaixas; c++) {
            System.out.println("Cliente ainda no caixa da fila " + (c + 1) + ": " + (caixas[c].getClienteAtual() != null));
        }
        System.out.println("Total de clientes gerados: " + geradorClientes.getQuantidadeGerada());
        System.out.println("Tempo medio de espera: " + statTemposEsperaFila.getMedia());
        System.out.println("Comprimento medio da fila: " + statComprimentosFila.getMedia());
        System.out.println();
        imprimiDadosPilha();
    }
    
    public void imprimiDadosPilha() {
        System.out.println();
        System.out.println("Resultados da Simulacao PILHA");
        System.out.println("Duracao:" + duracao);

        for (int c = 0; c < totalCaixas; c++) {
            System.out.println("Cliente atendidos pelo caixa da pilha " + (c + 1) + ": " + caixasPilha[c].getNumeroAtendidos());
        }

        for (int c = 0; c < totalCaixas; c++) {
            System.out.println("Cliente ainda no caixa da pilha" + (c + 1) + ": " + (caixasPilha[c].getClienteAtual() != null));
        }

        System.out.println("Clientes ainda na pilha:" + pilha.size());
        System.out.println("Total de clientes pilha:" + geradorClientes.getQuantidadeGerada());//melhoria
        System.out.println("Tempo medio de espera:" + statTemposEsperaFila.getMedia());

    }
    
    public String salva() {
        auxString = "Resultados da Simulacao FILA" + "\n";
        auxString = auxString + "\n" + "Duracao: " + duracao;
        auxString = auxString + "\n" + "Probabilidade de chegada de clientes: " + probabilidadeChegada;
        auxString = auxString + "\n" + "Tempo de atendimento minimo: " + Cliente.tempoMinAtendimento;
        auxString = auxString + "\n" + "Tempo de atendimento maximo: " + Cliente.tempoMaxAtendimento;

        auxString = auxString + "\n" + "Clientes ainda na fila: " + fila.size();

        auxString = auxString + "\n" + "Tempo medio de espera: " + statTemposEsperaFila.getMedia();
        auxString = auxString + "\n" + "Comprimento medio da fila: " + statComprimentosFila.getMedia();
        auxString = auxString + "\n" + "Quantidade da pilha: " + pilha.size() + "\n";
        auxString = auxString + "\n" + "Resultados da Simulacao PILHA\n";
        auxString = auxString + "\n" + "Duracao:" + duracao;

        auxString = auxString + "\n" + "Clientes ainda na pilha:" + pilha.size();

        auxString = auxString + "\n" + "Total de clientes gerados:" + geradorClientes.getQuantidadeGerada();
        auxString = auxString + "\n" + "Tempo medio de espera:" + statTemposEsperaFila.getMedia();

        return auxString;

    }



}
