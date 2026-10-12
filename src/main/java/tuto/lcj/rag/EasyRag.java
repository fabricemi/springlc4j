package tuto.lcj.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tuto.lcj.agents.RagAssistant;


import java.util.List;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocuments;
import static tuto.lcj.utils.Utils.*;

@Service
public class EasyRag {
    @Autowired
    private GoogleAiGeminiChatModel gemini;

   public  String mainTest()
   {
       List<Document> documentList=loadDocuments(
               toPath("documents/"),glob("*.txt")
       );
       RagAssistant ragAssistant= AiServices
               .builder(RagAssistant.class)
               .chatModel(gemini)
               .contentRetriever(createContentRetriever(documentList))
               .build();

       String answer =ragAssistant.chat("What is the main objective of football?");
       return answer;

   }

    private static ContentRetriever createContentRetriever(List<Document> documents) {

        // Here, we create an empty in-memory store for our documents and their embeddings.
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // Here, we are ingesting our documents into the store.
        // Under the hood, a lot of "magic" is happening, but we can ignore it for now.
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);

        // Lastly, let's create a content retriever from an embedding store.
        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }




}
