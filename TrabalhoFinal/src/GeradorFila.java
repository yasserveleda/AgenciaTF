
public class GeradorFila {

    private ListArray<Fila> listaFila;
    private Fila fila;
    private boolean gerado = false;

    public GeradorFila(Fila fila) {
        this.fila = fila;
        listaFila = new ListArray<>();
    }

    public boolean gerar() {
        if (listaFila != null) {
            gerado = true;
        }
        return false;
    }

    public int getQuantidadeGerada() {
        int cont = 0;
        
        if (gerado == true){
            cont++;
        }
        
        return cont;
    }
}
