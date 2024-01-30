package fr.iut;

import static fr.iut.StringUtil.prettyCurrencyPrint;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.Locale;


public class StringUtilTest {

    @Test
    public void prettyCurrencyPrintTest() {
        double amount = 128.02;
        Locale locale = Locale.CANADA;
        assertEquals(prettyCurrencyPrint (amount, locale),"$128.02");
    }

}
