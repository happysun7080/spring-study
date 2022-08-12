package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ExamMergeMain {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {

        // 1
        Member member = createMember("memberA", "화원1", 20);

        // 2 (준영속 상태에서 변경)
        member.setUsername("회원명변경");

        // 3
        mergeMember(member);
    }

    /**
     *  영속성 컨텍스트1
     */
    static Member createMember(String id, String username, int age) {

        // ===영속성 컨텍스트1 시작===
        EntityManager em1 = emf.createEntityManager();
        EntityTransaction tx1 = em1.getTransaction();
        tx1.begin();

        Member member = new Member();
        member.setId(id);
        member.setUsername(username);
        member.setAge(age);

        em1.persist(member);
        tx1.commit();

        em1.close();
        // ===영속성 컨텍스트1 종료===
        // member 엔티티는 준영속 상태가 된다

        return member;  // 준영속 상태의 member 엔티티 반환
    }

    /**
     *  영속성 컨텍스트2
     */
    static void mergeMember(Member member) {

        // ===영속성 컨텍스트2 시작===
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();


        tx2.begin();
        Member mergeMember = em2.merge(member);
        tx2.commit();
        
        // 준영속 상태
        System.out.println("member.getUsername() = " + member.getUsername());
        
        // 영속 상태
        System.out.println("mergeMember.getUsername() = " + mergeMember.getUsername());

        System.out.println("em2.contains(member) = " + em2.contains(member));
        System.out.println("em2.contains(mergeMember) = " + em2.contains(mergeMember));

        em2.close();
        // ===영속성 컨텍스트2 종료===
    }
}
