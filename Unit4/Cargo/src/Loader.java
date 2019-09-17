import java.util.Scanner;

public class Loader {
    public static void main(String[] args)
    {
        System.out.println("Введите количество ящиков для отправки: ");
        Scanner value = new Scanner(System.in);
        int amount = value.nextInt();
        System.out.println("Введите вместимость грузовика (в контейнерах): ");
        int truckCapacity = value.nextInt();
        System.out.println("Введите вместимость контейнера (в ящиках): ");
        int containerCapacity = value.nextInt();
        int j = 1, k = 1; //k - счетчик машин, j - счетчик контейнеров
        for (int i = 1; i < amount + 1; i++) //i - счетчик ящиков
            {
                if (i == 1 || i % (truckCapacity*containerCapacity) == 1)
                {
                    System.out.println("Грузовик " + k);
                    k++;
                }
                if (i == 1 || i % containerCapacity == 1)
                {
                    System.out.println("    Контейнер " + j);
                    j++;
                }
                System.out.println("        Ящик " + i);

            }
        System.out.println("Количество полных машин: " + (amount / (truckCapacity * containerCapacity)) +
                " , " + "неполная машина с " + (amount % (truckCapacity * containerCapacity)) / containerCapacity +
                " полным/и контейнером/ами и одним неполным контейнером с "
                + (amount % containerCapacity) + " ящиками/ящиком");
        System.out.println("Контроль: " + (amount/(truckCapacity * containerCapacity)) + " * "
                + truckCapacity * containerCapacity + " + " + (amount % (truckCapacity * containerCapacity)) /
                containerCapacity + " * " + containerCapacity + " + " + (amount%containerCapacity));
    }
}
