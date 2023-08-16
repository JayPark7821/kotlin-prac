package kotlinjava.jvmstatic;

import kotlinjava.jvmstatic.HelloClass;
import kotlinjava.jvmstatic.HiObject;

/**
 * JavaStaticExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/16
 */
public class JavaStaticExample {
	public static void main(String[] args) {
		// final String hello = HelloClass.Companion.hello();
		final String hello = HelloClass.hello();
		final String hi = HiObject.INSTANCE.hi();
	}
}
