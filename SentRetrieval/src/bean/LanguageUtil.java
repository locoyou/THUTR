package bean;

import java.util.regex.Pattern;

/**
 *@author liuqi
 *2011-11-26下午04:23:36
 *
 */
public class LanguageUtil {
	public static Pattern EngPat = Pattern.compile("\\(*[a-zA-Z\\s,?:&;’!-]+\\)*");
	public static String removeEngInChi(String chiStr){
		return EngPat.matcher(chiStr).replaceAll("");
	}
	public static Pattern ChiPat = Pattern.compile("（*\\(*[\u4e00-\u9fa5，。？：；！‘”“’]+\\)*）*");
	public static String removeChiInEng(String engStr){
		return ChiPat.matcher(engStr).replaceAll("");
	}
}
