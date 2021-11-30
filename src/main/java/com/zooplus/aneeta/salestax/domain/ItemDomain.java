package com.zooplus.aneeta.salestax.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zooplus.aneeta.salestax.dto.Item;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Component
public class ItemDomain {

    public List<Item> getItems() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Item> items = objectMapper.readValue(new URL("file:src/main/resources/ItemList.json"),
                new TypeReference<List<Item>>() {});

        return items;
    }


}
