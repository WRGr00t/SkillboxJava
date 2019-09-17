public class Loader
{
    public static void main(String[] args)
    {
        PhysicalPerson vano = new PhysicalPerson();
        PhysicalPerson nino = new PhysicalPerson();
        JuridicalPerson trust = new JuridicalPerson();
        JuridicalPerson trest = new JuridicalPerson();
        SoleProprietor ivanov = new SoleProprietor();
        SoleProprietor onopko = new SoleProprietor();
        vano.putOn(1000.0);
        nino.putOn(50000.0);
        trust.putOn(100500.0);
        trest.putOn(-20.0);
        trest.putOn(5000000.0);
        ivanov.putOn(200.0);
        onopko.putOn(123456.78);
        vano.printAmount();
        nino.printAmount();
        trust.printAmount();
        onopko.printAmount();
        nino.giveOut(50.0);
        vano.giveOut(2000.0);
        trest.giveOut(2000050.00);
        trust.giveOut(12345.67);
        onopko.giveOut(235.58);
        vano.printAmount();
        nino.printAmount();
        trust.printAmount();
        trest.printAmount();
        ivanov.printAmount();
        onopko.printAmount();
    }
}
