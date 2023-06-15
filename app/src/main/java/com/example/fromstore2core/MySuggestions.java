package com.example.fromstore2core;

import com.example.fromstore2core.Search.SearchResult;

import java.util.List;

public class MySuggestions {
    // Contains suggestions specific to search query
    public static List<SearchResult> newSuggestions;

    // Contains ids of newSuggestions
    public static List<Integer> newSuggestions_id;

    public static int [] moreresults = new int[20];
}
