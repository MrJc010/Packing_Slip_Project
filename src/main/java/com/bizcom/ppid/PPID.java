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

	public PPID(String pnNumber, String ppidNumber, String coNumber, String dateReceived, String lotNumber,
			String dpsNumber, String problemCode, String problemDescription) {
		super();
		this.pnNumber = pnNumber;
		this.ppidNumber = ppidNumber;
		this.coNumber = coNumber;
		this.dateReceived = dateReceived;
		this.lotNumber = lotNumber;
		this.dpsNumber = dpsNumber;
		this.problemCode = problemCode;
		this.problemDescription = problemDescription;
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
		return problemDescription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coNumber == null) ? 0 : coNumber.hashCode());
		result = prime * result + ((dpsNumber == null) ? 0 : dpsNumber.hashCode());
		result = prime * result + ((lotNumber == null) ? 0 : lotNumber.hashCode());
		result = prime * result + ((pnNumber == null) ? 0 : pnNumber.hashCode());
		result = prime * result + ((ppidNumber == null) ? 0 : ppidNumber.hashCode());
		result = prime * result + ((problemCode == null) ? 0 : problemCode.hashCode());
		result = prime * result + ((problemDescription == null) ? 0 : problemDescription.hashCode());
		result = prime * result + ((dateReceived == null) ? 0 : dateReceived.hashCode());
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
		if (dateReceived == null) {
			if (other.dateReceived != null)
				return false;
		} else if (!dateReceived.equals(other.dateReceived))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format(
				"PPID [pnNumber=%s, ppidNumber=%s, coNumber=%s, dateReceived=%s, lotNumber=%s, dpsNumber=%s, problemCode=%s, problemDescription=%s]",
				pnNumber, ppidNumber, coNumber, dateReceived, lotNumber, dpsNumber, problemCode, problemDescription);
	}

}
