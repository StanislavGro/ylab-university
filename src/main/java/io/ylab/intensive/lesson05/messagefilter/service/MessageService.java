package io.ylab.intensive.lesson05.messagefilter.service;

import java.sql.SQLException;

public interface MessageService {
    String getMessage();

    void sendMessage(String message) throws SQLException;
}