
public class GeradorCaixa {

    private ListArray<Caixa> listaCaixa;
    private Caixa caixa;
    private boolean gerado = false;

    public GeradorCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public boolean gerar() {
        return gerado;
    }

    public int getQuantidadeGerada() {
        int cont = 0;

        if (gerado == true) {
            cont++;
        }

        return cont;
    }
}
