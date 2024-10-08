public class Soution {
    public static void main(String[] args) {
        System.out.println(fun1("012731892731927913728931"));
        System.out.println(fun1("-21"));
        System.out.println(fun1("ab324sa"));
    }

    public static boolean fun(int x) {
        int digit = 0, temp = x, num = 0;
        while (temp > 0) {
            temp /= 10;
            digit++;
        }
        temp = x;
        while (temp > 0) {
            num += (temp % 10) * Math.pow(10, --digit);
            temp /= 10;
        }
        return num == x;
    }

    public static long fun1(String s) {
        long res = 0, digit = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (ch == '-' && i == 0) {
                res = -res;
                break;
            }
            if (ch < '0' || ch > '9')
                return 0;
            res += ((ch - 48) * Math.pow(10, digit++));

        }
        return res;
    }
}
