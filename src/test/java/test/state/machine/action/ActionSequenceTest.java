package test.state.machine.action;

import org.junit.jupiter.api.Test;
import state.machine.action.Action;
import state.machine.action.ActionSequence;
import test.state.machine.TestData;
import test.state.machine.action.TestActionA;
import test.state.machine.action.TestActionB;

import static org.junit.jupiter.api.Assertions.*;

class ActionSequenceTest {

    @Test
    void testActionSequence() {
        final TestData td = new TestData(3.0);
        final Action<TestData> aA = new TestActionA();
        final Action<TestData> aB = new TestActionB();

        final ActionSequence<TestData> aSeq = new ActionSequence<>(aA, aB);

        aSeq.execute(null, null, td);

        assertEquals(60.0,td.getValue());
    }
}