package sourceCode;

import java.util.*;

public class CompoundTransaction {
    private String Name;
    private ArrayList<String> children = new ArrayList<String>();

    /*public CompoundTransaction() {
    }*/

    public CompoundTransaction(String Name, ArrayList<String> children) {
        this.Name = Name;
        this.children = children;
    }
    
    public String getName() {
        return Name;
    }

    public ArrayList<String> getChildren() {
        return children;
    }
}
