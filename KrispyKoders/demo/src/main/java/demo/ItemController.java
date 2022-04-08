package demo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ItemController {

    private final ItemRepository repository;
    private ItemModelAssembler assembler;
    
    public ItemController(ItemRepository repository, ItemModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
        
    }

    @GetMapping("/items")
    CollectionModel<EntityModel<Item>> all() {

      List<EntityModel<Item>> items = repository.findAll().stream() //
          .map(assembler::toModel) //
          .collect(Collectors.toList());

      return CollectionModel.of(items, linkTo(methodOn(ItemController.class).all()).withSelfRel());
    }
    
    //TODO: implement POST, DELETE and PUT operations for items
    

    
}