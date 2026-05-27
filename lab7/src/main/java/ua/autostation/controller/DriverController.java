package ua.autostation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.autostation.service.DriverService;

@Controller
@RequestMapping("/")
public class DriverController {

    @Autowired
    private DriverService driverService;

    // Головна сторінка зі списком водіїв
    @GetMapping
    public String showMainPage(Model model) {
        model.addAttribute("drivers", driverService.getAllDrivers());
        return "index"; // Відкриє index.ftlh
    }

    // обробка форми додавання нового водія та рейсу
    @PostMapping("/add-driver")
    public String addDriver(@RequestParam String name,
                            @RequestParam String destination,
                            @RequestParam int weight,
                            @RequestParam String cargoType) {
        driverService.createDriverWithTrip(name, destination, weight, cargoType);
        return "redirect:/"; // Повертає користувача на головну сторінку після успішного додавання
    }

    // Видалення водія
    @GetMapping("/delete/{id}")
    public String deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return "redirect:/";
    }
}