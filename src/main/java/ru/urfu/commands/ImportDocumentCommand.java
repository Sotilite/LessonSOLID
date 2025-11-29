package ru.urfu.commands;

import org.springframework.stereotype.Component;
import ru.urfu.document.DocumentService;

import java.io.IOException;
import java.util.Scanner;

/**
 * Команда импорта документа
 */
@Component
public class ImportDocumentCommand implements Command {
    private final Scanner scanner = new Scanner(System.in);

    private final DocumentService documentService;

    public ImportDocumentCommand(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public void execute() {
        System.out.print("Введите путь к txt файлу: ");
        String path = scanner.nextLine();

        try {
            documentService.importTxt(path);
        } catch (IOException e) {
            System.out.println("Ошибка импорта: " + e.getMessage());
        }
    }

    @Override
    public String getCommand() {
        return "import";
    }
}
