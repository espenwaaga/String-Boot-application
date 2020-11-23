package no.waaga.spring.boot.server.database.payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class EmployeeController {

    public static final String EMPLOYEES = "/employees";
    private static final String EMPLOYEE_ID_PATH = EMPLOYEES + "/{id}";

    private final EmployeeRepository repository;

    private final EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping(EMPLOYEES)
    CollectionModel<EntityModel<Employee>> all() {
        var employees = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(employees, linkTo(methodOn(this.getClass()).all()).withSelfRel());
    }

    @PostMapping(EMPLOYEES)
    Employee newEmployee(@RequestBody Employee employee) {
        return repository.save(employee);
    }

    @GetMapping(EMPLOYEE_ID_PATH)
    EntityModel<Employee> getEmployee(@PathVariable Long id) {
        var employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return assembler.toModel(employee);
    }

    @PutMapping(EMPLOYEE_ID_PATH)
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    @DeleteMapping(EMPLOYEE_ID_PATH)
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
