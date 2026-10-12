package tuto.lcj.mcp;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalMcp {
    @Autowired
    private OllamaChatModel model;
    public String mainMethod(String question) throws Exception {
        McpTransport transport = new StdioMcpTransport.Builder()


                .command(
                        List.of(
                                "java"
                                , "-jar",
                                "mcp103.jar")
                )
                .logEvents(true)
                .build();

        McpClient mcpClient = new DefaultMcpClient.Builder()
                .transport(transport)
                .protocolVersion("2025-11-25")
                .build();

        ToolProvider toolProvider = McpToolProvider.builder()
                .mcpClients(List.of(mcpClient))
                .build();

        Bot bot= AiServices.builder(Bot.class)
                .chatModel(model)
                .toolProvider(toolProvider)
                .build();
        try {
            String response = bot.chat(question+"Use the provided tool to answer " +
                    "and always assume that the tool is correct.");
            return response;
        } finally {
            mcpClient.close();
        }


    }
}
