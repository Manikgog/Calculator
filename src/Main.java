import java.util.Scanner;

class RomanNumberLessOne extends  Exception
{
    public String getMessage()
    {
        return "The result is less than one!";
    }
}
class InputErrorException extends Exception
{
    public String getMessage()
    {
        return "Input error!";
    }
}
class DivisionByZeroException extends Exception
{
    public String getMessage()
    {
        return "Division by zero is prohibited!";
    }
}
class Calculator
{
    int operand1_;
    int operand2_;
    char operator_;
    boolean isArabicDigit_ = true;
    Calculator(int operand1, int operand2, char operator)
    {
        operand1_ = operand1;
        operand2_ = operand2;
        operator_ = operator;
    }

    Calculator(String expression) throws InputErrorException
    {
        String operand_1 = "";
        String operand_2 = "";
        String operator__ = "";
        int i = 0;
        // считывание первого числа из принятой строки expression
        for(; i < expression.length(); ++i)
        {
            if(expression.charAt(i) == ' ')
                continue;
            if(expression.charAt(i) < '0' || expression.charAt(i) > '9' &&
                    (expression.charAt(i) != 'I' && expression.charAt(i) != 'V' &&
                            expression.charAt(i)!='X'))
                break;
            operand_1 += expression.charAt(i);
        }

        // считывание оператора из принятой строки expression
        if(expression.charAt(i) != '+' && expression.charAt(i) != '-'
                && expression.charAt(i) != '*' && expression.charAt(i) != '/')
        {
            throw new InputErrorException();
        }
        else
        {
            operator__ += expression.charAt(i);
            ++i;
        }

        // считывание второго операнда из принятой строки expression
        for(; i < expression.length(); ++i)
        {
            if(expression.charAt(i) == ' ')
                continue;
            if(expression.charAt(i) < '0' || expression.charAt(i) > '9' &&
                    (expression.charAt(i) != 'I' && expression.charAt(i) != 'V' &&
                            expression.charAt(i)!='X'))
                throw new InputErrorException();
            operand_2 += expression.charAt(i);
        }

        // преобразование операндов и оператора из строкового вида в числовой
        if (operand_1.indexOf('I') != -1 || operand_1.indexOf('X') != -1 || operand_1.indexOf('V') != -1) {
            isArabicDigit_ = false;
            operand1_ = ToRomDigit(operand_1);
            if(operand_2.indexOf('I') == -1 && operand_2.indexOf('X') == -1 && operand_2.indexOf('V') == -1)
                throw new InputErrorException();
            operand2_ = ToRomDigit(operand_2);
            operator_ = operator__.charAt(0);
        } else {
            operand1_ = Integer.parseInt(operand_1);
            if (operand_2.indexOf('I') != -1 || operand_2.indexOf('X') != -1 || operand_2.indexOf('V') != -1)
                throw new InputErrorException();
            operand2_ = Integer.parseInt(operand_2);
            operator_ = operator__.charAt(0);
        }
        if(operand1_ > 10 || operand1_ <= 0 || operand2_ > 10 || operand2_ <= 0)
            throw new InputErrorException();
    }

    // преобразование римской цифры в арабскую
    int ToRomDigit(String operand)
    {
        int RomDigit = 0;
        int sign = 1;
        if(operand.charAt(0) == 'I' && (operand.indexOf('X') != -1 || operand.indexOf('V') != -1))
        {
            sign = -1;
        }
        for (int j = 0; j < operand.length(); ++j)
        {
            switch (operand.charAt(j))
            {
                case 'I':
                    RomDigit += sign * 1;
                    break;
                case 'V':
                    RomDigit += 5;
                    break;
                case 'X':
                    RomDigit += 10;
                    break;
            }
        }
        if(RomDigit < 0)
            RomDigit *= -1;
        return RomDigit;
    }

    // Вычисление результата выражения
    int solution() throws DivisionByZeroException
    {
        int result = 0;
        switch(operator_)
        {
            case '+':
                result = operand1_ + operand2_;
                break;
            case '-':
                result = operand1_ - operand2_;
                break;
            case '*':
                result = operand1_ * operand2_;
                break;
            case '/':
                if (operand2_ == 0)
                    throw new DivisionByZeroException();
                result = operand1_ / operand2_;
                break;
        }

        return result;
    }

    // Преобразование результата из целочисленного типа в строку арабских или римских символов
    String StringResult() throws RomanNumberLessOne
    {
        String resultStr = "";
        if(isArabicDigit_)
        {
            try {
                resultStr = String.valueOf(solution());
            } catch(DivisionByZeroException dz) {
                System.out.println((dz.getMessage()));
            }
            return resultStr;
        }
        else
        {
            int result = 0;

            try {
                result = solution();
                if(result < 1)
                    throw new RomanNumberLessOne();
            } catch(DivisionByZeroException dz) {
                System.out.println((dz.getMessage()));
            }
            switch(result/10)
            {
                case 1:
                    resultStr += "X";
                    break;
                case 2:
                    resultStr += "XX";
                    break;
                case 3:
                    resultStr += "XXX";
                    break;
                case 4:
                    resultStr += "XL";
                    break;
                case 5:
                    resultStr += "L";
                    break;
                case 6:
                    resultStr += "LX";
                    break;
                case 7:
                    resultStr += "LXX";
                    break;
                case 8:
                    resultStr += "LXXX";
                    break;
                case 9:
                    resultStr += "XC";
                    break;
                case 10:
                    resultStr += "C";
                    break;
            }

            switch (result%10)
            {
                case 1:
                    resultStr += "I";
                    break;
                case 2:
                    resultStr += "II";
                    break;
                case 3:
                    resultStr += "III";
                    break;
                case 4:
                    resultStr += "IV";
                    break;
                case 5:
                    resultStr += "V";
                    break;
                case 6:
                    resultStr += "VI";
                    break;
                case 7:
                    resultStr += "VII";
                    break;
                case 8:
                    resultStr += "VIII";
                    break;
                case 9:
                    resultStr += "IX";
                    break;
            }

        }
        return resultStr;
    }
}


public class Main {
    static String calc(String input)
    {
        try {
            Calculator c = new Calculator(input);
            return c.StringResult();
        }catch (InputErrorException ie) {
            System.out.println(ie.getMessage());
        }catch (RomanNumberLessOne lessOne){
            System.out.println(lessOne.getMessage());
        }
        return "";
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a expression: ");
        String input = in.nextLine();
        System.out.println(calc(input));
    }
}