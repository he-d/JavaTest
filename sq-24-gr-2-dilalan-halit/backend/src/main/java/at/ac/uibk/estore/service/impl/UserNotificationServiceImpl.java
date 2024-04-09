package at.ac.uibk.estore.service.impl;

import at.ac.uibk.estore.model.Product;
import at.ac.uibk.estore.model.User;
import at.ac.uibk.estore.repository.UserRepository;
import at.ac.uibk.estore.service.ProductService;
import at.ac.uibk.estore.service.UserEmailService;
import at.ac.uibk.estore.service.UserNotificationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class UserNotificationServiceImpl implements UserNotificationService {

    private UserRepository userRepository;

    private ProductService productService;

    private UserEmailService userEmailService;

    private String getEmailContent(User user, String message) {
        return "Dear " +
                user.getFirstname() +
                " " +
                user.getLastname() +
                ",\n\n" +
                message +
                "\nBest regards,\nYour E-Store Team";
    }

    private static String centerString (int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    public static List<String> formatDescription(String msg, int lineSize) {
        List<String> res = new ArrayList<>();

        Pattern p = Pattern.compile("\\b.{1," + (lineSize-1) + "}\\b\\W?");
        Matcher m = p.matcher(msg);

        while(m.find()) {
            res.add(m.group());
        }
        return res;
    }

    private String productToInfo(Product product) {
        StringBuilder sb = new StringBuilder();
        sb.append("."+"-".repeat(52)+".\n");
        sb.append(String.format("| %s |\n", centerString(50, product.getName())));
        sb.append(String.format("| %s |\n", centerString(50, product.getCategory())));
        List<String> description = formatDescription(product.getDescription(), 48);
        // only print the first 3 lines of the description
        for (int i = 0; i < Math.min(description.size(), 3); i++) {
            sb.append(String.format("| %-50s |\n", description.get(i)));
        }
        sb.append(String.format("| %-50s |\n", "Price: " + product.getPrice()));

        sb.append("'"+"-".repeat(52)+"'\n");

        return sb.toString();
    }

    @Override
    public void notifyAboutLatestProducts() {
        userRepository.findByNotificationTrue().forEach(user -> {
            Page<Product> latestProducts = productService.getLatestProducts(Pageable.ofSize(3));
            if(latestProducts.getTotalElements() != 0) {
                StringBuilder productInfo = new StringBuilder();
                productInfo.append("We have new products for you:\n");

                latestProducts.forEach(product -> productInfo.append(this.productToInfo(product)));

                String emailContent = this.getEmailContent(user, productInfo.toString());
                userEmailService.sendEmail(user.getEmail(), "New Products", emailContent);
            }
        });
    }

    @Override
    public void subscribe(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        user.setNotification(true);
        userRepository.save(user);
        String emailContent = this.getEmailContent(user, "You have successfully subscribed to our notifications.\n");
        userEmailService.sendEmail(user.getEmail(), "Subscription", emailContent);
    }

    @Override
    public void unsubscribe(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        user.setNotification(false);
        userRepository.save(user);
        String emailContent = this.getEmailContent(user, "You have successfully unsubscribed from our notifications.\n");
        userEmailService.sendEmail(user.getEmail(), "Unsubscription", emailContent);
    }
}
