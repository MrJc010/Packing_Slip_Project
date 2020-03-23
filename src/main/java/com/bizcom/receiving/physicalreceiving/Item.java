package com.bizcom.receiving.physicalreceiving;

public class Item {
	private String ppid;
	private String pn;
	private String sn;
	private String revision;
	private String description;
	private String specialInstruction;
	private String co;
	private String lot;
	private String problemCode;
	private String rma;
	private String dps;
	private String mfgPN;
	private String userId;
	private String date;

	public Item(String ppid, String pn, String sn, String revision, String description,
			String specialInstruction, String co, String lot, String problemCode, String rma, String dps, String mfgPN,
			String userId, String date) {
		this.ppid = ppid;
		this.pn = pn;
		this.sn = sn;
		this.revision = revision;
		this.description = description;
		this.specialInstruction = specialInstruction;
		this.co = co;
		this.lot = lot;
		this.problemCode = problemCode;
		this.rma = rma;
		this.dps = dps;
		this.mfgPN = mfgPN;
		this.userId = userId;
		this.date = date;
	}

	public Item(String CPO_SN, String ppid, String pn, String sn, String revision, String description,
			String specialInstruction, String co, String lot, String problemCode, String rma, String dps, String mfgPN) {
		super();
		this.ppid = ppid;
		this.pn = pn;
		this.sn = sn;
		this.revision = revision;
		this.description = description;
		this.specialInstruction = specialInstruction;
		this.co = co;
		this.lot = lot;
		this.problemCode = problemCode;
		this.rma = rma;
		this.dps = dps;
		this.mfgPN = mfgPN;
	}

	public String getMfgPN() {
		return mfgPN;
	}

	public void setMfgPN(String mfgPN) {
		this.mfgPN = mfgPN;
	}

	public String getDps() {
		return dps;
	}

	public void setDps(String dps) {
		this.dps = dps;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getProblemCode() {
		return problemCode;
	}

	public void setProblemCode(String problemCode) {
		this.problemCode = problemCode;
	}

	public String getRma() {
		return rma;
	}

	public void setRma(String rma) {
		this.rma = rma;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}

	public String getPpid() {
		return ppid;
	}

	public String getPn() {
		return pn;
	}

	public String getSn() {
		return sn;
	}

	public String getRevision() {
		return revision;
	}

	public String getDescription() {
		return description;
	}

	public String getSpecialInstruction() {
		return specialInstruction;
	}



	@Override
	public String toString() {
		return "Item [ppid=" + ppid + ", pn=" + pn + ", sn=" + sn + ", revision=" + revision
				+ ", description=" + description + ", specialInstruction=" + specialInstruction + ", co=" + co
				+ ", lot=" + lot + ", problemCode=" + problemCode + ", rma=" + rma + ", dps=" + dps + ", mfgPN=" + mfgPN
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((pn == null) ? 0 : pn.hashCode());
		result = prime * result + ((ppid == null) ? 0 : ppid.hashCode());
		result = prime * result + ((revision == null) ? 0 : revision.hashCode());
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
		result = prime * result + ((specialInstruction == null) ? 0 : specialInstruction.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (pn == null) {
			if (other.pn != null)
				return false;
		} else if (!pn.equals(other.pn))
			return false;
		if (ppid == null) {
			if (other.ppid != null)
				return false;
		} else if (!ppid.equals(other.ppid))
			return false;
		if (revision == null) {
			if (other.revision != null)
				return false;
		} else if (!revision.equals(other.revision))
			return false;
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		if (specialInstruction == null) {
			if (other.specialInstruction != null)
				return false;
		} else if (!specialInstruction.equals(other.specialInstruction))
			return false;
		return true;
	}

}
