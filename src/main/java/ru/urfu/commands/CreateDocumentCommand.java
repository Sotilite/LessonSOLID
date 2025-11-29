package ru.urfu.commands;

import org.springframework.stereotype.Component;
import ru.urfu.document.DocumentService;

import java.util.Scanner;

/**
 * Команда создания документа.
 */
@Component
public class CreateDocumentCommand implements Command {
    private final Scanner scanner = new Scanner(System.in);

    private final DocumentService documentService;

    public CreateDocumentCommand(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public void execute() {
        //Дабы не нарушить полиморфизм и принцип подстановки Барбары Лисков
        //вместо переопределения JavaDoc'а детали реализации можно описать внутри метода.
        //Создаёт документ через ввод данных в консоли.
        //Сначала пользователь вводит имя, затем — содержимое документа.
        //Ввод содержимого продолжается до пустой строки.

        System.out.print("Введите имя документа: ");
        String name = scanner.nextLine().trim();

        System.out.println("Введите содержимое документа (пустая строка — завершить ввод):");

        StringBuilder content = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break; // окончание ввода
            content.append(line).append(System.lineSeparator());
        }

        documentService.createDocument(name, content.toString());

        System.out.println("Документ создан и сохранён в памяти.");
    }

    @Override
    public String getCommand() {
        return "create";
    }
}
