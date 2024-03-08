package org.springframework.ai.openai.samples.helloworld.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.samples.helloworld.data.CarModel;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final ChatClient chatClient;

    @SneakyThrows
    @GetMapping("/car-model")
    public CarModel carModel(@RequestParam(value = "language", defaultValue = "dutch") String language,
                             @RequestParam(value = "quote", defaultValue = "Dit is een Kia Sorento PHEV Edition 1.6 T-GDi Plug-in Hybrid AT6 AWD (A). Een hele mooie auto") String quote) {

        String systemStringPrompt = """
                You are an employee of a car leasing company whose daily task is to make lease offers based on a quote from a car dealer.
                Your job is to determine which car model is on the car dealer quote. Before you determine what car model is on the car dealer quote, you will
                call the function 'carModelFunction' to get an overview of available car models. The overview is in format:
                {format}.
                """;
        BeanOutputParser<CarModel[]> systemParser = new BeanOutputParser<>(CarModel[].class);
        PromptTemplate systemTemplate = new PromptTemplate(systemStringPrompt);
        systemTemplate.setOutputParser(systemParser);
        Prompt systemPrompt = systemTemplate.create(Map.of("format", systemParser.getFormat()));
        SystemMessage systemMessage = new SystemMessage(systemPrompt.getContents());


        String userStringPrompt = """
                What car model is present on this quote from a car dealer?
                The quote is written in {language}.

                QUOTE:
                {quote}
                                
                {format}
                """;

        BeanOutputParser<CarModel> userParser = new BeanOutputParser<>(CarModel.class);
        PromptTemplate userTemplate = new PromptTemplate(userStringPrompt);
        userTemplate.setOutputParser(userParser);
        Prompt prompt = userTemplate.create(Map.of("language", language, "quote", new String(quote.getBytes(), StandardCharsets.UTF_8), "format", userParser.getFormat()));
        UserMessage userMessage = new UserMessage(prompt.getContents());
        ChatResponse response = chatClient.call(new Prompt(List.of(systemMessage, userMessage),
                                                           OpenAiChatOptions.builder()
                                                                            .withModel("gpt-4-turbo-preview")
                                                                            .withFunction("carModelFunction")
                                                                            .build()));
        String message = response.getResult().getOutput().getContent();
        return userParser.parse(message);
    }
}

