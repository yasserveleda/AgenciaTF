
public class GeradorFila {

    private ListArray<Fila> listaFila;
    private Fila fila;
    private boolean gerado = false;

    public GeradorFila(Fila fila) {
        this.fila = fila;
        listaFila = new ListArray<>();
    }

    public boolean gerar() {
        // Verifica se a lista está vazia
        if (listaFila == null) {
            // Caso esteja gera uma lista
            gerado = true;
        }
        // Caso não, return false
        return false;
    }

    public int getQuantidadeGerada() {
        int cont = 0;
        
        // Verifica se fila foi gerado, caso sim
        if (gerado == true){
            // Concatena o contador
            cont++;
        }
        
        return cont;
    }
}
