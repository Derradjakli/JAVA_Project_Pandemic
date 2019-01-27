package fr.dauphine.ja.pandemiage.common;

public class UnsupportedOperationException extends Exception {
	private static final long serialVersionUID = 5818612515970836887L;

	public UnsupportedOperationException() {
		this("Unspecified reason.");
	}
	
	public UnsupportedOperationException(String msg) {
		super(msg);
	}

}
