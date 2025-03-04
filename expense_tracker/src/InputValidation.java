import java.util.*;

public class InputValidation {

    /**
     * Validate amount input
     * @param amount - the amount to entered for transaction
     * @throws IllegalArgumentException - amount should be a number and greater than 0 and less than 1000
     */
    public double validateAmount(String amountInput) {
        double amount = 0;
        try {
            amount = Double.parseDouble(amountInput);
            if (amount <= 0 || amount > 1000) {
                throw new IllegalArgumentException("Amount should be greater then 0 and less than 1000.");
            }
        } catch(NumberFormatException error) {
            throw new NumberFormatException("Amount can only be a number.");
        }
        return amount;
    }

    /**
     * Validate amount input
     * @param amount - the amount to entered for transaction
     * @throws IllegalArgumentException - amount should be a number and greater than 0 and less than 1000
     */
    public String validateCategory(String category) {
        String[] categoryList = {"FOOD", "TRAVEL", "BILLS", "ENTERTAINMENT", "OTHER"};

        if (!Arrays.asList(categoryList).contains(category.toUpperCase())) {
            throw new IllegalArgumentException("Category is invalid.");
        }
        return category;

    }
}