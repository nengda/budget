package test.budget;

import budget.Budget;
import budget.BudgetControl;
import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.*;

public class BudgetControlConcurrentTest {
    private static int INITIAL_VALUE = 10000;
    private static Budget budget = BudgetControl.getInstance(INITIAL_VALUE);

    @Rule
    public ConcurrentRule concurrently = new ConcurrentRule();

    @Rule
    public RepeatingRule repeats = new RepeatingRule();

    @Test
    @Concurrent(count = 100)
    @Repeating(repetition = 99)
    public void runConcurrently() {
        budget.budgetDeduct(1);
    }

    @AfterClass
    public static void canReduceUnderStress() {
        Assert.assertEquals("Should have the correct remaining budget", (INITIAL_VALUE - 100 * 99), budget.get());
        budget.close();
    }

}
