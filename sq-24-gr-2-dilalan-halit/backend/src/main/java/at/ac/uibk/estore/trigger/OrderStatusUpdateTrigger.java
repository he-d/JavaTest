package at.ac.uibk.estore.trigger;

import at.ac.uibk.estore.model.OrderStatus;
import org.h2.api.Trigger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderStatusUpdateTrigger implements Trigger {

    @Override
    public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type) {
        // Initialization logic if needed
    }

    @Override
    public void fire(Connection conn, Object[] oldRow, Object[] newRow) throws SQLException {
        if (newRow == null || oldRow == null) {
            return;
        }
        if ((Byte) oldRow[1] == OrderStatus.PENDING.ordinal() && (Byte) newRow[1] == OrderStatus.SHIPPED.ordinal()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT product_id, quantity FROM order_items WHERE order_id = ?")) {
                ps.setObject(1, newRow[0]);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int productId = rs.getInt("product_id");
                        int quantity = rs.getInt("quantity");
                        try (PreparedStatement ps2 = conn.prepareStatement("UPDATE products SET stock = stock - ? WHERE id = ?")) {
                            ps2.setInt(1, quantity);
                            ps2.setInt(2, productId);
                            ps2.executeUpdate();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void close() throws SQLException {
        // Cleanup logic if needed
    }

    @Override
    public void remove() throws SQLException {
        // Logic to execute when the trigger is removed
    }
}