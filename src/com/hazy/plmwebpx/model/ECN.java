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

import net.sf.json.JSONObject;
/**
 * 
 * @author Hua.Huang
 */
public class ECN {
	private Object ObjectID;
	private String Number;
	public Object getObjectID() {
		return ObjectID;
	}
	public void setObjectID(Object objectID) {
		ObjectID = objectID;
	}
	public String getNumber() {
		return Number;
	}
	public void setNumber(String number) {
		Number = number;
	}
	
	public JSONObject toJSON() {
	JSONObject jObj=new JSONObject();
	jObj.put("index", ObjectID);
	jObj.put("value", Number);
	return jObj;
	}
	

}
