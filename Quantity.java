
public final class Quantity {

    private final int value;

    private Quantity(int value) {
        this.value = value;
    }

    public static Quantity of(int v) {

        if (v < 1) {
            throw new IllegalArgumentException(
                "Quantity must be at least 1"
            );
        }

        return new Quantity(v);
    }

    public int value() {
        return value;
    }

    public Quantity plus(Quantity q) {
        return Quantity.of(Math.addExact(value, q.value));
    }

    public Quantity minus(Quantity q) {
        return Quantity.of(Math.subtractExact(value, q.value));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity)) return false;

        Quantity other = (Quantity) obj;

        return value == other.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
