package jpabook.start;

import javax.persistence.*;
import java.util.List;

/**
 * @author holyeye
 */
public class JpaMain {

    public static void main(String[] args) {

        //[엔티티 매니저 팩토리] 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        //[엔티티 매니저] 생성
        EntityManager em = emf.createEntityManager();
        //[트랜잭션] 기능 획득
        EntityTransaction tx = em.getTransaction();

        try {

            tx.begin(); //[트랜잭션] 시작
            logic(em);  //비즈니스 로직 실행
            testDetached(em, tx);
            closeEntityManager(emf);
            tx.commit();//[트랜잭션] 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //[트랜잭션] 롤백
        } finally {
            em.close(); //[엔티티 매니저] 종료
        }

        emf.close(); //[엔티티 매니저 팩토리] 종료
    }

    /**
     * 비즈니스 로직
     */
    public static void logic(EntityManager em) {

        String id = "id1";

        // 객체를 생성한 상태 (비영속)
        Member member = new Member();
        member.setId(id);
        member.setUsername("지한");
        member.setAge(2);

        //등록
        em.persist(member);  // INSERT INTO MEMBER (ID, NAME, AGE) VALUES ('id1', '지한', 2)

        //수정
        member.setAge(20);  // UPDATE MEMBER SET AGE=20, NAME='지한' WHERE ID='id1'

        //한 건 조회
        Member findMember = em.find(Member.class, id);  // SELECT * FROM MEMBER WHERE ID='id1'
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        //목록 조회 (JPQL)
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();  // SELECT M.ID, M.NAME, M.AGE FROM MEMBER M
        System.out.println("members.size=" + members.size());

        //삭제
        em.remove(member);  // DELETE FROM MEMBER WHERE ID='id1'
    }

    /**
     *  [예제 3.8] 준영속 상태 테스트
     */
    public static void testDetached(EntityManager em, EntityTransaction tx) {

        // 회원 엔티티 생성, 비영속 상태
        Member member = new Member();
        member.setId("memberA");
        member.setUsername("회원A");
        member.setAge(2);

        // 회원 엔티티 영속 상태
        em.persist(member);

        // 회원 엔티티를 영속성 컨텍스트에서 분리, 준영속 상태
        em.detach(member);

        // 트랜잭션 커밋
        tx.commit();
    }

    /**
     * [예제 3.10] 영속성 컨텍스트 닫기
     */
    public static void closeEntityManager(EntityManagerFactory emf) {

        Member newMemberA = new Member();
        newMemberA.setId("memberA");
        newMemberA.setUsername("회원A");
        newMemberA.setAge(10);

        Member newMemberB = new Member();
        newMemberA.setId("memberB");
        newMemberA.setUsername("회원B");
        newMemberA.setAge(20);


        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        // [트랜잭션] 시작
        tx.begin();

        //
        em.persist(newMemberA);
        em.persist(newMemberB);

        Member memberA = em.find(Member.class, "memberA");
        Member memberB = em.find(Member.class, "memberB");

        // [트랜잭션] 커밋
        tx.commit();

        // 영속성 컨텍스트 닫기 (종료)
        em.close();
    }
}
