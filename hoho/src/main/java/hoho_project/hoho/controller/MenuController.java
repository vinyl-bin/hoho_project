package hoho_project.hoho.controller;

import hoho_project.hoho.controller.form.MenuForm;
import hoho_project.hoho.domain.Home;
import hoho_project.hoho.domain.Menu;
import hoho_project.hoho.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MenuController {

    @Autowired
    private final MenuService menuService;

    //메뉴 홈화면
    @RequestMapping("/menu")
    public String menu(Model model) {
        List<Menu> menus = menuService.findMenus();

        model.addAttribute("menus", menus);

        List<String> types = new ArrayList<>();
        for(Menu menu : menus) {
            if (!types.contains(menu.getMenu_type())) {
                types.add(menu.getMenu_type());
            }
        }
        model.addAttribute("types", types);

        return "menu/menu";
    }

    //메뉴 수정 리스트
    @RequestMapping("/menu/editList")
    public String menuList(Model model) {
        List<Menu> menus = menuService.findMenus();

        model.addAttribute("menus", menus);

        List<String> types = new ArrayList<>();
        for(Menu menu : menus) {
            if (!types.contains(menu.getMenu_type())) {
                types.add(menu.getMenu_type());
            }
        }
        model.addAttribute("types", types);

        return "menu/editList";
    }

    //메뉴 추가
    @GetMapping("/menu/save")
    public String createSaveMenu(Model model) {

        model.addAttribute("form", new MenuForm());
        return "menu/saveForm";
    }

    @PostMapping("/menu/save")
    public String saveMenu(@Validated MenuForm form) {
        menuService.saveMenu(form.getMenu_type(), form.getFileSave(), form.getMenu_name(), form.getMenu_ingredient(), form.getMenu_price());

        return "redirect:/menu/editList";
    }

    //수정
    @GetMapping("/menu/{id}/update")
    public String updateMenuForm(@PathVariable("id") Long id, Model model) {
        //menu 정보 불러오기
        Menu menu = menuService.findOne(id);

        MenuForm menuForm = new MenuForm();
        menuForm.setMenu_type(menu.getMenu_type());
        menuForm.setFileName(menu.getMenu_imgStoreName());
        menuForm.setFilePath(menu.getMenu_imgPath());
        menuForm.setMenu_name(menu.getMenu_name());
        menuForm.setMenu_ingredient(menu.getMenu_ingredient());
        menuForm.setMenu_price(menu.getMenu_price());

        model.addAttribute("form", menuForm);
        return "menu/update";
    }

    @PostMapping("/menu/{id}/update")
    public String updateMenu(@PathVariable String id, @ModelAttribute("form") MenuForm menuForm) {

        Long menuId = Long.parseLong(id);

        menuService.updateMenu(menuId, menuForm.getMenu_type(), menuForm.getFileName(), menuForm.getFilePath(), menuForm.getFileSave(), menuForm.getMenu_name(), menuForm.getMenu_ingredient(), menuForm.getMenu_price());

        return "redirect:/menu/editList";
    }

    //삭제
    @RequestMapping("/menu/{id}/delete")
    public String deleteMenu(@PathVariable String id) {

        Long menuId = Long.parseLong(id);
        Menu menu = menuService.findOne(menuId);

        menuService.deleteMenu(menu);

        return "redirect:/menu/editList";
    }



}
