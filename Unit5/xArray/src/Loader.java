import java.util.Scanner;

public class Loader {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите символ, из которого будем строить Х");
        String sign = scanner.nextLine();
        System.out.println("Введите символ для фона");
        String fill = scanner.nextLine();
        System.out.println("Введите размер квадрата отрисовки");
        int size = scanner.nextInt() + 1;
        String result = "";
            for (int j = 0; j < size; j++){
                if ((j == (size / 2 + 1) && (size % 2 == 1))){
                    continue;
                }
                for (int i = 0; i < size; i++){
                    if ((i == j) || i == (size - j)){
                        result = result.concat(sign);
                    } else {
                        result = result.concat(fill);
                    }
                }
                result = result.concat("\n");
            }

        System.out.println(result);
    }
}
