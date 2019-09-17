package core;

public class Car
{
    public String number; //строковая переменная
    public int height; //целочисленная переменная
    public double weight; //вещественная переменная
    public boolean hasVehicle; //логическая переменная
    public boolean isSpecial; //логическая переменная

    public String toString()
    {
        String special = isSpecial ? "СПЕЦТРАНСПОРТ " : ""; //строковая переменная
        return "\n=========================================\n" +
            special + "Автомобиль с номером " + number +
            ":\n\tВысота: " + height + " мм\n\tМасса: " + weight + " кг";
    }
}