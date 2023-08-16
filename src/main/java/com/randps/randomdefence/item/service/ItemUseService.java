package com.randps.randomdefence.item.service;

import com.randps.randomdefence.item.domain.Item;
import com.randps.randomdefence.item.domain.ItemRepository;
import com.randps.randomdefence.item.domain.UserItem;
import com.randps.randomdefence.item.domain.UserItemRepository;
import com.randps.randomdefence.user.domain.User;
import com.randps.randomdefence.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

public abstract class ItemUseService {

    protected final UserRepository userRepository;

    protected final ItemRepository itemRepository;

    protected final UserItemRepository userItemRepository;

    protected ItemUseService(UserRepository userRepository, ItemRepository itemRepository, UserItemRepository userItemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.userItemRepository = userItemRepository;
    }

    /*
     * 유저가 아이템을 사용한다.
     */
    @Transactional
    public Boolean useItem(String bojHandle, Long itemId) {
        User user = userRepository.findByBojHandle(bojHandle).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));
        Optional<UserItem> userItem = userItemRepository.findByBojHandleAndItem(bojHandle, item);

        // 아이템이 없다면 사용에 실패한다.
        if (!userItem.isPresent()) {
            return false;
        }

        // 아이템이 있다면 사용한다.
        itemEffect(bojHandle, itemId);

        // 아이템의 개수가 1개라면 사용하고 삭제한다.
        if (userItem.get().getCount() == 1) {
            userItemRepository.delete(userItem.get());
            return true;
        }
        // 아이템의 개수가 1개 이상이라면 하나 사용한다.
        userItem.get().decreaseCount();

        // 유저 아이템의 상태를 저장한다.
        userItemRepository.save(userItem.get());

        return true;
    }

    /*
     * 아이템의 효과를 실행시킨다.
     */
    @Transactional
    abstract public Object itemEffect(String bojHandle, Long itemId);
}
