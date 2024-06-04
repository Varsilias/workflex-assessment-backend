package tech.danielokoronkwo.workflexassessmentbackend.v1.workation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.workation.import") // Only run if property is set
public class WorkationImportRunner implements ApplicationRunner {

    public WorkationImportRunner(WorkationService workationService) {
        this.workationService = workationService;
    }

    private final WorkationService workationService;

    @Value("${app.workation.file}") // Read file path from application.properties
    private String filePath;

    @Value("${app.workation.import}")
    private String  shouldImportRun; // toggle this in the application properties file to import workation data

    @Override
    public void run(ApplicationArguments args) throws Exception {
        workationService.importWorkationsFromCsv(filePath);
    }
}