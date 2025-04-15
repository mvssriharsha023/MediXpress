package com.medixpress.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medixpress.service.GeoLocationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;


@Service
public class GeoLocationServiceImpl implements GeoLocationService {

    @Override
    public Optional<double[]> getLatLongFromAddress(String address) {
        try {
            String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + address.replace(" ", "+");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "MediXpress")
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());

            if (root.isArray() && !root.isEmpty()) {
                double lat = root.get(0).get("lat").asDouble();
                double lon = root.get(0).get("lon").asDouble();
                return Optional.of(new double[]{lat, lon});
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }
}
