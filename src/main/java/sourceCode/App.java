package sourceCode;

import java.util.*;

public class App {

    private static Scanner sc;

    public static void main(String[] args) {

        sc = new Scanner(System.in);
        int input = 0;
        int acc1, acc2;
        long amount;
        AccountDatabase db = new AccountDatabase();
        TransactionManager tm = new TransactionManager();

        do {
            System.out.println("1. Add account");
            System.out.println("2. Add transaction");
            System.out.println("3. Return all");
            /*System.out.println("4. Search Item\n");
             System.out.println("5. Clear Array\n");
             System.out.println("6. Enumerate Array\n");
             System.out.println("7. Cardinality of Array\n");
             System.out.println("8. Subset of Array\n");*/
            System.out.println("9. Quit\n\n");
            System.out.print("Enter an option: ");
            input = sc.nextInt();

            switch (input) {
                case 1:
                    db.addAccount();
                    break;

                case 2:
                    System.out.print("Enter bank account number: ");
                    acc1 = sc.nextInt();
                    System.out.print("Enter bank account number: ");
                    acc2 = sc.nextInt();
                    System.out.print("Enter amount to be transferred: ");
                    amount = sc.nextLong();

                    tm.processTransaction(acc1, acc2, amount);
                    break;

                case 3:
                    db.returnAll();
                    break;

                default:
                    break;

            }

        } while (input != 9);

    }
}
