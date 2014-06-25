
public class Acumulador {

    private double valor;
    private int contador;

    public Acumulador(double valor, int contador) {
        this.valor = valor;
        this.contador = contador;
    }

    public double getValor() {
        return valor;
    }

    public int getContador() {
        return contador;
    }

    public void adicionar(double valor) {
        if (this.valor != valor) {
            this.valor = valor;
        }
    }

    public void adicionar(int valor) {
        if (this.valor != valor){
            this.valor = valor;
        }
    }

    public double getMedia() {
        double media = 0;

        if (contador <= 0) {
            return 0;
        }

        if (contador > 0) {
            media = getValor() / contador;
        }

        return media;
    }
}