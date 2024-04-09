package at.ac.uibk.estore.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "addresses", uniqueConstraints = {
        @UniqueConstraint(name = "unique_country_city_street", columnNames = {"county", "city", "street"})
})
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String country;
    private String city;
    private String street;
    private String zip;
}
