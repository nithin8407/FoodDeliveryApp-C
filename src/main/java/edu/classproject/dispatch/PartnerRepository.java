package edu.classproject.dispatch;

import java.util.Optional;

public interface PartnerRepository {

    Optional<String> findAvailablePartner();

    void markUnavailable(String partnerId);

    void reset();
}