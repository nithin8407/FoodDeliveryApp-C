package edu.classproject.dispatch;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DispatchServiceTest {

    @Test
    void testDispatchSuccess() {

        DispatchService service = new DispatchServiceImpl();

        DispatchAssignment result =
                service.assignPartner("order1","rest1","cust1");

        assertNotNull(result);
        assertEquals("order1", result.orderId());
    }
}