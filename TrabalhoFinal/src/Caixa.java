
public class Caixa {

    private Cliente clienteAtual;
    private int numeroAtendidos;


    public Caixa(Cliente clienteAtual, int numeroAtendidos) {
        this.clienteAtual = clienteAtual;
        this.numeroAtendidos = numeroAtendidos;
    }

    public void atenderNovoCliente(Cliente c) {
        if (clienteAtual != c) {
            clienteAtual = c;
        }

    }

    public Cliente dispensarClienteAtual() {

        return clienteAtual;
    }

    public boolean estaVazio() {
        // Verifica se a fila está vazia
        if (clienteAtual == null){
            return true;
        }
        // Caso contrário return false
        return false;
    }

    public Cliente getClienteAtual() {
        return clienteAtual;
    }

    public int getNumeroAtendidos() {
        return numeroAtendidos;
    }
}
