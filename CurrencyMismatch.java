public class CurrencyMismatch extends RuntimeException {

    public CurrencyMismatch() {
        super("Cannot perform operations on different currencies");
    }
}