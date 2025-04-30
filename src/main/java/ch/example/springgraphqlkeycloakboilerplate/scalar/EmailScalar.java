package ch.example.springgraphqlkeycloakboilerplate.scalar;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.util.regex.Pattern;

@Component
public class EmailScalar {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(emailScalarType());
    }

    private GraphQLScalarType emailScalarType() {
        return GraphQLScalarType.newScalar()
                .name("Email")
                .description("A custom scalar that validates email addresses")
                .coercing(new Coercing<String, String>() {
                    @Override
                    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                        if (dataFetcherResult instanceof String) {
                            String email = String.valueOf(dataFetcherResult);
                            if (EMAIL_PATTERN.matcher(email).matches()) {
                                return email;
                            }
                            throw new CoercingSerializeException("Invalid email address format: " + email);
                        }
                        throw new CoercingSerializeException("Expected a String");
                    }

                    @Override
                    public String parseValue(Object input) throws CoercingParseValueException {
                        if (input instanceof String) {
                            String email = String.valueOf(input);
                            if (EMAIL_PATTERN.matcher(email).matches()) {
                                return email;
                            }
                            throw new CoercingParseValueException("Invalid email address format: " + email);
                        }
                        throw new CoercingParseValueException("Expected a String");
                    }

                    @Override
                    public String parseLiteral(Object input) throws CoercingParseLiteralException {
                        if (input instanceof StringValue) {
                            String email = ((StringValue) input).getValue();
                            if (EMAIL_PATTERN.matcher(email).matches()) {
                                return email;
                            }
                            throw new CoercingParseLiteralException("Invalid email address format: " + email);
                        }
                        throw new CoercingParseLiteralException("Expected a StringValue");
                    }
                })
                .build();
    }
}