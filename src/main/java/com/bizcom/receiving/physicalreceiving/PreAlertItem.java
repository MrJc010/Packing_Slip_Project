package com.bizcom.receiving.physicalreceiving;

public class PreAlertItem {
	private String ppid;
	private String pn;
	private String co;
	private String date_received;
	private String lot;
	private String dps;
	private String problem_code;
	private String problem_desc;
	private String rma;

	public PreAlertItem(String ppid, String pn, String co, String date_received, String lot, String dps,
			String problem_code, String problem_desc, String rma) {
		super();
		this.ppid = ppid;
		this.pn = pn;
		this.co = co;
		this.date_received = date_received;
		this.lot = lot;
		this.dps = dps;
		this.problem_code = problem_code;
		this.problem_desc = problem_desc;
		this.rma = rma;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getDate_received() {
		return date_received;
	}

	public void setDate_received(String date_received) {
		this.date_received = date_received;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getDps() {
		return dps;
	}

	public void setDps(String dps) {
		this.dps = dps;
	}

	public String getProblem_code() {
		return problem_code;
	}

	public void setProblem_code(String problem_code) {
		this.problem_code = problem_code;
	}

	public String getProblem_desc() {
		return problem_desc;
	}

	public void setProblem_desc(String problem_desc) {
		this.problem_desc = problem_desc;
	}

	public String getRma() {
		return rma;
	}

	public void setRma(String rma) {
		this.rma = rma;
	}

	@Override
	public String toString() {
		return "PreAlertItem [ppid=" + ppid + ", pn=" + pn + ", co=" + co + ", date_received=" + date_received
				+ ", lot=" + lot + ", dps=" + dps + ", problem_code=" + problem_code + ", problem_desc=" + problem_desc
				+ ", rma=" + rma + "]";
	}

}
