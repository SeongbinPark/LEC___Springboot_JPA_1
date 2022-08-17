package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)//일단 true로 깔아둠. 값 변경로직 시 false로 각각 설정
@RequiredArgsConstructor//final 붙은 필드로만 생성자를 만들어준다.
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional//readOnly false로 하기 위해 ( 어노테이션 가까운게 우선권 (오버라이드))
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);

        //return findItem; 리턴은 생략하자.

        //itemRepository.save(findItem); 필요 없다.

        //findItem은 영속상태이다.
        // -> 메서드가 끝나면 스프링의 @Transactional 에 의해서 열렸던 트랜젝션이 commit이 된다.
        // -> commit이 되면 JPA는 flush를 한다.(flush : 영속성컨텍스트중에서 변경된 애들을 찾는다. )
        // -> 찾으면 바뀐 부분을 UPDATE 쿼리를 DB에 날려서 업데이트한다.

    }


    //여긴 readOnly true 가 먹힌다.
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    //여긴 readOnly true 가 먹힌다.
    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }
}
