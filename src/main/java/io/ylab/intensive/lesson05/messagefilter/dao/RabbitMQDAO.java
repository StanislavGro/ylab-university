package io.ylab.intensive.lesson05.messagefilter.dao;


public interface RabbitMQDAO {
    String receiveMessage();
    void sendMessage(String message);
}
