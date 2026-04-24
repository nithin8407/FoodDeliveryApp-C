package edu.classproject.dispatch;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryPartnerRepository implements PartnerRepository {

    private final Map<String, Boolean> partnerDB = new HashMap<>();

    public InMemoryPartnerRepository() {
        reset();
    }

    @Override
    public Optional<String> findAvailablePartner() {
        for (Map.Entry<String, Boolean> entry : partnerDB.entrySet()) {
            if (entry.getValue()) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }

    @Override
    public void markUnavailable(String partnerId) {
        partnerDB.put(partnerId, false);
    }

    @Override
    public void reset() {
        partnerDB.clear();
        partnerDB.put("driver1", true);
        partnerDB.put("driver2", true);
        partnerDB.put("driver3", false);
    }
}