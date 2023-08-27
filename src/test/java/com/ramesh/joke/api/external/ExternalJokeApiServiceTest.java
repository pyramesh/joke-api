package com.ramesh.joke.api.external;

import static com.ramesh.joke.api.constant.ErrorMessages.NO_SAFE_JOKES_FOUND;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.ramesh.joke.api.client.JokeApiFeignClient;
import com.ramesh.joke.api.exceptions.JokeNotFoundException;
import com.ramesh.joke.api.exceptions.ServiceNotAvailableException;
import com.ramesh.joke.api.helper.TestDataHelper;
import com.ramesh.joke.api.response.JokeResponse;

@ExtendWith(MockitoExtension.class)
public class ExternalJokeApiServiceTest {

    @InjectMocks
    private ExternalJokeAPIService externalAPIFeignService;

    @Mock
    private JokeApiFeignClient jokeApiFeignClient;

    @Test
    void testCallExternalService_Success() {
        // Given
        Mockito.when(jokeApiFeignClient.getJokes()).thenReturn(ResponseEntity.ok(TestDataHelper.createJokeApiServiceResponse()));

        // When
        final JokeResponse jokeResponse = externalAPIFeignService.callExternalService();

        // Then
        Assertions.assertThat(jokeResponse).isNotNull();
        Assertions.assertThat(jokeResponse.getAmount()).isEqualTo(5);
        Assertions.assertThat(jokeResponse.isError()).isFalse();
        Assertions.assertThat(jokeResponse.getJokes().size()).isEqualTo(5);
    }

    @Test
    void testCallExternalService_WhenNoRecordsFound() {
        // Given
        final ResponseEntity<JokeResponse> response = ResponseEntity.of(Optional.of(JokeResponse.builder()
                .error(true)
                .message(NO_SAFE_JOKES_FOUND)
                        .internalError(false)
                .build()));

        Mockito.when(jokeApiFeignClient.getJokes()).thenReturn(response);

        // When
        // Then
        Assertions.assertThatThrownBy(() -> externalAPIFeignService.callExternalService())
                        .isInstanceOf(JokeNotFoundException.class)
                                .hasMessage(NO_SAFE_JOKES_FOUND);
    }

    @Test
    void testCallExternalService_WhenServiceNotAvailable() {
        // Given
        final ServiceNotAvailableException serviceNotAvailableException = new ServiceNotAvailableException(404, "Service is not available");

        Mockito.when(jokeApiFeignClient.getJokes()).thenThrow(serviceNotAvailableException);

        // When
        // Then
        Assertions.assertThatThrownBy(() -> externalAPIFeignService.callExternalService())
                .isInstanceOf(ServiceNotAvailableException.class)
                .hasMessage("Service is not available");
    }
}
