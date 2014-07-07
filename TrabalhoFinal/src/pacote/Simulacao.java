
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Simulacao {
    
    private ListArray<Integer> listaValores;
    private double mediana, media;
    private int moda;
    private static double duracao ; 
    private static double probabilidadeChegada ;
    private static final int totalCaixas = 5;
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
    public Simulacao(boolean t) {

        pilha = new StackArray<Cliente>();
        listaValores = new ListArray<Integer>();
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
        mediana= 0;
        media = 0;
        ArrayList<Integer> valores = new ArrayList<>();
        
        for (int c = 0; c < totalCaixas; c++) {
            caixas[c] = new Caixa();

        }

        for (int c = 0; c < totalCaixasPilha; c++) {

            caixasPilha[c] = new Caixa();
        }

    }

    public void leitura() {
        // TODO code application logic here
        Path path = Paths.get("C:\\" + texto);
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
                Cliente c = new Cliente(geradorClientes.getQuantidadeGerada(), tempo);
                fila.add(c);
                if (trace) {
                    auxString2 = auxString2 + tempo + ": cliente " + c.getNumero() + " (" + c.getTempoAtendimento() + " min) entra na fila - " + fila.size() + " pessoa(s)" + "\n";
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
                            auxString2 = auxString2 + "\n" + tempo + ": cliente " + caixas[c].getClienteAtual().getNumero() + " chega ao caixa " + (c + 1);
                            auxString2 = auxString2 + "\n" + tempo + ": cliente " + caixas[c].getClienteAtual().getNumero() + " (" + caixas[c].getClienteAtual().getTempoAtendimento() + " min) entra na pilha - " + pilha.size() + " pessoa(s)";
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

    public void limpar() {
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

        auxString = "\n" + "RESULTADOS DA SILMULAÇÃO" + "\n";
        auxString = auxString + "\n" + "Duracao: " + duracao;
        auxString = auxString + "\n" + "Probabilidade de chegada de clientes: " + probabilidadeChegada;
        auxString = auxString + "\n" + "Tempo de atendimento minimo: " + Cliente.tempoMinAtendimento;
        auxString = auxString + "\n" + "Tempo de atendimento maximo: " + Cliente.tempoMaxAtendimento;
        auxString = auxString + "\n" + "Total de clientes gerados:" + geradorClientes.getQuantidadeGerada() + "\n" + "\n";
        auxString = auxString + "\n" + "Mediana dos caixas da fila: "+ mediana;
        auxString = auxString + "\n" + "Media dos caixas da fila: "+media;
        auxString = auxString + "\n" + "Moda dos caixas da fila: "+moda;
        for (int c = 0; c < totalCaixas; c++) {
            double caixaGetNumeroAtendidos = caixas[c].getNumeroAtendidos();
            auxString = auxString + "\n" + "Cliente atendidos pelo caixa " + (c + 1) + " da fila: " + caixas[c].getNumeroAtendidos() + "(" + ((caixaGetNumeroAtendidos / geradorClientes.getQuantidadeGerada() * 100)) + "%)"
                    + "\nTempo médio de atendimento: " + (caixas[c].getNumeroAtendidos() / statTemposEsperaFila.getMedia())
                    + "\n";
            
        }
        auxString = auxString + "\n" + "Clientes ainda na fila: " + fila.size();
        for (int c = 0; c < totalCaixas; c++) {
            auxString = auxString + "\n" + "Cliente ainda no caixa " + (c + 1) + " da fila: " + (caixas[c].getClienteAtual() != null);
        }

        auxString = auxString + "\n" + "Tempo medio de espera na fila: " + statTemposEsperaFila.getMedia();
        auxString = auxString + "\n" + "Comprimento medio da fila: " + statComprimentosFila.getMedia();
        for (int c = 0; c < totalCaixasPilha; c++) {
            double caixaGetNumeroAtendidos = caixasPilha[c].getNumeroAtendidos();
            auxString = auxString + "\n" + "Cliente atendidos pelo caixa " + (c + 1) + " da pilha: " + caixasPilha[c].getNumeroAtendidos() + "(" + ((caixaGetNumeroAtendidos / geradorClientes.getQuantidadeGerada() * 100)) + "%)"
                    + "\nTempo médio de atendimento: " + (caixasPilha[c].getNumeroAtendidos() / statTemposEsperaFila.getMedia())
                    + "\n";
        }
        auxString = auxString + "\n" + "Clientes ainda na pilha:" + pilha.size();
        for (int c = 0; c < totalCaixasPilha; c++) {
            auxString = auxString + "\n" + "Cliente ainda no caixa da pilha" + (c + 1) + ": " + (caixasPilha[c].getClienteAtual() != null);
        }
        auxString = auxString + "\n" + "Tempo medio de espera na pilha: " + statTemposEsperaPilha.getMedia();

        return auxString;

    }
    
    public void adicionaValores(){
        for (int c = 0; c < totalCaixas; c++) {
            int caixaGetNumeroAtendidos = caixas[c].getNumeroAtendidos();
            listaValores.add(caixaGetNumeroAtendidos);
            
        }
    
    }
    
    public void calculaMediana() {
             
        
        //listaValores.ordena();

        if (listaValores.size() % 2 == 0) {
            int posicaoMediana = (listaValores.size() + 1) / 2;
            posicaoMediana--; //nosso ArrayList começa do zero
            mediana = listaValores.get(posicaoMediana);
        } else {
            int posicao1 = listaValores.size() / 2;
            posicao1--; 
            int posicao2 = posicao1 + 1;
            mediana = (listaValores.get(posicao1) + listaValores.get(posicao2))/2;
 
        }
                
    
    }
    
    public void calculaMedia(){
        double somatorio=0;
        
        for(int i=0; i<listaValores.size(); i++){
            somatorio = somatorio+listaValores.get(i);
        }
        media = somatorio/listaValores.size();
    }
    
    public void ordena(){
        int aux, aux2;
        for(int i=0; i<listaValores.size()-1; i++){
            aux = listaValores.get(i);
            aux2 = listaValores.get(i+1);
            if(aux>aux2){
                listaValores.set(i,aux2);
                listaValores.set(i, aux);
            }
        }
    }
    
    public void calculaModa(){        
        int nVezes = 0;  
          
        int comparaV = 0;  
        for(int i = 0; i < listaValores.size(); i++){  
            nVezes = 0;  
            for(int k = i+1; k < listaValores.size(); k++){  
                if( listaValores.get(i) == listaValores.get(k) ){  
                    ++nVezes;                 
                }  
            }  
            if (nVezes > comparaV ){  
                moda = listaValores.get(i);
                comparaV = nVezes;  
            }      
        }         
        
    }  

}
