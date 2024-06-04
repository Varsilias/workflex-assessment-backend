package tech.danielokoronkwo.workflexassessmentbackend.v1.workation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tech.danielokoronkwo.workflexassessmentbackend.common.constants.WorkationRisk;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class WorkationService {
    private static final Logger logger = LoggerFactory.getLogger(WorkationService.class);
    private final WorkationRepository workationRepository;

    public WorkationService(WorkationRepository workationRepository) {
        this.workationRepository = workationRepository;
    }

    public List<WorkationEntity> getAllWorkations() {
        return workationRepository.findAll();
    }

    public void importWorkationsFromCsv(String fileName) throws IOException, ParseException {
        logger.warn("FilePath: {}", new ClassPathResource(fileName).getPath());

//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(fileName).getInputStream()))) {
            try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/workations.csv"))) {
            String line;
            // Skip header row (optional)
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                WorkationEntity workation = parseWorkationFromLine(line);
                if (workation == null) {
                    continue;
                }
                workationRepository.save(workation);
            }
            logger.info("Workations imported successfully!");
        } catch (FileNotFoundException ex) {
            logger.info("Error: CSV file not found: {}", fileName);
            throw ex;
        } catch (IOException ex) {
            logger.info("Error: An error occurred while processing the CSV file.");
            throw ex;
        }
    }

    private WorkationEntity parseWorkationFromLine(String line) throws ParseException {

        logger.info("Line... {}", line);

        String[] parts = line.split(",");
        if (parts.length != 8) {
            throw new IllegalArgumentException("Invalid workation string format. Expected 8 comma-separated values.");
        }

        if (workationRepository.findOneByWorkationId(parts[0]).isPresent()) {
            logger.info("Workation with ID {} already exists. Skipping.", parts[0]);
            return null;        }

        try {
            WorkationEntity workation = new WorkationEntity();
            workation.setWorkationId(parts[0]);
            workation.setEmployee(parts[1]);
            workation.setOrigin(parts[2]);
            workation.setDestination(parts[3]);


            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            workation.setStart(dateFormat.parse(parts[4]));
            workation.setEnd(dateFormat.parse(parts[5]));


            workation.setWorkingDays(Integer.parseInt(parts[6]));
            workation.setRisk(WorkationRisk.valueOf(parts[7]));

            return workation;
        } catch (ParseException ex) {
            logger.warn("Unable to parse date: {}", ex.getMessage());
            throw ex;
        }
    }
}
