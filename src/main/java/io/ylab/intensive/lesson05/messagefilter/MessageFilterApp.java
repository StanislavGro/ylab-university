package io.ylab.intensive.lesson05.messagefilter;

import io.ylab.intensive.lesson05.messagefilter.dao.TableDAO;
import io.ylab.intensive.lesson05.messagefilter.service.MessageService;
import io.ylab.intensive.lesson05.messagefilter.util.FileConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
public class MessageFilterApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        TableDAO tableDAO = applicationContext.getBean(TableDAO.class);
        MessageService messageService = applicationContext.getBean(MessageService.class);
        try {
            File inputFile = new File(".\\src\\main\\resources\\files\\input_file.txt");
            File file = FileConverter.createObsceneWordsFile(inputFile);
            tableDAO.fillTableFromFile(file);
            String message;
            while ((message = messageService.getMessage()) != null) {
                messageService.sendMessage(message);
            }
        } catch (IOException | SQLException e) {
            log.error(e.getMessage());
        } finally {
            applicationContext.close();
        }
    }
}
