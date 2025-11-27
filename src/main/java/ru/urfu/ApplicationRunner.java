package ru.urfu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.commands.Command;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Обработчик запуска основного цикла выполнения консольного приложения.
 * Если бы я просто перенес из ConsoleApp методы по обработке команд, то
 * нарушил бы второй принцип SOLID - принцип открытой закрытости.
 * При добавлении новой команды мне бы пришлось изменять существующий код.
 * Благодаря созданию интерфейса Command и возможностям Spring'а этого я избежал.
 * Теперь для добавления новой команды нужно всего лишь создать новый класс.
 */
@Component
public class ApplicationRunner {
    private final Scanner scanner = new Scanner(System.in);

    private final Map<String, Command> commands;

    @Autowired
    public ApplicationRunner(List<Command> commands) {
        this.commands = commands.stream().collect(Collectors.toMap(
                Command::getCommand,
                Function.identity()));
    }

    /**
     * Запустить основной цикл выполнения консольного приложения
     */
    public void run() {
        System.out.println("=== Консольное приложение ===");

        while (true) {
            System.out.println("\nКоманды: import, list, create, export, exit");
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
