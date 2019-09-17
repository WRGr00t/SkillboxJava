import core.*;
import core.Camera;

import java.util.Scanner;

public class RoadController
{
    private static double passengerCarMaxWeight = 3500.0; // kg     вещественная константа
    private static int passengerCarMaxHeight = 2000; // mm          целочисленная константа
    private static int controllerMaxHeight = 4000; // mm            целочисленная константа

    private static int passengerCarPrice = 100; // RUB              целочисленная константа
    private static int cargoCarPrice = 250; // RUB                  целочисленная константа
    private static int vehicleAdditionalPrice = 200; // RUB         целочисленная константа

    public static void main(String[] args)
    {
        System.out.println("Сколько автомобилей сгенерировать?");

        Scanner scanner = new Scanner(System.in); //экземпляр класса Scanner
        int carsCount = scanner.nextInt(); //целочисленная переменная

        for(int i = 0; i < carsCount; i++) //целочисленная переменная
        {
            Car car = Camera.getNextCar(); //экземпляр класса Car
            System.out.println(car);

            //Пропускаем автомобили спецтранспорта бесплатно
            if (car.isSpecial) {
                openWay();
                continue;
            }

            //Проверяем высоту и массу автомобиля, вычисляем стоимость проезда
            int price = calculatePrice(car); //целочисленная переменная
            if(price == -1) {
                continue;
            }

            System.out.println("Общая сумма к оплате: " + price + " руб.");
        }
    }

    /**
     * Расчёт стоимости проезда исходя из массы и высоты
     */
    private static int calculatePrice(Car car) //целочисленный метод с аргументом в виде экземпляры класса Car
    {
        int carHeight = car.height; //целочисленная переменная
        int price = 0; //целочисленная переменная
        if (carHeight > controllerMaxHeight)
        {
            blockWay("высота вашего ТС превышает высоту пропускного пункта!");
            return -1;
        }
        else if (carHeight > passengerCarMaxHeight)
        {
            double weight = car.weight; //вещественная переменная
            //Грузовой автомобиль
            if (weight > passengerCarMaxWeight)
            {
                price = passengerCarPrice;
                if (car.hasVehicle) {
                    price = price + vehicleAdditionalPrice;
                }
            }
            //Легковой автомобиль
            else {
                price = cargoCarPrice;
            }
        }
        else {
            price = passengerCarPrice;
        }
        return price;
    }

    /**
     * Открытие шлагбаума
     */
    private static void openWay()
    {
        System.out.println("Шлагбаум открывается... Счастливого пути!");
    }

    /**
     * Сообщение о невозможности проезда
     */
    private static void blockWay(String reason) //аргумент метода - строковая переменная
    {
        System.out.println("Проезд невозможен: " + reason);
    }
}