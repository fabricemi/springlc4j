package tuto.lcj.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tuto.lcj.agentsServices.AgentInvocation;
import tuto.lcj.dtos.PromptDto;
import tuto.lcj.mcp.HttpMcpImp;
import tuto.lcj.mcp.LocalMcp;
import tuto.lcj.rag.EasyRag;

@Controller
@RequestMapping(path = "/")
public class Home {

    @Autowired
    private EasyRag easyRag;

    @Autowired
    private LocalMcp httpMcpImp;

    @GetMapping()
    public String home(Model model){
        /*String content = easyRag.mainTest();
        System.out.println(content);*/
        try {
           // httpMcpImp.mainMethod();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("promptDto", new PromptDto());
        return "index";
    }

    @PostMapping()
    public String request(PromptDto promptDto, Model model){
        try {
            String response= httpMcpImp.mainMethod(promptDto.getQuestion());
            System.out.println(response);
            model.addAttribute("promptDto",
                    new PromptDto(promptDto.question, response));

            return "index";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
