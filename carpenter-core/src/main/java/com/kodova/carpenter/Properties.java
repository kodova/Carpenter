package com.kodova.carpenter;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Properties {

	private final Map<String, Object> overrides = new HashMap<String, Object>();


	public Properties set(String key, Object value){
		overrides.put(key, value);
		return this;
	}

	public Map<String, Object> getOverrides(){
		return overrides;
	}
}
