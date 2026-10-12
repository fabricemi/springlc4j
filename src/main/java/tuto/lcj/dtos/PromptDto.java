package tuto.lcj.dtos;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data

public class PromptDto {
    public String question;
    public String response;

    public PromptDto(String question, String response) {
        this.question = question;
        this.response = response;
    }

    public PromptDto() {
    }
}
