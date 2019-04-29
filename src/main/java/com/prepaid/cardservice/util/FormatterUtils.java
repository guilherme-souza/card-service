package com.prepaid.cardservice.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class FormatterUtils {

    private FormatterUtils(){}

    public static String formatBigDecimalToString(BigDecimal value) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        df.setGroupingUsed(false);

        return df.format(value);
    }
}
