package org.thunlp.mt.wa.phrase;

public class BilingualPhrase {
	/**
	 * source language phrase, words are seperated by space
	 */
	protected String source;

	/**
	 * target language phrase
	 */
	protected String target;
	
	// prepared for json
	protected BilingualPhrase() {}
	
	public BilingualPhrase(String source, String target) {
		this.source = source;
		this.target = target;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		else if (o instanceof BilingualPhrase) {
			BilingualPhrase phrase = (BilingualPhrase) o;
			return (source == phrase.source || (source != null && source.equals(phrase.source)))
				&& (target == phrase.target || (target != null && target.equals(phrase.target)));
		} else
			return false;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = result * 31 + source == null ? 0 : source.hashCode();
		result = result * 31 + target == null ? 0 : target.hashCode();
		return result;
	}
}
