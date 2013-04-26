package com.kodova.carpenter.progression;

import java.util.Calendar;
import java.util.Date;

public class DateMovable implements Moveable<Date> {

	Calendar calendar = Calendar.getInstance();
	int field = Calendar.DATE;
	int step = 1;

	public DateMovable(Calendar calendar, int field, int step) {
		this.calendar = calendar;
		this.field = field;
		this.step = step;

		calendar.add(field, step * -1);
	}

	public DateMovable(int field, int step) {
		this(Calendar.getInstance(), field, step);
	}

	public DateMovable() { }

	@Override
	public  Date next() {
		synchronized (this){
			calendar.add(field, step);
			return calendar.getTime();
		}
	}
}
