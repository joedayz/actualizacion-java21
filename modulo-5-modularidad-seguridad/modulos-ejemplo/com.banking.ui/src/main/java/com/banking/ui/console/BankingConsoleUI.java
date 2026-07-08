package com.banking.ui.console;

import com.banking.core.account.BankAccount;
import com.banking.core.exception.InsufficientFundsException;
import com.banking.core.exception.InvalidAccountOperationException;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 * Interfaz de usuario por consola para operaciones bancarias.
 * Esta clase proporciona un menú interactivo para realizar operaciones.
 */
public class BankingConsoleUI {
    
    private final BankAccount account;
    private final Supplier<String> reportSupplier;
    private final Scanner scanner;
    private boolean running;
    
    public BankingConsoleUI(BankAccount account) {
        this(account, null);
    }

    public BankingConsoleUI(BankAccount account, Supplier<String> reportSupplier) {
        this.account = account;
        this.reportSupplier = reportSupplier;
        this.scanner = new Scanner(System.in);
        this.running = true;
    }
    
    /**
     * Inicia el menú interactivo
     */
    public void start() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   Bienvenido al Sistema Bancario       ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
        printAccountInfo();
        
        while (running) {
            displayMenu();
            String choice = scanner.nextLine().trim();
            processChoice(choice);
        }
        
        scanner.close();
        System.out.println("\n¡Gracias por usar nuestro sistema bancario!");
    }
    
    private void displayMenu() {
        System.out.println("\n┌──────────────────────────────────────┐");
        System.out.println("│          MENÚ PRINCIPAL              │");
        System.out.println("├──────────────────────────────────────┤");
        System.out.println("│ 1. Ver balance                       │");
        System.out.println("│ 2. Realizar depósito                 │");
        System.out.println("│ 3. Realizar retiro                   │");
        System.out.println("│ 4. Ver historial de transacciones    │");
        System.out.println("│ 5. Ver información de la cuenta      │");
        if (reportSupplier != null) {
            System.out.println("│ 6. Ver reporte                       │");
            System.out.println("│ 7. Salir                             │");
        } else {
            System.out.println("│ 6. Salir                             │");
        }
        System.out.println("└──────────────────────────────────────┘");
        System.out.print("Seleccione una opción: ");
    }
    
    private void processChoice(String choice) {
        try {
            switch (choice) {
                case "1" -> showBalance();
                case "2" -> performDeposit();
                case "3" -> performWithdrawal();
                case "4" -> showTransactionHistory();
                case "5" -> showAccountInfo();
                case "6" -> {
                    if (reportSupplier != null) {
                        showReport();
                    } else {
                        running = false;
                    }
                }
                case "7" -> {
                    if (reportSupplier != null) {
                        running = false;
                    } else {
                        System.out.println("❌ Opción no válida. Intente de nuevo.");
                    }
                }
                default -> System.out.println("❌ Opción no válida. Intente de nuevo.");
            }
        } catch (InsufficientFundsException e) {
            System.out.println("⚠️  Error: " + e.getMessage());
        } catch (InvalidAccountOperationException e) {
            System.out.println("⚠️  Error de operación: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("⚠️  Error: Por favor ingrese un número válido.");
        } catch (Exception e) {
            System.out.println("⚠️  Error inesperado: " + e.getMessage());
        }
    }
    
    private void showBalance() {
        System.out.println("\n💰 Balance actual: $" + formatCurrency(account.getBalance()));
    }

    private void showReport() {
        String report = reportSupplier.get();
        System.out.println();
        System.out.println(report);
    }
    
    private void performDeposit() {
        System.out.print("\nIngrese la cantidad a depositar: $");
        String input = scanner.nextLine();
        BigDecimal amount = new BigDecimal(input);
        account.deposit(amount);
        System.out.println("✅ Depósito exitoso. Nuevo balance: $" + formatCurrency(account.getBalance()));
    }
    
    private void performWithdrawal() {
        System.out.print("\nIngrese la cantidad a retirar: $");
        String input = scanner.nextLine();
        BigDecimal amount = new BigDecimal(input);
        account.withdraw(amount);
        System.out.println("✅ Retiro exitoso. Nuevo balance: $" + formatCurrency(account.getBalance()));
    }
    
    private void showTransactionHistory() {
        System.out.println("\n📋 Historial de transacciones:");
        System.out.println("─".repeat(50));
        String history = account.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No hay transacciones");
        } else {
            System.out.println(history);
        }
        System.out.println("─".repeat(50));
    }
    
    private void printAccountInfo() {
        System.out.println("\n📱 Información de la cuenta:");
        System.out.println("├─ Número: " + account.getAccountNumber());
        System.out.println("├─ Titular: " + account.getAccountHolder());
        System.out.println("├─ Balance: $" + formatCurrency(account.getBalance()));
        System.out.println("└─ Última transacción: " + account.getLastTransactionTime());
    }
    
    private void showAccountInfo() {
        printAccountInfo();
    }
    
    private String formatCurrency(BigDecimal amount) {
        return String.format("%,.2f", amount);
    }
}

