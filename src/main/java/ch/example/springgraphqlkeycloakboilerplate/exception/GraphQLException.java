package ch.example.springgraphqlkeycloakboilerplate.exception;

public class GraphQLException extends RuntimeException {
    public GraphQLException(String message) {
        super(message);
    }
}