
public class GeradorFila {

    private ListArray<Fila> lista;
    private Fila fila;
    private boolean gerado = false;

    public GeradorFila(Fila fila) {
        this.fila = fila;
    }

    public boolean gerar() {
        if (fila != null) {
            gerado = true;
        }
        return false;
    }

    public int getQuantidadeGerada() {
        return lista.size();
    }
}
