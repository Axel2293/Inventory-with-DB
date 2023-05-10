package Users;

/**
 * Exception for account that dont have a type int he specified range
 */
public class InvalidAccountType extends Exception{
    int type = 0;

    public InvalidAccountType(int type){
        this.type = type;
    }

    @Override    
    public String toString() {
        return "Tipo de cuenta invalido: "+type;
    }
}
