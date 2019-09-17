
public class Loader {
    public static void main(String[] args) {
        Cat vaska = new Cat(3000.0, "Васька", Colored.Black);
        Cat murka = new Cat(2550.0, "Мурка", Colored.Tortoiseshell);
        Cat pushok = new Cat(5000.0);
        Cat vaskaTwin = Cat.getTwin(vaska);
        pushok.setColored(Colored.Red);
        pushok.setName("Пушок");
        murka.setColored(Colored.Agouti);
        System.out.println("У нас " + Cat.getCount() + " кошки");
        System.out.println("У Васьки есть близнец-копия - " + vaskaTwin.getName() + " с весом " + vaskaTwin.getWeight()
                + " гр. и окрасом " + vaskaTwin.getColored());
        System.out.println(vaska.getName() + " весит " + vaska.getWeight() + " гр.");
        System.out.println(murka.getName() + " весит " + murka.getWeight() + " гр.");
        System.out.println(pushok.getName() + " весит " + pushok.getWeight() + " гр.");

        while (vaska.getWeight() >= vaska.getMinWeight()) {
            vaska.meow();
        }
        System.out.println(vaska.getName() + " теперь весит " + vaska.getWeight() +
                " гр. и " + vaska.getStatus());

        while (murka.getWeight() < murka.getMaxWeight()) {
            murka.drink(200.0);
            if (!murka.getStatus().equals("Exploded")) { //если ее не добило питье, то кормим дальше
                murka.feed(500.0);
            }
        }
        System.out.println(murka.getName() + " съела " + murka.getFoodAmount() + " гр. еды");
        System.out.println(murka.getName() + " теперь весит " + murka.getWeight() + " гр. и " +
                "" + murka.getStatus());
        pushok.feed(500.0);
        System.out.println(pushok.getName() + " теперь весит " + pushok.getWeight() + " гр.");
        System.out.println("Пушок у нас цвета - " + pushok.getColored());
        pushok.doShit();
        System.out.println("А теперь " + pushok.getName() + " весит " + pushok.getWeight() + " гр.");
        System.out.println("У Васьки есть близнец-копия - " + vaskaTwin.getName() + " с весом " + vaskaTwin.getWeight()
                + " гр. и окрасом " + vaskaTwin.getColored());
        vaskaTwin.feed(100.0);
        vaskaTwin.drink(200.0);
        System.out.println("Клон съел " + vaskaTwin.getFoodAmount() + " гр. еды");
        System.out.println("Итого " + Cat.getCount() + " кошка/кошек/кошки");
        while (!vaskaTwin.getStatus().equals("Dead"))
        {
            vaskaTwin.doShit();
        }
        //========== мучаем труп во благо приложения ======
        vaskaTwin.drink(200.0);
        vaskaTwin.feed(500.0);
        vaskaTwin.meow();
        vaskaTwin.doShit();
        vaskaTwin.doShit(500.0);
        Cat vaskaTwin2 = Cat.getTwin(vaskaTwin);
        if (vaskaTwin2 != null) {
            System.out.println(vaskaTwin2.getWeight());
            System.out.println(vaskaTwin2.getName());
            System.out.println(vaskaTwin2.getStatus());
        }
    }
}