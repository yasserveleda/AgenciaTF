
public class GeradorCaixa {

    private ListArray<Caixa> listaCaixa;
    private Caixa caixa;
    private boolean gerado = false;

    public GeradorCaixa(Caixa caixa) {
        this.caixa = caixa;
        listaCaixa = new ListArray<>();
    }

    public boolean gerar() {
        // Verifica se a lista está vazia
        if (listaCaixa == null) {
            // Caso esteja, gera uma lista
            gerado = true;
        }
        // Caso não, return false
        return false;
    }

    public int getQuantidadeGerada() {
        int cont = 0;

        // Verifica se foi gerada uma fila
        if (gerado == true) {
            // Caso sim, concatena o contador
            cont++;
        }

        return cont;
    }
}
