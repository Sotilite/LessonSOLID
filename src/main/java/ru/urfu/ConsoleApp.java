package ru.urfu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.urfu.commands.Command;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Основной класс консольного приложения.
 * На класс было возложено слишком много ответственности (запуск приложения,
 * обработка команд, их выполнение) - это и темная сторона ООП и нарушение
 * первого принципа SOLID - принципа единой ответственности.
 * Кроме этого, у методов listDocuments и exportDocument были неправильные JavaDoc'и.
 * Теперь же это исправляется за счет реализации классов-команд,
 * реализующих интерфейс Command (название класса говорит само за себя).
 * Также, если вернуться к изначальной версии этого класса, то можно увидеть, что
 * нарушается принцип инверсии зависимостей. То есть верхнеуровневый класс ConsoleApp
 * зависел от экспортера PdfExporter.
 */
@SpringBootApplication
public class ConsoleApp implements CommandLineRunner {
    private final Scanner scanner = new Scanner(System.in);

    private final Map<String, Command> commands;

    @Autowired
    public ConsoleApp(List<Command> commands) {
        this.commands = commands.stream().collect(Collectors.toMap(
                Command::getCommand,
                Function.identity()));
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
        //Сохранив методы обработки команд в ConsoleApp, нарушается второй принцип SOLID -
        //принцип открытой закрытости. При добавлении новой команды мне бы пришлось
        //изменять существующий код. Благодаря созданию интерфейса Command и возможностям
        //Spring'а этого я избежал. Теперь для добавления новой команды нужно всего лишь
        //создать новый класс.

        System.out.println("=== Консольное приложение ===");

        while (true) {
            //Для того чтобы не нарушать принцип открытой закрытости,
            //можно автоматически сгенерировать сроку доступных команд
            String availableCommands = String.join(", ", commands.keySet());
            System.out.println("\nКоманды: " + availableCommands + ", exit");
            System.out.print("> ");
            String cmd = scanner.nextLine().trim();

            if("exit".equals(cmd)) {
                break;
            }
            if(!commands.containsKey(cmd)) {
                System.out.println("Неизвестная команда");
            }
            commands.get(cmd).execute();
        }
    }
}
