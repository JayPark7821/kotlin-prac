package kotlinjava.extensions;

/**
 * ExtensionExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/16
 */
public class ExtensionExample {
	public static void main(String[] args) {
		final char first = MyExtensionsKt.first("ABCD");
		System.out.println("first = " + first);

		final String addFirst = MyExtensionsKt.addFirst("ABCD", 'Z');
		System.out.println("addFirst = " + addFirst);

	}
}
