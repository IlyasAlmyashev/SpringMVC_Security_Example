package jm.security.example.dao;

import jm.security.example.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void updateRole(Role role) {
        entityManager.merge(role);
    }

    @Override
    public void removeRoleById(long id) {
        entityManager.remove(entityManager.find(Role.class, id));
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public Role getRoleByName(String rolename) {
        return entityManager.createQuery("select r from Role r where r.rolename=:rolename",
                Role.class).setParameter("rolename", rolename).getSingleResult();
    }
}