package sourceCode;

import java.util.*;

public class AccountDatabase {
	
	Scanner sc = new Scanner(System.in);
	
    public static ArrayList<Account> database = new ArrayList<Account>();

    static Account getAccount(int accountNumber) {
        int cnt;
        
        Account a1 = null;
        
        for (cnt = 0; cnt < database.size(); cnt++){
            if(accountNumber == database.get(cnt).getAccountNumber()){
                return database.get(cnt);
            }
        }
        
        System.out.println("Account not found!\n");
        return a1;
    }
    
    void addAccount() {
        //Method untestable due to its void return type and it requires input from the user.
        boolean flag = false;
        int counter, accountNumber;
        long accountBalance;
        String accountName;
        
        do {
            System.out.println("Enter the account number\n");
            accountNumber = sc.nextInt();
            
            for (counter = 0; counter < getSize(); counter++) {
                flag = accountNumber != database.get(counter).getAccountNumber();
            }
            
            if(flag == true)
            	System.out.println("Account already exists!\n");
        } while (flag == true);

        System.out.println("Enter the account Name\n");
        accountName = sc.next();
        
        do {
            System.out.println("Enter the account balance\n");
            accountBalance = sc.nextLong();
            flag = accountBalance >= 0;
            
            if(flag == false)
            	System.out.println("Invalid balance!\n");
        } while (flag == false);

        Account newAcc = new Account(accountNumber, accountName, accountBalance);

        database.add(newAcc);
    }

    static int getSize() {
        return database.size();
    }
}
