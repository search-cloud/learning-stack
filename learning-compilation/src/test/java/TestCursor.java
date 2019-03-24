import io.vincent.compiler.c.utils.Cursor;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestCursor {
    static public void main(String[] args) {
        JUnitCore.main(TestCursor.class.getName());
    }

    @Test public void test_hasNext_0() {
        List<Integer> list = new ArrayList<>();
        Cursor<Integer> it = new Cursor<>(list);
	    assertFalse("[].hasNext #1", it.hasNext());
	    assertFalse("[].hasNext #2", it.hasNext());
    }

    @Test public void test_hasNext_1() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        Cursor<Integer> it = new Cursor<>(list);
	    assertTrue("[1].hasNext #1", it.hasNext());
	    assertTrue("[1].hasNext #2", it.hasNext());
        it.next();
	    assertFalse("[].hasNext #1", it.hasNext());
	    assertFalse("[].hasNext #2", it.hasNext());
    }

    @Test public void test_hasNext_2() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Cursor<Integer> it = new Cursor<>(list);
	    assertTrue("[1,2].hasNext #1", it.hasNext());
	    assertTrue("[1,2].hasNext #2", it.hasNext());
        it.next();
	    assertTrue("[2].hasNext #1", it.hasNext());
	    assertTrue("[2].hasNext #2", it.hasNext());
        it.next();
	    assertFalse("[].hasNext #1", it.hasNext());
	    assertFalse("[].hasNext #2", it.hasNext());
    }

    @Test public void test_next() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Cursor<Integer> it = new Cursor<>(list);
        assertEquals("[1,2].next", 1, (Object)it.next());
        assertEquals("[2].next", 2, (Object)it.next());
    }

    @Test public void test_clone() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Cursor<Integer> it = new Cursor<>(list);
        it.next();
        Cursor<Integer> it2 = it.clone();
	    assertTrue("clone+hasNext #1", it2.hasNext());
        assertEquals("clone+next #1", 2, (Object)it2.next());
    }
}
