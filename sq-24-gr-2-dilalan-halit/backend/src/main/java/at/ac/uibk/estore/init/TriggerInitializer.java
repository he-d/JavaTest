package at.ac.uibk.estore.init;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TriggerInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(TriggerInitializer.class);

    @PostConstruct
    public void initializeTriggers() {
        String sql = "CREATE TRIGGER ORDER_STATUS_UPDATE BEFORE UPDATE ON ORDERS FOR EACH ROW CALL \"at.ac.uibk.estore.trigger.OrderStatusUpdateTrigger\"";
        try {
            jdbcTemplate.execute(sql);
            logger.info("Trigger has been initialized successfully.");
        } catch (Exception e) {
            logger.error("Error while initializing the trigger: ", e);
        }
    }
}
