package hello.itemservice.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Item {

    @Id  // 테이블의 PK와 해당 필드를 매핑한다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // PK 생성 값을 DB에서 생성하는 'IDENTITY' 방식을 사용한다.
    private Long id;

    @Column(name = "item_name", length = 10)  // 객체의 필드를 테이블의 컬럼과 매핑한다.
    private String itemName;
    private Integer price;
    private Integer quantity;

    /**
     * JPA는 public 또는 protected의 기본 생성자가 필수이다.
     */
    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
