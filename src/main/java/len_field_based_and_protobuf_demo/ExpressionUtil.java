package len_field_based_and_protobuf_demo;

import len_field_based_and_protobuf_demo.beans.ExpressionProto;

import java.util.regex.*;

public class ExpressionUtil {

    private static final Pattern p = Pattern.compile("\\D+");

    /**
     * Parse a string expression to ExpressionProto.Expression object
     *
     * @param strExp
     * @return
     */
    public static ExpressionProto.Expression fromString(String strExp) {

        String[] args = p.split(strExp);
        Matcher m = p.matcher(strExp);
        m.find();

        String op = String.valueOf(strExp.charAt(m.start()));
        int arg1 = Integer.parseInt(args[0]);
        int arg2 = Integer.parseInt(args[1]);

        // Fake huge string
        StringBuilder sb = new StringBuilder(1000 * 1000);
        for (int i = 0; i < 1000 * 1000 / 10; i++) {
            sb.append("1234567890");
        }

        ExpressionProto.Expression exp = ExpressionProto.Expression.newBuilder().setArg1(arg1).setArg2(arg2).setOp(op).setMsg(sb.toString()).build();

        return exp;
    }
}
