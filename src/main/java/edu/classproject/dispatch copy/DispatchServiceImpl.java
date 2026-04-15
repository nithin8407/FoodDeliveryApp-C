package edu.classproject.dispatch;

import java.util.List;
import java.util.UUID;

public class DispatchServiceImpl implements DispatchService {

    @Override
    public DispatchAssignment assignPartner(String orderId, String restaurantId, String customerId) {

        if (orderId == null || orderId.isEmpty()) {
            return new DispatchAssignment(null, orderId, null, "Invalid order");
        }

        List<String> partners = List.of("driver1","driver2");

        if (partners.isEmpty()) {
            return new DispatchAssignment(null, orderId, null, "No delivery partner available");
        }

        String partnerId = partners.get(0);

        String assignmentId = UUID.randomUUID().toString();

        return new DispatchAssignment(
                assignmentId,
                orderId,
                partnerId,
                "Partner assigned successfully"
        );
    }
}