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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemDomain itemDomain;

    @BeforeEach
    void setUp() {
       // itemService = new ItemService(itemDomain);

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
}