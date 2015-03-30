package sourceCode;

import java.util.*;

public class AccountDatabase {
    public static ArrayList<Account> database = new ArrayList<Account>();

    static Account getAccount(int accountNumber) {
        int cnt;
        
        Account a1 = null;
        
        for (cnt = 0; cnt < database.size(); cnt++){
            if(accountNumber == database.get(cnt).getAccountNumber()){
                return database.get(cnt);
            }
        }
        
        return a1;
    }

    static int getSize() {
        return database.size();
    }
}
