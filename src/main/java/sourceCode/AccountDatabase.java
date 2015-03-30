package sourceCode;

import java.util.*;

public class AccountDatabase {
    public ArrayList<Account> database = new ArrayList<Account>();

    Account getAccount(int accountNumber) {
        int cnt = 0;
        
        Account a1 = null;
        
        for (cnt = 0; cnt < database.size(); cnt++){
            if(accountNumber == database.get(cnt).getAccountNumber()){
                return database.get(cnt);
            }
        }
        
        return a1;
    }

    int getSize() {
        return database.size();
    }
    
}
