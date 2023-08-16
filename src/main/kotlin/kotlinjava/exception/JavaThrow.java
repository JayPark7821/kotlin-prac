package kotlinjava.exception;

import java.io.IOException;

/**
 * JavaThrow
 *
 * @author jaypark
 * @version 1.0.0
 * @since 2023/08/16
 */
public class JavaThrow {

	public void throwIOException() throws IOException{
		throw new IOException("Checked exception IO Exception");
	}

	public static void main(String[] args) {
		final JavaThrow javaThrow = new JavaThrow();
		try{
			javaThrow.throwIOException();
		} catch (IOException e) {
			e.printStackTrace();
		}

		final KotlinThrow kotlinThrow = new KotlinThrow();
		kotlinThrow.throwIOException();
	}
}
