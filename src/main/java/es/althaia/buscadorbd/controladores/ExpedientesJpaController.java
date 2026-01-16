/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.althaia.buscadorbd.controladores;

import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import es.althaia.buscadorbd.clases.Clientes;
import es.althaia.buscadorbd.clases.Expedientes;
import es.althaia.buscadorbd.controladores.exceptions.NonexistentEntityException;
import es.althaia.buscadorbd.controladores.exceptions.PreexistingEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author Ainara
 */
public class ExpedientesJpaController implements Serializable {

    public ExpedientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Expedientes expedientes) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes idCliente = expedientes.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getIdCliente());
                expedientes.setIdCliente(idCliente);
            }
            em.persist(expedientes);
            if (idCliente != null) {
                idCliente.getExpedientesCollection().add(expedientes);
                idCliente = em.merge(idCliente);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findExpedientes(expedientes.getNExpediente()) != null) {
                throw new PreexistingEntityException("Expedientes " + expedientes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Expedientes expedientes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Expedientes persistentExpedientes = em.find(Expedientes.class, expedientes.getNExpediente());
            Clientes idClienteOld = persistentExpedientes.getIdCliente();
            Clientes idClienteNew = expedientes.getIdCliente();
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getIdCliente());
                expedientes.setIdCliente(idClienteNew);
            }
            expedientes = em.merge(expedientes);
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getExpedientesCollection().remove(expedientes);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getExpedientesCollection().add(expedientes);
                idClienteNew = em.merge(idClienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = expedientes.getNExpediente();
                if (findExpedientes(id) == null) {
                    throw new NonexistentEntityException("The expedientes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Expedientes expedientes;
            try {
                expedientes = em.getReference(Expedientes.class, id);
                expedientes.getNExpediente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The expedientes with id " + id + " no longer exists.", enfe);
            }
            Clientes idCliente = expedientes.getIdCliente();
            if (idCliente != null) {
                idCliente.getExpedientesCollection().remove(expedientes);
                idCliente = em.merge(idCliente);
            }
            em.remove(expedientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Expedientes> findExpedientesEntities() {
        return findExpedientesEntities(true, -1, -1);
    }

    public List<Expedientes> findExpedientesEntities(int maxResults, int firstResult) {
        return findExpedientesEntities(false, maxResults, firstResult);
    }

    private List<Expedientes> findExpedientesEntities(boolean all, int maxResults, int firstResult) {
        try (EntityManager em = getEntityManager()) {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Expedientes.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        }
    }

    public Expedientes findExpedientes(String id) {
        try (EntityManager em = getEntityManager()) {
            return em.find(Expedientes.class, id);
        }
    }

    public int getExpedientesCount() {
        try (EntityManager em = getEntityManager()) {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Expedientes> rt = cq.from(Expedientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
    }
    
}
