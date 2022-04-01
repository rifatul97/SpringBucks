package com.rifatul.SpringBucks.domain.dto;

import com.rifatul.SpringBucks.domain.model.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public final class CategoryDTO {

    public static final class Parent {
        public sealed interface Request {
            record Create(String name) implements Request {}
            record Delete(int id) implements Request {}
            record AddChild(int subId) implements Request {}
            record RemoveChild(int id) implements Request {}
            record GetById(int id) implements Request {}
            record GetChild(int id) implements Request {}
        }
        public sealed interface Response {
            record Get() implements Response {}
        }
    }

    public static final class Child {
        public sealed interface Request {
            record Create(String name) implements Request {}
            record Delete(int id) implements Request {}
        }
    }

    public sealed interface Request {
        record GetAll() implements Request {}
    }

    public sealed interface Response {
        record GetAll() implements Response {};
    }

}