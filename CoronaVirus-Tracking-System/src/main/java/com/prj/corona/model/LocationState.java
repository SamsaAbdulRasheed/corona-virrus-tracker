package com.prj.corona.model;

public class LocationState {

	private String state;
	private String country;
	private int lastTotalDeath;
	private int differFromPrevay;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLastTotalDeath() {
		return lastTotalDeath;
	}
	public void setLastTotalDeath(int lastTotalDeath) {
		this.lastTotalDeath = lastTotalDeath;
	}
	public int getDifferFromPrevay() {
		return differFromPrevay;
	}
	public void setDifferFromPrevay(int differFromPrevay) {
		this.differFromPrevay = differFromPrevay;
	}
	@Override
	public String toString() {
		return "LocationState [state=" + state + ", country=" + country + ", lastTotalDeath=" + lastTotalDeath
				+ ", differFromPrevay=" + differFromPrevay + "]";
	}
	
}
