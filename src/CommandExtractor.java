
import java.io.*;

import parser.ParseException;

public class CommandExtractor {

	private InputStreamReader isr;
	
	StringBuilder sb;
	private char curr, prev;
	private boolean whitespaceOnly, inLineComment, inBlockComment;
	
	public CommandExtractor(InputStream is) {
		isr = new InputStreamReader(is);
	}
	
	public void close() {
		try {
			isr.close();
		} catch (IOException e){
		}
	}
	
	// returns true if eof was found
	private boolean readUntil(char c) throws IOException {
		curr = 0;
		prev = 0;
		whitespaceOnly = true;
		inLineComment = false;
		inBlockComment = false;
		while (true) {
			
			int intChar = isr.read();
			if (intChar==-1)
				return true;
			
			prev = curr;
			curr = (char)intChar;
						
			// if we're inside comments, check if they ended
			if (inLineComment) {
				if (curr=='\n') {
					inLineComment = false;	// allow \n to be appended normally
				} else {
					curr = 0;	// prevent this comment char from being part of anything else
					continue;
				}
			}
			else if (inBlockComment) {
				if (prev=='*' && curr=='/') {
					inBlockComment = false;
					curr = 0;	// prevent this comment char from being part of anything else
				}
				continue;
			}
			
			// if prev char could be start of comments, check curr char
			// if curr char does not start comments, write the prev char
			// since it was skipped over
			if (prev=='-') {
				if (curr=='-') {
					inLineComment = true;
					curr = 0; // prevent this comment char from being part of anything else
					continue;
				} else {
					sb.append('-');
					whitespaceOnly = false;
				}
			} else if (prev=='/') {
				if (curr=='*') {
					inBlockComment = true;
					sb.append(' ');
					curr = 0; // prevent this comment char from being part of anything else
					continue;
				} else {
					sb.append('/');
					whitespaceOnly = false;
				}
			}
			
			// if curr char could be start of comments, skip over them.
			// they'll be appended next iteration if they weren't start of comments
			if (curr=='/' || curr=='-') {
				continue;
			}
			
			sb.append(curr);
			// check if we've found a non-whitespace character (not including comments)
			if (!Character.isWhitespace(curr)) {
				whitespaceOnly = false;
			}
			
			if (curr==c)
				break;
		}
		return false;
	}
	
	
	
	// returns null if EOF has been reached
	public String readCommand() throws IOException, ParseException {
		
		sb = new StringBuilder();
		boolean eofFound;
		
		// read until the next ; or eof
		whitespaceOnly = true;
		eofFound = readUntil(';');
		if (eofFound) {
			if (whitespaceOnly) {
				return null;
			} else {
				throw new ParseException("Encountered EOF before command was terminated.");
			}
		}
		
		// read until the next newline or eof
		whitespaceOnly = true;
		eofFound = readUntil('\n');
		if (whitespaceOnly) {
			return sb.toString();
		} else {
			throw new ParseException("Encountered non-whitespace characters after \";\" in same line.");
		}
	}
}
