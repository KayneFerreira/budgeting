package dio.budgeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "GROQ_API_KEY", matches = ".+")
public class OpenAiChatModelIT {

    @Autowired
    private OpenAiChatModel chatModel;

    @Test
    void should_receiveResponse_when_chatModelIsCalling() {
        var options = OpenAiChatOptions.builder()
                .model("llama-3.1-8b-instant")
                .temperature(0.8)
                .build();

        var response = chatModel.call("Gere um registro de budgeting com descrição de gasto, valor em reais e local");
        assertThat(response).isNotEmpty();

        System.out.println(response);
    }
}