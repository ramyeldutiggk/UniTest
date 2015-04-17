package sourceCode;

import java.util.*;

public class AccountDatabase {

    public static void setDatabase(ArrayList<Account> aDatabase) {
        database = aDatabase;
    }
	
    Scanner sc = new Scanner(System.in);
	
   private static ArrayList<Account> database = new ArrayList<Account>();

    public static Account getAccount(int accountNumber) {
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
    
    boolean addAccount(int accountNumber, String accountName, long accountBalance) {
        boolean flag = true;
        int counter;
        
            for (counter = 0; counter < getSize(); counter++) {
                flag = accountNumber != database.get(counter).getAccountNumber();
                if(flag == false){
                    break;
                }
            }
            
            if(flag == false){
            	System.out.println("Account already exists!\n");
                return false;
            }
            
            flag = accountBalance >= 0;
            
            if(flag == false){
            	System.out.println("Invalid balance!\n");
                return false;
            }

        Account newAcc = new Account(accountNumber, accountName, accountBalance);

        database.add(newAcc);
        
        return true;
    }
    
    /*void returnAll()
    {
    	for(int i = 0; i < getSize(); i++)
    	{
    		System.out.println(database.get(i).getAccountNumber());
    		System.out.println(database.get(i).getAccountName());
    		System.out.println(database.get(i).getAccountBalance());
    	}
    }*/

    int getSize() {
        return database.size();
    }

    public ArrayList<Account> getDatabase() {
        return database;
    }
}
