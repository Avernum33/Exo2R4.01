package fr.iut;

import java.util.Locale;
import java.text.NumberFormat;
import java.util.Currency;

public class StringUtil {

    public static String prettyCurrencyPrint (final double amount, final Locale locale) {

        String var = NumberFormat.getCurrencyInstance(locale).format(amount);

        /*Currency currency = Currency.getInstance(locale);
        String currencySymbol = currency.getSymbol();
        String var=""+amount+" "+currencySymbol;*/
        System.out.println(var);
        return var;
    }
}
