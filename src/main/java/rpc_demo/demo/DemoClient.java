package rpc_demo.demo;

import rpc_demo.client.RPCClient;
import rpc_demo.client.RPCException;

public class DemoClient {

	private RPCClient client;

	public DemoClient(RPCClient client) {
		this.client = client;
		this.client.rpc("fib_res", Long.class).rpc("exp_res", ExpResponse.class);
	}

	public long fib(int n) {
		return (Long) client.send("fib", n);
	}

	public ExpResponse exp(int base, int exp) {
		return (ExpResponse) client.send("exp", new ExpRequest(base, exp));
	}

	public static void main(String[] args) throws InterruptedException {
		RPCClient client = new RPCClient("localhost", 8888);
		DemoClient demo = new DemoClient(client);
		for (int i = 0; i < 30; i++) {
			try {
				System.out.printf("fib(%d) = %d\n", i, demo.fib(i));
				Thread.sleep(100);
			} catch (RPCException e) {
				i--; // retry
			}
		}
		for (int i = 0; i < 30; i++) {
			try {
				ExpResponse res = demo.exp(2, i);
				Thread.sleep(100);
				System.out.printf("exp2(%d) = %d cost=%dns\n", i, res.getValue(), res.getCostInNanos());
			} catch (RPCException e) {
				i--; // retry
			}
		}
		client.close();
	}

}
