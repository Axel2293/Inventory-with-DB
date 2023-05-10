package Users;

/**
 * Data of the employee on the company
 */
public abstract class Employee extends Person {

    protected final int ID;
    protected final String department;
    protected final TypeAccount type;

    protected Employee(String name, String surname, int ID, String department, TypeAccount type){
        super(name, surname);
        this.ID=ID;
        this.department=department;
        this.type=type;
    }

    public int getID() {
        return ID;
    }

    public String getDepartment() {
        return department;
    }

    public TypeAccount getType() {
        return type;
    }

    @Override
    public String toString() {
        return super.toString()+"\nID: "+ID+"\nDepartamento: "+department+"\nTipo de cuenta: "+type;
    }

    /*
     * Returns the ordinal value of the type
     */
    public abstract int getPermisions();

    
}
