package io.ylab.intensive.lesson05.messagefilter.service.impl;

import io.ylab.intensive.lesson05.messagefilter.dao.RabbitMQDAO;
import io.ylab.intensive.lesson05.messagefilter.dao.TableDAO;
import io.ylab.intensive.lesson05.messagefilter.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

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
        // Сырое сообщение из input rabbit-а
        String rawMassage = rabbitMQDAO.receiveMessage();
        return rawMassage;
    }

    @Override
    public void sendMessage(String rawMassage) throws SQLException {
        if (rawMassage == null) {
            log.info("Ничего не было получено из очереди");
            return;
        }
        // Парсим сырую строку
        String parsedMessage = parseMessage(rawMassage);
        // Отправляем в output результат
        rabbitMQDAO.sendMessage(parsedMessage);
    }

    private String parseMessage(String rawMassage) throws SQLException {
        // Делим весь месседж на переносы строк для удобной обработки каждой из них
        // ЗАМЕЧАНИЕ! Если хотите добавить переносы строк в сообщениях очереди input,
        // используйте enter, а не \n или \r
        // Сложность O(n)
        String[] splitByNewLineMessage = rawMassage.split("\\r\\n");
        // Используем стринг билдер так как будем много раз добавлять символы
        StringBuilder result = new StringBuilder();
        for (String line : splitByNewLineMessage) {
            // Используем метод двух укзателей
            // Запоминаем позицию первого левого и правого указателей, и размер всего токена
            int right = 0;
            int size = line.length();
            // сложность O(n), так как мы не начинаем сначала, а каждый раз продолжаем с места right
            while (right < size) {
                char ch = line.charAt(right);
                // Создаем еще один стринг билдер, для того чтобы в след раз нам не пришлось вызывать метод substring
                // и увеличивать сложность (O-большая) алгоритма
                StringBuilder word = new StringBuilder();
                // Циклом проходимся до одного из этих символов
                while (ch != ' ' && ch != ',' && ch != '.' && ch != '?' && ch != '!' && ch != ';') {
                    word.append(ch);
                    right = right + 1;
                    if (right == size) {
                        break;
                    }
                    ch = line.charAt(right);
                }
                // Если такое слово есть в базе данных плохих слов, то заменяем
                // иначе сохраняем
                // + небольшая оптимизация если слово меньше 3 символов
                // будет работать пока не появится плохое слово из 2-х букв))
                int wordSize = word.length();
                if (wordSize > 2 && tableDAO.isWordExist(word.toString().toLowerCase())) {
                    result.append(word.charAt(0));
                    for (int i = 1; i < wordSize - 1; i++) {
                        result.append("*");
                    }
                    result.append(word.charAt(wordSize - 1));
                } else {
                    result.append(word);
                }
                if (right < size) {
                    result.append(ch);
                    right = right + 1;
                }
            }
            result.append("\r");
        }
        // Суммарная сложность O(n + m) что примерно равно O(n)
        return result.toString();
    }
}
