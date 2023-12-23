package dao.custom.impl;


import dao.util.CrudUtil;
import db.DBConnection;
import dto.CustomerDto;
import dao.custom.CustomerDao;
import entity.Customer;

import javax.security.auth.login.Configuration;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class CustomerDaoImpl implements CustomerDao{
    @Override
    public CustomerDto searchCustomer(String id) {
        return null;
    }

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {

//        Configuration configuration = new Configuration()
//                .configure("hibernate.cfg.xml")
//                .addAnnotatedClass(Customer.class);
//
//        SessionFactory sessionFactory = configuration.buildSessionFactory();
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        session.save(entity);
//        transaction.commit();
//        session.close();
//        return true;

        String sql = "INSERT INTO customer VALUES(?,?,?,?)";
        return CrudUtil.execute(sql,entity.getId(),entity.getName(),entity.getAddress(),entity.getSalary());
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE customer SET name=?, address=?, salary=? WHERE id=?";
//        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
//        pstm.setString(1,entity.getName());
//        pstm.setString(2,entity.getAddress());
//        pstm.setDouble(3,entity.getSalary());
//        pstm.setString(4,entity.getId());

        return CrudUtil.execute(sql,entity.getName(),entity.getAddress(),entity.getSalary(),entity.getId());


    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        String sql = "DELETE from customer WHERE id=?";
//        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
//        pstm.setString(1,value);
//        return pstm.executeUpdate()>0;
        return CrudUtil.execute(sql,value);
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        List<Customer> list=new ArrayList<>();

        String sql = "SELECT * FROM customer";
//        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
//        ResultSet result = pstm.executeQuery();
        ResultSet result=CrudUtil.execute(sql);

        while (result.next()) {
            list.add(new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4)
            ));
        }
        return list;
    }
}
