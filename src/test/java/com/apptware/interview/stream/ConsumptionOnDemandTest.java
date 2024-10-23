package com.apptware.interview.stream;

import com.apptware.interview.stream.impl.DataReaderImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ConsumptionOnDemandTest {

    @Mock
    private PaginationService paginationService;

    @InjectMocks
    private DataReaderImpl dataReader;

    private static final List<String> PAGE_1_DATA = List.of("Item 1", "Item 2", "Item 3");
    private static final List<String> PAGE_2_DATA = List.of("Item 4", "Item 5");
    private static final List<String> EMPTY_PAGE = List.of();
    private static final int FULL_DATA_SIZE = PAGE_1_DATA.size() + PAGE_2_DATA.size(); // Total 5 items

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(paginationService.getPaginatedData(1, 100)).thenReturn(PAGE_1_DATA);
        when(paginationService.getPaginatedData(2, 100)).thenReturn(PAGE_2_DATA);
        when(paginationService.getPaginatedData(3, 100)).thenReturn(EMPTY_PAGE);
    }


    @Test
    void testConsumptionOnDemand() {
        int limit = 2;
        List<String> limitedData = dataReader.fetchLimitadData(limit).toList();
        Assertions.assertThat(limitedData).hasSize(limit);

        List<String> fullData = dataReader.fetchFullData().toList();
        Assertions.assertThat(fullData).hasSize(FULL_DATA_SIZE);
    }
}
