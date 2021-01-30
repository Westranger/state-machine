package test.state.machine.guard;

import org.junit.jupiter.api.Test;
import state.machine.guard.Or;
import state.machine.guard.Guard;
import test.state.machine.TestData;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class OrGuardTest {

    @Test
    void testCheckBothTrue() {
        final TestData td = new TestData(5.0);
        final Guard<TestData> gA = new TestGuardA(15.0);
        final Guard<TestData> gB = new TestGuardB(10.0);
        final Guard<TestData> gOr = new Or<>(gA, gB);

        assertTrue(gOr.check(null, td));

        td.setValue(12.0);
        assertFalse(gOr.check(null, td));

        assertEquals("[x > 15.0 or x < 10.0]",gOr.getDescription());
    }
}