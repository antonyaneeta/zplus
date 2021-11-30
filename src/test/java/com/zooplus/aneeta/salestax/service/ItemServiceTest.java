package com.zooplus.aneeta.salestax.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zooplus.aneeta.salestax.domain.ItemDomain;
import com.zooplus.aneeta.salestax.dto.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemDomain itemDomain;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void Should_return_items() throws IOException {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        List<Item> items = objectMapper.readValue(new URL("file:src/main/resources/ItemList.json"),
                new TypeReference<List<Item>>() {
                });
        when(itemDomain.getItems()).thenReturn(items);

        //when
        List<Item> fullItems = itemService.getItems();

        //then
        Assertions.assertNotNull(fullItems);
    }

    @Test
    public void Should_throw_exception_when_file_not_available() throws IOException {
        //given
        when(itemDomain.getItems()).thenThrow(FileNotFoundException.class);

        //then
        assertThrows(IOException.class, () -> itemService.getItems());
    }
}