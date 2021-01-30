package test.state.machine.guard;

import org.junit.jupiter.api.Test;
import state.machine.guard.And;
import state.machine.guard.Guard;
import test.state.machine.TestData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AndGuardTest {

    @Test
    void testCheckBothTrue() {
        final TestData td = new TestData(5.0);
        final Guard<TestData> gA = new TestGuardA(2.0);
        final Guard<TestData> gB = new TestGuardB(10.0);
        final Guard<TestData> gAnd = new And<>(gA, gB);

        assertTrue(gAnd.check(null, td));

        td.setValue(20.0);
        assertFalse(gAnd.check(null, td));

        assertEquals("[x > 2.0 and x < 10.0]",gAnd.getDescription());
    }

    @Test
    void testCheckOneTrue() {
        final TestData td = new TestData(5.0);
        final Guard<TestData> gA = new TestGuardA(6.0);
        final Guard<TestData> gB = new TestGuardB(10.0);
        final Guard<TestData> gAnd = new And<>(gA, gB);

        assertFalse(gAnd.check(null, td));

        td.setValue(8.0);
        assertTrue(gAnd.check(null, td));
    }
}