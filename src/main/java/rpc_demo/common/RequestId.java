package rpc_demo.common;

import java.util.*;

public class RequestId {

	public static String next() {
		return UUID.randomUUID().toString();
	}

}
