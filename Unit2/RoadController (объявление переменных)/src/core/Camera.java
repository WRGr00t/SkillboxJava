package core;

public class Camera
{
    public static Car getNextCar()
    {
        String randomNumber = Double.toString(Math.random()).substring(2, 5); //строковая переменная
        int randomHeight = (int) (1000 + 3500. * Math.random()); //целочисленная переменная
        double randomWeight = 600 + 10000 * Math.random(); //вещественная переменная

        Car car = new Car(); //переменная(экземпляр класса) Car
        car.number = randomNumber;
        car.height = randomHeight;
        car.weight = randomWeight;
        car.hasVehicle = Math.random() > 0.5;
        car.isSpecial = Math.random() < 0.15;

        return car;
    }
}