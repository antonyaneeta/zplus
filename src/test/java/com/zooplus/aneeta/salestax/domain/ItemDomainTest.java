package com.zooplus.aneeta.salestax.domain;

import com.zooplus.aneeta.salestax.dto.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class ItemDomainTest {

    private ItemDomain itemDomain = new ItemDomain();

    @BeforeEach
    void setUp() throws IOException {

    }

    @Test
    void getItems() throws IOException {
        List<Item> items = itemDomain.getItems();
        Assertions.assertNotNull(items);
        Item item = items.stream().findFirst().get();
        Assertions.assertEquals("1", item.getItemId(), "The first item is " + item.getItemId() + "and item name is" + item.getItemName());
    }
}