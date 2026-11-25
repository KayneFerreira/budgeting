package dio.budgeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "GROQ_API_KEY", matches = ".+")
public class ToolCallingIT {
    @Autowired
    OpenAiChatModel chatModel;

    static class MathTools {
        @Tool(description = "soma dois números inteiros 'a' e 'b'")
        public int sum(int a, int b) {
            System.out.println("sum");
            return a + b;
        }

        @Tool(description = "subtrai dois números inteiros 'b' de 'a'")
        public int diff(int a, int b) {
            System.out.println("diff");
            return a - b;
        }

    }

    @Test
    void should_executeSum_when_prompted() {
        var chatClient = ChatClient.builder(chatModel)
                .defaultSystem("Voce é um matemático")
                .defaultTools(new MathTools())
                .build();

        var response = chatClient
                .prompt("Some 10 mais 20. Subtraia 30 do resultado anterior. Exiba apenas o resultado sem explicações")
                .call().content();

        assertThat(response).contains("0");
        System.out.println(response);
    }
}
