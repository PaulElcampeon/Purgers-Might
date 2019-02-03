package com.purgersmight.purgersmightapp;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Bag {

    private List<Item> inventory = new ArrayList<>();
}
