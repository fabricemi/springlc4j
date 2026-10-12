package tuto.lcj.agentsServices;



import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.supervisor.SupervisorAgent;
import dev.langchain4j.agentic.supervisor.SupervisorContextStrategy;
import dev.langchain4j.agentic.supervisor.SupervisorResponseStrategy;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tuto.lcj.agents.CreditAgent;
import tuto.lcj.agents.ExchangeAgent;
import tuto.lcj.agents.WithdrawAgent;
import tuto.lcj.tools.BankTool;
import tuto.lcj.tools.ExchangeTool;

@Service
public class AgentInvocation {


    @Autowired
    private OllamaChatModel chatModel;


    public SupervisorAgent invokOne(){
        BankTool bankTool=new BankTool();
        bankTool.createAccount("Mario", 1000.0);
        bankTool.createAccount("Georgios", 1000.0);

        WithdrawAgent withdrawAgent = AgenticServices
                .agentBuilder(WithdrawAgent.class)
                .chatModel(chatModel)
                .tools(bankTool)
                .build();
        CreditAgent creditAgent = AgenticServices
                .agentBuilder(CreditAgent.class)

                .chatModel(chatModel)
                .tools(bankTool)
                .build();

        ExchangeAgent exchangeAgent = AgenticServices
                .agentBuilder(ExchangeAgent.class)
                .chatModel(chatModel)
                .tools(new ExchangeTool())
                .build();

        return AgenticServices
                .supervisorBuilder()
                .chatModel(chatModel)
                .subAgents(withdrawAgent,creditAgent,exchangeAgent)
                .contextGenerationStrategy(SupervisorContextStrategy.SUMMARIZATION) //Quelles informations le Supervisor utilise pour décider ?
                .responseStrategy(SupervisorResponseStrategy.SCORED) // Quelle réponse finale dois-je retourner ?
                .build();
    }
}
