/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.jbang.core.platform.destination.service;

import java.util.List;

import io.debezium.jbang.core.platform.destination.dto.DestinationRequest;
import io.debezium.jbang.core.platform.destination.dto.DestinationResponse;

public interface DestinationService {

    List<DestinationResponse> listDestinations();

    DestinationResponse getDestination(Long id);

    DestinationResponse createDestination(DestinationRequest destinationRequest);

    DestinationResponse updateDestination(Long id, DestinationRequest destinationRequest);

    void deleteDestination(Long id);
}
