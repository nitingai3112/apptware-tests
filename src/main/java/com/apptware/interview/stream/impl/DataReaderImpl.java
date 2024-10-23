package com.apptware.interview.stream.impl;

import com.apptware.interview.stream.DataReader;
import com.apptware.interview.stream.PaginationService;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataReaderImpl implements DataReader {

  @Autowired private PaginationService paginationService;

  @Override
  public Stream<String> fetchLimitadData(int limit) {
    return fetchPaginatedDataAsStream().limit(limit);
  }

  @Override
  public Stream<String> fetchFullData() {
    return fetchPaginatedDataAsStream();
  }

  /**
   * This method is where the candidate should add the implementation. Logs have been added to track
   * the data fetching behavior. Do not modify any other areas of the code.
   */
  private @Nonnull Stream<String> fetchPaginatedDataAsStream() {
    log.info("Fetching paginated data as stream.");

    // Start with page 1 and keep fetching until an empty page is encountered
    int pageNumber = 1;
    Stream<String> dataStream = Stream.empty();

    List<String> pageData;
    do {
      pageData = paginationService.getPaginatedData(pageNumber++, 100);
      log.info("Fetched page {}: {} items", pageNumber - 1, pageData.size());
      dataStream = Stream.concat(dataStream, pageData.stream());
    } while (!pageData.isEmpty()); // Stop when an empty page is returned

    return dataStream.peek(item -> log.info("Fetched Item: {}", item));
  }

}
