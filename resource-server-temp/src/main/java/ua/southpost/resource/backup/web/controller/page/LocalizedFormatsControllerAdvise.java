package ua.southpost.resource.backup.web.controller.page;

import ua.southpost.resource.backup.web.controller.util.DecimalSeparator;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

@ControllerAdvice
public class LocalizedFormatsControllerAdvise {


	@ModelAttribute("moneyPattern")
	public String moneyPattern(Locale locale) {
		final DecimalFormatSymbols dfs = new DecimalFormatSymbols(locale);
		final char decimalSeparator = dfs.getDecimalSeparator();
		return DecimalSeparator.find(decimalSeparator).orElse(DecimalSeparator.DOT).getMoneyPatternExpression();
	}
}
