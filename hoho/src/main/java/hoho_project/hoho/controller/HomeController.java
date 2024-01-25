package hoho_project.hoho.controller;

import hoho_project.hoho.domain.Home;
import hoho_project.hoho.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @Autowired
    private final HomeService homeService;

    @RequestMapping("/")
    public String home(Model model) {
        List<Home> homes = homeService.findHomes();

        model.addAttribute("homes", homes);

        return "home/home";
    }

    @RequestMapping("/home/editList")
    public String editListHome(Model model) {
        List<Home> homes = homeService.findHomes();

        model.addAttribute("homes", homes);

        return "home/editList";
    }

    @RequestMapping("/home/{homeId}/delete")
    public String deleteHome(@PathVariable String homeId) {

        Long homeIdL = Long.parseLong(homeId);
        Home home = homeService.findOne(homeIdL);

        homeService.deleteHome(home);

        return "redirect:/home/editList";
    }

    @GetMapping("/home/save")
    public String createSaveHome() {
        return "home/saveForm";
    }

    @PostMapping("/home/save")
    public String saveHome(@RequestParam MultipartFile file) {

        homeService.saveHome(file);

        return "redirect:/home/editList";
    }

}