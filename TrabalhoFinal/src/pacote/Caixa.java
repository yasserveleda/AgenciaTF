
public class Caixa {

    private Cliente clienteAtual; // cliente sendo atendido no caixa
    private int numeroAtendidos;

    public Caixa() {
        clienteAtual = null;
        numeroAtendidos = 0;
    }

    public void atenderNovoCliente(Cliente c) {
        // Verifica se o clienteAtual é diferente do cliente passado por parâmetro
        if (clienteAtual != c) {
            clienteAtual = c;
        }
    }

    public Cliente dispensarClienteAtual() {
        Cliente c = clienteAtual;
        clienteAtual = null;
        numeroAtendidos++;
        return c;

    }

    public boolean estaVazio() {
        return (clienteAtual == null);
    }

    public Cliente getClienteAtual() {
        return clienteAtual;
    }

    public int getNumeroAtendidos() {
        return numeroAtendidos;
    }
}
