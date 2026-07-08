/**
 * Módulo aplicación principal del sistema bancario modular.
 *
 * <ul>
 *   <li><b>No exporta nada</b> — es el punto de entrada (top-level), no una librería.</li>
 *   <li><b>Requiere todos los módulos de negocio</b> — core, operations y ui.</li>
 * </ul>
 */
module com.banking.app {
    requires com.banking.core;
    requires com.banking.operations;
    requires com.banking.ui;
    requires com.banking.reports;
}
