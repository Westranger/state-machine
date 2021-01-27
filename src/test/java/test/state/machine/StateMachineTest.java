package test.state.machine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import state.machine.Event;
import state.machine.State;
import state.machine.StateMachine;
import state.machine.action.Action;
import state.machine.guard.Guard;
import test.state.machine.action.TestActionA;
import test.state.machine.action.TestActionB;
import test.state.machine.events.TestEventA;
import test.state.machine.events.TestEventB;
import test.state.machine.events.TestEventC;
import test.state.machine.guard.TestGuardA;
import test.state.machine.guard.TestGuardB;
import test.state.machine.states.TestStateA;
import test.state.machine.states.TestStateB;
import test.state.machine.states.TestStateC;

import static org.junit.jupiter.api.Assertions.*;

class StateMachineTest {

    private StateMachine<TestData> fsm;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSimpleStateTransition() {
        final TestData td = new TestData(1.0);
        final State<TestData> sA = new TestStateA();
        final State<TestData> sB = new TestStateB();
        Event eA = new TestEventA();
        this.fsm = new StateMachine<>();

        this.fsm.addAction(TestEventA.class, sA, sB, null, null);
        this.fsm.setInitialState(sA, td);
        this.fsm.setTerminalState(sB);

        assertTrue(this.fsm.getCurrentState() instanceof TestStateA);
        this.fsm.cycle(eA);
        assertTrue(this.fsm.getCurrentState() instanceof TestStateB);

        assertTrue(fsm.hasTerminated());
        assertEquals(2.0 / 3.0, td.getValue(), 1e-6);
    }

    @Test
    void testSimpleStateTransitionIrrelevantEvent() {
        final TestData td = new TestData(1.0);
        final State<TestData> sA = new TestStateA();
        final State<TestData> sB = new TestStateB();
        Event eC = new TestEventC();
        this.fsm = new StateMachine<>();

        this.fsm.addAction(TestEventA.class, sA, sB, null, null);
        this.fsm.setInitialState(sA, td);
        this.fsm.setTerminalState(sB);

        assertTrue(this.fsm.getCurrentState() instanceof TestStateA);
        this.fsm.cycle(eC);
        assertTrue(this.fsm.getCurrentState() instanceof TestStateA);

        assertFalse(fsm.hasTerminated());
        assertEquals(2.0, td.getValue(), 1e-6);
    }

    @Test
    void testSimpleStateTransitionWithGuard() {
        final TestData td = new TestData(-1.0);
        final State<TestData> sA = new TestStateA();
        final State<TestData> sB = new TestStateB();
        final Guard<TestData> gA = new TestGuardA(0.0);
        Event eA = new TestEventA();
        this.fsm = new StateMachine<>();

        this.fsm.addAction(TestEventA.class, sA, sB, gA, null);
        this.fsm.setInitialState(sA, td);
        this.fsm.setTerminalState(sB);

        assertTrue(this.fsm.getCurrentState() instanceof TestStateA);
        this.fsm.cycle(eA);
        assertTrue(this.fsm.getCurrentState() instanceof TestStateA);

        assertFalse(fsm.hasTerminated());
        assertEquals(-2.0, td.getValue(), 1e-6);

        td.setValue(1.0);

        assertTrue(this.fsm.getCurrentState() instanceof TestStateA);
        this.fsm.cycle(eA);
        assertTrue(this.fsm.getCurrentState() instanceof TestStateB);

        assertTrue(fsm.hasTerminated());
        assertEquals(1.0 / 3.0, td.getValue(), 1e-6);
    }

    @Test
    void testSimpleStateTransitionWithAction() {
        final TestData td = new TestData(1.0);
        final State<TestData> sA = new TestStateA();
        final State<TestData> sB = new TestStateB();
        final Action<TestData> aA = new TestActionA();
        Event eA = new TestEventA();
        this.fsm = new StateMachine<>();

        this.fsm.addAction(TestEventA.class, sA, sB, null, aA);
        this.fsm.setInitialState(sA, td);
        this.fsm.setTerminalState(sB);

        assertTrue(this.fsm.getCurrentState() instanceof TestStateA);
        this.fsm.cycle(eA);
        assertTrue(this.fsm.getCurrentState() instanceof TestStateB);

        assertTrue(fsm.hasTerminated());
        assertEquals((2.0 / 3.0) * 0.1, td.getValue(), 1e-6);
    }

    @Test
    void testSimpleStateTransitionWithActionAndGuard() {
        final TestData td = new TestData(-1.0);
        final State<TestData> sA = new TestStateA();
        final State<TestData> sB = new TestStateB();
        final Action<TestData> aA = new TestActionA();
        final Guard<TestData> gA = new TestGuardA(0.0);
        Event eA = new TestEventA();
        this.fsm = new StateMachine<>();

        this.fsm.addAction(TestEventA.class, sA, sB, gA, aA);
        this.fsm.setInitialState(sA, td);
        this.fsm.setTerminalState(sB);

        assertTrue(this.fsm.getCurrentState() instanceof TestStateA);
        this.fsm.cycle(eA);
        assertTrue(this.fsm.getCurrentState() instanceof TestStateA);

        assertFalse(fsm.hasTerminated());
        assertEquals(-2.0, td.getValue(), 1e-6);

        td.setValue(1.0);

        assertTrue(this.fsm.getCurrentState() instanceof TestStateA);
        this.fsm.cycle(eA);
        assertTrue(this.fsm.getCurrentState() instanceof TestStateB);

        assertTrue(fsm.hasTerminated());
        assertEquals((1.0 / 3.0) * 0.1, td.getValue(), 1e-6);
    }

