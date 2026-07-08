package com.banking.reports.api;

import com.banking.core.account.BankAccount;

/**
 * API pública: genera reportes a partir del contrato {@link BankAccount}.
 *
 * <p>Este módulo depende solo de {@code com.banking.core}: no accede a implementaciones
 * de {@code com.banking.operations} (encapsuladas).
 */
public final class AccountReportGenerator {

    private AccountReportGenerator() {}

    public static String generarReporte(BankAccount account) {
        return """
                === Reporte de cuenta ===
                Número: %s
                Titular: %s
                Balance: %s
                Última transacción: %s
                """.formatted(
                account.getAccountNumber(),
                account.getAccountHolder(),
                account.getBalance(),
                account.getLastTransactionTime());
    }
}

