package len_field_based_and_protobuf_demo.beans;

import com.google.protobuf.InvalidProtocolBufferException;

public class ProtobufTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        ExpressionProto.Expression exp = ExpressionProto.Expression.newBuilder().setArg1(1).setArg2(2).setOp("+").setMsg("EXP1").build();

        ExpressionProto.Expression exp2 = ExpressionProto.Expression.parseFrom(exp.toByteArray());

        System.out.println(exp2);

        ResultProto.Result rs = ResultProto.Result.newBuilder().setResult(3).setMsg("RESULT1").build();

        ResultProto.Result rs2 = ResultProto.Result.parseFrom(rs.toByteArray());

        System.out.println(rs2);

    }
}
