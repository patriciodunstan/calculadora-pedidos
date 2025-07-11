package com.ejemplo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private DiscountRepository repository;
    private OrderService service;

    @BeforeEach
    public void setup() {
        repository = mock(DiscountRepository.class);
        service = new OrderService(repository);
    }

    @Test
    public void testWithoutDiscountAndStandardShipment() {
        when(repository.getRate(null)).thenReturn(0.0);

        double result = service.getTotal(100.0, null, false);
        assertEquals(110.0, result, 0.001);
    }

    @Test
    public void testWithDiscountAndStandardShipment() {
        when(repository.getRate("SALES10")).thenReturn(0.10);

        double result = service.getTotal(100.0, "SALES10", false);
        assertEquals(100.0, result, 0.001); // 100 - 10 + 10
    }

    @Test
    public void testWithDiscountAndExpressShipment() {
        when(repository.getRate("SALES10")).thenReturn(0.10);

        double result = service.getTotal(200.0, "SALES10", true);
        assertEquals(200.0 * 0.9 + 20.0, result, 0.001); // 180 + 20 = 200
    }

    @Test
    public void testWithUnknownDiscountCode() {
        when(repository.getRate("UNKNOWN")).thenReturn(0.0);

        double result = service.getTotal(50.0, "UNKNOWN", true);
        assertEquals(50.0 + 20.0, result, 0.001); // sin descuento
    }

    @Test
    public void testZeroSubtotal() {
        when(repository.getRate("SALES10")).thenReturn(0.10);

        double result = service.getTotal(0.0, "SALES10", false);
        assertEquals(10.0, result, 0.001); // solo envío estándar
    }

    @Test
    public void testNegativeSubtotalThrowsException() {
        when(repository.getRate("SALES10")).thenReturn(0.10);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.getTotal(-50.0, "SALES10", true)
        );
        assertEquals("Subtotal cannot be negative", exception.getMessage());
    }

    @Test
public void testNegativeSubtotal() {
IllegalArgumentException exception = assertThrows(
IllegalArgumentException.class,
() -> service.getTotal(-100.0, true, false)
);
assertEquals("Subtotal no puede ser negativo", exception.getMessage());
}
}