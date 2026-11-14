
public class Main {

    public static void main(String[] args) {

        // Creating money
        Money price = Money.of("1000.00", Currency.USD);

        // Creating discount
        Percentage discount = Percentage.ofBasisPoints(2000);

        // Creating quantity
        Quantity quantity = Quantity.of(2);

        // Applying discount
        Money discountedPrice = discount.applyTo(price);

        // Calculating final total
        Money total = discountedPrice.times(quantity);

        // Displaying results
        System.out.println("Original Price: " + price);
        System.out.println("Discount: " + discount);
        System.out.println("Discounted Price: " + discountedPrice);
        System.out.println("Quantity: " + quantity);
        System.out.println("Final Total: " + total);

        // Testing invalid quantity
        try {
            Quantity invalid = Quantity.of(0);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid quantity rejected");
        }

        // Testing invalid discount
        try {
            Percentage invalid = Percentage.ofBasisPoints(15000);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid discount rejected");
        }

        // Testing currency mismatch
        try {
            Money euro = Money.of("100.00", Currency.EUR);
            Money dollar = Money.of("200.00", Currency.USD);

            euro.plus(dollar);

        } catch (CurrencyMismatch e) {
            System.out.println("Currency mismatch rejected");
        }
    }
}
