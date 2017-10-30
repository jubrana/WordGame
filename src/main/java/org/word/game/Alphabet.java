package org.word.game;

public abstract class Alphabet {

	public final String alphabet;


	public final static Alphabet LOWERCASE = new Alphabet("abcdefghijklmnopqrstuvwxyz\u010D\u0107\u0111\u0161\u017E") {

		private final int charOffest = Character.getNumericValue('a');


		public byte getIndex(char c) {

			switch (c) {

				case '\u010D':
					return (byte) (getIndexAsciiAlphabet('z') + 1);

				case '\u0107':
					return (byte) (getIndexAsciiAlphabet('z') + 2);

				case '\u0111':
					return (byte) (getIndexAsciiAlphabet('z') + 3);

				case '\u0161':
					return (byte) (getIndexAsciiAlphabet('z') + 4);

				case '\u017E':
					return (byte) (getIndexAsciiAlphabet('z') + 5);

			}

			return getIndexAsciiAlphabet(c);
		}

		public byte getIndexAsciiAlphabet(char c) {

			return (byte) (Character.getNumericValue(c) - charOffest);
		}
	}; 
	
	
	public Alphabet(String alphabet) {
		this.alphabet = alphabet;
	}

	
	public byte getIndex(char c) {
		return (byte) alphabet.indexOf(c);
	};


	public int size() {
		return alphabet.length();
	}
}
