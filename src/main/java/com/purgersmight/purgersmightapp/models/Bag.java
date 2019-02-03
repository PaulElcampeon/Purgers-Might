package com.purgersmight.purgersmightapp.models;

import com.purgersmight.purgersmightapp.models.items.Item;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Bag {

    private List<Item> inventory = new ArrayList<>();
}
