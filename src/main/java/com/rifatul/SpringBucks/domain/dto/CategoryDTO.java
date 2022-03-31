package com.rifatul.SpringBucks.domain.dto;

import com.rifatul.SpringBucks.domain.model.Category;

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
    }

    public static final class Child {
        public sealed interface Request {
            record Create(String name) implements Request {}
            record Delete(int id) implements Request {}
        }
    }

    public sealed interface Request {
        record GetAll();
    }

}
/*
sealed class Category {

    object Parent : CategoryDTO() {

        object Request {
            data class Create (val name : String)
            data class Delete (val id: Int)
            data class AddChild (val Id : Int, val subId : Int)
            data class RemoveChild (val parentId : Int, val subId : Int)
            object GetAll
        }
    }

    sealed class Child {
        sealed class Request : Child() {
            data class Create (val name : String)
            data class Delete (val id : Int)
            object GetAll
            data class GetAllByParentId(val parentId: Int)
        }
    }

    sealed class Request : CategoryDTO() {
        data class GetAll(val categories : List<Child.Request.GetAllByParentId>)
    }


}
 */