package at.ac.uibk.estore.repository;

import at.ac.uibk.estore.model.Order;
import org.springframework.data.repository.CrudRepository;


public interface OrderRepository extends CrudRepository<Order, Integer> {
}
