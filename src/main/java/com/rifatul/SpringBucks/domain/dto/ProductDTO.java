package com.rifatul.SpringBucks.domain.dto;

public final class ProductDTO {

    public abstract sealed interface Request {
        record Create (String name, double price, int categoryId) implements Request {}
        record Update (int id, double price, int categoryId) implements Request {}
        record Delete (int id) implements Request {}
    }


}

/**
 sealed class Request : ProductDTO() {;
 data class Create(val name : String, val price : Double, val categoryId : Int)
 data class Update(val id : Int, val price : Double, val categoryId : Int)
 data class Delete(val id : Int)
 data class GetById(val id : Int)
 data class GetByStartingName(val name : String)
 data class GetByCategoryId(val categoryId : Int)
 }

 sealed class Response : ProductDTO() {
 data class Public (val products : List<Product>) : Response()
 }
 */