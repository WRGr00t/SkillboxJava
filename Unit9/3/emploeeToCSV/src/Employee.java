import java.time.LocalDate;

public class Employee {
    private String surname;
    private String firstName;
    private String middleName;
    private LocalDate birthdate;
    private String position;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Employee(String surname, String firstName, String middleName, LocalDate birthdate, String position) {
        this.surname = surname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.birthdate = birthdate;
        this.position = position;
    }

    public String employeesToImport(){
        String resultString = String.format("%s;%s;%s;%s;;;;;;;;;;%s;;;;;;;;;;;;;;\n",
                this.getSurname(),
                this.getFirstName(),
                this.getMiddleName(),
                this.getBirthdate().toString(),
                this.getPosition());
        return resultString;
    }
}
