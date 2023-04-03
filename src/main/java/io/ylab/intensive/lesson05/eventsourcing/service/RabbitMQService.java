package io.ylab.intensive.lesson05.eventsourcing.service;

import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.eventsourcing.Person;

public interface RabbitMQService {
    void sendSaveMessage(Person person);
    void sendDeleteMessage(Long id);
    GetResponse receiveMessage();
}
