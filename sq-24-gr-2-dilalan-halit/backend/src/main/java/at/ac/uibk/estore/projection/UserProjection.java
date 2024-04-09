package at.ac.uibk.estore.projection;

import at.ac.uibk.estore.model.Address;

public interface UserProjection {
    String getUsername();

    String getEmail();

    String getFirstname();

    String getLastname();

    Address getAddress();
}
