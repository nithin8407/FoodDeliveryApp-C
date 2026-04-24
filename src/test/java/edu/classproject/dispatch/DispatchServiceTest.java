package edu.classproject.dispatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DispatchServiceTest {

    private InMemoryPartnerRepository repo;
    private DispatchServiceImpl service;

    // 🔥 Reset before each test
    @BeforeEach
    void setup() {
        repo = new InMemoryPartnerRepository();
        service = new DispatchServiceImpl(repo);
        repo.reset();
    }

    // 1️⃣ Valid assignment
    @Test
    void testValidAssignment() {
        DispatchAssignment result = service.assignPartner("ORD1", "RES1", "CUST1");
        assertNotNull(result.partnerId());
    }

    // 2️⃣ Null orderId
    @Test
    void testNullOrderId() {
        DispatchAssignment result = service.assignPartner(null, "RES1", "CUST1");
        assertEquals("Invalid order", result.reason());
    }

    // 3️⃣ Empty orderId
    @Test
    void testEmptyOrderId() {
        DispatchAssignment result = service.assignPartner("", "RES1", "CUST1");
        assertEquals("Invalid order", result.reason());
    }

    // 4️⃣ OrderId with spaces
    @Test
    void testOrderWithSpaces() {
        DispatchAssignment result = service.assignPartner("   ", "RES1", "CUST1");
        assertEquals("Invalid order", result.reason());
    }

    // 5️⃣ Assignment ID generated
    @Test
    void testAssignmentIdGenerated() {
        DispatchAssignment result = service.assignPartner("ORD2", "RES1", "CUST1");
        assertNotNull(result.assignmentId());
    }

    // 6️⃣ Partner assigned
    @Test
    void testPartnerAssigned() {
        DispatchAssignment result = service.assignPartner("ORD3", "RES1", "CUST1");
        assertNotNull(result.partnerId());
    }

    // 7️⃣ Success message
    @Test
    void testSuccessMessage() {
        DispatchAssignment result = service.assignPartner("ORD4", "RES1", "CUST1");
        assertEquals("Partner assigned successfully", result.reason());
    }

    // 8️⃣ Multiple assignments
    @Test
    void testMultipleAssignments() {
        DispatchAssignment r1 = service.assignPartner("ORD5", "RES1", "CUST1");
        DispatchAssignment r2 = service.assignPartner("ORD6", "RES1", "CUST1");

        assertNotNull(r1.partnerId());
        assertNotNull(r2.partnerId());
    }

    // 9️⃣ Assignment ID uniqueness
    @Test
    void testUniqueAssignmentId() {
        DispatchAssignment r1 = service.assignPartner("ORD7", "RES1", "CUST1");
        DispatchAssignment r2 = service.assignPartner("ORD8", "RES1", "CUST1");

        assertNotEquals(r1.assignmentId(), r2.assignmentId());
    }

    // 🔟 Large orderId
    @Test
    void testLargeOrderId() {
        DispatchAssignment result = service.assignPartner("ORD123456", "RES1", "CUST1");
        assertNotNull(result.partnerId());
    }

    // 1️⃣1️⃣ Different customer
    @Test
    void testDifferentCustomer() {
        DispatchAssignment result = service.assignPartner("ORD9", "RES1", "CUST2");
        assertNotNull(result.partnerId());
    }

    // 1️⃣2️⃣ Different restaurant
    @Test
    void testDifferentRestaurant() {
        DispatchAssignment result = service.assignPartner("ORD10", "RES2", "CUST1");
        assertNotNull(result.partnerId());
    }

    // 1️⃣3️⃣ Null customer
    @Test
    void testNullCustomer() {
        DispatchAssignment result = service.assignPartner("ORD11", "RES1", null);
        assertNotNull(result);
    }

    // 1️⃣4️⃣ Null restaurant
    @Test
    void testNullRestaurant() {
        DispatchAssignment result = service.assignPartner("ORD12", null, "CUST1");
        assertNotNull(result);
    }

    // 1️⃣5️⃣ Minimal input
    @Test
    void testMinimalInput() {
        DispatchAssignment result = service.assignPartner("O", "R", "C");
        assertNotNull(result.partnerId());
    }

    // 1️⃣6️⃣ No partner available
    @Test
    void testNoPartnerAvailable() {
        repo.markUnavailable("driver1");
        repo.markUnavailable("driver2");

        DispatchAssignment result = service.assignPartner("ORD13", "RES1", "CUST1");

        assertEquals("No delivery partner available", result.reason());
    }
}