package com.halo.eventer.user.controller;


import com.halo.eventer.user.dto.MissingPersonDto;
import com.halo.eventer.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/lostItem/{itemId}")
    public ResponseEntity<?> getLostItem(@PathVariable(name="itemId") Long itemId){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getLostItem(itemId));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/lostItem")
    public ResponseEntity<?> getAllLostItems(){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getAllLostItems());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    @PostMapping("/missingPerson")
    public ResponseEntity<?> uploadMissingPerson(@RequestBody MissingPersonDto missingPerson){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.uploadMissingPerson(missingPerson));
    }

    @GetMapping("/missingPerson")
    public ResponseEntity<?> getMissingPerson(){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getAllMissingPeople());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/missingPerson/{personId}")
    public ResponseEntity<?> getMissingPerson(@PathVariable(name="personId") Long personId){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getMissingPerson(personId));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

}
