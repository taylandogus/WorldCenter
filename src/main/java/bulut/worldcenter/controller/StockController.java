package bulut.worldcenter.controller;

import bulut.worldcenter.model.Stock;
import bulut.worldcenter.model.User;
import bulut.worldcenter.repository.StockRepository;
import bulut.worldcenter.repository.UserRepository;
import bulut.worldcenter.service.StockService;
import bulut.worldcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private UserService userService;

    @PostMapping("/addstock")
    public String addStock(@ModelAttribute("stock") Stock stock) {
         stockService.saveStock(stock);
         return "adminhome";
    }

    @GetMapping("/adminallstock")
    public String allStock(Model model) {
        List<Stock> stocks = stockService.getAllStocks();
        model.addAttribute("stocks", stocks);
        return "adminallstock";
    }

    @GetMapping("/deletestock/{id}")
    public String deleteStock(@PathVariable Long id, Model model) {
        stockService.deleteStock(id);
        return "redirect:/adminallstock";
    }

    @PostMapping("/updatestock")
    public String updateStock(@ModelAttribute("stock") Stock updatedStock){
        Stock existingStock = stockService.getStockById(updatedStock.getId());

        if(updatedStock.getSymbol() != null && !updatedStock.getSymbol().isEmpty()){
            existingStock.setSymbol(updatedStock.getSymbol());
        }

        if(updatedStock.getName() != null && !updatedStock.getName().isEmpty()){
            existingStock.setName(updatedStock.getName());
        }

        if(updatedStock.getPrice() != 0.0){
            existingStock.setPrice(updatedStock.getPrice());
        }

        stockService.updateStock(existingStock);
        return "redirect:/adminallstock";

    }

    @GetMapping("/editstock/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Stock stock = stockService.getStockById(id);
        model.addAttribute("stock", stock);
        return "editstock";
    }

    @PostMapping("/buy/{id}")
    public String buyStock(@PathVariable("id") Long stockId,
                           @RequestParam("quantity") int quantity,
                           RedirectAttributes redirectAttributes) {
        try {

            stockService.buyStock(stockId, quantity);

            redirectAttributes.addFlashAttribute("successMessage", "Hisse başarıyla satın alındı.");
        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }


        return "redirect:/userstock";
    }

    @PostMapping("/sell/{id}")
    public String sellStock(@PathVariable("id") Long stockId, @RequestParam ("quantity") int quantity , RedirectAttributes redirectAttributes) {
        try {
            stockService.sellStock(stockId, quantity);
            redirectAttributes.addFlashAttribute("successMessage","Hisse basariyle satildi");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/userstock";
    }















}
