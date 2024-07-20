import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProdutoPU");
        EntityManager em = emf.createEntityManager();

        // Exemplo de persistência de um produto
        em.getTransaction().begin();

        Produto produto = new Produto("Produto Teste", 19.99, 10);
        em.persist(produto);

        em.getTransaction().commit();

        // Fechar o EntityManager e o EntityManagerFactory ao finalizar
        em.close();
        emf.close();
    }
}
