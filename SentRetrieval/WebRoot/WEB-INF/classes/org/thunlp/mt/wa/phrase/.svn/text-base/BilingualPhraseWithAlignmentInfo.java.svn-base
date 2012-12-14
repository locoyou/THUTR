package org.thunlp.mt.wa.phrase;

public class BilingualPhraseWithAlignmentInfo extends BilingualPhrase {
	/**
	 * example "0-1 1-2"
	 */
	protected String alignmentInfo;
	
	/**
	 * prepared for json
	 */
	protected BilingualPhraseWithAlignmentInfo() {
		super();
		
	}
	
	public BilingualPhraseWithAlignmentInfo(String source, String target, String alignmentInfo) {
		super(source, target);
		this.alignmentInfo = alignmentInfo;		
	}
	
	/**
	 * index starts from 0
	 * @return
	 */
	public boolean isAligned(int sourceIndex, int targetIndex) {
		return alignmentInfo.matches("(^| )" + sourceIndex + "-" + targetIndex + "($| )");
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = result * 31 + alignmentInfo == null ? 0 : alignmentInfo.hashCode();
		result = result * 31 + super.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		else if (o instanceof BilingualPhraseWithAlignmentInfo) {
			BilingualPhraseWithAlignmentInfo rop = (BilingualPhraseWithAlignmentInfo)o;
			return (source == rop.source || (source != null && source.equals(rop.source)))
				&& (target == rop.target || (target != null && target.equals(rop.target)))
				&& (alignmentInfo == rop.alignmentInfo || 
						(alignmentInfo != null && alignmentInfo.equals(rop.alignmentInfo)));
		} else
			return false;
	}
	
	
	public String getAlignmentInfo() {
		return alignmentInfo;
	}

	public void setAlignmentInfo(String alignmentInfo) {
		this.alignmentInfo = alignmentInfo;
	}
	
	public static void main(String[] args) {
		BilingualPhrase phrase = new BilingualPhrase("source", "target");
		BilingualPhraseWithAlignmentInfo phrase2 = new BilingualPhraseWithAlignmentInfo("source", "target", "1-2");
		System.out.println(phrase2.equals(phrase));
		System.out.println(null == null);
	}
}
