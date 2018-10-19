package ch2_getting_started.your_first_netty_application;

public class EE {

    public static void main(String[] args) {
        Object[] objs = new Object[1];
        objs[0] = new String("a");
        String[] strs = (String[])objs;
        System.out.println(strs);
    }
}
