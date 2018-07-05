package rpc_demo.demo;

public class ExpRequest {
	private int base;
	private int exp;

	public ExpRequest() {
	}

	public ExpRequest(int base, int exp) {
		this.base = base;
		this.exp = exp;
	}

	public int getBase() {
		return base;
	}

	public void setBase(int base) {
		this.base = base;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}
}
