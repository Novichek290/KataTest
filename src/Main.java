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
            int actionCount = 0;

            for (int i = 0; i < actions.length; i++) //опредиляем знак действия и сколько знаков введено
            {
                if (text.contains(actions[i])) {
                    actionIndex = i;
                    actionCount++;
                }
            }


            if (actionIndex == -1)  //Если не нашли знаков действий, то выбрасывается исключение
            {throw new NumberFormatException();}

            String[] data = text.split(regexActions[actionIndex]); //создаём массив, поделённый знаком действия

            if (actionCount >1 || data.length>2) {System.out.println("Ошибка ввода: В строке более двух цифр или чисел.");
                continue;}

            if(Calculator.isRoman(data[0]) && !Calculator.isRoman(data[1])
            || Calculator.isRoman(data[1]) && !Calculator.isRoman(data[0])) //Если 1 римское, а второе - арабское
            {throw new NumberFormatException();}

            else if (Calculator.isRoman(data[0]) && Calculator.isRoman(data[1])) //если оба римские
            {
                String num1, num2;
                num1 = data[0];
                num2 = data[1];

                int a = Calculator.romaToArab(num1);
                int b = Calculator.romaToArab(num2);

                if (a < 1 || b < 1 || b > 10 || a >10) //ограничение по диапозону
                {throw new NumberFormatException();}

                String result = Calculator.calc(a, b, actions[actionIndex]);

                if (Integer.parseInt(result)<1)
                {throw new NumberFormatException();}

                System.out.println(Calculator.arabToRoma(result));
            } else {
                double checkA = Double.parseDouble(data[0]);
                double checkB = Double.parseDouble(data[1]);

                if (checkA *10%10!=0 || checkB *10%10!=0)
                {
                        System.out.println("Ошибка ввода: На ввод допустимы только целые цифры.");
                        continue;
                }

                int num1 = Integer.parseInt(data[0]); //делаем числами
                int num2 = Integer.parseInt(data[1]);

                if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) //ограничение на ввод по наминалу
                {throw new NumberFormatException();}

                String result = Calculator.calc(num1, num2, actions[actionIndex]);
                System.out.println(result);
            }
        } while (quit=='q');
    }
}

class Calculator
    {
        static HashMap<Character, Integer> romanKeyMap = new HashMap<>();
        static TreeMap<Integer, Character> arabianKeyMap = new TreeMap<>();

        public static String calc(int value1, int value2, String operation)
        {
            int res = 0;
            switch (operation)
            {
                case "+": res = value1 + value2;
                break;
                case "*": res = value1 * value2;
                break;
                case "-": res = value1 - value2;
                break;
                case "/": res = value1 / value2;

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
            floorKey = arabianKeyMap.floorKey(arabiaNum);//Находим ближайшее снизу
            ceilingKey = arabianKeyMap.ceilingKey(arabiaNum);//ближайшее равное или больше
            firstKey = arabianKeyMap.firstKey();

            if (ceilingKey - arabiaNum == 1) //с какой стороны ставит I в случаях с числами V,X и тд.
            {
                roman += arabianKeyMap.get(firstKey); //Например IV. Находим ближайшее наибольшее(V) и ставим слева I
                roman += arabianKeyMap.get(ceilingKey);
                break;
            } else roman += arabianKeyMap.get(floorKey); //иначе первый элемент - наименьшее снизу

            arabiaNum -= floorKey;
        } while (arabiaNum != 0);
        return roman;
       }
       public static int romaToArab (String romanNum) {
           romanKeyMap.put('I', 1);
           romanKeyMap.put('V', 5);
           romanKeyMap.put('X', 10);
           romanKeyMap.put('L', 50);
           romanKeyMap.put('C', 100);

           char[] arr = romanNum.toCharArray();
           int end = romanNum.length() - 1;
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




