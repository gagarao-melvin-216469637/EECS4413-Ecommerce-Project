package org.o7planning.krispykart.dao;

import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.o7planning.krispykart.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class AccountDAO {

    @Autowired //auto generated constructor, getter and setter methods.
    private SessionFactory sessionFactory;
    
    //find the account based on the username
    public Account findAccount(String username) {
        Session session = this.sessionFactory.getCurrentSession(); // get the current session scope.
        return session.find(Account.class, username); //find the entity instance of the account entity with the username specified
    }

}