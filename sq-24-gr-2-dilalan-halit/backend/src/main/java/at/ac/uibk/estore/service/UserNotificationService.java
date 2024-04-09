package at.ac.uibk.estore.service;

public interface UserNotificationService {
    void notifyAboutLatestProducts();

    void subscribe(String username);

    void unsubscribe(String username);
}
