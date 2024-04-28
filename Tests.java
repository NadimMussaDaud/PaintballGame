/**
 * @author POO team 2023/24
 */


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

/**
 * The Tests class specifies a set of tests implemented using the JUnit tool. 
 * These tests use Mooshak test files as input and generate the expected result of 
 * running these tests as output. The class is implemented for testing the 
 * Paintball project.
 * To use this class you need to include the JUnit 4 library in your 
 * runtime environment.
 */
public class Tests {
	/**
	 * Use the following lines to specify the tests you are going to carry out. 
	 * For example, the expected result for the test input01.txt is output01.txt. 
	 * You don't have to do anything else in the rest of the class. 
	 * Just set up this sequence of tests!
	 */
	
	/**
	 * Tests commands game, help, quit.
	 */
	@Test public void test01() { test("input01.txt","output01.txt"); }
	
	/**
	 * Tests commands status, game, help, quit.
	 */
	@Test public void test02() { test("input02.txt","output02.txt"); }
	
	/**
	 * Tests commands bunkers, players, game, help, quit.
	 */
	@Test public void test03() { test("input03.txt","output03.txt"); }
	
	/**
	 * Tests commands map, status, game, help, quit.
	 */
	@Test public void test04() { test("input04.txt","output04.txt"); }
	
	/**
	 * Tests commands create, game, help, quit.
	 */
	@Test public void test05() { test("input05.txt","output05.txt"); }
	
	/**
	 * Tests commands create, map, status, bunkers, players, game, help, quit.
	 */
	@Test public void test06() { test("input06.txt","output06.txt"); }
	
	/**
	 * Tests commands move, create, players, game, help, quit.
	 */
	@Test public void test07() { test("input07.txt","output07.txt"); }
	
	/**
	 * Tests commands move, create, players, bunkers, map, game, help, quit.
	 */
	@Test public void test08() { test("input08.txt","output08.txt"); }
	
	/**
	 * Tests commands move, create, players, bunkers, status, map, game, help, quit.
	 */
	@Test public void test09() { test("input09.txt","output09.txt"); }
	
	/**
	 * Tests commands move, create, players, bunkers, status, map, game, help, quit.
	 */
	@Test public void test10() { test("input10.txt","output10.txt"); }
	
	/**
	 * Tests commands move, create, players, bunkers, status, map, game, help, quit.
	 */
	@Test public void test11() { test("input11.txt","output11.txt"); }
	
	/**
	 * Tests commands move, create, players, bunkers, status, map, game, help, quit.
	 */
	@Test public void test12() { test("input12.txt","output12.txt"); }
	
	/**
	 * Tests commands attack (red players), move, create, players, bunkers, status, map, 
	 * game, help, quit.
	 */
	@Test public void test13() { test("input13.txt","output13.txt"); }
	
	/**
	 * Tests commands attack (blue players), move, create, players, bunkers, status, map, 
	 * game, help, quit.
	 */
	@Test public void test14() { test("input14.txt","output14.txt"); }
	
	/**
	 * Tests commands attack (green players), move, create, players, bunkers, status, map, 
	 * game, help, quit.
	 */
	@Test public void test15() { test("input15.txt","output15.txt"); }
	
	/**
	 * Tests all commands.
	 */
	@Test public void test16() { test("input16.txt","output16.txt"); }
	
	private static final File BASE = new File("tests");

	private PrintStream consoleStream;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setup() {
		consoleStream = System.out;
		System.setOut(new PrintStream(outContent));
	}

	public void test(String intput, String output) {
		test(new File(BASE, intput), new File(BASE, output));
	}

	public void test(File input, File output) {
		consoleStream.println("Testing!");
		consoleStream.println("Input: " + input.getAbsolutePath());
		consoleStream.println("Output: " + output.getAbsolutePath());

		String fullInput = "", fullOutput = "";
		try {
			fullInput = new String(Files.readAllBytes(input.toPath()));
			fullOutput = new String(Files.readAllBytes(output.toPath()));
			consoleStream.println("INPUT ============");
			consoleStream.println(new String(fullInput));
			consoleStream.println("OUTPUT ESPERADO =============");
			consoleStream.println(new String(fullOutput));
			consoleStream.println("OUTPUT =============");
		} catch(Exception e) {
			e.printStackTrace();
			fail("Erro a ler o ficheiro");
		}

		try {
			Locale.setDefault(Locale.US);
			System.setIn(new FileInputStream(input));
			Class<?> mainClass = Class.forName("Main");
			mainClass.getMethod("main", String[].class).invoke(null, new Object[] { new String[0] });
		} catch (Exception e) {
			e.printStackTrace();
			fail("Erro no programa");
		} finally {
			byte[] outPrintBytes = outContent.toByteArray();
			consoleStream.println(new String(outPrintBytes));

			assertEquals(removeCarriages(fullOutput), removeCarriages(new String(outContent.toByteArray())));
		}
	}

	private static String removeCarriages(String s) {
		return s.replaceAll("\r\n", "\n");
	}

}