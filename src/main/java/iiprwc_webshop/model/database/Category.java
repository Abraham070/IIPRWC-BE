package iiprwc_webshop.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="categories")
public class Category {
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        private String id;

        @CreationTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "create_date")
        private Date createDate;

        @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
        @JsonIgnore
        private Set<Product> products = new HashSet<>();

        @Column
        private String name;

        public Category() { }

        public Category(String id) {
            this.id = id;
        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
