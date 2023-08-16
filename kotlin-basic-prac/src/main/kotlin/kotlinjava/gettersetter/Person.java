package kotlinjava.gettersetter;

import java.util.UUID;

/**
 * Person
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/16
 */
public class Person {

	private String name;
	private int age;
	private String address;

	public String getUUID(){
		return UUID.randomUUID().toString();
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(final int age) {
		this.age = age;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String myAddress(){
		return this.address;
	}
}
