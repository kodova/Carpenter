package com.kodova.carpenter.core;

import java.beans.PropertyDescriptor;

public class OverrideException extends RuntimeException {
	public OverrideException(PropertyDescriptor propertyDescriptor, Throwable t) {
		super(String.format("Failed to override the value of %s:", propertyDescriptor.getName()), t);
	}


	public OverrideException(String message, Throwable t) {
		super(message, t);
	}
}
