package demo;

import java.util.ArrayList;
import java.util.List;

public class Demo {
	public static void main(String[] args) {
		try {
//			List list = new ArrayList<>();
//			list.get(3);
			String a = null;
			boolean b = a.equals("1");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e);
		}
		int a = Integer.parseInt(null);
		System.out.println(a);
	}
}
