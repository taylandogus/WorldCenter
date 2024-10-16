package bulut.worldcenter.repository;

import bulut.worldcenter.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    public Stock findByName(String name);

    public Stock findBySymbol(String symbol);



}
