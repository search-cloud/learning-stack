import org.junit.Test;
import org.junit.runner.JUnitCore;

import static io.vincent.compiler.c.utils.TextUtils.dumpString;
import static io.vincent.compiler.c.utils.TextUtils.isPrintable;
import static org.junit.Assert.*;


public class TestTextUtils {
    static public void main(String[] args) {
        JUnitCore.main(TestTextUtils.class.getName());
    }

    @Test public void test_dumpString() {
        assertEquals("dumpString #1", "\"\"", dumpString(""));
        assertEquals("dumpString #2", "\"x\"", dumpString("x"));
        assertEquals("dumpString #3", "\"xx\"", dumpString("xx"));
        assertEquals("dumpString #4", "\"x\\\"x\"", dumpString("x\"x"));
        assertEquals("dumpString #5", "\"x\\nx\"", dumpString("x\nx"));
    }

    @Test public void test_isPrintable() {
	    assertTrue(isPrintable('a'));
	    assertTrue(isPrintable('A'));
	    assertTrue(isPrintable(' '));
	    assertFalse(isPrintable('\t'));
	    assertFalse(isPrintable('\r'));
	    assertFalse(isPrintable('\n'));
	    assertFalse(isPrintable('\0'));
    }
}
