package ru.ifmo.neuro;

import nl.dannyj.mistral.MistralClient;
import nl.dannyj.mistral.builders.MessageListBuilder;
import nl.dannyj.mistral.models.completion.ChatCompletionRequest;
import nl.dannyj.mistral.models.completion.message.AssistantMessage;
import nl.dannyj.mistral.models.completion.message.ChatMessage;

import java.util.List;

public class Mistral implements NeuroCommGetter{
    private MistralClient mistralClient;
    private String systemPrompt;

    public Mistral(){
        mistralClient = new MistralClient();
    }

    @Override
    public String getStringCommand(String input) {
        List<ChatMessage> messages = new MessageListBuilder().system(systemPrompt).user(input).build();
        ChatCompletionRequest request = ChatCompletionRequest.builder().messages(messages).temperature(0d).model("mistral-small-latest").build();
        var response = mistralClient.createChatCompletion(request);
        AssistantMessage assistantMessage = response.getChoices().get(0).getMessage();
        String text = assistantMessage.getTextContent();

        return text;
    }

    public void setSystemPrompt(String systemPrompt){
        this.systemPrompt = systemPrompt;
    }

}
