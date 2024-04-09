package at.ac.uibk.estore.projection;

public interface ProductProjection {
    Integer getId();

    String getName();

    Double getPrice();

    Integer getStock();

    String getCategory();

    String getImage();
}
