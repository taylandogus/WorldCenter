package bulut.worldcenter.controller;

import bulut.worldcenter.model.Transaction;
import bulut.worldcenter.service.StockService;
import bulut.worldcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class TranstactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private StockService stockService;

    @GetMapping("/transaction")  //format hatasi yuzunden formatlama
    public String transaction(Model model) {
        List<Transaction> transactions = stockService.getUserTransaction();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");


        transactions.forEach(transaction -> {
            String formattedDate = transaction.getTransactionDate().format(formatter);
            transaction.setFormattedDate(formattedDate);
        });

        model.addAttribute("transactions", transactions);
        return "transaction";
    }



}
