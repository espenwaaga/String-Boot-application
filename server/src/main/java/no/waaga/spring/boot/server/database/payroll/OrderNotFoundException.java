package no.waaga.spring.boot.server.database.payroll;

class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}
