

import java.util.Random;
import java.util.Random;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class Simulacao
{
    private static double duracao; //arquivo
    private static double probabilidadeChegada; //arquivo
    private static final int totalCaixas = 3;
    private static final int totalCaixasPilha = 3; 
    private QueueTAD<Cliente> fila;
    private Caixa[] caixas, caixasPilha;
    private GeradorClientes geradorClientes;
    private Acumulador statTemposEsperaFila, statTemposEsperaPilha;
    private Acumulador statComprimentosFila, statComprimentosPilha;
    private Random random = new Random();
    private boolean trace; // valor indica se a simulacao ira imprimir
    private StackArray<Cliente> pilha;
    private static String auxString, auxString2, aux, texto = "arquivo.txt"; 
    
    public Simulacao(boolean t){
        
        pilha = new StackArray<Cliente>();
        fila = new QueueLinked<Cliente>();
        caixas = new Caixa[totalCaixas];
        caixasPilha = new Caixa[totalCaixasPilha];
        geradorClientes = new GeradorClientes(probabilidadeChegada);
        statTemposEsperaFila = new Acumulador();
        statComprimentosFila = new Acumulador();
        statTemposEsperaPilha = new Acumulador();
        statComprimentosPilha = new Acumulador();
        trace = t;
        auxString2 = "";

        for (int c = 0; c < totalCaixas; c++) {
            caixas[c] = new Caixa();
            
        }
        
        for (int c = 0; c < totalCaixasPilha; c++) {
       
            caixasPilha[c] = new Caixa();
        }
 
    }
    
    public void leitura() {
        // TODO code application logic here
        Path path = Paths.get("C:\\"+texto);
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.defaultCharset()))) {
            String linha = null;
            sc.useDelimiter("[;,:\\n]"); // separadores: ; e nova linha
            while (sc.hasNext()) {
                aux = sc.next();
                duracao = Double.parseDouble(aux);
                aux = sc.next();
                probabilidadeChegada = Double.parseDouble(aux);

            }
        } catch (IOException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }
    }

    public void alteraArquivo(String aux) {
        this.texto = aux;
    }
    
    
    public String simular() {
        // realizar a simulacao por um certo numero de passos de duracao
        for (int tempo = 0; tempo < duracao; tempo++) {
            // verificar se um cliente chegou
            if (geradorClientes.gerar()) {
                // se cliente chegou, criar um cliente e inserir na fila do
                // caixa
                Cliente c = new Cliente(geradorClientes.getQuantidadeGerada(),tempo);
                fila.add(c);
                if (trace) {
                    auxString2 = auxString2 + tempo + ": cliente " + c.getNumero()+ " (" + c.getTempoAtendimento()+ " min) entra na fila - " + fila.size()+ " pessoa(s)" + "\n";
                }
            }
            // verificar se os caixas estÃ£o vazios
            for (int c = random.nextInt(totalCaixas), i = 0; i < totalCaixas; i++) {
                if (caixas[c].estaVazio()) {
                    // se o caixa esta vazio, atender o primeiro cliente da fila
                    // se ele existir
                    if (!fila.isEmpty()) {
                        // tirar o cliente do inicio da fila e atender no caixa
                        caixas[c].atenderNovoCliente(fila.remove());
                        statTemposEsperaFila.adicionar(tempo - caixas[c].getClienteAtual().getInstanteChegada());
                        if (trace) {
                            auxString2 = auxString2 + "\n" + tempo + ": cliente " + caixas[c].getClienteAtual().getNumero()+ " chega ao caixa " + (c + 1);
                            auxString2 = auxString2 + "\n" + tempo + ": cliente "+ caixas[c].getClienteAtual().getNumero()+ " (" + caixas[c].getClienteAtual().getTempoAtendimento()+ " min) entra na pilha - " + pilha.size() + " pessoa(s)";
                        }
                    }
                } else {
                    // se o caixa ja esta ocupado, diminuir de um em um o tempo
                    // de atendimento ate chegar a zero
                    if (caixas[c].getClienteAtual().getTempoAtendimento() == 0) {
                        if (trace) {
                            auxString2 = auxString2 + "\n" + tempo + ": cliente "
                                    + caixas[c].getClienteAtual().getNumero()
                                    + " deixa o caixa " + (c + 1);
                        }
                        pilha.push(caixas[c].dispensarClienteAtual());
                    } else {
                        caixas[c].getClienteAtual().decrementarTempoAtendimento();
                    }
                }

                //verificar se o caixa esta vazio
                for (int c2 = random.nextInt(totalCaixasPilha), j = 0; j < totalCaixasPilha; j++) {
                    if (caixasPilha[c2].estaVazio()) {

                        //se o caixa esta vazio, atender o primeiro cliente da fila se ele existir
                        if (!pilha.isEmpty()) {
                            //tirar o cliente do inicio da pilha e atender no caixa
                            caixasPilha[c2].atenderNovoCliente(pilha.pop());
                            statTemposEsperaPilha.adicionar(tempo - caixasPilha[c2].getClienteAtual().getInstanteChegada());
                            if (trace) {
                                auxString2 = auxString2 + "\n" + tempo + ": cliente " + caixasPilha[c2].getClienteAtual().getNumero() + " chega ao caixa.";
                            }
                        }
                    } else {
                        //se o caixa ja esta ocupado, diminuir de um em um o tempo de atendimento ate chegar a zero
                        if (caixasPilha[c2].getClienteAtual().getTempoAtendimento() == 0) {
                            if (trace) {
                                auxString2 = auxString2 + "\n" + tempo + ": cliente " + caixasPilha[c2].getClienteAtual().getNumero() + " deixa o caixa.";
                            }
                            caixasPilha[c2].dispensarClienteAtual();
                        } else {
                            caixasPilha[c2].getClienteAtual().decrementarTempoAtendimento();
                        }
                    }
                    statComprimentosPilha.adicionar(pilha.size());
                }

            }
            statComprimentosFila.adicionar(fila.size());
        }

        return auxString2;
    }
    
    public void limpar(){
        fila = new QueueLinked<Cliente>();
        pilha = new StackArray<Cliente>();
        caixas = new Caixa[totalCaixas];
        caixasPilha = new Caixa[totalCaixasPilha];
        geradorClientes = new GeradorClientes(probabilidadeChegada);
        statTemposEsperaFila = new Acumulador();
        statComprimentosFila = new Acumulador();

        for (int c = 0; c < totalCaixas; c++) {
            caixas[c] = new Caixa();
        }
        for (int c2 = 0; c2 < totalCaixasPilha; c2++) {
            caixasPilha[c2] = new Caixa();
        }
    }
    
    
    
    public String salva() {

        auxString = "\n" + "RESULTADOS DA SILMULAÇÃO"+"\n";
        auxString = auxString + "\n" + "Duracao: " + duracao;
        auxString = auxString + "\n" + "Probabilidade de chegada de clientes: " + probabilidadeChegada;
        auxString = auxString + "\n" + "Tempo de atendimento minimo: " + Cliente.tempoMinAtendimento;
        auxString = auxString + "\n" + "Tempo de atendimento maximo: " + Cliente.tempoMaxAtendimento;
        auxString = auxString + "\n" + "Total de clientes gerados:" + geradorClientes.getQuantidadeGerada()+"\n"+"\n";
        for (int c = 0; c < totalCaixas; c++) {
            double caixaGetNumeroAtendidos = caixas[c].getNumeroAtendidos();
            auxString = auxString + "\n" + "Cliente atendidos pelo caixa " + (c + 1) + " da fila: " + caixas[c].getNumeroAtendidos()+"("+((caixaGetNumeroAtendidos/geradorClientes.getQuantidadeGerada()*100))+"%)"
                                  +"\nTempo médio de espera: "+(caixas[c].getNumeroAtendidos()/statTemposEsperaFila.getMedia())
                                  +"\n";
        }
        auxString = auxString + "\n" + "Clientes ainda na fila: " + fila.size();
        for (int c = 0; c < totalCaixas; c++) {
            auxString = auxString + "\n" + "Cliente ainda no caixa " + (c + 1) + " da fila: " + (caixas[c].getClienteAtual() != null);
        }
        
        auxString = auxString + "\n" + "Tempo medio de espera da fila: " + statTemposEsperaFila.getMedia();
        auxString = auxString + "\n" + "Comprimento medio da fila: " + statComprimentosFila.getMedia();
        for 
            (int c = 0; c < totalCaixasPilha; c++) {
            double caixaGetNumeroAtendidos = caixasPilha[c].getNumeroAtendidos();
            auxString = auxString + "\n" + "Cliente atendidos pelo caixa " + (c + 1) + " da pilha: " + caixasPilha[c].getNumeroAtendidos()+"("+((caixaGetNumeroAtendidos/geradorClientes.getQuantidadeGerada()*100))+"%)"
                                  +"\nTempo médio de espera: "+(caixasPilha[c].getNumeroAtendidos()/statTemposEsperaFila.getMedia())
                                  +"\n";  
        }
        auxString = auxString + "\n" + "Clientes ainda na pilha:" + pilha.size();
        for (int c = 0; c < totalCaixasPilha; c++) {
            auxString = auxString + "\n" + "Cliente ainda no caixa da pilha" + (c + 1) + ": " + (caixasPilha[c].getClienteAtual() != null);
        }
        auxString = auxString + "\n" + "Tempo medio de espera da pilha: " + statTemposEsperaPilha.getMedia();
        

        return auxString;

    }



}
