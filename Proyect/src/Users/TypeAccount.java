package Users;

/**
 * Enum with all the types of account available
 */
public enum TypeAccount {
    ADMINISTRATOR("Administrador"), CASHIER("Cajero/a"), INVENTORY("Administración de inventario");

    private final String typeName;
    private TypeAccount(String typeName){
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }
}
