
public class Contas{

	
	private int saldoTotal = 100000;
	private int valor;
	private int id;
	
	public int getValor() {
		return valor;
	}
	

	public int getSaldo() {
		return saldoTotal;
	}
	
	public Contas(int valor, int id) {
		this.valor = valor;
		this.id = id;
	}
	

	public void setSaldo(int saldoTotal) {
		this.saldoTotal = saldoTotal;
	}

	public static synchronized boolean transferencia(Contas origem, Contas destino, int valor){
		if(origem.getSaldo() >= valor) {
			origem.setSaldo(origem.getSaldo() - valor);
			destino.setSaldo(destino.getSaldo() + valor);
			return true;
		}else {
			System.out.println("Valor maior que saldo da conta.");
			return false;
		}
	}		
}
