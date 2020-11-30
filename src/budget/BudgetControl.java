package budget;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * Factory class to construct different budget implementations
 * */
final public class BudgetControl {

    private BudgetControl() {}

    public static Budget getInstance(int initialValue) {
        return new CasBudgetImpl(initialValue);
    }

    /*
     * Budget implementation using the CAS atomic class
     * */
    final static class CasBudgetImpl implements Budget {
        private final AtomicInteger internalValue;
        private CasBudgetImpl() { internalValue =  new AtomicInteger(-1); }
        private CasBudgetImpl(int initialValue) { internalValue =  new AtomicInteger(initialValue); }

        @Override
        public int get() {
            return internalValue.get();
        }

        @Override
        public boolean budgetDeduct(int consumption) {
            if (consumption < 0) return false;
            while (true) {
                int existingValue = get();
                int reducedValue = existingValue - consumption;
                if (reducedValue < 0) return false;
                if (internalValue.compareAndSet(existingValue, reducedValue)) {
                    return true;
                }
            }
        }
    }


    /*
     * Budget implementation using the intrinsic lock
     * */
    final static class IntrinsicLockBudgetImpl implements Budget {
        private volatile int internalValue;
        private final Object lock = new Object();

        private IntrinsicLockBudgetImpl() {
            internalValue = -1;
        }
        private IntrinsicLockBudgetImpl(int initialValue) {
            internalValue = initialValue;
        }

        @Override
        public int get() {
            return internalValue;
        }

        @Override
        public boolean budgetDeduct(int consumption) {
            if (consumption < 0) return false;
            int reducedValue = internalValue - consumption;
            if (reducedValue < 0) return false;
            synchronized (lock) {
                reducedValue = internalValue - consumption;
                if (reducedValue < 0) return false;
                internalValue = reducedValue;
            }
            return true;
        }
    }

}

