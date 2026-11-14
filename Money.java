
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public final class Money {

    private final long minor;
    private final Currency currency;

    private Money(long minor, Currency currency) {
        if (minor < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }

        this.minor = minor;
        this.currency = Objects.requireNonNull(currency);
    }

    public static Money of(String decimal, Currency currency) {
        Objects.requireNonNull(decimal);
        Objects.requireNonNull(currency);

        BigDecimal amount = new BigDecimal(decimal);

        if (amount.signum() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        long minorUnits = amount
                .movePointRight(currency.getMinorUnits())
                .setScale(0, RoundingMode.UNNECESSARY)
                .longValueExact();

        return new Money(minorUnits, currency);
    }

    public Money plus(Money other) {
        requireSame(other);

        long result = Math.addExact(minor, other.minor);

        return new Money(result, currency);
    }

    public Money times(Quantity quantity) {
        Objects.requireNonNull(quantity);

        long result = Math.multiplyExact(minor, quantity.value());

        return new Money(result, currency);
    }

    public Money minus(Percentage percentage) {
        Objects.requireNonNull(percentage);

        long result = BigDecimal.valueOf(minor)
                .multiply(BigDecimal.valueOf(10000 - percentage.basisPoints()))
                .divide(BigDecimal.valueOf(10000), 0, RoundingMode.HALF_EVEN)
                .longValueExact();

        return new Money(result, currency);
    }

    private void requireSame(Money other) {
        Objects.requireNonNull(other);

        if (currency != other.currency) {
            throw new CurrencyMismatch();
        }
    }

    public long minor() {
        return minor;
    }

    public Currency currency() {
        return currency;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Money)) return false;

        Money other = (Money) obj;

        return minor == other.minor && currency == other.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minor, currency);
    }

    @Override
    public String toString() {
        BigDecimal amount = BigDecimal.valueOf(minor)
                .movePointLeft(currency.getMinorUnits());

        return currency + " " + amount.toPlainString();
    }
}
