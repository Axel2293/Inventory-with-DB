package Users;

/**
 * Type of account for an inventory administrator
 */
public class InventoryAdmin extends Employee{
    public InventoryAdmin(String name, String surname, int ID, String department){
        super(name, surname, ID, department, TypeAccount.INVENTORY);
    }

    /*
     * Returns the ordinal value of the type
     */
    public int getPermisions(){
        return type.ordinal();
    }
}
