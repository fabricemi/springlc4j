package tuto.lcj.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

public class ExchangeTool {

    @Tool("Exchange the given amount of money from the original to the target currency")
    Double exchange(
            @P("originalCurrency") String originalCurrency,
            @P("amount") Double amount,
            @P("targetCurrency") String targetCurrency) {

        if (originalCurrency.equalsIgnoreCase(targetCurrency)) {
            return amount;
        }

        if (originalCurrency.equalsIgnoreCase("EUR")
                && targetCurrency.equalsIgnoreCase("USD")) {
            return amount * 1.10;
        }

        if (originalCurrency.equalsIgnoreCase("USD")
                && targetCurrency.equalsIgnoreCase("EUR")) {
            return amount * 0.91;
        }

        throw new IllegalArgumentException(
                "Unsupported currency conversion: "
                        + originalCurrency + " -> " + targetCurrency
        );
    }
}