    @Test
    void testInitialStateUnknown() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            final TestData td = new TestData(1.0);
            final State<TestData> sA = new TestStateA();
            this.fsm = new StateMachine<>();

            this.fsm.setInitialState(sA, td);
        });
    }

    @Test
    void testTerminalStateUnknown() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            final TestData td = new TestData(1.0);
            final State<TestData> sA = new TestStateA();
            this.fsm = new StateMachine<>();

            this.fsm.setInitialState(sA, td);
        });
    }

    @Test
    void testNotInitialized() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            final StateMachine fsm = new StateMachine<>();
            final Event evt = new TestEventC();

            fsm.cycle(evt);
        });
    }

    @Test
    void testUnknownTerminalState() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            final StateMachine fsm = new StateMachine<>();
            final State<TestData> sA = new TestStateA();
            final State<TestData> sB = new TestStateB();
            final State<TestData> sC = new TestStateC();

            this.fsm = new StateMachine<>();
            this.fsm.addAction(TestEventA.class, sA, sB, null, null);

            this.fsm.setTerminalState(sC);
        });
    }

    @Test
    void testWrongActionParameter() {
        final State<TestData> sA = new TestStateA();
        final State<TestData> sB = new TestStateB();

        this.fsm = new StateMachine<>();

        assertFalse(this.fsm.addAction(null, sA, sB, null, null));
        assertFalse(this.fsm.addAction(TestEventA.class, null, sB, null, null));
        assertFalse(this.fsm.addAction(TestEventA.class, sA, null, null, null));
    }

    @Test
    void testSimpleStateTransitionMultiplePathsNoActionNoGuards() {
        final TestData td = new TestData(1.0);
        final State<TestData> sA = new TestStateA();
        final State<TestData> sB = new TestStateB();
        final State<TestData> sC = new TestStateC();
        final Event eA = new TestEventA();
        final Event eB = new TestEventB();
        this.fsm = new StateMachine<>();

        this.fsm.addAction(TestEventA.class, sA, sB, null, null);
        this.fsm.addAction(TestEventB.class, sA, sC, null, null);
        this.fsm.addAction(TestEventA.class, sC, sA, null, null);

        this.fsm.setInitialState(sA, td);
        this.fsm.setTerminalState(sB);

        assertTrue(this.fsm.getCurrentState() instanceof TestStateA);
        this.fsm.cycle(eB);
        assertTrue(this.fsm.getCurrentState() instanceof TestStateC);
        this.fsm.cycle(eA);
        assertTrue(this.fsm.getCurrentState() instanceof TestStateA);
        this.fsm.cycle(eA);
        assertTrue(this.fsm.getCurrentState() instanceof TestStateB);

        assertTrue(fsm.hasTerminated());
        assertEquals(-4.0 / 3.0, td.getValue(), 1e-6);
    }

    @Test
    void testSimpleStateTransitionMultiplePathsActionAndGuards() {
        final TestData td = new TestData(-1.0);
        final State<TestData> sA = new TestStateA();
        final State<TestData> sB = new TestStateB();
        final State<TestData> sC = new TestStateC();

        final Action<TestData> aA = new TestActionA();
        final Action<TestData> aB = new TestActionB();

        final Guard<TestData> gA = new TestGuardA(0.0);
        final Guard<TestData> gB = new TestGuardB(0.0);

        final Event eA = new TestEventA();
        final Event eB = new TestEventB();
        this.fsm = new StateMachine<>();

        this.fsm.addAction(TestEventA.class, sA, sB, gA, aA);
        this.fsm.addAction(TestEventB.class, sA, sC, gB, aB);
        this.fsm.addAction(TestEventA.class, sC, sA, null, null);

        this.fsm.setInitialState(sA, td);
        this.fsm.setTerminalState(sB);

        System.out.println(this.fsm.toGraphVIZ());

        assertTrue(this.fsm.getCurrentState() instanceof TestStateA);
        this.fsm.cycle(eB);
        assertTrue(this.fsm.getCurrentState() instanceof TestStateC);
        this.fsm.cycle(eA);
        assertTrue(this.fsm.getCurrentState() instanceof TestStateA);
        this.fsm.cycle(eA);
        assertTrue(this.fsm.getCurrentState() instanceof TestStateB);

        assertTrue(fsm.hasTerminated());
        assertEquals(26.0 + (2.0 / 3.0), td.getValue(), 1e-6);
    }

}