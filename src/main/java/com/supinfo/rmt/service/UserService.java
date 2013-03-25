package com.supinfo.rmt.service;

import com.supinfo.rmt.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Ahmad
 * @version $Id$
 */
@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager em;

    public User authenticate(final String username, final String password) {
        final String passwordHashed = DigestUtils.sha1Hex(password);

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = query.from(User.class);
        query.where(
                criteriaBuilder.equal(userRoot.get("username"), username),
                criteriaBuilder.equal(userRoot.get("password"), passwordHashed)
        );

        return em.createQuery(query).getSingleResult();
    }

    public User getById(final Long id) {
        return em.find(User.class, id);
    }

}