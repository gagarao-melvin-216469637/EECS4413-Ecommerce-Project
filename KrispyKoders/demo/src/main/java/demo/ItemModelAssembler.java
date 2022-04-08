package demo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class ItemModelAssembler implements RepresentationModelAssembler<Item, EntityModel<Item>> {

  
  public EntityModel<Item> toModel(Item item) {

    return EntityModel.of(item, //
        linkTo(methodOn(EmployeeController.class).one(item.getId())).withSelfRel(),
        linkTo(methodOn(EmployeeController.class).all()).withRel("items"));
  }





}