
import java.util.Random;


public class Cliente {

    private int numero; // numero do cliente
    private int instanteChegada;
    private int tempoAtendimento; // quantidade de tempo que resta para finalizar o atendimento do cliente
    private static final Random gerador = new Random();
    public static final int tempoMinAtendimento = 5; //mudará
    public static final int tempoMaxAtendimento = 10; //mudará

    public Cliente(int numero, int chegada) {
        this.numero = numero;
        instanteChegada = chegada;
        tempoAtendimento = gerador.nextInt(tempoMaxAtendimento-tempoMinAtendimento+1)+tempoMinAtendimento; //gera valores entre 5 e 20
    }

    public int getNumero() {
        return numero;
    }

    public int getInstanteChegada() {
        return instanteChegada;
    }

    public void decrementarTempoAtendimento() {
        tempoAtendimento--;
    }

    public int getTempoAtendimento() {
        return tempoAtendimento;
    }
}
