package com.AMS.backendDevTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.AMS.backendDevTest.model.dtos.ProductDetailDto;
import com.AMS.backendDevTest.service.SimilarProdsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import reactor.core.publisher.Flux;

@RestController
@Api(tags = {"Similar Products"})
public class SimilarProductsController {
	
	@Autowired
	private SimilarProdsService similarProdsService;
	
	@GetMapping(value = "product/{productId}/similar")
	@ApiOperation(produces = "application/json", value = "Get details of similar products to the given one", httpMethod ="GET", response = ProductDetailDto.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"), 
		@ApiResponse(code = 404, message = "Product Not Found")})
	public ResponseEntity<Flux<ProductDetailDto>> getSimilarProdsById(@PathVariable(value = "productId") String id){
		ResponseEntity<Flux<ProductDetailDto>> response = null;
		try {
			response = ResponseEntity.status(HttpStatus.OK).body(similarProdsService.getSimilarProds(id));
		}catch (HttpClientErrorException e ) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Flux.empty());
		}catch (Exception e) {
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Flux.empty());
		}
		
		return response;
	}
}
