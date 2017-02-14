package com.google.demo3.observer;

/**
 * @author  Administrator
 * @time 	2015-7-20 上午10:49:27
 * @des	TODO
 *
 * @version $Rev$
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes TODO
 */
public class Test {
	public static void main(String[] args) {
		Teacher teacher = new Teacher();
		Student student = new Student();

		teacher.addObserver(student);

		teacher.pulishMessage("2015年7月20日10:50:34 xx同学,拿到了一个12k的offer");
	}
}
