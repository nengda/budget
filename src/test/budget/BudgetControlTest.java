package test.budget;

import budget.Budget;
import budget.BudgetControl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BudgetControlTest {
    private int INITIAL_VALUE = 10000;
    private Budget budget;
    @Before
    public void init() {
        budget = BudgetControl.getInstance(INITIAL_VALUE);
    }

    @After
    public void tearDown() {
        budget.close();
    }

    @Test
    public void testCanReduceBudget() {
        Assert.assertTrue("Should return true in the case of under budget", budget.budgetDeduct(50));
        Assert.assertEquals("Should have the correct remaining budget", INITIAL_VALUE - 50, budget.get());

        Assert.assertTrue("Should return true at budget", budget.budgetDeduct(INITIAL_VALUE - 50));
        Assert.assertEquals("Should have 0 remaining budget", 0, budget.get());
    }

    @Test
    public void testOutofBudget() {
        Assert.assertFalse("Should return false in the case of out budget", budget.budgetDeduct(INITIAL_VALUE + 1));
        Assert.assertTrue(budget.budgetDeduct(4500));
        Assert.assertEquals(INITIAL_VALUE - 4500, budget.get());
        Assert.assertFalse("Should return false in the case of out budget after one reduction",
                budget.budgetDeduct(INITIAL_VALUE - 4500 + 1));
    }

    @Test
    public void testNegativeConsumption() {
        Assert.assertFalse("Should return false in the case of negative consumption", budget.budgetDeduct(-1));
    }

    @Test
    public void testZeroConsumption() {
        Assert.assertTrue("Should return true in the case of zero consumption", budget.budgetDeduct(0));
        Assert.assertEquals("Should have the same remaining budget", INITIAL_VALUE, budget.get());
    }
}
