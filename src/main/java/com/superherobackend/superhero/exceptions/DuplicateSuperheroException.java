package com.superherobackend.superhero.exceptions;

public class DuplicateSuperheroException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public String message;

	public DuplicateSuperheroException(String message) {
		super(message);
	}

	public DuplicateSuperheroException() {
		super("Schedule conflict.");
	}
}
