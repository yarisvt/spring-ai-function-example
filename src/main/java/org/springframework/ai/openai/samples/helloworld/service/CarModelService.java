package org.springframework.ai.openai.samples.helloworld.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.util.function.Function;
import lombok.SneakyThrows;
import org.springframework.ai.openai.samples.helloworld.data.CarModels;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class CarModelService implements Function<CarModelService.Request, CarModelService.Response> {


    @Value("classpath:kia.json")
    Resource allModels;

    private CarModels[] carModels;

    @SneakyThrows
    @PostConstruct
    public void postConstruct() {
        this.carModels = new ObjectMapper().readValue(allModels.getFile(), CarModels[].class);
    }

    public record Request(String model) {
    }

    public record Response(CarModels[] model) {
    }

    @Override

    public CarModelService.Response apply(CarModelService.Request request) {
        return new Response(carModels);
    }
}
