package bulut.worldcenter.repository;

import bulut.worldcenter.model.Stock;
import bulut.worldcenter.model.User;
import bulut.worldcenter.model.UserStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserStockRepository extends JpaRepository<UserStock, Long> {

    Optional<UserStock> findByUserAndStock(User user, Stock stock);

    List<UserStock> findByUser(User user);
}
