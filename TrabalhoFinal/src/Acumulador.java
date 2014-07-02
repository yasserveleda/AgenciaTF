
public class Acumulador {

    private double valor;
    private int contador;

    public Acumulador() {
        valor = 0;
        contador = 0;
    }

    public double getValor() {
        return (valor);
    }

    public int getContador() {
        return contador;
    }

    public void adicionar(double v) {
        valor = valor + v;
        contador++;
    }

    public void adicionar(int v) {
        valor = valor + v;
        contador++;
    }

    public double getMedia() {
        if (contador != 0) {
            return valor / contador;
        } else {
            return 0;
        }
    }
}
