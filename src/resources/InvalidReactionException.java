package resources;

public class InvalidReactionException extends Exception {

	public InvalidReactionException(String string, String input) {
		super(string + " at reaction " + input);
	}

}
