package com.kodova.carpenter.progression;

public class Sequence<T> {

	private final Moveable<T> moveable;

	public Sequence(Moveable<T> moveable){
		this.moveable = moveable;
	}

	public T get(){
		return moveable.next();
	}

	public String get(String format){
		return String.format(format, moveable.next());
	}

}
