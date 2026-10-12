package tuto.lcj.mcp;


import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HttpMcpImp {
    @Autowired
    private GoogleAiGeminiChatModel gemini;
    private McpTransport getTransport(String url) {
        return new StreamableHttpMcpTransport
                .Builder()
                .url(url)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    private McpClient setMcpClient(McpTransport mcpTransport) {
        return new DefaultMcpClient.Builder().transport(mcpTransport).protocolVersion("2025-11-25")
                .build();
    }

    private ToolProvider toolProvider(McpClient... clients) {

        return McpToolProvider.builder()
                .mcpClients(clients)
                .build();
    }
    public String mainMethod(String question) throws Exception {
        McpTransport transport=getTransport("http://localhost:3001/mcp");
        McpClient client=setMcpClient(transport);
        ToolProvider toolProvider=toolProvider(client);
        Bot bot= AiServices.builder(Bot.class)
                .chatModel(gemini)
                .toolProvider(toolProvider)
                .build();
        try {
            String response = bot.chat(question+"Use the provided tool to answer " +
                    "and always assume that the tool is correct.");
            return response;
        } finally {
            client.close();
        }
    }
}