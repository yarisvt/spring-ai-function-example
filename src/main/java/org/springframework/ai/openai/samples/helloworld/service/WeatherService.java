package org.springframework.ai.openai.samples.helloworld.service;

import java.util.Random;
import java.util.function.Function;

public class WeatherService implements Function<WeatherService.Request, WeatherService.Response> {

    public enum Unit {C, F}

    public record Request(String location, Unit unit) {
    }

    public record Response(double temp, Unit unit) {
    }

    @Override
    public Response apply(Request request) {
        double randomTempC = new Random().nextDouble(-20, 50);
        return new Response(mapTemp(randomTempC, request.unit), request.unit);
    }

    private double mapTemp(double temp, Unit unit) {
        return switch (unit) {
            case C -> temp;
            case F -> (temp * 1.8) + 32;
        };
    }

}
