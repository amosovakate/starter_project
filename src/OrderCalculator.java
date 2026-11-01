import java.util.NoSuchElementException;
import java.util.Scanner;

public class OrderCalculator {

    // Константы: все пороги и ставка лежат в одном месте
    private static final double VAT_RATE = 0.10; // вариант 1: ставка НДС 10 %
    private static final int MIN_QUANTITY = 1;
    private static final int MAX_QUANTITY = 10_000;
    private static final double MAX_PRICE = 5_000_000;
    private static final double MIN_DISCOUNT = 0;
    private static final double MAX_DISCOUNT = 30;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int quantity;
        double price;
        double discountPercent;

        try {
            System.out.print("Количество единиц: ");
            quantity = scanner.nextInt();
            System.out.print("Цена единицы, руб.: ");
            price = scanner.nextDouble();
            System.out.print("Скидка, %: ");
            discountPercent = scanner.nextDouble();
        } catch (NoSuchElementException e) {
            System.out.println("Ошибка: нужно ввести числа (количество - целое), расчёт не выполнен");
            return;
        }

        String error = validate(quantity, price, discountPercent);
        if (error != null) {
            System.out.println(error);
            return;
        }

        double base = calculateBase(quantity, price);
        double afterDiscount = applyDiscount(base, discountPercent);
        double vat = calculateVat(afterDiscount);
        double total = afterDiscount + vat;

        System.out.printf("Базовая сумма: %.2f руб.%n", base);
        System.out.printf("Сумма со скидкой: %.2f руб.%n", afterDiscount);
        System.out.printf("НДС: %.2f руб.%n", vat);
        System.out.printf("Итого: %.2f руб.%n", total);
    }

    // Вернёт текст ошибки или null, если данные корректны
    public static String validate(int quantity, double price, double discountPercent) {
        if (quantity < MIN_QUANTITY || quantity > MAX_QUANTITY) {
            return "Ошибка: количество должно быть от " + MIN_QUANTITY + " до " + MAX_QUANTITY;
        }
        if (!(price > 0 && price <= MAX_PRICE)) {
            return "Ошибка: цена должна быть больше 0 и не больше " + (long) MAX_PRICE + " руб.";
        }
        if (!(discountPercent >= MIN_DISCOUNT && discountPercent <= MAX_DISCOUNT)) {
            return "Ошибка: скидка должна быть от " + (int) MIN_DISCOUNT + " до " + (int) MAX_DISCOUNT + " процентов";
        }
        return null; // null значит «ошибок нет»
    }

    public static double calculateBase(int quantity, double price) {
        return quantity * price;
    }

    public static double applyDiscount(double base, double discountPercent) {
        double discountAmount = base * discountPercent / 100;
        return base - discountAmount; // сумма ПОСЛЕ скидки
    }

    public static double calculateVat(double amountAfterDiscount) {
        return amountAfterDiscount * VAT_RATE;
    }
}