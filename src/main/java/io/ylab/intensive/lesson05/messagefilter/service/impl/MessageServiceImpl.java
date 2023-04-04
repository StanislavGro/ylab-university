package io.ylab.intensive.lesson05.messagefilter.service.impl;

import io.ylab.intensive.lesson05.messagefilter.dao.RabbitMQDAO;
import io.ylab.intensive.lesson05.messagefilter.dao.TableDAO;
import io.ylab.intensive.lesson05.messagefilter.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.StringTokenizer;

@Slf4j
@Component
public class MessageServiceImpl implements MessageService {
    private final RabbitMQDAO rabbitMQDAO;
    private final TableDAO tableDAO;

    public MessageServiceImpl(@Autowired RabbitMQDAO rabbitMQDAO, @Autowired TableDAO tableDAO) {
        this.rabbitMQDAO = rabbitMQDAO;
        this.tableDAO = tableDAO;
    }

    @Override
    public String getMessage() {
        String rawMassage = rabbitMQDAO.receiveMessage();
        return rawMassage;
    }

    @Override
    public void sendMessage(String message) throws SQLException {
        if(message == null) {
            log.info("Ничего не было получено из очереди");
            return;
        }
        String parsedMessage = parseMessage(message);
        rabbitMQDAO.sendMessage(parsedMessage);
    }

    private String parseMessage(String message) throws SQLException {
        // Делим весь месседж на переносы строк для удобной обработки каждой из них
        // Сложность O(n)
        String[] splitByNewLineMessage = message.split("\\r\\n");
        // Используем стринг билдер так как будем много раз добавлять символы
        StringBuilder result = new StringBuilder();
        for(String line : splitByNewLineMessage) {
            // Используем метод двух укзателей
            // Запоминаем позицию первого левого и правого указателей, и размер всего токена
            int right = 0;
            int size = line.length();
            // сложность O(n)
            while (right < size) {
                char ch = line.charAt(right);
                // Создаем еще один стринг билдер, для того чтобы в след раз нам не пришлось вызывать метод substring
                // и увеличивать сложность (O-большая) алгоритма
                StringBuilder word = new StringBuilder();
                // Циклом проходимся до одного из этих символов
                while (ch != ' ' && ch != ',' && ch != '.' && ch != '?' && ch != '!' && ch != ';') {
                    word.append(ch);
                    right = right + 1;
                    if(right == size) {
                        break;
                    }
                    ch = line.charAt(right);
                }
                // Если такое слово есть в базе данных плохих слов, то заменяем
                // иначе сохраняем
                // + небольшая оптимизация если слово меньше 3 символов (будет работать пока не появится плохое слово из 2х букв)
                int tokenSize = word.length();
                if (tokenSize > 2 && tableDAO.isWordExist(word.toString().toLowerCase())) {
                    result.append(word.charAt(0));
                    for (int i = 1; i < tokenSize - 1; i++) {
                        result.append("*");
                    }
                    result.append(word.charAt(tokenSize - 1));
                } else {
                    result.append(word);
                }
                if(right < size) {
                    result.append(ch);
                    right = right + 1;
                }
            }
            result.append("\r");
        }
        return result.toString();
    }
}
