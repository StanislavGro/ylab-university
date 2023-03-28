package io.ylab.intensive.lesson04.eventsourcing.request;

public enum RequestMethod {
    // Добавил только post и delete, так как по дз именно они отправляются в rabbit
    POST,
    DELETE
}
