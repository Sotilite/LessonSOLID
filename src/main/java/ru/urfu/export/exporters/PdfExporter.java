package ru.urfu.export.exporters;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Экспортёр текста в PDF.
 */
@Component
public class PdfExporter implements Exporter {
    @Override
    public void export(String outputPath, String content) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, outputStream);

            pdf.open();
            pdf.add(new Paragraph(content));
            pdf.close();
        } catch (DocumentException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    @Override
    public String getFormat() {
        return "pdf";
    }
}
