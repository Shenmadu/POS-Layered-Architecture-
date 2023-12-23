package entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class OrderDetails {

    @EmbeddedId

    private OrderDetailsKey id;


    @ManyToOne
    @MapsId("itemCode")
    @JoinColumn(name = "item_code")
    Item item;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    Orders orders;

    private int qty;
    private double unitPrice;
}