package com.zooplus.aneeta.salestax.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zooplus.aneeta.salestax.dto.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.List;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
class SalesServiceTest {
    @Mock
    private ItemService itemService;

    @InjectMocks
    private SalesService salesService;


    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        ObjectMapper objectMapper = new ObjectMapper();

        List<Item> items = objectMapper.readValue(new URL("file:src/main/resources/ItemList.json"),
                new TypeReference<List<Item>>() {
                });
        Mockito.when(itemService.getItems()).thenReturn(items);
    }

    private static Stream<Arguments> toRoundOffArgs() {
        return Stream.of(
                Arguments.of(12.33d, 12.35d),
                Arguments.of(12.36d, 12.40d),
                Arguments.of(12.34d, 12.35d)
        );
    }

    @ParameterizedTest
    @MethodSource("toRoundOffArgs")
    @DisplayName("RoundOff to nearest 0.05")
    void testRoundOff(Double input, Double expected) {
        Double newValue = new BigDecimal(input).setScale(2, RoundingMode.HALF_UP).doubleValue();
        Double aDouble = salesService.roundOff(newValue);
        double v = new BigDecimal(aDouble).setScale(2, RoundingMode.HALF_UP).doubleValue();
        Assertions.assertEquals(expected, v);

    }


    @Test
    @DisplayName("Test successful decimal conversion")
    public void test_billing() throws IOException {

        System.setIn(new ByteArrayInputStream(("2\n1\ny\n1\n1\ny\n5\n1\nn").getBytes()));

        salesService.doBilling();
    }
    @Test
    @DisplayName("Test successful decimal conversion")
    public void test_billing_out() throws IOException {

        System.setIn(new ByteArrayInputStream(("2\n1\ny\n1\n1\ny\n5\n1\nn").getBytes()));

        salesService.doBilling();
    }
}