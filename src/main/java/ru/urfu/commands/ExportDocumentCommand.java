package ru.urfu.commands;

import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Component;
import ru.urfu.document.Document;
import ru.urfu.document.DocumentService;
import ru.urfu.export.ExportManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;

/**
 * Команда экспорта документа
 */
@Component
public class ExportDocumentCommand implements Command {
    private final Path OUTPUT_DIR = Path.of(System.getProperty("user.home"), "lessonSOLID");

    private final Scanner scanner = new Scanner(System.in);

    private final DocumentService documentService;

    private final ExportManager exportManager;

    public ExportDocumentCommand(DocumentService documentService,
                                 ExportManager exportManager) {
        this.documentService = documentService;
        this.exportManager = exportManager;
    }

    @Override
    public void execute() {
        System.out.print("Введите номер документа: ");
        int index = Integer.parseInt(scanner.nextLine());

        Optional<Document> documentOptional = documentService.getDocument(index);
        if (documentOptional.isEmpty()) {
            System.out.println("Нет документа с таким номером.");
            return;
        }

        Document document = documentOptional.get();

        System.out.print("Введите формат (txt/pdf): ");
        String format = scanner.nextLine().trim().toLowerCase();

        try {
            Files.createDirectories(OUTPUT_DIR);
        } catch (IOException e) {
            System.out.println("Ошибка создания директории: " + e);
            return;
        }

        Path outputPath = OUTPUT_DIR.resolve(document.name() + "." + format);

        try {
            exportManager.handleExport(format, outputPath.toString(),document.content());
            System.out.println("Экспорт выполнен: " + outputPath);
        } catch (IOException | DocumentException e) {
            System.out.println("Ошибка экспорта: " + e.getMessage());
        }
    }

    @Override
    public String getCommand() {
        return "export";
    }
}
