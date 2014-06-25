
public class Caixa {

    private Cliente clienteAtual;
    private int numeroAtendidos;

    public Caixa(Cliente clienteAtual, int numeroAtendidos) {
        this.clienteAtual = clienteAtual;
        this.numeroAtendidos = numeroAtendidos;
    }

    public void atenderNovoCliente(Cliente c) {
        // Verifica se o clienteAtual é diferente do cliente passado por parâmetro
        if (clienteAtual != c) {
            clienteAtual = c;
        }
    }

    public Cliente dispensarClienteAtual() {
        boolean atendido = true;
        Cliente c = getClienteAtual();

        // Se o cliente já foi atendido
        if (atendido) {
            // Sistema gera um novo cliente
            atenderNovoCliente(clienteAtual);
        }

        // E exibe o cliente que foi atendido
        return c;
    }

    public boolean estaVazio() {
        // Verifica se a fila está vazia
        if (clienteAtual == null) {
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
