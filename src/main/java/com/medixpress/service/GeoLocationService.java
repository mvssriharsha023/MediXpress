package com.medixpress.service;

import java.util.Optional;

public interface GeoLocationService {
    Optional<double[]> getLatLongFromAddress(String address);
}
