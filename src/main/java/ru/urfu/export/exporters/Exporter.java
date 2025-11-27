package ru.urfu.export.exporters;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

/**
 * Экспортер текстовых документов
 */
public interface Exporter {
    /**
     * Экспортировать содержимое в файл
     */
    void export(String outputPath, String content)
            throws DocumentException, IOException;

    /**
     * Получить формат экспорта
     */
    String getFormat();
}
