package org.o7planning.krispykart.dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.o7planning.krispykart.entity.Product;
import org.o7planning.krispykart.entity.Registration;
import org.o7planning.krispykart.form.RegistrationForm;
import org.o7planning.krispykart.model.ProductInfo;
import org.o7planning.krispykart.model.RegistrationInfo;
import org.o7planning.krispykart.pagination.PaginationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class RegistrationDAO {
	
	@Autowired
    private SessionFactory sessionFactory;

    public Registration findRegistration(String code) {
        try {
            String sql = "Select e from " + Registration.class.getName() + " e Where e.code =:code ";

            Session session = this.sessionFactory.getCurrentSession();
            Query<Registration> query = session.createQuery(sql, Registration.class);
            query.setParameter("code", code);
            return (Registration) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public RegistrationInfo registrationDetails(String code) {
        Registration reg = this.findRegistration(code);
        if (reg != null) {
            return new RegistrationInfo(reg.getUsername(), reg.getActive(), reg.getEncryptedPassword(), reg.getUser_role());

        }else {
        	return null;
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(RegistrationForm regForm) {

        Session session = this.sessionFactory.getCurrentSession();
        String code = regForm.getUsername();

        Registration registration = null;

        boolean isNew = false;
        if (code == null) {
        	System.out.println("the code is null");
        }
        else {
            registration = this.findRegistration(code);

        }
        if (registration == null) {
            isNew = true;
            registration = new Registration();
        }
        registration.setUsername(code);
        registration.setActive(regForm.getActive());
        registration.setEncryptedPassword(regForm.getEncryptedPassword());
        registration.setUser_role(regForm.getUser_role());
        
        if (regForm.getFileData() == null) {
            
           System.out.println("file data is null");
        }

        if (isNew) {
            session.persist(registration);
        }
        
        session.flush();
    }
    
    public PaginationResult<RegistrationInfo> queryRegistration(int page, int maxResult, int maxNavigationPage,
            String likeName) {
        String sql = "Select new " + ProductInfo.class.getName() //
                + "(p.code, p.name, p.price) " + " from "//
                + Product.class.getName() + " p ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(p.name) like :likeName ";
        }
        sql += " order by p.createDate desc ";
        // 
        Session session = this.sessionFactory.getCurrentSession();
        Query<RegistrationInfo> query = session.createQuery(sql, RegistrationInfo.class);

        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<RegistrationInfo>(query, page, maxResult, maxNavigationPage);
    }
    
    public PaginationResult<RegistrationInfo> queryRegistration(int page, int maxResult, int maxNavigationPage) {
        return queryRegistration(page, maxResult, maxNavigationPage, null);
    }
    
    
}
