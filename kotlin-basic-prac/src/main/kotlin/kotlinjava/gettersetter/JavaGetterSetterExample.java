package kotlinjava.gettersetter;

import java.time.LocalDate;

import kotlinjava.gettersetter.Student;

/**
 * JavaGetterSetterExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/16
 */
public class JavaGetterSetterExample {
	public static void main(String[] args) {
		Student student = new Student();

		student.name = "steve";
		student.setBirthDate(LocalDate.of(1918, 7, 4));

		System.out.println("student.getName() = " + student.name);
		System.out.println("student.getBirthDate() = " + student.getBirthDate());

		// student.setAge(10);
		System.out.println("student.getAge() = " + student.getAge());

		// student.setGrade("");
		System.out.println("student.getGrade() = " + student.getGrade());

		student.changeGrade("A");
		System.out.println("student.getGrade() = " + student.getGrade());


	}
}
