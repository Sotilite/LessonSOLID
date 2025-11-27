package ru.urfu.commands;

import org.springframework.stereotype.Component;
import ru.urfu.document.Document;
import ru.urfu.document.DocumentService;

import java.util.List;
import java.util.Scanner;

/**
 * Команда получения списка документов
 */
@Component
public class ListDocumentsCommand implements Command {
    private final Scanner scanner = new Scanner(System.in);

    private final DocumentService documentService;

    public ListDocumentsCommand(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public void execute() {
        List<Document> documents = documentService.list();
        if (documents.isEmpty()) {
            System.out.println("Документов нет");
            return;
        }
        int i = 0;
        for (Document doc : documents) {
            System.out.println(i + ": " + doc.name());
            i++;
        }
    }

    @Override
    public String getCommand() {
        return "list";
    }
}
