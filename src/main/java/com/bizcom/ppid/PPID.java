package com.bizcom.ppid;

public class PPID {
	private String pnNumber;
	private String ppidNumber;
	private String coNumber;
	private String dateReceived;
	private String lotNumber;
	private String dpsNumber;
	private String problemCode;
	private String problemDescription;
	private String rma;

	public PPID(String pnNumber, String ppidNumber, String coNumber, String dateReceived, String lotNumber,
			String dpsNumber, String problemCode, String problemDescription, String rma) {
		super();
		this.pnNumber = pnNumber;
		this.ppidNumber = ppidNumber;
		this.coNumber = coNumber;
		this.dateReceived = dateReceived;
		this.lotNumber = lotNumber;
		this.dpsNumber = dpsNumber;
		this.problemCode = problemCode;
		this.problemDescription = problemDescription;
		this.rma = rma;
	}

	public String getPnNumber() {
		return pnNumber;
	}

	public String getPpidNumber() {
		return ppidNumber;
	}

	public String getCoNumber() {
		return coNumber;
	}

	public String getDateReceived() {
		return dateReceived;
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public String getDpsNumber() {
		return dpsNumber;
	}

	public String getProblemCode() {
		return problemCode;
	}

	public String getProblemDescription() {
		return problemDescription.replaceAll("'", "");
	}

	public String getRma() {
		return rma;
	}

	public void setRma(String rma) {
		this.rma = rma;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coNumber == null) ? 0 : coNumber.hashCode());
		result = prime * result + ((dateReceived == null) ? 0 : dateReceived.hashCode());
		result = prime * result + ((dpsNumber == null) ? 0 : dpsNumber.hashCode());
		result = prime * result + ((lotNumber == null) ? 0 : lotNumber.hashCode());
		result = prime * result + ((pnNumber == null) ? 0 : pnNumber.hashCode());
		result = prime * result + ((ppidNumber == null) ? 0 : ppidNumber.hashCode());
		result = prime * result + ((problemCode == null) ? 0 : problemCode.hashCode());
		result = prime * result + ((problemDescription == null) ? 0 : problemDescription.hashCode());
		result = prime * result + ((rma == null) ? 0 : rma.hashCode());
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
		PPID other = (PPID) obj;
		if (coNumber == null) {
			if (other.coNumber != null)
				return false;
		} else if (!coNumber.equals(other.coNumber))
			return false;
		if (dateReceived == null) {
			if (other.dateReceived != null)
				return false;
		} else if (!dateReceived.equals(other.dateReceived))
			return false;
		if (dpsNumber == null) {
			if (other.dpsNumber != null)
				return false;
		} else if (!dpsNumber.equals(other.dpsNumber))
			return false;
		if (lotNumber == null) {
			if (other.lotNumber != null)
				return false;
		} else if (!lotNumber.equals(other.lotNumber))
			return false;
		if (pnNumber == null) {
			if (other.pnNumber != null)
				return false;
		} else if (!pnNumber.equals(other.pnNumber))
			return false;
		if (ppidNumber == null) {
			if (other.ppidNumber != null)
				return false;
		} else if (!ppidNumber.equals(other.ppidNumber))
			return false;
		if (problemCode == null) {
			if (other.problemCode != null)
				return false;
		} else if (!problemCode.equals(other.problemCode))
			return false;
		if (problemDescription == null) {
			if (other.problemDescription != null)
				return false;
		} else if (!problemDescription.equals(other.problemDescription))
			return false;
		if (rma == null) {
			if (other.rma != null)
				return false;
		} else if (!rma.equals(other.rma))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PPID [pnNumber=" + pnNumber + ", ppidNumber=" + ppidNumber + ", coNumber=" + coNumber
				+ ", dateReceived=" + dateReceived + ", lotNumber=" + lotNumber + ", dpsNumber=" + dpsNumber
				+ ", problemCode=" + problemCode + ", problemDescription=" + problemDescription + ", rma=" + rma + "]";
	}
	
	

}
