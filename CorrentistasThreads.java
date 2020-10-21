import java.util.ArrayList;
import java.util.Random;

public class CorrentistasThreads {

	public static void main(String[] args) {
		
		//Usei ArrayList pois não é thread safe, assim como LinkedList
		ArrayList<Contas> contaDoBanco = new ArrayList<>();
		
		Random randValue = new Random();

		//a variavel total_inicio guarda o total do banco antes das transações, que são 100.000 reais
		//assim como o total_final que deverá ser de 100.000 reais tbm
		int total_inicio = 0;
		int total_final = 0;
		//Setei 10.000 reais inicialmente para as contas, para dar o total de 100.000
		for(int i = 0; i < 10; i++) {
			contaDoBanco.add(i, new Contas(0, i));
			contaDoBanco.get(i).setSaldo(10000);
			total_inicio = total_inicio + contaDoBanco.get(i).getSaldo();
		}
		System.out.println("Saldo total do banco antes das transações: R$" + total_inicio + " reais");
		
		Runnable thread = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i = 0; i < 20; i++) {
					synchronized(this) {
						System.out.println("\nTransação " + (i+1) + " ocorrendo...");
						int contaInicial = randValue.nextInt(10);
						int contaFinal = randValue.nextInt(10);
						int quantia = randValue.nextInt(5000);
						System.out.println("Conta de origem: " + (contaInicial+1) + "\nSaldo inicial: R$" + 
											contaDoBanco.get(contaInicial).getSaldo() + "\nConta destino: " + 
											(contaFinal+1) + "\nSaldo inicial: R$" + contaDoBanco.get(contaFinal)
											.getSaldo());
						System.out.println("Valor da transação: R$" + quantia);
						
						Contas.transferencia(contaDoBanco.get(contaInicial), 
								contaDoBanco.get(contaFinal), quantia);
						
						System.out.println("\nConta de origem: " + (contaInicial+1) + ", Saldo final: " + "\nR$" + 
						contaDoBanco.get(contaInicial).getSaldo());
						System.out.println("\nConta destino: " + (contaFinal+1) + ", Saldo final: " + "\nR$" + 
						contaDoBanco.get(contaFinal).getSaldo());
						
						System.out.println("\n--------------------------------------\n");
						
					}
				}
			}
			
		};
		
		Thread[] threads = new Thread[5];
		
		for(int i = 0; i < 5; i++) {
			threads[i] = new Thread(thread, Integer.toString(i));
            threads[i].start();
		}
		
		for (int i = 0; i < 5; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println("Transação falhou, erro de thread." + e.getMessage());
            }
        }
		System.out.println("\n-------------------------------\n");
		for(int i = 0; i < 10; i++) {
			System.out.println("ID: " + (i+1) + ", Saldo: R$" + contaDoBanco.get(i).getSaldo() + " reais");
			total_final = total_final + contaDoBanco.get(i).getSaldo();
		}
		System.out.println("\nSaldo total do banco depois das transações: R$" + total_final + " reais");
	}

}
