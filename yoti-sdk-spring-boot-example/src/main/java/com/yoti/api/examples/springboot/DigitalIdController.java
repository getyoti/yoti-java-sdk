package com.yoti.api.examples.springboot;

import java.net.URI;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.yoti.api.client.DigitalIdentityClient;
import com.yoti.api.client.identity.MatchNotification;
import com.yoti.api.client.identity.MatchRequest;
import com.yoti.api.client.identity.MatchResult;
import com.yoti.api.client.spi.remote.call.ResourceException;
import com.yoti.api.examples.springboot.model.MatchInputForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@RequestMapping("/did")
public class DigitalIdController implements WebMvcConfigurer {

    private final DigitalIdentityClient client;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Autowired
    public DigitalIdController(DigitalIdentityClient client) {
        this.client = client;
    }

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("matchInputForm", new MatchInputForm());
        return "match-input";
    }

    @PostMapping("/match")
    public String match(@ModelAttribute("matchInputForm") MatchInputForm form, Model model) {
        MatchNotification.Builder notification = MatchNotification.forUrl(URI.create(form.getEndpoint()))
                .withVerifyTls(form.isVerifyTls())
                .withMethod(form.getHttpMethod());

        List<String> keys = form.getHeaderKeys();
        List<String> values = form.getHeaderValues();

        Map<String, String> headers = IntStream.range(0, keys.size())
                .mapToObj(i -> new AbstractMap.SimpleEntry<>(
                        keys.get(i),
                        i < values.size() ? values.get(i) : ""
                ))
                .filter(entry -> entry.getKey() != null && !entry.getKey().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (!headers.isEmpty()) {
            notification.withHeaders(headers);
        }

        MatchRequest.Builder match = MatchRequest.builder(form.getValue()).withNotification(notification.build());

        MatchResult result = execute(() -> client.fetchMatch(match.build()), model);

        return Optional.ofNullable(result)
                .map(r -> {
                    model.addAttribute("matchId", r.getId());
                    model.addAttribute("matchResult", r.getResult());

                    return "match-result";
                })
                .orElse("match-error");
    }

    private static <T> T execute(Supplier<T> supplier, Model model) {
        try {
            return supplier.get();
        } catch (Exception ex) {
            if (ex.getCause() instanceof ResourceException) {
                ResourceException resourceEx = (ResourceException) ex.getCause();
                model.addAttribute("jsonError", resourceEx.getResponseBody());
            } else {
                model.addAttribute("error", ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
            }

            return null;
        }
    }

}
