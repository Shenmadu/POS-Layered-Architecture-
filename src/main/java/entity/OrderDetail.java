package entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class OrderDetail {

    @EmbeddedId
    private OrderDetailsKey id;


    @ManyToOne
    @MapsId("itemCode")
    @JoinColumn(name = "itemCode")
    Item item;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "orderId")
    Orders orders;

    private int qty;
    private double unitPrice;
}