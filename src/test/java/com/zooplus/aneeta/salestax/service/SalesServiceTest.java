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
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
class SalesServiceTest {
    @Mock
    private ItemService itemService;

    @InjectMocks
    private SalesService salesService;


    @BeforeEach
    void setUp() throws IOException {
       // MockitoAnnotations.initMocks(this);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Item> items = objectMapper.readValue(new URL("file:src/main/resources/ItemList.json"),
                new TypeReference<List<Item>>() {});
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
    @DisplayName("Test successful receipt creation for chosen items")
    public void test_billing() throws IOException {

        System.setIn(new ByteArrayInputStream(("2\n1\ny\n1\n1\ny\n5\n1\nn").getBytes()));

        salesService.doBilling();
    }

    @Test
    @DisplayName("Throws inputmismatch exception for type mismatch input")
    public void test_billing__input_mismatch_expetion() throws IOException {
        // 1 imported box of chocolates at 10.00
        //given
        System.setIn(new ByteArrayInputStream(("y\n1\nn").getBytes()));
        //then

        assertThrows(InputMismatchException.class, () -> salesService.doBilling());
    }

    @Test
    @DisplayName("Imported items successful reciept creation")
    public void test_billing_imported_items() throws IOException {
        // 1 imported box of chocolates at 10.00
        // 1 imported bottle of perfume at 47.50

        //given
        System.setIn(new ByteArrayInputStream(("4\n1\ny\n3\n1\nn").getBytes()));
        //then
        salesService.doBilling();
    }

    @Test
    @DisplayName("Choose imported as well as normal products receipt creation")
    public void test_billing_mixed_item_list_of_imported_normal_products() throws IOException {
        //1 imported bottle of perfume at 27.99
        //1 bottle of perfume at 18.99
        //1 packet of headache pills at 9.75
        //1 box of imported chocolates at 11.25

        //given
        System.setIn(new ByteArrayInputStream(("7\n1\ny\ny\n8\n1\ny\n9\n1\ny10\n1\nn").getBytes()));
        //then
        salesService.doBilling();
    }

    @Test
    @DisplayName("Same item twice successful reciept creation")
    public void test_billing_duplicate_items() throws IOException {
        // 1 imported box of chocolates at 10.00

        //given
        System.setIn(new ByteArrayInputStream(("4\n1\ny\n4\n1\nn").getBytes()));
        //then
        salesService.doBilling();
    }

    @Test
    @DisplayName("Throws exception for wrong input")
    public void test_billing_exception() throws IOException {
        // 1 imported box of chocolates at 10.00
        //given
        System.setIn(new ByteArrayInputStream(("4\n1\ny\nnull").getBytes()));
        //then

        assertThrows(NoSuchElementException.class, () -> salesService.doBilling());
    }

    @Test
    @DisplayName("Test getaDouble method")
    public void should_return_proper_values_in_calculation(){
        //given
        Item item = new Item();
        item.setTotalPrice(47.50);
        Double taxAmount = item.getTotalPrice() * 10.0d / 100;
        AtomicReference<Double> salesTaxAmount = new AtomicReference<>(0.0d);

        //when
        Double aDouble = salesService.getaDouble(salesTaxAmount.get(), item, taxAmount);
        assertEquals(4.75,aDouble);
    }


}