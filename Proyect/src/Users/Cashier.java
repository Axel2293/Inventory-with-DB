package Users;

/**
 * Type of account for a cashier employee
 */
public class Cashier extends Employee{
    public Cashier(String name, String surname, int ID, String department){
        super(name, surname, ID, department, TypeAccount.CASHIER);
    }

    /*
     * Returns the ordinal value of the type
     */
    public int getPermisions(){
        return type.ordinal();
    }
}
