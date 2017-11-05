package cjb.compiler;
public class Error {
	//行号
	private Integer hh;
	//列号
	private Integer lh;
	//错误信息
	private String msg;
	public Integer getHh() {
		return hh;
	}
	public void setHh(Integer hh) {
		this.hh = hh;
	}
	public Integer getLh() {
		return lh;
	}
	public void setLh(Integer lh) {
		this.lh = lh;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Error(Integer hh, Integer lh, String msg) {
		super();
		this.hh = hh;
		this.lh = lh;
		this.msg = msg;
	}
	public Error(){
		
	}
}
