import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args)
            throws NumberFormatException, ArrayIndexOutOfBoundsException, NullPointerException, StringIndexOutOfBoundsException {

        String[] regexActions = {"\\+", "-", "/", "\\*"};
        String[] actions = {"+", "-", "/", "*"};

        Scanner scn = new Scanner(System.in);
        System.out.println("______________________________________________________________________________");
        System.out.println("|\t\t\tКАЛЬКУЛЯТОР (Для завершения программы введите 'q')               |");
        System.out.println("|____________________________________________________________________________|");
        System.out.println("|Введите выражение формата 'цифра операция цифра' без пробелов (пример a+b). |");
        System.out.println("|На ввод принимаются целые цифры от 1 до 10 включительно.                    |");
        System.out.println("|____________________________________________________________________________|");

        char quit = 'q';
        do {
            String text = scn.nextLine();
            if (text.charAt(0) == quit) //Закрыть калькулятор
            {
                System.out.println("Работа с калькулятором завершена");
                System.exit(0);
            }
            int actionIndex = -1;
            for (int i = 0; i < actions.length; i++) //опредиляем знак действия
            {
                if (text.contains(actions[i])) {
                    actionIndex = i;
                    checklength+=1;
                }
            }

            if (actionIndex == -1)  //Если не нашли, то выбрасывается исключение
            {
                try {
                    throw new ArrayIndexOutOfBoundsException();
                } catch (ArrayIndexOutOfBoundsException _) {

                }
                try {
                    throw new NumberFormatException();
                }catch (NumberFormatException e) {
                    System.out.println("ошибка ввода: Введен не верный знак действия.");
                    return;
                }
            
            String[] data = text.split(regexActions[actionIndex]);
            if (data.length > 2) {System.out.println("Ошибка ввода: В строке более двух цифр или чисел.");
            return;}


            if(Calculator.isRoman(data[0]) && !Calculator.isRoman(data[1])
            || Calculator.isRoman(data[1]) && !Calculator.isRoman(data[0]))
            {
                try {
                    throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка ввода: Вводите только арабские или только римские цифры.");
                }
                return;
            } else if (Calculator.isRoman(data[0]) && Calculator.isRoman(data[1]))
            {
                String num1, num2;
                num1 = data[0];
                num2 = data[1];

                int a = Calculator.romaToArab(num1);
                int b = Calculator.romaToArab(num2);

                if (a < 1 || b < 1 || b > 10 || a >10)
                {
                    try
                    {
                        throw new NumberFormatException();
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка ввода: Не верный диапозон введённых значений.");
                        return;
                    }
                }

                String result = Calculator.calc(a, b, actions[actionIndex]);
                if (Integer.parseInt(result)<1)
                {
                    try
                    {
                        throw new NumberFormatException();
                    } catch (NumberFormatException e)
                    {
                        System.out.println("Ошибка римлян: Римляне не знали чисел меньше единицы.");
                        return;
                    }
                }
                System.out.println(Calculator.arabToRoma(result));
            }

            if (!Calculator.isRoman(data[0]) && !Calculator.isRoman(data[1]))
            {
                double checkA = Double.parseDouble(data[0]);
                double checkB = Double.parseDouble(data[1]);

                if (checkA *10%10!=0 || checkB *10%10!=0)
                {
                    try {
                        throw new NumberFormatException();
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Ошибка ввода: На ввод допустимы только целые цифры.");
                        return;
                    }
                }

                int num1 = Integer.parseInt(data[0]); //делаем числами
                int num2 = Integer.parseInt(data[1]);

                if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) //ограничение на ввод по наминалу
                {
                    try {
                        throw new NumberFormatException();
                    } catch (NumberFormatException e) {
                        System.out.println("ошибка ввода: Не верный диапозон введённых значений.");
                        return;
                    }
                }
                String result = Calculator.calc(num1, num2, actions[actionIndex]);//заставляем метод работать
                System.out.println(result);
            }
        } while (quit=='q');//Это правда нужно комментировать?
    }
}

class Calculator
    {
        static String  oper, roma;
        static int num1, num2;
        static int res;

        static HashMap<Character, Integer> romanKeyMap = new HashMap<>();
        static TreeMap<Integer, Character> arabianKeyMap = new TreeMap<>();

        public static String calc(int value1, int value2, String operation)
        {
            num1 = value1;
            num2 = value2;
            oper = operation;
            res = 0;

            switch (oper)
            {
                case "+": res = num1 + num2;
                break;
                case "*": res = num1 * num2;
                break;
                case "-": res = num1 - num2;
                break;
                case "/": res = num1 / num2;

            }


        return Integer.toString(res);
       }

       public static String arabToRoma(String arabianNum)
       {
           arabianKeyMap.put(100, 'C');
           arabianKeyMap.put(50, 'L');
           arabianKeyMap.put(10, 'X');
           arabianKeyMap.put(5, 'V');
           arabianKeyMap.put(1, 'I');
           int floorKey;
           int ceilingKey;
           int firstKey;

           String roman = "";
           int arabiaNum = Integer.parseInt(arabianNum);

        do
        {
            floorKey = arabianKeyMap.floorKey(arabiaNum);
            ceilingKey = arabianKeyMap.ceilingKey(arabiaNum);
            firstKey = arabianKeyMap.firstKey();

            if (ceilingKey - arabiaNum == 1)
            {
                roman += arabianKeyMap.get(firstKey);
                roman += arabianKeyMap.get(ceilingKey);
                break;
            } else roman += arabianKeyMap.get(floorKey);

            arabiaNum -= floorKey; //
        } while (arabiaNum != 0);
        return roman;
       }
       public static int romaToArab (String romaNum) {
           roma = romaNum;
           romanKeyMap.put('I', 1);
           romanKeyMap.put('V', 5);
           romanKeyMap.put('X', 10);
           romanKeyMap.put('L', 50);
           romanKeyMap.put('C', 100);

           char[] arr = roma.toCharArray();
           int end = roma.length() - 1;
           int result = romanKeyMap.get(arr[end]);

           int arabian;
           for (int i = end-1; i >= 0; i--)
           {
               arabian = romanKeyMap.get(arr[i]);
               if (arabian<romanKeyMap.get(arr[i+1])) result -= arabian;
               else result += arabian;
           }
           return result;
       }

       public static boolean isRoman(String romario)
       {
           romanKeyMap.put('X', 10);
           romanKeyMap.put('V', 5);
           romanKeyMap.put('I', 1);

           if(romanKeyMap.get(romario.charAt(0))!=null) return true;
           else return false;

       }

            }




