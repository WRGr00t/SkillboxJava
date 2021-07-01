import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Voter
{
    private String name;
    private String birthDay;

    public Voter(String name, String birthDay)
    {
        this.name = name;
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object obj)
    {
        Voter voter = (Voter) obj;
        return name.equals(voter.name) && birthDay.equals(voter.birthDay);
    }

    @Override
    public int hashCode()
    {
        long code = name.hashCode() + birthDay.hashCode();
        while(code > Integer.MAX_VALUE) {
            code = code/10;
        }
        return (int) code;
    }

    public String toString()
    {
        //SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
        //return name + " (" + dayFormat.format(birthDay) + ")";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name)
                .append(" (")
                .append(birthDay)
                .append(")");
        return stringBuilder.toString();
    }

    public String getName()
    {
        return name;
    }

    public String getBirthDay()
    {
        return birthDay;
    }

    public String toQuery() {
        //SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("('")
                .append(name)
                .append("', '")
                .append(birthDay)
                //.append(dayFormat.format(birthDay))
                .append("', 1)");
        return stringBuilder.toString();
    }
}
