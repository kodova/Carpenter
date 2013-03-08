package com.kodova.carpenter;

public class ConstructionContext {

	int buildCount = 0;
	int createCount = 0;

	public ConstructionContext() {
	}

	public void startBuild() {
		buildCount++;
	}

	public void endBuild() {
		buildCount--;
	}

	public void startCreate() {
		createCount++;
	}

	public void endCreate() {
		createCount--;
	}

	public boolean isCreate(){
		return createCount >= buildCount;
	}

	public <T> T get(Class<T> type, Properties properties) {
		if(isCreate()){
			return Carpenter.create(type, properties);
		} else {
			return Carpenter.build(type, properties);
		}
	}

	public <T> T get(Class<T> type) {
		if(isCreate()){
			return Carpenter.create(type);
		} else {
			return Carpenter.build(type);
		}
	}
}
