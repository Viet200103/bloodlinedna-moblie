package com.prm.android.bloodlinedna.models.services;

import java.util.ArrayList;
import java.util.List;

public class ServiceApiResponse {
    private List<DnaService> items = new ArrayList<>();
    private int totalPages;
    private int pageNumber;

    public List<DnaService> getItems() {
        return items;
    }

    public void setItems(List<DnaService> items) {
        this.items = items;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
