package bulut.worldcenter.service;

import bulut.worldcenter.model.Stock;
import bulut.worldcenter.model.Transaction;
import bulut.worldcenter.model.User;
import bulut.worldcenter.model.UserStock;
import bulut.worldcenter.repository.StockRepository;
import bulut.worldcenter.repository.TransactionRepository;
import bulut.worldcenter.repository.UserRepository;
import bulut.worldcenter.repository.UserStockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStockRepository userStockRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public List<UserStock> getAllUserStocks(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        return userStockRepository.findByUser(user);
    }


    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }


    public void deleteStock(long id) {
        stockRepository.deleteById(id);
    }

    public void updateStock(Stock stock) {
        stockRepository.save(stock);
    }

    public Stock getStockById(long id) {
        return stockRepository.findById(id).orElse(null);
    }

    @Transactional
    public void buyStock(Long stockId, int quantity) {
        User user = userRepository.findCurrentUser();
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Hisse bulunamadı"));

        double totalCost = stock.getPrice() * quantity;
        if (user.getBalance() < totalCost) {
            throw new RuntimeException("Yeterli bakiye yok");
        }

        double newBalance = user.getBalance() - totalCost;
        user.setBalance(newBalance);
        userRepository.save(user);

        Optional<UserStock> userStockOptional = userStockRepository.findByUserAndStock(user, stock);

        if (userStockOptional.isPresent()) {
            UserStock userStock = userStockOptional.get();
            userStock.setQuantity(userStock.getQuantity() + quantity);
            userStockRepository.save(userStock);
        } else {
            UserStock userStock = new UserStock();
            userStock.setUser(user);
            userStock.setStock(stock);
            userStock.setQuantity(quantity);
            userStockRepository.save(userStock);
        }

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setStock(stock);
        transaction.setPrice(stock.getPrice()); // O siraki fiyat icin
        transaction.setQuantity(quantity);
        transaction.setPurchase(true);  //buy islemi oldugu icin true
        transaction.setTransactionDate(LocalDateTime.now()); // alim sirasindaki tarih icin now

        transactionRepository.save(transaction);
    }

    @Transactional
    public void sellStock(long stockId, int quantity) {
        User user = userRepository.findCurrentUser();
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Satmak istediginiz hisse bulunamadi"));
        double currentBalance = user.getBalance();
        double transactionCost = stock.getPrice() * quantity;

        Optional<UserStock> userStockOptional = userStockRepository.findByUserAndStock(user, stock);

        if (quantity > userStockOptional.get().getQuantity()) {
            throw new RuntimeException("Yeterli hisse yok");
        }

        if (userStockOptional.isPresent()) {
            UserStock userStock = userStockOptional.get();
            userStock.setQuantity(userStock.getQuantity() - quantity);
            userStockRepository.save(userStock);
            user.setBalance(transactionCost + currentBalance);
        }
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setStock(stock);
        transaction.setPrice(stock.getPrice());
        transaction.setQuantity(quantity);
        transaction.setPurchase(false);
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

    }

    public List<Transaction> getTransactions(){
        return transactionRepository.findAll();
    }

    public List<Transaction> getUserTransaction(){
        User currentUser = userRepository.findCurrentUser();
        return transactionRepository.findByUser(currentUser);
    }



}