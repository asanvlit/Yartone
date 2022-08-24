package ru.asanvlit.data.provider;

import com.github.curiousoddman.rgxgen.RgxGen;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import ru.asanvlit.dto.request.SignUpRequest;

import java.util.stream.Stream;

import static ru.asanvlit.constant.YartoneApiConstants.USERNAME_PATTERN;
import static ru.asanvlit.data.constant.YartoneTestConstants.SIGN_UP_TEST_COUNT;

public class SignUpDataProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.generate(() -> Arguments.of(SignUpRequest.builder()
                .email("test@gmail.com")
                .username(new RgxGen(USERNAME_PATTERN).generate())
                .build())
        ).limit(SIGN_UP_TEST_COUNT);
    }
}
