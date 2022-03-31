package com.rifatul.SpringBucks.domain.model;

import java.util.List;

public record ParentCategory(int id, String name, List<Category> subCategories) {
}
