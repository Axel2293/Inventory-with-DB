package Users;

/**
 * Type of account for an administrator (Full access to all tables)
 */
public class Administrator extends Employee{
    public Administrator(String name, String surname, int ID, String department){
        super(name, surname, ID, department, TypeAccount.ADMINISTRATOR);
    }

    /*
     * Returns the ordinal value of the type
     */
    public int getPermisions(){
        return type.ordinal();
    }


}
