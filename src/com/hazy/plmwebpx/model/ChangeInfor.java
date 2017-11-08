package com.hazy.plmwebpx.model;

import java.util.Collection;

import net.sf.json.JSONArray;

public class ChangeInfor {
	private String changeNumber;
	private String status;
	private Collection<AgileUser> reviewers;

	public ChangeInfor(String chgNum) {
		this.changeNumber = chgNum;
	}

	public String getChangeNumber() {
		return changeNumber;
	}

	public void setChangeNumber(String changeNumber) {
		this.changeNumber = changeNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Collection<AgileUser> getReviewers() {
		return reviewers;
	}

	public void setReviewers(Collection<AgileUser> reviewers) {
		this.reviewers = reviewers;
	}

	public JSONArray toJSONReviewers() {
		JSONArray array = new JSONArray();

		for (AgileUser user : reviewers) {
			array.add(user.toJSON());
		}
		return array;
	}

}
