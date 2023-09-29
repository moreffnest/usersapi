package com.moreffnest.usersapi.controllers;

import com.moreffnest.usersapi.dto.LocalDateRange;
import com.moreffnest.usersapi.hateoas.UserModelAssembler;
import com.moreffnest.usersapi.models.User;
import com.moreffnest.usersapi.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private UserModelAssembler userModelAssembler;
    private PagedResourcesAssembler<User> pagedResourcesAssembler;

    @GetMapping("/users")
    public ResponseEntity<PagedModel<EntityModel<User>>> getUsers(Pageable pageable) {
        PagedModel<EntityModel<User>> pagedModel = pagedResourcesAssembler.
                toModel(userService.findAllUsers(pageable), userModelAssembler);

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<EntityModel<User>> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(
                userModelAssembler.toModel(userService.findUserById(id)));
    }

    @GetMapping("/users/search")
    public ResponseEntity<PagedModel<EntityModel<User>>> getUsersByDateRange(
            @RequestBody LocalDateRange range,
            Pageable pageable) {
        PagedModel<EntityModel<User>> pagedModel = !range.isValid() ? PagedModel.empty() :
                pagedResourcesAssembler.toModel(userService
                        .findAllUsersByBirthDateRange(pageable, range.getFrom(), range.getTo()),
                        userModelAssembler);
        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user) {
        EntityModel<User> entityModel = userModelAssembler.toModel(userService.addUser(user));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> replaceUser(@Valid @RequestBody User user, @PathVariable Long id) {
        EntityModel<User> entityModel = userModelAssembler.toModel(userService.replaceUser(id, user));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }


    @PatchMapping("/users/{id}")
    public ResponseEntity<?> changeUser(@RequestBody User user, @PathVariable Long id) {
        EntityModel<User> entityModel = userModelAssembler.toModel(userService.changeUser(id, user));

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }

}
