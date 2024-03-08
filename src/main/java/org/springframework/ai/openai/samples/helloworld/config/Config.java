package org.springframework.ai.openai.samples.helloworld.config;

import java.util.function.Function;
import org.springframework.ai.openai.samples.helloworld.service.CarModelService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
public class Config {

    @Bean
    @Description("Get all available car models")
    public Function<CarModelService.Request, CarModelService.Response> carModelFunction() {
        return new CarModelService();
    }
}
