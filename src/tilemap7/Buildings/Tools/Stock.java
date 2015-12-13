/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings.Tools;

import Tools.Sprite;
import Tools.SpriteStore;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import tilemap7.CarryObjects.CarryObject;
import tilemap7.Entity;

/**
 *
 * @author Steve
 */
public class Stock {

    private Point position;
    private CarryObject[] stock;
    private int reportedPickUps=0;
    private int reportedDropDowns=0;
    private int size;
    private int rows;
    
    Sprite sprite;

    /**
     * Creates a new stock with maximum size at the specified position
     *
     * @param position
     * @param maximumSize
     */
    public Stock(Point position, int maximumSize) {
        this.position = position;
        stock = new CarryObject[maximumSize];
        rows = 1;
        sprite = SpriteStore.get().getSprite("stock.png");
    }
    
    /**
     * Creates a new stock with maximum size at the specified position and specified number of rows for deposited goods
     *
     * @param position
     * @param maximumSize
     */
    public Stock(Point position, int maximumSize, int rows){
        this.position = position;
        stock = new CarryObject[maximumSize];
        this.rows = rows;
        sprite = SpriteStore.get().getSprite("stock.png");
    }
    
    
    public Stock(int maximumSize){
        sprite = SpriteStore.get().getSprite("stock.png");
        stock = new CarryObject[maximumSize];
    }

    /**
     * Draws this stock at the specified position
     *
     * @param g
     * @param x xPosition on screen of the stock
     * @param y yPosition on screen of the stock
     */
    public synchronized void draw(Graphics g, int x, int y) {
        int i = 0;
        int row = 0;

        for (CarryObject c : stock) {
            if (c != null) {
                if(row == rows){
                    row = 0;
                    i += 2;
                }
                c.draw(g, x + row*8 + i, y + i * 2);
                row++;  
            }
        }
    }

    /**
     * Adds a Carryobject to the stock
     *
     * @param c
     * @return true if stack is not full
     */
    public synchronized boolean add(CarryObject c) {
        reportedDropDowns--;
        if (size < stock.length) {
            stock[size] = c;
            size++;
            return true;
        }
        return false;
    }

    /**
     * Removes the first occurence of this object in the stock
     *
     * @param c the object to be removed
     * @return null if not in the list
     */
    public synchronized CarryObject remove(CarryObject carryObject) {
        return remove(carryObject.getType());
    }
    
    /**
     * Takes the first object out of the stack
     * @return 
     */
    public synchronized CarryObject remove(){
        CarryObject returnObject = stock[0];
        for(int i = 1; i < stock.length; i++){
            stock[i]=stock[i-1];
        }
        return returnObject;
    } 

    /**
     * Returns the first occurence of an carry Object of this type
     *
     * @param type type of the carryObject
     * @return null if not in list
     */
    public synchronized CarryObject remove(String type) {
        CarryObject returnObject = null;
        for (int i = 0; i < stock.length;i++) {
            if (returnObject == null && stock[i] != null && stock[i].getClass().getSimpleName().equals(type)) {
                reportedPickUps--;
                size--;
                returnObject = stock[i];
            }
            if(returnObject != null){
                if(i < stock.length-1){
                    stock[i] = stock[i+1];
                }else{
                    stock[i] = null;
                }
            }
        }
        return returnObject;
    }

    
    /**
     * Returns a carryObject of this type if in stock, null if not.
     * Does not delete the carryObject from the stock.
     * @param type
     * @return 
     */
    public synchronized CarryObject get(String type){
        for (int i = 0; i < stock.length;i++) {
            if(stock[i] != null && stock[i].getType().equals(type)){
                return stock[i];
            }
        }
        return null;
    }
    
    
    /**
     * Returns a carryObject of this type if in stock, null if not.
     * Does not delete the carryObject from the stock.
     * @param type
     * @return 
     */
    public synchronized CarryObject get(int position){
        if(position >= 0 && position < stock.length){
            return stock[position];
        }
        return null;
    }
    
    /**
     * Returns true if an element of this type is stored in stock
     * @param type 
     */
    public synchronized boolean hasElement(String type){
        for(CarryObject c: stock){
            if(c != null && c.getType().equals(type)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns how many elements of this type are in the stock
     * @param type
     * @return 
     */
    public int howMany(String type){
        int i = 0;
        for(CarryObject c : stock){
            if(c != null && c.getType().equals(type)){
                i++;
            }
        }
        return i;
    }
    
    
    /**
     * Returns a set off all Types in this stock.
     * @return 
     */
    public HashSet<String> getTypes(){
        HashSet<String> set = new HashSet();
        for(CarryObject c : stock){
            set.add(c.getType());
        }
        return set;
        
    }
    
    

    
    
    /**
     * Should be called before a new LittleMan is send to pickUp sth from stock
     */
    public void reportPickUp() {
        reportedPickUps++;
    }
    
    
    /**
     * Must be called before a littleMan drops sth
     */
    public void reportDropDown(){
        reportedDropDowns++;
    }

    /**
     * Returns the position of this stock
     *
     * @return
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Retuns the number of elemts in this stock
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the maximum possible amount of elements in this stock
     * @return 
     */
    public int getLimit(){
        return stock.length;
    }
    /**
     * Gets the expected amount of elemts in the outStack, calculating reportet
     * pickups
     *
     * @return
     */
    public int getExpectedStock() {  
        return (getSize()-reportedPickUps+reportedDropDowns);
    }
    
    /**
     * tells if the stock is full
     * @return 
     */
    public boolean isFull(){
        return stock.length==size;
    }


}
