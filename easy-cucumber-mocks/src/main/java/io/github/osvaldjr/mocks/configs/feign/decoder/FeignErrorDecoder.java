package io.github.osvaldjr.mocks.configs.feign.decoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import feign.Response;
import feign.codec.ErrorDecoder;
import io.github.osvaldjr.mocks.objects.ClientResponse;
import io.github.osvaldjr.mocks.objects.exceptions.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    io.github.osvaldjr.mocks.objects.ClientResponse.ClientResponseBuilder exceptionClientResponse =
        ClientResponse.builder()
            .status(response.status())
            .reason(response.reason())
            .headers(response.headers());
    try {
      if (response.body() != null) {
        exceptionClientResponse.jsonBody(
            IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8.name()));
      }
    } catch (IOException e) {
      log.error("Occurred error for convert response body", e);
    }
    return new FeignException(exceptionClientResponse.build());
  }
}
