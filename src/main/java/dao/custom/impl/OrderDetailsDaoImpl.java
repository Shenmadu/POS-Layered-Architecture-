package dao.custom.impl;

import dao.util.HibernateUtil;
import db.DBConnection;
import dto.OrderDetailsDto;
import dao.custom.OrderDetailsDao;
import entity.Item;
import entity.OrderDetail;
import entity.OrderDetailsKey;
import entity.Orders;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {
    @Override
    public boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException {
       boolean isDetailsSaved=true;
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        for (OrderDetailsDto dto : list) {
            Orders order = session.get(Orders.class, dto.getOrderId());
            Item item = session.get(Item.class, dto.getItemCode());

            OrderDetail orderDetail = new OrderDetail(
                    new OrderDetailsKey(dto.getOrderId(), dto.getItemCode()),
                    item,
                    order,
                    dto.getQty(),
                    dto.getUnitPrice()
            );

            session.save(orderDetail);
        }

        transaction.commit();

    return isDetailsSaved;
    }

    @Override
    public List<OrderDetailsDto> getOrders(String id) throws SQLException, ClassNotFoundException {
        List<OrderDetailsDto> list=new ArrayList<>();
        String sql="SELECT * FROM orderdetail WHERE orderId =?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);


        pstm.setString(1, id);
        ResultSet result = pstm.executeQuery();

        while (result.next()){
            list.add(new OrderDetailsDto(
                    result.getString(1),
                    result.getString(2),
                    result.getInt(3),
                    result.getDouble(4)
            ));
        }
        return list;
    }

}
