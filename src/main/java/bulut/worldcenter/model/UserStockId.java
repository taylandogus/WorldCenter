package bulut.worldcenter.model;

import java.io.Serializable;
import java.util.Objects;

public class UserStockId implements Serializable {
    private Long user;
    private Long stock;

    public UserStockId() {}

    public UserStockId(Long user, Long stock) {
        this.user = user;
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserStockId)) return false;
        UserStockId that = (UserStockId) o;
        return Objects.equals(user, that.user) && Objects.equals(stock, that.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, stock);
    }
}
