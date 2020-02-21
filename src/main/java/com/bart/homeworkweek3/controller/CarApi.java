package com.bart.homeworkweek3.controller;

import com.bart.homeworkweek3.model.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/cars")
public class CarApi {
    private List<Car> carList;


    public CarApi() {
        this.carList = new ArrayList<>();
        carList.add(new Car(1L, "Kia", "Sportage", "white"));
        carList.add(new Car(2L, "Kia", "Ceed", "grey"));
        carList.add(new Car(3L, "Kia", "Sorento", "blue"));
        carList.add(new Car(4L, "Hyundai", "Tucson", "blue"));
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars() {
        return new ResponseEntity<>(carList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Optional<Car> first = carList.stream().filter(a -> a.getId() == id).findFirst();
        if (first.isPresent()) {
            return new ResponseEntity(first.get(), HttpStatus.OK);
        }

        return new ResponseEntity("Car with this id number doesn't exist",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/colors")
    public ResponseEntity<List<Car>> getCarsByColor(@RequestParam String color){

        List<Car> foundColors = carList.stream().filter(a -> a.getColor().equals(color.toLowerCase())).collect(Collectors.toList());

        if (foundColors.size()>0){
            return new ResponseEntity(foundColors,HttpStatus.OK);
        }
        return new ResponseEntity("Car with this color doesnt exist", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car newCar){
        Optional<Car> first = carList.stream().filter(a -> a.getId() == newCar.getId()).findFirst();
        if(!first.isPresent()){
            carList.add(newCar);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<Car> editCar(@RequestBody Car car){
        Optional<Car> first = carList.stream().filter(a -> a.getId() == car.getId()).findFirst();
        if (first.isPresent()){
            carList.remove(first.get());
            carList.add(car);
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity("Car with this id number doesn't exist",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> modPosition(@PathVariable long id, @RequestParam(required = false ,defaultValue = "") String mark, @RequestParam(required = false, defaultValue = "") String model,
                            @RequestParam(required = false,defaultValue = "") String color) {
        Optional<Car> first = carList.stream().filter(a -> a.getId() == id).findFirst();
        if (first.isPresent()) {

            if (!mark.equals("")) {
                first.get().setMark(mark);
            }
            if (!model.equals("")) {
                first.get().setModel(model);
            }
            if (!color.equals("")) {
                first.get().setColor(color);
            }
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity("Car with this id number doesn't exist",HttpStatus.NOT_FOUND);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Car> deleteCar(@PathVariable long id){
        Optional<Car> first = carList.stream().filter(a -> a.getId() == id).findFirst();
        if (first.isPresent()){
            carList.remove(first.get());
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity("Car with this id number doesn't exist",HttpStatus.NOT_FOUND);
    }


}
