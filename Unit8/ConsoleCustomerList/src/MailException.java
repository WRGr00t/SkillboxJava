public class MailException extends RuntimeException {
    private String mail;

    public String getMail() {
        return mail;
    }

    public MailException(String message, String email) {
        super(message);
        mail = email;
    }
}
