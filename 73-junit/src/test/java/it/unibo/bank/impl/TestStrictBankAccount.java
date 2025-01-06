package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static it.unibo.bank.impl.SimpleBankAccount.MANAGEMENT_FEE;
import static it.unibo.bank.impl.StrictBankAccount.TRANSACTION_FEE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder DanielAlejandro;
    private BankAccount bankAccount;
    private static final int INITIAL_AMOUNT = 100;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.DanielAlejandro = new AccountHolder("Daniel Alejandro", "Horna", 1);
        this.bankAccount = new StrictBankAccount(DanielAlejandro, 0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(0, bankAccount.getBalance());
        assertEquals(DanielAlejandro, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    void testManagementFees() {
        assertTransactionsAre(0);
        bankAccount.deposit(DanielAlejandro.getUserID(), INITIAL_AMOUNT);
        assertTransactionsAre(1);
        assertEquals(INITIAL_AMOUNT, bankAccount.getBalance());
        bankAccount.chargeManagementFees(DanielAlejandro.getUserID());
        assertTransactionsAre(0);
        assertEquals(INITIAL_AMOUNT - TRANSACTION_FEE - MANAGEMENT_FEE, bankAccount.getBalance());
    }

    void assertTransactionsAre(final int expectedTransactions) {
        assertEquals(expectedTransactions, bankAccount.getTransactionsCount());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try {
            bankAccount.withdraw(DanielAlejandro.getUserID(), -INITIAL_AMOUNT);
        } catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isEmpty());
        }
        
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try{
            bankAccount.withdraw(DanielAlejandro.getUserID(), INITIAL_AMOUNT*100);
        }
        catch(IllegalArgumentException e){
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isEmpty());
        }
    }
}
