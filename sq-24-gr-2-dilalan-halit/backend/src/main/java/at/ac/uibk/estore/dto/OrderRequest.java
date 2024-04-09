package at.ac.uibk.estore.dto;

import at.ac.uibk.estore.model.Address;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class OrderRequest {
    @NotNull
    private Address address;

    @NotNull
    private Map<Integer, Integer> products;
}
