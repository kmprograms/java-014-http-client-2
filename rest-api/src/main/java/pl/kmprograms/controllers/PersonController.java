package pl.kmprograms.controllers;

import org.springframework.web.bind.annotation.*;
import pl.kmprograms.model.Person;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {

    @GetMapping
    public List<Person> getAll() {
        return List.of(
                Person.builder().name("A").age(10).build(),
                Person.builder().name("B").age(20).build()
        );
    }

    @PostMapping
    public Person add(@RequestBody Person person) {
        person.setAge(person.getAge() + 1);
        return person;
    }
}
