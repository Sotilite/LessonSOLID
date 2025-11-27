package ru.urfu.export.exporters;

import com.itextpdf.text.DocumentException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Экспортер текста в TXT
 */
@Component
public class TxtExporter implements Exporter {
    @Override
    public void export(String outputPath, String content) throws DocumentException, IOException {
        Files.writeString(Path.of(outputPath), content);
    }

    @Override
    public String getFormat() {
        return "txt";
    }
}
