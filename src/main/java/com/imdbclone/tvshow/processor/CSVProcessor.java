package com.imdbclone.tvshow.processor;

import com.imdbclone.tvshow.handler.ProgressWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@Slf4j
public class CSVProcessor<T> {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    private final ProgressWebSocketHandler progressWebSocketHandler;

    @Autowired
    public CSVProcessor(ProgressWebSocketHandler progressWebSocketHandler) {
        this.progressWebSocketHandler = progressWebSocketHandler;
    }

    @Transactional
    public <E> UUID processCsv(MultipartFile multipartFile, Function<CSVRecord, E> mapper, Consumer<List<E>> saveFunction) {
        if (multipartFile.isEmpty()) {
            throw new RuntimeException("File is Empty");
        }
        UUID uploadId = UUID.randomUUID();
        executorService.submit(() -> parseAndStoreCSV(multipartFile, uploadId, mapper, saveFunction));
        return uploadId;
    }

    @Transactional
    private <E> void parseAndStoreCSV(MultipartFile multipartFile, UUID uploadId, Function<CSVRecord, E> mapper, Consumer<List<E>> saveFunction) {
        List<E> entities = new ArrayList<>();

        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
                CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .build())
        ) {

            List<CSVRecord> csvRecords = csvParser.getRecords();
            int totalRecords = csvRecords.size();
            int batchSize = 10;
            int count = 0;

            for (CSVRecord csvRecord : csvRecords) {
                E entity = mapper.apply(csvRecord);
                entities.add(entity);
                count++;
                //Thread.sleep(200);
                if (entities.size() == batchSize) {
                    saveFunction.accept(entities);
                    entities.clear();
                    updateProgress(uploadId, count, totalRecords);
                }
            }

            if (!entities.isEmpty()) {
                //Thread.sleep(200);
                saveFunction.accept(entities);
                entities.clear();
                updateProgress(uploadId, count, totalRecords);
            }

        } catch (Exception e) {
            log.error("CSV Error while processing : {} ", e.getMessage());
        }
    }

    @Transactional
    private void updateProgress(UUID uploadId, int processed, int total) {
        int percentage = (int) ((processed * 100.0) / total);
        AtomicInteger uploadPercentage = new AtomicInteger(percentage);
        progressWebSocketHandler.sendProgressUpdate(uploadId, uploadPercentage);
    }

}
