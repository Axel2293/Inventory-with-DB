package Users;
/**
 * Personal data of the employee
 */
public class Person {
    protected final String name;
    protected final String surnames;

    protected Person(String name, String surnames){
        this.name=name;
        this.surnames =surnames;
    }

    public String getName() {
        return name;
    }

    public String getSurnames() {
        return surnames;
    }

    @Override
    public String toString() {
        return "Nombre: "+name+" "+surnames;
    }
}
