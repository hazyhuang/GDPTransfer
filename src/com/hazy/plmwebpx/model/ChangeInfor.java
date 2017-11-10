/*
 * Copyright 2012 - 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hazy.plmwebpx.model;

import java.util.Collection;

import net.sf.json.JSONArray;
/**
 * 
 * @author Hua.Huang
 */
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
