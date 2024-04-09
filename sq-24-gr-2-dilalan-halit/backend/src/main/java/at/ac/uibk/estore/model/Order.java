package at.ac.uibk.estore.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "orders", uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_timestamp", columnNames = {"username", "order_timestamp"})
})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;

    private LocalDateTime orderTimestamp;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = {@JoinColumn(name = "order_id")})
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> orderedProducts = new HashMap<>();
}
