import java.util.Random;

public class Loader {
    public static void main(String[] args) {
        DonutFactory factory = new DonutFactory();

        Donut cherry = factory.getDonut(DonutTypes.CHERRY);
        Donut chocolate = factory.getDonut(DonutTypes.CHOCOLATE);
        Donut nut = factory.getDonut(DonutTypes.NUT);

        cherry.eat();
        chocolate.eat();
        nut.eat();

        for (int i = 0; i < 10; i++) {
            eatRandomDonut(factory);
        }
        factory.printHistory();
    }

    public static void eatRandomDonut (DonutFactory factory) {
        Donut randomDonut = getRandomDonut(factory);

        randomDonut.eat();
    }

    public static Donut getRandomDonut (DonutFactory factory) {
        Random random = new Random();
        DonutTypes type = DonutTypes.values()[random.nextInt(DonutTypes.values().length)];

        return factory.getDonut(type);
    }
}
