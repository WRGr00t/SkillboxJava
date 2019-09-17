import java.util.Scanner;

public class Main
{
    private static int minIncome = 200000; //минимальный порог прибыли компании
    private static int maxIncome = 900000; //потолок прибыли компании

    private static int officeRentCharge = 140000; //аренда офиса
    private static int telephonyCharge = 12000; //расходы на телефон
    private static int internetAccessCharge = 7200; //расходы на интернет

    private static int assistantSalary = 45000; //зарплата ассистента
    private static int financeManagerSalary = 90000; //зарплата финдиректора

    private static double mainTaxPercent = 0.24; //налог на прибыль
    private static double managerPercent = 0.15; //процент от прибыли менеджера

    private static double minInvestmentsAmount = 100000; //минимальная сумма инвестиций

    public static void main(String[] args)
    {
        while(true) //бесконечный цикл для ввода нескольких фирм
        {
            System.out.println("Введите сумму доходов компании за месяц " +
                "(от 200 до 900 тысяч рублей): "); //приглашение пользователю ввести сумму дохода
            int income = (new Scanner(System.in)).nextInt(); //переменная с суммой дохода фирмы

            if(!checkIncomeRange(income)) { //метод проверки попал ли пользователь цифрой дохода в заданный отрезок
                continue; //если не попал, пусть вводит заново
            }

            double managerSalary = income * managerPercent; //зарплата менеджера с процентами от прибыли
            double pureIncome = income - managerSalary - //расчет чистой прибыли...
                calculateFixedCharges(); //за вычетом издержек
            double taxAmount = mainTaxPercent * pureIncome; //сумма налогов
            double pureIncomeAfterTax = pureIncome - taxAmount; //прибыль за вычетом налогов

            boolean canMakeInvestments = pureIncomeAfterTax >= //проверка позволяют ли прибыли вести инвестиционную деятельность
                minInvestmentsAmount;

            System.out.println("Зарплата менеджера: " + managerSalary); //вывод на экран зарплаты менеджера
            System.out.println("Общая сумма налогов: " + //вывод на экран суммы налогов
                (taxAmount > 0 ? taxAmount : 0));
            System.out.println("Компания может инвестировать: " + //результат проверки на возможность инвестиций
                (canMakeInvestments ? "да" : "нет"));
            if(pureIncome < 0) { //если чистая прибыль отрицательная
                System.out.println("Бюджет в минусе! Нужно срочно зарабатывать!"); //готовьтесь работать без выходных
            }
                    System.out.println(calculateFixedCharges()+managerSalary); //строка для "нащупывания" точки безубыточности
        }
    }

    private static boolean checkIncomeRange(int income) //метод проверки попал ли пользователь цифрой дохода в заданный отрезок
    {
        if(income < minIncome)
        {
            System.out.println("Доход меньше нижней границы");
            return false;
        }
        if(income > maxIncome)
        {
            System.out.println("Доход выше верхней границы");
            return false;
        }
        return true;
    }

    private static int calculateFixedCharges() //расчет издержек с расходами и выплатами персоналу без
    {
        return officeRentCharge +
                telephonyCharge +
                internetAccessCharge +
                assistantSalary +
                financeManagerSalary;
    }
}
