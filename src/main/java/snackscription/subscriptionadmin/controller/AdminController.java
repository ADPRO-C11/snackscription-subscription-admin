package snackscription.subscriptionadmin.controller;

import snackscription.subscriptionadmin.model.AdminSubscription;
import snackscription.subscriptionadmin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService service;

    @GetMapping("")
    public String adminPage(Model model) {
        AdminSubscription adminSubscription = new AdminSubscription();
        model.addAttribute("adminSubscription", adminSubscription);
        return "admin";
    }

    @PostMapping("/create")
    public String createAdminSubscription(@ModelAttribute AdminSubscription adminSubscription, Model model) {
        service.create(adminSubscription);
        return "redirect:admin";
    }

    @GetMapping("/list")
    public String subscriptionListPage(Model model){
        List<AdminSubscription> adminSubscriptions = service.findAll();
        model.addAttribute("adminSubscriptions", adminSubscriptions);
        return "adminSubscriptionList";
    }
}