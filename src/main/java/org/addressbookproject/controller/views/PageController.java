package org.addressbookproject.controller.views;

import org.addressbookproject.entity.AddressBook;
import org.addressbookproject.repository.AddressBookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {

    private final AddressBookRepository addressBookRepository;

    public PageController(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("addressBooks", addressBookRepository.findAll());
        return "homepage";
    }

    @GetMapping("/addressBooks/{id}/view")
    public String viewAddressBook(@PathVariable Long id, Model model) {
        AddressBook book = addressBookRepository.findById(id).orElse(null);
        model.addAttribute("book", book);
        return "view";
    }

    @GetMapping("/addressBooks/{id}/addBuddy")
    public String addBuddyForm(@PathVariable Long id, Model model) {
        model.addAttribute("addressBookId", id);
        return "addBuddy";
    }
}
