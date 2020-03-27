package com.bizcom.repair01;

import com.bizcom.database.DBHandler;

public class RevesionUpgrade {
	private String pn;
	private String location;
	private String desc;
	private String ecoAction;
	private String oldMaterial;
	private String newMaterial;
	private String shortcut;
	private int currentRev;
	public RevesionUpgrade() {
	}
	public RevesionUpgrade(String pn, String location, String desc, String ecoAction, String oldMaterial,
			String newMaterial, String shortcut, int currentRev) {
		super();
		this.pn = pn;
		this.location = location;
		this.desc = desc;
		this.ecoAction = ecoAction;
		this.oldMaterial = oldMaterial;
		this.newMaterial = newMaterial;
		this.shortcut = shortcut;
		this.currentRev = currentRev;
	}
	public String getPn() {
		return pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getEcoAction() {
		return ecoAction;
	}
	public void setEcoAction(String ecoAction) {
		this.ecoAction = ecoAction;
	}
	public String getOldMaterial() {
		return oldMaterial;
	}
	public void setOldMaterial(String oldMaterial) {
		this.oldMaterial = oldMaterial;
	}
	public String getNewMaterial() {
		return newMaterial;
	}
	public void setNewMaterial(String newMaterial) {
		this.newMaterial = newMaterial;
	}
	public String getShortcut() {
		return shortcut;
	}
	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}
	public int getCurrentRev() {
		return currentRev;
	}
	public void setCurrentRev(int currentRev) {
		this.currentRev = currentRev;
	}
	@Override
	public String toString() {
		return "RevesionUpgrade [pn=" + pn + ", location=" + location + ", desc=" + desc + ", ecoAction=" + ecoAction
				+ ", oldMaterial=" + oldMaterial + ", newMaterial=" + newMaterial + ", shortcut=" + shortcut
				+ ", currentRev=" + currentRev + "]";
	}
	
	
	
	
}
