package ru.urfu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс консольного приложения.
 * На класс было возложено слишком много ответственности (запуск приложения,
 * обработка команд, их выполнение) - это и темная сторона ООП и нарушение
 * первого принципа SOLID - принципа единой ответственности.
 * Кроме этого, у методов listDocuments и exportDocument были неправильные JavaDoc'и.
 * Теперь же это исправляется за счет реализации классов-команд,
 * реализующих интерфейс Command (название класса говорит само за себя)
 */
@SpringBootApplication
public class ConsoleApp implements CommandLineRunner {
    private final ApplicationRunner applicationRunner;

    @Autowired
    public ConsoleApp(ApplicationRunner applicationRunner) {
        this.applicationRunner = applicationRunner;
    }

    /**
     * Точка входа приложения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(ConsoleApp.class, args);
    }

    @Override
    public void run(String... args) {
        applicationRunner.run();
    }
}
