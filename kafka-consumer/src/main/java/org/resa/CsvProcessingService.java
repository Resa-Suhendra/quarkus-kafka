package org.resa;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

/**
 * Created by Resa S.
 * Date: 21-11-2023
 * Created in IntelliJ IDEA.
 */
@ApplicationScoped
public class CsvProcessingService {
    @Incoming("csv-upload")
    public void process(String csv) {
        System.out.println("==> CSV: " + csv);
    }
}
