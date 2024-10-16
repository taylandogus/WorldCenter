package bulut.worldcenter.repository;

import bulut.worldcenter.model.Transaction;
import bulut.worldcenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    public Transaction save(Transaction transaction);

    List<Transaction> findByUser(User user);

}
