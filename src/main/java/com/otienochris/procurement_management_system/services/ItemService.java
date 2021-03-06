package com.otienochris.procurement_management_system.services;

import com.otienochris.procurement_management_system.models.Item;
import com.otienochris.procurement_management_system.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepo;

//    returns a list of all available items from the database
    public List<Item> getAllItems(){

        return itemRepo.findAll();
    }

    public Optional<Item> getItemById(Long id){
        return itemRepo.findById(id);
    }

    public Optional<Item> getItemByBatchNumber(String batchNumber){
        return itemRepo.findByBatchSerialNumber(batchNumber);
    }

    public Item addItem(Item item){
        if (itemRepo.findByBatchSerialNumber(item.getBatchSerialNumber()).isPresent()){
            return itemRepo.findByBatchSerialNumber(item.getBatchSerialNumber()).get(); // does not add an item with an item already containing an existing batch numer
        }
        return itemRepo.save(item);
    }

    /*
     * deletes an item and returns the remaining list of items
     * */
    public List<Item> deleteItem(Item item){
        if (getItemById(item.getItemId()).isPresent()){
            itemRepo.delete(item);  // delete an item only when it exists
        }
        return itemRepo.findAll(); // return the list of the remaining items
    }

    public Item updateItem(Item newItem){
        if (itemRepo.findById(newItem.getItemId()).isPresent()){ // update if exists
            Item item = itemRepo.findById(newItem.getItemId()).get();
            item.setQuantity(newItem.getQuantity());
            item.setExpiry(newItem.getExpiry());
            item.setDescription(newItem.getDescription());
            item.setBatchSerialNumber(newItem.getBatchSerialNumber());
            item.setDate(newItem.getDate());

            itemRepo.save(item);
            return itemRepo.findById(newItem.getItemId()).get(); // we are using get cause we know it exists
        }

        return newItem;
    }

}
