package com.itheima.quickindex;

public class Friend implements Comparable<Friend>{
    public String name;
    public String pinyin;
    public Friend(String name) {
	super();
	this.name = name;
	//因为汉字转拼音会消耗性能，所以就只在构造方法掉一次,赋值给pinyin
	pinyin=PinYinUtil.getPinYin(name);
    }

    @Override
    public int compareTo(Friend another) {
	//此处实现的比较方法，就直接用拼音进行比较
	return pinyin.compareTo(another.pinyin);
    }

}
