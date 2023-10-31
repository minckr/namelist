package ie.tcd.scss.namelist.controller;

import ie.tcd.scss.namelist.domain.NameDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/namelist")

public class NamelistController {
    private List<String> nameList = new ArrayList<>();


    @PostMapping("/reset")
    public void resetList() {
        nameList.clear();
    }


    @PostMapping("/names")
    public ResponseEntity<Object> addName(@RequestBody NameDto nameDto) {
        String name = nameDto.getName();
        if (name != null && !name.isEmpty()) {
            if (!nameList.contains(name)) {
                nameList.add(name);
                return ResponseEntity.status(HttpStatus.CREATED).body("Name added.");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("Name already exists.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Empty names are not allowed.");
        }
    }


    @GetMapping("/names")
    public ResponseEntity<Object> getNames() {
        if (nameList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("(List of names is empty)");
        } else {
            nameList.sort(String::compareTo);
            String names = String.join(", ", nameList);
            return ResponseEntity.status(HttpStatus.OK).body(names);
        }
    }


}
