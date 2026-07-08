module com.banking.operations {
    requires com.banking.core;
    
    // Exportamos la factory para crear cuentas
    exports com.banking.operations.factory;
    
    // La implementación (com.banking.operations.account) NO se exporta
    // Está encapsulada dentro del módulo
}

