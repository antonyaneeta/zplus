package com.zooplus.aneeta.salestax.service;

import com.zooplus.aneeta.salestax.domain.ItemDomain;
import com.zooplus.aneeta.salestax.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemDomain itemDomain;

    public List<Item> getItems() throws IOException {
        return itemDomain.getItems();
    }
}
