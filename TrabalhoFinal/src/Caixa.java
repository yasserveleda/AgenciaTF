

public class Caixa
{
	private Cliente clienteAtual;
	private int numeroAtendidos;

	public Caixa(){}	  

	public void atenderNovoCliente(Cliente c){}
		
	public Cliente dispensarClienteAtual(){return clienteAtual;}	
	
	public boolean estaVazio(){return true;}
	
	public Cliente getClienteAtual(){return clienteAtual;}
	
	public int getNumeroAtendidos(){return numeroAtendidos;}
	
}
