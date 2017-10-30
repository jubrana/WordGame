package org.word.game.vocabulary;

import java.util.Collection;

import static org.word.game.Alphabet.LOWERCASE;


public class Vocabulary {
	
	private boolean isWord = false;

	private Vocabulary[] children = new Vocabulary[LOWERCASE.size()];

	private int numChildren = 0;
	
	public Vocabulary() {
	}
	
	public Vocabulary(Collection<String> words) {
		for (String w:words) {
			add(w);
		}
	}
	
	public boolean add(String s) {
		char first = s.charAt(0);
		int index = LOWERCASE.getIndex(first);
		if (index < 0) {
			System.out.println("uf");
		}
		Vocabulary child = children[index];
		if (child == null) {
			child = new Vocabulary();
			children[index] = child;
			numChildren++;
		}
		if (s.length() == 1) {
			if (child.isWord) {
				// The word is already in the trie
				return false;
			}
			child.isWord = true;
			return true;
		} else {
			// Recurse into sub-trie
			return child.add(s.substring(1));
		}
	}


	/**
	 * Searches for a word represented as indices in this vocabulary trie
	 */
	public boolean contains(byte[] indices) {

		Vocabulary n = getNode(indices);
		return n != null && n.isWord;
	}
	
	/**
	 * Searches for a string prefix represented as indices in this vocabulary trie
	 */
	public boolean isPrefix(byte[] indices) {

		Vocabulary n = getNode(indices);
		return n != null && n.numChildren > 0;
	}

	/**
	 * Returns the node corresponding to the string represented as indices
	 */
	public Vocabulary getNode(byte[] indices) {

		Vocabulary node = this;

		for (int i = 0; i < indices.length; i++) {

			int index = indices[i];
			Vocabulary child = node.children[index];

			if (child == null) {
				// There is no such word
				return null;
			}
			node=child;
		}
		return node;
	}
	
	public boolean isWord() {
		return isWord;
	}
	
	public boolean hasChildren() {
		return numChildren > 0;
	}
}