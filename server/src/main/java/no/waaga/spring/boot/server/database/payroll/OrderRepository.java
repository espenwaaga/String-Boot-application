package no.waaga.spring.boot.server.database.payroll;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepository extends JpaRepository<Order, Long> {

}
