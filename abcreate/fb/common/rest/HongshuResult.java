package abcreate.fb.common.rest;

import java.util.List;
import java.util.Map;

public class HongshuResult {

	private List list;
	private Map map;
	private Integer total;
	private String status;
	private String type;
	private String msg;
	private Map pager;

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map getPager() {
		return pager;
	}

	public void setPager(Map pager) {
		this.pager = pager;
	}

}