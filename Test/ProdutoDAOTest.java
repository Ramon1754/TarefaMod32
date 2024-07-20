

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;


public class ProdutoDAOTest {

    private static EntityManagerFactory emf;

    @BeforeAll
    public static void setUp() {
        emf = Persistence.createEntityManagerFactory("ProdutoPU");
    }

    @AfterAll
    public static void tearDown() {
        emf.close();
    }

    @Test
    public void testPersistencia() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Produto produto = new Produto("Teclado mecânico", 299.99, 5);
        em.persist(produto);

        em.getTransaction().commit();
        em.close();

        assertNotNull(produto.getId());
        System.out.println("Produto persistido com sucesso: " + produto.getId());
    }

    @Test
    public void testRecuperacao() {
        EntityManager em = emf.createEntityManager();
        Produto produto = em.find(Produto.class, 1L); // Supondo que o produto com id 1 exista

        assertNotNull(produto);
        assertEquals("Produto Teste", produto.getNome());
        assertEquals(19.99, produto.getPreco(), 0.001); // Comparação com tolerância para double
        assertEquals(10, produto.getQuantidade());

        System.out.println("Produto recuperado do banco de dados:");
        System.out.println("ID: " + produto.getId());
        System.out.println("Nome: " + produto.getNome());
        System.out.println("Preço: " + produto.getPreco());
        System.out.println("Quantidade: " + produto.getQuantidade());

        em.close();
    }

    @Test
    public void testAtualizacao() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Produto produto = em.find(Produto.class, 1L); // Supondo que o produto com id 1 exista
        assertNotNull(produto);

        produto.setPreco(29.99);
        em.merge(produto);

        em.getTransaction().commit();
        em.close();

        EntityManager em2 = emf.createEntityManager();
        Produto produtoAtualizado = em2.find(Produto.class, 1L);

        assertEquals(29.99, produtoAtualizado.getPreco(), 0.001); // Comparação com tolerância para double

        em2.close();
    }

    @Test
    public void testRemocao() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Produto produto = em.find(Produto.class, 1L); // Supondo que o produto com id 1 exista
        assertNotNull(produto);

        em.remove(produto);

        em.getTransaction().commit();
        em.close();

        EntityManager em2 = emf.createEntityManager();
        Produto produtoRemovido = em2.find(Produto.class, 1L);

        assertNull(produtoRemovido);

        em2.close();
    }
}
