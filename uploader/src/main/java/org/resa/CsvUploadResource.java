package org.resa;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Created by Resa S.
 * Date: 21-11-2023
 * Created in IntelliJ IDEA.
 */

@Path("/upload")
public class CsvUploadResource {

    @Inject
    @Channel("csv-upload")
    Emitter<String> csvUploadEmitter;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadCsv(InputStream file) {
        // Baca file CSV sebagai string
        String csvData = readCsvFile(file);

        System.out.println("==> CSV: " + csvData);
        // Kirim pesan ke topik Kafka
        csvUploadEmitter.send(csvData);
    }

    private String readCsvFile(InputStream file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file", e);
        }
    }
}
