package sourceCode;

import java.util.*;

public class App {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int input = 0;
		int acc1, acc2;
		long amount;
		AccountDatabase db = new AccountDatabase();
		TransactionManager tm = new TransactionManager();

		do
		{
			System.out.println("1. Add account\n");
			System.out.println("2. Add transaction\n");
			/*System.out.println("3. Remove Item\n");
            System.out.println("4. Search Item\n");
            System.out.println("5. Clear Array\n");
            System.out.println("6. Enumerate Array\n");
            System.out.println("7. Cardinality of Array\n");
            System.out.println("8. Subset of Array\n");*/
			System.out.println("9. Quit\n\n");
			System.out.println("Enter an option: ");
			input = sc.nextInt();

			switch(input)
			{
				case 1: db.addAccount();
						break;
						
				case 2: System.out.println("Enter bank account number: ");
						acc1 = sc.nextInt();
						System.out.println("Enter bank account number: ");
						acc2 = sc.nextInt();
						System.out.println("Enter amount to be transferred: ");
						amount = sc.nextLong();

						tm.processTransaction(acc1, acc2, amount);
						break;
				
				default: break;
				
			}
			
		} while(input != 9);
		
	}
}
