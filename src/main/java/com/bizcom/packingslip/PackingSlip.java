package com.bizcom.packingslip;

/**
 * This object is used to read the column name of the excel file
 * @author viet
 *
 */
public class PackingSlip {
	private String partNumber;
	private String poNumber;
	private String lotNumber;
	private int quality;
	private String RMANumber;

	public PackingSlip(String partNumber, String poNumber, String lotNumber, int quality, String rMANumber) {
		super();
		this.partNumber = partNumber;
		this.poNumber = poNumber;
		this.lotNumber = lotNumber;
		this.quality = quality;
		RMANumber = rMANumber;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public String getRMANumber() {
		return RMANumber;
	}

	public void setRMANumber(String rMANumber) {
		RMANumber = rMANumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((RMANumber == null) ? 0 : RMANumber.hashCode());
		result = prime * result + ((lotNumber == null) ? 0 : lotNumber.hashCode());
		result = prime * result + ((partNumber == null) ? 0 : partNumber.hashCode());
		result = prime * result + ((poNumber == null) ? 0 : poNumber.hashCode());
		result = prime * result + quality;
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
		PackingSlip other = (PackingSlip) obj;
		if (RMANumber == null) {
			if (other.RMANumber != null)
				return false;
		} else if (!RMANumber.equals(other.RMANumber))
			return false;
		if (lotNumber == null) {
			if (other.lotNumber != null)
				return false;
		} else if (!lotNumber.equals(other.lotNumber))
			return false;
		if (partNumber == null) {
			if (other.partNumber != null)
				return false;
		} else if (!partNumber.equals(other.partNumber))
			return false;
		if (poNumber == null) {
			if (other.poNumber != null)
				return false;
		} else if (!poNumber.equals(other.poNumber))
			return false;
		if (quality != other.quality)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("PackingSlipRow [partNumber=%s, poNumber=%s, lotNumber=%s, quality=%s, RMANumber=%s]",
				partNumber, poNumber, lotNumber, quality, RMANumber);
	}

}
