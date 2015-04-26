package org.springframework.social.shell.navigation;

/**
 * CLI navigation enum  
 *
 * @author robin
 */
public enum StepNavigation {
	NEW_LINE('\n'),
	RETURN('\r'),
	NEXT('n'), 
	PREVIOUS('p'), 
	QUIT('q');
	
	private char inputChar;
	
	private StepNavigation(char inputChar) {
		this.inputChar = inputChar;	
	}
	
	public char getInputChar() {
		return inputChar;
	}
	
	public static String inputPrompt() {
		return "Next (" + NEXT.inputChar + "), Prev (" + PREVIOUS.inputChar + "), or Quit (" + QUIT.inputChar + "):";
	}
}