/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.althaia.buscadorbd.controladores;

import es.althaia.buscadorbd.clases.Clientes;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import es.althaia.buscadorbd.clases.Expedientes;
import es.althaia.buscadorbd.controladores.exceptions.NonexistentEntityException;
import es.althaia.buscadorbd.controladores.exceptions.PreexistingEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Ainara
 */
public class ClientesJpaController implements Serializable {

    public ClientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clientes clientes) throws PreexistingEntityException, Exception {
        if (clientes.getExpedientesCollection() == null) {
            clientes.setExpedientesCollection(new ArrayList<>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Expedientes> attachedExpedientesCollection = new ArrayList<Expedientes>();
            for (Expedientes expedientesCollectionExpedientesToAttach : clientes.getExpedientesCollection()) {
                expedientesCollectionExpedientesToAttach = em.getReference(expedientesCollectionExpedientesToAttach.getClass(), expedientesCollectionExpedientesToAttach.getNExpediente());
                attachedExpedientesCollection.add(expedientesCollectionExpedientesToAttach);
            }
            clientes.setExpedientesCollection(attachedExpedientesCollection);
            em.persist(clientes);
            for (Expedientes expedientesCollectionExpedientes : clientes.getExpedientesCollection()) {
                Clientes oldIdClienteOfExpedientesCollectionExpedientes = expedientesCollectionExpedientes.getIdCliente();
                expedientesCollectionExpedientes.setIdCliente(clientes);
                expedientesCollectionExpedientes = em.merge(expedientesCollectionExpedientes);
                if (oldIdClienteOfExpedientesCollectionExpedientes != null) {
                    oldIdClienteOfExpedientesCollectionExpedientes.getExpedientesCollection().remove(expedientesCollectionExpedientes);
                    oldIdClienteOfExpedientesCollectionExpedientes = em.merge(oldIdClienteOfExpedientesCollectionExpedientes);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClientes(clientes.getIdCliente()) != null) {
                throw new PreexistingEntityException("Clientes " + clientes + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clientes clientes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes persistentClientes = em.find(Clientes.class, clientes.getIdCliente());
            Collection<Expedientes> expedientesCollectionOld = persistentClientes.getExpedientesCollection();
            Collection<Expedientes> expedientesCollectionNew = clientes.getExpedientesCollection();
            Collection<Expedientes> attachedExpedientesCollectionNew = new ArrayList<Expedientes>();
            for (Expedientes expedientesCollectionNewExpedientesToAttach : expedientesCollectionNew) {
                expedientesCollectionNewExpedientesToAttach = em.getReference(expedientesCollectionNewExpedientesToAttach.getClass(), expedientesCollectionNewExpedientesToAttach.getNExpediente());
                attachedExpedientesCollectionNew.add(expedientesCollectionNewExpedientesToAttach);
            }
            expedientesCollectionNew = attachedExpedientesCollectionNew;
            clientes.setExpedientesCollection(expedientesCollectionNew);
            clientes = em.merge(clientes);
            for (Expedientes expedientesCollectionOldExpedientes : expedientesCollectionOld) {
                if (!expedientesCollectionNew.contains(expedientesCollectionOldExpedientes)) {
                    expedientesCollectionOldExpedientes.setIdCliente(null);
                    expedientesCollectionOldExpedientes = em.merge(expedientesCollectionOldExpedientes);
                }
            }
            for (Expedientes expedientesCollectionNewExpedientes : expedientesCollectionNew) {
                if (!expedientesCollectionOld.contains(expedientesCollectionNewExpedientes)) {
                    Clientes oldIdClienteOfExpedientesCollectionNewExpedientes = expedientesCollectionNewExpedientes.getIdCliente();
                    expedientesCollectionNewExpedientes.setIdCliente(clientes);
                    expedientesCollectionNewExpedientes = em.merge(expedientesCollectionNewExpedientes);
                    if (oldIdClienteOfExpedientesCollectionNewExpedientes != null && !oldIdClienteOfExpedientesCollectionNewExpedientes.equals(clientes)) {
                        oldIdClienteOfExpedientesCollectionNewExpedientes.getExpedientesCollection().remove(expedientesCollectionNewExpedientes);
                        oldIdClienteOfExpedientesCollectionNewExpedientes = em.merge(oldIdClienteOfExpedientesCollectionNewExpedientes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = clientes.getIdCliente();
                if (findClientes(id) == null) {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getIdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            Collection<Expedientes> expedientesCollection = clientes.getExpedientesCollection();
            for (Expedientes expedientesCollectionExpedientes : expedientesCollection) {
                expedientesCollectionExpedientes.setIdCliente(null);
                expedientesCollectionExpedientes = em.merge(expedientesCollectionExpedientes);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Clientes findClientes(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
