package ru.practicum.ewm.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class StatsClient {

    public static final String NIT = "/hit";
    public static final String STATS = "/stats";
    private final RestTemplate rest;
    private final String app;
    private final DateTimeFormatter formatter;

    public StatsClient(@Value("${app.stats-server-url}") String serverUrl,
                       @Value("${app.name}") String app,
                       @Value("${app.format.date-time}") String format,
                       RestTemplateBuilder builder) {
        this.app = app;
        this.formatter = DateTimeFormatter.ofPattern(format);
        this.rest = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    public void add(@NotNull HttpServletRequest servletRequest) {
        log.debug("Trying send request for 'add'");
        StatsDtoRequest dto = new StatsDtoRequest(
                app,
                servletRequest.getRequestURI(),
                servletRequest.getRemoteAddr(),
                LocalDateTime.now()
        );
        HttpEntity<StatsDtoRequest> request = new HttpEntity<>(dto, defaultHeaders());
        try {
            rest.exchange(NIT, HttpMethod.POST, request, Object.class);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException(String.format(" StatusCode: %d, ResponseBody: %s",
                    e.getStatusCode().value(), e.getResponseBodyAsString()));
        }
    }

    public ResponseEntity<Object> find(@NotNull LocalDateTime start,
                                       @NotNull LocalDateTime end,
                                       List<String> uris,
                                       Boolean unique) {
        log.debug("Trying send request for 'find'");
        Map<String, Object> parameters = Map.of(
                "start", start.format(formatter),
                "end", end.format(formatter),
                "uris", uris != null ? String.join(",", uris) : "",
                "unique", unique != null ? unique : "false"
        );
        HttpEntity<Object> httpEntity = new HttpEntity<>(defaultHeaders());
        String path = STATS + "?start={start}&end={end}&uris={uris}&unique={unique}";
        ResponseEntity<Object> response = rest.exchange(path, HttpMethod.GET, httpEntity, Object.class, parameters);
        return prepareResponse(response);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static ResponseEntity<Object> prepareResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());
        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }
        return responseBuilder.build();
    }
}
