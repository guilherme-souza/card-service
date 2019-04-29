package com.prepaid.cardservice.util;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class FormatterUtilsTest {

    @Test
    public void shouldFormatMonetaryValueWithTwoDecimals() {
        assertEquals(FormatterUtils.formatBigDecimalToString(new BigDecimal("100")), "100,00");
        assertEquals(FormatterUtils.formatBigDecimalToString(new BigDecimal("100.33")), "100,33");
        assertEquals(FormatterUtils.formatBigDecimalToString(new BigDecimal("100.555")), "100,55");
        assertEquals(FormatterUtils.formatBigDecimalToString(new BigDecimal("100.6")), "100,60");
    }
}
