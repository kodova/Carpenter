package com.kodova.carpenter.progression;

import java.util.concurrent.atomic.AtomicInteger;

public class IntegerMovable implements Moveable<Integer> {

	private final int step;
	private AtomicInteger currentValue;

	public IntegerMovable(int step){
		this(step, 0);
	}

	public IntegerMovable(int step, int start){
		this.step = step;
		int initialValue = start + (step * -1);
		currentValue = new AtomicInteger(initialValue);
	}

	@Override
	public Integer next() {
		return currentValue.addAndGet(step);
	}
}
