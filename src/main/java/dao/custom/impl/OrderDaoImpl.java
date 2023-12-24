package dao.custom.impl;

import dao.util.HibernateUtil;
import db.DBConnection;
import dto.OrderDetailsDto;
import dto.OrderDto;
import dao.custom.OrderDetailsDao;
import dao.custom.OrderDao;
import entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    OrderDetailsDao orderDetailsDao =new OrderDetailsDaoImpl();
    @Override
    public boolean save(OrderDto dto) throws SQLException {
//        Connection connection=null;
//        try {
//            connection = DBConnection.getInstance().getConnection();
//            connection.setAutoCommit(false);
//
//            String sql = "INSERT INTO orders VALUES(?,?,?)";
//            PreparedStatement pstm = connection.prepareStatement(sql);
//            pstm.setString(1, dto.getOrderId());
//            pstm.setString(2, dto.getDate());
//            pstm.setString(3, dto.getCustId());
//            if (pstm.executeUpdate() > 0) {
//                boolean isDetailSaved = orderDetailsDao.saveOrderDetails(dto.getList());
//                if (isDetailSaved) {
//                    connection.commit();
//                    return true;
//                }
//            }
//        }catch (SQLException|ClassNotFoundException ex){
//            connection.rollback();
//        }finally {
//            connection.setAutoCommit(true);
//        }
//       return false;
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Orders order = new Orders(
                dto.getOrderId(),
                dto.getDate()
        );
        order.setCustomer(session.find(Customer.class,dto.getCustId()));
        session.save(order);

        List<OrderDetailsDto> list = dto.getList(); //dto type

        for (OrderDetailsDto detailDto:list) {
            OrderDetail orderDetail = new OrderDetail(
                    new OrderDetailsKey(detailDto.getOrderId(), detailDto.getItemCode()),
                    session.find(Item.class, detailDto.getItemCode()),
                    order,
                    detailDto.getQty(),
                    detailDto.getUnitPrice()
            );
            session.save(orderDetail);
        }

        transaction.commit();
        session.close();
        return true;

    }

    @Override
    public boolean update(OrderDto entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<OrderDto> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public OrderDto lastOrder() throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM orders ORDER BY id DESC LIMIT 1";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            return new OrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    null
            );
        }
        return null;
    }

    @Override
    public List<OrderDto> allOrders() throws SQLException, ClassNotFoundException {
        List<OrderDto> list=new ArrayList<>();
        String sql="SELECT * FROM orders";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = pstm.executeQuery();

        while (result.next()){
            list.add(new OrderDto(
                result.getString(1),
                result.getString(2),
                result.getString(3),
                null
            ));
        }
        return list;
    }


}
