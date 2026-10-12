package tuto.lcj.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Bean
    public GoogleAiGeminiChatModel googleAiGeminiChatModel() {
        System.setProperty(
                "langchain4j.http.clientBuilderFactory",
                "dev.langchain4j.http.client.jdk.JdkHttpClientBuilderFactory"
        );
        GoogleAiGeminiChatModel gemini = GoogleAiGeminiChatModel.builder()

                .modelName("gemini-3.5-flash-lite")
                .sendThinking(true)
                .returnThinking(true)
                .logRequests(true)
                .logResponses(true)
                .build();
    return gemini;
    }
}