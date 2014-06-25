

public class Cliente
{
	private int numero;
	private int instanteChegada;
	private int tempoAtendimento;
	public static final int tempoMinAtendimento = 5; //mudará
	public static final int tempoMaxAtendimento = 10; //mudará

	public Cliente(int numero, int chegada, int tempoAtendimento){
            this.numero = numero;
            instanteChegada = chegada;
            this.tempoAtendimento = tempoAtendimento;
        }
		
	public int getNumero(){return numero;}
		
	public int getInstanteChegada(){return instanteChegada;}
	
	public void decrementarTempoAtendimento(){}
	
	public int getTempoAtendimento(){return tempoAtendimento;}
}
