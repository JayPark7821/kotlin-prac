package kotlinjava.jvmstatic;

import kotlinjava.jvmstatic.JvmFieldClass;
import kotlinjava.jvmstatic.JvmFieldObject;

/**
 * JvmFieldExample
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/16
 */
public class JvmFieldExample {

	public static void main(String[] args) {
		// JvmFieldClass.Companion.getId();
		final int id = JvmFieldClass.id;

		JvmFieldObject.INSTANCE.getName();

		final int code = JvmFieldClass.CODE;
		final String finalName = JvmFieldObject.FINAL_NAME;
	}
}
