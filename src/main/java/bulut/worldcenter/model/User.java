package bulut.worldcenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;

    String lastname;

    String email;

    String idnumber;

    String username;

    String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserStock> userStocks = new HashSet<>();

    double balance;

    public Set<Stock> getStocks() {
        Set<Stock> stocks = new HashSet<>();
        for (UserStock userStock : userStocks) {
            stocks.add(userStock.getStock());
        }
        return stocks;
    }

    public User(String name, String lastname, String email, String idnumber, String username, String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.idnumber = idnumber;
        this.username = username;
        this.password = password;
    }



}
