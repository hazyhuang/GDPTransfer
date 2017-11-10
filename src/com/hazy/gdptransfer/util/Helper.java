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
package com.hazy.gdptransfer.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.hazy.common.HazyException;
import com.hazy.common.HazyUtil;
import com.hazy.pool.DataBasePoolFactory;
/**
 * 
 * @author Hua.Huang
 */
public class Helper {


	public static Connection getConnection() throws SQLException {
		String fullpath= Helper.class.getResource("/").getPath()+"dbcp-config.properties";
		Connection con2=DataBasePoolFactory.getInstance(fullpath).getConnection();
		return con2;

	}
	
	public static Properties loadConfig() throws HazyException {
		String fullPath= Helper.class.getResource("/").getPath()+"conf.properties";
		
		return HazyUtil.getInstance().loadPropertiesUTF8(fullPath);
	}
}
