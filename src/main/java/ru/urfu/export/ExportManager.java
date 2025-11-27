package ru.urfu.export;

import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Component;
import ru.urfu.export.exporters.Exporter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Менеджер экспорта файлов из текстового формата.
 * Для того чтобы добавить новый формат, нужно сделать всего одно действие -
 * это создать сам класс-экспортер, реализующий интерфейс Exporter
 */
@Component
public class ExportManager {
    /**
     * Хранилище экспортеров, где ключом является формат экспорта
     */
    private final Map<String, Exporter> exporters;

    /**
     * Конструктор, автоматически заполняющий хранилище экспортеров
     */
    public ExportManager(List<Exporter> exporters) {
        this.exporters = exporters.stream()
                .collect(Collectors.toMap(
                        Exporter::getFormat,
                        Function.identity()
                ));
    }

    /**
     * Обработчик экспорта файлов
     */
    public void handleExport(String format, String outputPath, String content)
            throws IOException, DocumentException {
        if(!exporters.containsKey(format)) {
            System.out.println("Неверный формат");
            return;
        }
        exporters.get(format).export(outputPath, content);
    }
}
