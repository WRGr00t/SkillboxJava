package core;

public class Car
{

    public String number;
    public int height;
    public double weight;
    public boolean hasVehicle;
    public boolean isSpecial;

    public String toString()
    {
        String special = isSpecial ? "СПЕЦТРАНСПОРТ " : "";
        return "\n=========================================\n" +
            special + "Автомобиль с номером " + number +
            ":\n\tВысота: " + height + " мм\n\tМасса: " + weight + " кг";
    }

    //========= Геттеры================
    public String getNumber() {
        return number;
    }

    public int getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isHasVehicle() {
        return hasVehicle;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    //========= Сеттеры================

    public void setNumber(String number) {
        this.number = number;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHasVehicle(boolean hasVehicle) {
        this.hasVehicle = hasVehicle;
    }

    public void setSpecial(boolean special) {
        isSpecial = special;
    }
}