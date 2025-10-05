package org.addressbookproject.controller.views;

import org.addressbookproject.entity.AddressBook;
import org.addressbookproject.repository.AddressBookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class AddressBookViewController {

    private final AddressBookRepository addressBookRepository;

    public AddressBookViewController(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    @GetMapping("/addressBooks/{id}/view")
    public String viewAddressBook(@PathVariable Long id, Model model) {
        Optional<AddressBook> optional = addressBookRepository.findById(id);
        if (optional.isPresent()) {
            model.addAttribute("addressBook", optional.get());
            model.addAttribute("buddies", optional.get().getBuddies());
        }
        return "addressBookView";
    }
}
