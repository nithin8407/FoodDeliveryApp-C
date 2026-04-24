package edu.classproject.dispatch;

import java.util.Optional;
import java.util.UUID;

public class DispatchServiceImpl implements DispatchService {

    private final PartnerRepository repository;

    // Constructor injection
    public DispatchServiceImpl(PartnerRepository repository) {
        this.repository = repository;
    }

    @Override
    public DispatchAssignment assignPartner(String orderId, String restaurantId, String customerId) {

        if (orderId == null || orderId.trim().isEmpty()) {
            return new DispatchAssignment(null, orderId, null, "Invalid order");
        }

        Optional<String> partnerOpt = repository.findAvailablePartner();

        if (partnerOpt.isEmpty()) {
            return new DispatchAssignment(null, orderId, null, "No delivery partner available");
        }

        String partnerId = partnerOpt.get();

        String assignmentId = UUID.randomUUID().toString();

        repository.markUnavailable(partnerId);

        return new DispatchAssignment(
                assignmentId,
                orderId,
                partnerId,
                "Partner assigned successfully"
        );
    }
}