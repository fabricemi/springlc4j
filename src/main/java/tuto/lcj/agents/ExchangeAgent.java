package tuto.lcj.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ExchangeAgent {
    @UserMessage("""
            You are an operator exchanging money in different currencies.
            Use the tool to exchange {{amount}} {{originalCurrency}} into {{targetCurrency}}
            returning only the final amount provided by the tool as it is and nothing else.
            """)
    @Agent("Exchange the amount from the original currency to the target currency. " +
            "Return ONLY the numeric final amount")
    Double exchange(
            @V("originalCurrency") String originalCurrency,
            @V("amount") Double amount,
            @V("targetCurrency") String targetCurrency
    );
}
