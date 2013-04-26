package com.kodova.carpenter.progression;

import java.util.Calendar;
import java.util.Date;

public class Sequencer {

	/**
	 * Creates a sequence that starts at 0 and increments by on on every call.
	 * The first call will return 0
	 * @return
	 */
	public static Sequence<Integer> createInt() {
		return createInt(0, 1);
	}

	/**
	 * Creates a sequence that starts with the given number and increments by the number given.
	 * @param start The starting number, will be the number returned the first time next is called
	 * @param step The size of the step each time get is called. To get it to decrement pass in a negative number
	 * @return
	 */
	public static Sequence<Integer> createInt(int start, int step) {
		return new Sequence<Integer>(new IntegerMovable(step, start));
	}

	/**
	 * Creates a date sequencer that starts now and increments by on day each time called
	 * @return
	 */
	public static Sequence<Date> createDate(){
		return new Sequence<Date>(new DateMovable());
	}

	/**
	 * Creates a date sequence that starts now and increments by the given field and size
	 * @param field Calendar constant representing which field to work with
	 * @param step the size of the step to take
	 * @return
	 */
	public static Sequence<Date> createDate(int field, int step){
		return new Sequence<Date>(new DateMovable(field, step));
	}

	/**
	 * Creates a date sequence that starts with the given time and increments by the given field and size
	 * @param start The start time for the calendar
	 * @param field Calendar constant representing which field to work with
	 * @param step the size of the step to take
	 * @return
	 */
	public static Sequence<Date> createDate(Calendar start, int field, int step){
		return new Sequence<Date>(new DateMovable(start, field, step));
	}
}
