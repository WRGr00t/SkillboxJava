public class NumberException extends RuntimeException {
    private String number;

    public String getNumber() {
        return number;
    }

    public NumberException(String message, String num) {
        super(message);
        number = num;
    }
}
