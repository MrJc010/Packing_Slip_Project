package com.bizcom.services;

public class PNServices {
	public PNServices() {
		
	}
	public String exportPartNumber(String originalPN, int location) {
		return originalPN.trim().split("-")[location];
	}
	
	public static void main(String[] args) {
		PNServices sample = new PNServices();
		System.out.println(sample.exportPartNumber("CMP-0C220"	, 1));
	}
}
