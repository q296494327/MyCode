package com.itheima.quickindex;

import android.R.fraction;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 将汉字转换成拼音的工具
 * @author xiemiao
 */
public class PinYinUtil {
    /**
     * 将汉字转换成拼音，频繁调用可能会影响性能
     * @param chinese 请输入要转换的汉字
     * @return
     */
    public static String getPinYin(String chinese) {
	//1先将空格去掉
	chinese=chinese.replace(" ", "");
	//2因为类库只能对单个汉字转拼音，所以先将字符串转换成字符数组
	char[] charArray = chinese.toCharArray();
	//3创建一个拼接结果的StringBuilder
	StringBuilder sb=new StringBuilder();
	//4设置转换汉语拼音的格式
	HanyuPinyinOutputFormat format=new HanyuPinyinOutputFormat();
	format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写
	format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//不要音标
	
	for (int i = 0; i < charArray.length; i++) {
	    //5判断是不是汉字,汉字有2个字节，一个字节是-128~127,所以汉字的字节肯定大于127
	    if (charArray[i]>127) {
		try {
		    //6可能是汉字,使用类库的拼音工具进行转换拼音，之所以是数组，是因为有多音字
		    String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i], format);
		    if (hanyuPinyinStringArray!=null) {
			sb.append(hanyuPinyinStringArray[0]);
		    }
		} catch (BadHanyuPinyinOutputFormatCombination e) {
		    e.printStackTrace();
		}
		
	    }else {
		//不是汉字，直接拼接上去
		sb.append(charArray[i]);
	    }
	}
	return sb.toString();
    }
}
